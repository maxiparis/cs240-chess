package services;

import DAO.AuthDAO;
import DAO.GameDAO;
import DAO.UserDAO;
import chess.ChessGame;
import chess.ChessGameImpl;
import dataAccess.DataAccessException;
import model.AuthToken;
import model.Game;
import model.User;
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
    UserDAO userDB;

    @BeforeEach
    void setUp() throws DataAccessException {
        service = new CreateGameService();
        request = new CreateGameRequest("MyGame");
        gameDB = GameDAO.getInstance();
        authDB = AuthDAO.getInstance();
        userDB = UserDAO.getInstance();
        gameDB.clear();
        authDB.clear();
        userDB.clear();

        userDB.insert(new User("testUser", "sdfsdf", "sdfsdf"));
        token = new AuthToken("testUser","testToken");
        authDB.insert(token);
    }

    @Test
    void createGame_Valid() throws DataAccessException {
        //valid
        CreateGameResponse response = service.createGame(request, token.getToken());

        Game gameDbShouldHave = new Game(0, null, null, "MyGame",
                new ChessGameImpl(ChessGame.TeamColor.WHITE));
        //validate what the db has
        Game foundGame = GameDAO.getInstance().find(gameDbShouldHave.getGameName());
        assertEquals(gameDbShouldHave.getGameName(), foundGame.getGameName());
        assertEquals(gameDbShouldHave.getWhiteUsername(), foundGame.getWhiteUsername());
        assertEquals(gameDbShouldHave.getBlackUsername(), foundGame.getBlackUsername());
        assertEquals(gameDbShouldHave.getGame(), foundGame.getGame());


        //validate the response
        assertNull(response.getMessage());
    }

    @Test
    void createGame_Invalid() throws DataAccessException {
        CreateGameResponse response = service.createGame(request, token.getToken());
        Game gameDbShouldHave = new Game(0, null, null, "MyGame",
                new ChessGameImpl(ChessGame.TeamColor.WHITE));

        //invalid -- game is already there
        CreateGameResponse invalidResponse = service.createGame(request, token.getToken());
        assertEquals(null, invalidResponse.getGameID(), "The GameID is not null. ");
        assertEquals("Error: bad request", invalidResponse.getMessage());

        //Invalid - unauthorized
        CreateGameResponse invalidResponse2 = service.createGame(request, "wrongToken");
        assertEquals(null, invalidResponse2.getGameID(), "The GameID is not null. ");
        assertSame("Error: unauthorized", invalidResponse2.getMessage());
    }
    
}