package services;

import DAO.AuthDAO;
import DAO.GameDAO;
import DAO.UserDAO;
import chess.ChessGame;
import chess.ChessGameImpl;
import dataAccess.DataAccessException;
import model.AuthToken;
import model.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import requests.CreateGameRequest;
import responses.CreateGameResponse;

import static org.junit.jupiter.api.Assertions.*;

class CreateGameServiceTest {
    CreateGameService service;
    CreateGameRequest request;
    GameDAO gameDB;
    AuthDAO authDB;
    AuthToken token;

    @BeforeEach
    void setUp() throws DataAccessException {
        service = new CreateGameService();
        request = new CreateGameRequest("MyGame");
        gameDB = GameDAO.getInstance();
        authDB = AuthDAO.getInstance();
        token = new AuthToken("testUser","testToken");

        authDB.insert(token);
    }

    @Test
    void createGame() throws DataAccessException {
        //valid
        CreateGameResponse response = service.createGame(request, token.getToken());
        Game gameDbShouldHave = new Game(0, null, null, "MyGame",
                new ChessGameImpl(ChessGame.TeamColor.WHITE));
        //validate what the db has
        assertNotNull(GameDAO.getInstance().findGameByName(gameDbShouldHave.getGameName()));
        //validate the response
        assertNull(response.getMessage());
        assertSame(1, response.getGameID());


        //invalid -- game is already there
         CreateGameResponse invalidResponse = service.createGame(request, token.getToken());
        assertEquals(0, invalidResponse.getGameID(), "The GameID is not null. ");
        assertSame("Error: there is another game with the same name", invalidResponse.getMessage());

        //Invalid - unauthorized
        CreateGameResponse invalidResponse2 = service.createGame(request, "wrongToken");
        assertEquals(0, invalidResponse2.getGameID(), "The GameID is not null. ");
        assertSame("Error: unauthorized", invalidResponse2.getMessage());

    }
    
}