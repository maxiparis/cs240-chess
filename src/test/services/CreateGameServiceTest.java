package services;

import DAO.GameDAO;
import DAO.UserDAO;
import chess.ChessGame;
import chess.ChessGameImpl;
import dataAccess.DataAccessException;
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

    @BeforeEach
    void setUp() {
        service = new CreateGameService();
        request = new CreateGameRequest("MyGame");
        gameDB = GameDAO.getInstance();
    }

    @Test
    void createGame() throws DataAccessException {
        //valid
        CreateGameResponse response = service.createGame(request);
        Game gameDbShouldHave = new Game(0, null, null, "MyGame",
                new ChessGameImpl(ChessGame.TeamColor.WHITE));
        //validate what the db has
        assertNotNull(GameDAO.getInstance().find(gameDbShouldHave));
        //validate the response
        //invalid -- game is already there

    }

    @Test
    void generateNewGameID() {
    }
}