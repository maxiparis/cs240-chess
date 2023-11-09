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
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import requests.CreateGameRequest;
import requests.JoinGameRequest;
import responses.CreateGameResponse;
import responses.JoinGameResponse;

import static org.junit.jupiter.api.Assertions.*;

class JoinGameServiceTest {
    private JoinGameService service;
    private AuthDAO authDB;
    private GameDAO gameDB;
    private UserDAO userDB;

    @BeforeEach
    void setUp() throws DataAccessException {
        service = new JoinGameService();
        authDB = AuthDAO.getInstance();
        gameDB = GameDAO.getInstance();
        userDB = UserDAO.getInstance();

        authDB.clear();
        userDB.clear();
        gameDB.clear();


        Game game1 = new Game(1,"white1", "black1", "game1",
                new ChessGameImpl(ChessGame.TeamColor.WHITE));
        Game game2 = new Game(2,"white2", "black2", "game2",
                new ChessGameImpl(ChessGame.TeamColor.BLACK));
        Game game3 = new Game(3,null, "black3", "game3",
                new ChessGameImpl(ChessGame.TeamColor.WHITE));
        Game game4 = new Game(4,"white4", null, "game4",
                new ChessGameImpl(ChessGame.TeamColor.BLACK));
        Game game5 = new Game(5,null, null, "game5",
                new ChessGameImpl(ChessGame.TeamColor.WHITE));

        gameDB.insert(game1);
        gameDB.insert(game2);
        gameDB.insert(game3);
        gameDB.insert(game4);
        gameDB.insert(game5);

        User user1 = new User("user1", "password1", "email1");
        User user2 = new User("user2", "password2", "email2");
        User user3 = new User("user3", "password3", "email3");
        User user4 = new User("user4", "password4", "email4");
        User user5 = new User("user5", "password5", "email5");

        userDB.insert(user1);
        userDB.insert(user2);
        userDB.insert(user3);
        userDB.insert(user4);
        userDB.insert(user5);


        AuthToken token1 = new AuthToken("user1", "token1");
        AuthToken token2 = new AuthToken("user2", "token2");
        AuthToken token3 = new AuthToken("user3", "token3");
        AuthToken token4 = new AuthToken("user4", "token4");
        AuthToken token5 = new AuthToken("user5", "token5");

        authDB.insert(token1);
        authDB.insert(token2);
        authDB.insert(token3);
        authDB.insert(token4);
        authDB.insert(token5);
    }

    @AfterEach
    void tearDown() throws DataAccessException {
        authDB.clear();
        gameDB.clear();
    }

    @Test
    void joinGame_Valid_PlayerWhite() throws DataAccessException {
        CreateGameRequest createGameRequest = new CreateGameRequest("game6");
        CreateGameService createGameService = new CreateGameService();
        CreateGameResponse createGameResponse = createGameService.createGame(createGameRequest, "token1");
        int wrongID = createGameResponse.getGameID() + 1;
        int correctID = createGameResponse.getGameID();
        JoinGameRequest request = new JoinGameRequest(ChessGame.TeamColor.WHITE, correctID);

        JoinGameResponse response = service.joinGame(request, "token3");

        Game gameUpdated = gameDB.findGameById(correctID);
        String expectedNewUserName = "user3";
        String actualNewUserName = gameUpdated.getWhiteUsername();


        assertEquals(expectedNewUserName, actualNewUserName);
        assertNull(response.getMessage());
    }

    @Test
    void joinGame_Valid_PlayerBlack() throws DataAccessException {
        CreateGameRequest createGameRequest = new CreateGameRequest("game6");
        CreateGameService createGameService = new CreateGameService();
        CreateGameResponse createGameResponse = createGameService.createGame(createGameRequest, "token1");
        int wrongID = createGameResponse.getGameID() + 1;
        int correctID = createGameResponse.getGameID();

        JoinGameRequest request = new JoinGameRequest(ChessGame.TeamColor.BLACK, correctID);

        JoinGameResponse response = service.joinGame(request, "token3");

        Game gameUpdated = gameDB.findGameById(correctID);
        String expectedNewUserName = "user3";
        String actualNewUserName = gameUpdated.getBlackUsername();


        assertEquals(expectedNewUserName, actualNewUserName);
        assertNull(response.getMessage());
    }

    @Test
    void joinGame_Valid_Spectator() throws DataAccessException {
        CreateGameRequest createGameRequest = new CreateGameRequest("game6");
        CreateGameService createGameService = new CreateGameService();
        CreateGameResponse createGameResponse = createGameService.createGame(createGameRequest, "token1");
        int wrongID = createGameResponse.getGameID() + 1;
        int correctID = createGameResponse.getGameID();

        JoinGameRequest request = new JoinGameRequest(null, correctID);

        JoinGameResponse response = service.joinGame(request, "token3");

        Game gameNotUpdated = gameDB.findGameById(correctID);

        assertEquals(null, gameNotUpdated.getBlackUsername());
        assertEquals(null, gameNotUpdated.getWhiteUsername());
        assertNull(response.getMessage());
    }
    @Test
    void joinGame_Invalid_EmptyDB() throws DataAccessException {
        gameDB.clear();
        JoinGameRequest request = new JoinGameRequest(ChessGame.TeamColor.BLACK, 4);

        JoinGameResponse response = service.joinGame(request, "token3");

//        Game gameUpdated = gameDB.findGameById(4);

        assertEquals("Error: bad request", response.getMessage(), "The Error message is different.");
    }

    @Test
    void joinGame_Invalid_AlreadyTaken() throws DataAccessException {
        CreateGameRequest createGameRequest = new CreateGameRequest("game6");
        CreateGameService createGameService = new CreateGameService();
        CreateGameResponse createGameResponse = createGameService.createGame(createGameRequest, "token1");
        int wrongID = createGameResponse.getGameID() + 1;
        int correctID = createGameResponse.getGameID();

        JoinGameRequest request = new JoinGameRequest(ChessGame.TeamColor.BLACK, correctID);
        JoinGameResponse response = service.joinGame(request, "token3");
        response = service.joinGame(request, "token3");


        assertEquals("Error: already taken", response.getMessage());
    }

    @Test
    void joinGame_Invalid_Unauthorized() throws DataAccessException {
        CreateGameRequest createGameRequest = new CreateGameRequest("game6");
        CreateGameService createGameService = new CreateGameService();
        CreateGameResponse createGameResponse = createGameService.createGame(createGameRequest, "token1");
        int wrongID = createGameResponse.getGameID() + 1;
        int correctID = createGameResponse.getGameID();

        JoinGameRequest request = new JoinGameRequest(ChessGame.TeamColor.BLACK, wrongID);

        JoinGameResponse response = service.joinGame(request, "INVALID_TOKEN");

        assertEquals("Error: unauthorized", response.getMessage());
    }

    @Test
    void joinGame_Invalid_GameWasNotFound() throws DataAccessException {
        CreateGameRequest createGameRequest = new CreateGameRequest("game6");
        CreateGameService createGameService = new CreateGameService();
        CreateGameResponse createGameResponse = createGameService.createGame(createGameRequest, "token1");
        int wrongID = createGameResponse.getGameID() + 1;

        JoinGameRequest request = new JoinGameRequest(ChessGame.TeamColor.BLACK, wrongID);

        JoinGameResponse response = service.joinGame(request, "token4");

        Game gameUpdated = gameDB.findGameById(createGameResponse.getGameID());

        assertEquals("Error: bad request", response.getMessage());
    }
}