package net;

import chess.ChessGame;
import model.Game;
import org.junit.jupiter.api.*;
import requests.CreateGameRequest;
import requests.JoinGameRequest;
import requests.LoginRequest;
import requests.RegisterRequest;
import responses.*;
import webSocketMessages.serverMessages.ServerMessage;

import static org.junit.jupiter.api.Assertions.*;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ServerFacadeTest {
    private ServerFacade serverFacade;
    private static String authToken;
    private static int gameID;

    @BeforeEach
    void setUp() {
        serverFacade = new ServerFacade(new ServerMessageObserver() {
            @Override
            public void notify(ServerMessage notification) {

            }
        });
        //before running this, run server and make sure
        // - there is no user called "test23" in the DB
        // - there is no game called TestGame23 in the DB
        // - there is no auth for user test23 in authD
        /*Run this in sql:
        *   delete from authToken;
            delete from user where username = 'test23';
            delete from game where gameName = 'TestGame23';
            delete from game where gameName = 'InvalidGame';
        * */
    }

    @Test
    @Order(1)
    void register() {
        //before running this, run server and make sure there is no user called "test23" in the DB
        RegisterRequest request = new RegisterRequest("test23", "test23", "test23");
        RegisterResponse response = serverFacade.register(request);

        authToken = response.getAuthToken();

        assertNull(response.getMessage());
        assertEquals("test23", response.getUsername());
    }

    @Test
    @Order(2)
    void registerInvalid() {
        //trying to register a person that is already there
        RegisterRequest request = new RegisterRequest("test23", "test23", "test23");
        RegisterResponse response = serverFacade.register(request);
        assertNotNull(response.getMessage());
        assertNull(response.getUsername());
        assertNull(response.getAuthToken());
    }

    @Test
    @Order(3)
    void login() {
        //before running this, run server and make sure there is no user called "test23" in the DB
        LoginRequest request = new LoginRequest("test23", "test23");
        LoginResponse response = serverFacade.login(request);
        assertEquals("test23", response.getUsername());
    }

    @Test
    @Order(4)
    void loginInvalid() {
        LoginRequest request = new LoginRequest("Invalid", "Invalid");
        LoginResponse response = serverFacade.login(request);
        assertNotNull(response.getMessage());
        assertNull(response.getUsername());
        assertNull(response.getAuthToken());
    }

    @Test
    @Order(5)
    void createGame() {
        CreateGameResponse response = serverFacade.createGame(new CreateGameRequest("TestGame23"), authToken);
        gameID = response.getGameID();
        assertNotNull(gameID);
        assertNull(response.getMessage());
    }

    @Test
    @Order(6)
    void createGameInvalid() {
        CreateGameResponse response = serverFacade.createGame(new CreateGameRequest("InvalidGame"), "badToken");
        assertNotNull(response.getMessage());
    }

    @Test
    @Order(7)
    void listGames() {
        ListGamesResponse response = serverFacade.listGames(authToken);
        boolean responseContainsGameCreated = false;
        for (Game game : response.getGames()) {
            if(game.getGameName().equals("TestGame23")){
                responseContainsGameCreated = true;
                break;
            }
        }
        assertTrue(responseContainsGameCreated);
        assertNotNull(response.getGames());
        assertNull(response.getMessage());
    }

    @Test
    @Order(8)
    void listGamesInvalid() {
        ListGamesResponse response = serverFacade.listGames("IncorrectToken");
        assertNull(response.getGames());
        assertNotNull(response.getMessage());
    }
    @Test
    @Order(9)
    void joinGame() {
        JoinGameResponse response = serverFacade.joinGame(new JoinGameRequest(ChessGame.TeamColor.WHITE, gameID), authToken);
        assertNull(response.getMessage());
    }

    @Test
    @Order(10)
    void joinGameInvalid() {
        JoinGameResponse response = serverFacade.joinGame
                (new JoinGameRequest(ChessGame.TeamColor.WHITE, gameID), "Incorrect");
        assertNotNull(response.getMessage());
    }

    @Test
    @Order(11)
    void logout() {
        LogoutResponse response = serverFacade.logout(authToken);
        assertNull(response.getMessage());
    }


    @Test
    @Order(12)
    void logoutInvalid() { //logging out after It has logged out already
        LogoutResponse response = serverFacade.logout(authToken);
        assertNotNull(response.getMessage());
    }
}