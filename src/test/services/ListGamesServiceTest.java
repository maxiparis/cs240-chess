package services;

import DAO.AuthDAO;
import DAO.GameDAO;
import chess.ChessGame;
import chess.ChessGameImpl;
import dataAccess.DataAccessException;
import model.AuthToken;
import model.Game;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import responses.ListGamesResponse;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class ListGamesServiceTest {
    private ListGamesService service;
    private GameDAO gameDB;
    private AuthDAO authDB;
    private HashSet<Game> expectedGamesSet;


    @BeforeEach
    void setUp() throws DataAccessException {

        service = new ListGamesService();
        gameDB = GameDAO.getInstance();
        authDB = AuthDAO.getInstance();

        try {
            gameDB.clear();
            authDB.clear();
        } catch (DataAccessException e){

        }

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

        expectedGamesSet = new HashSet<>();
        expectedGamesSet.add(game1);
        expectedGamesSet.add(game2);
        expectedGamesSet.add(game3);
        expectedGamesSet.add(game4);
        expectedGamesSet.add(game5);

        gameDB.insert(game1);
        gameDB.insert(game2);
        gameDB.insert(game3);
        gameDB.insert(game4);
        gameDB.insert(game5);

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
        gameDB.clear();
        authDB.clear();
    }

    @Test
    void listGames_Valid() throws DataAccessException {

        ListGamesResponse response = service.listGames("token2");

        assertNull(response.getMessage());
        assertEquals(expectedGamesSet, response.getGames());
    }

    @Test
    void listGames_Invalid() throws DataAccessException {
        //invalid cases
            //unauthorized
        ListGamesResponse response = service.listGames("invalidToken");

        assertNull(response.getGames());
        assertEquals("Error: unauthorized", response.getMessage());
    }
}