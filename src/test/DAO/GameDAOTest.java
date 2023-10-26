package DAO;

import chess.ChessGame;
import chess.ChessGameImpl;
import dataAccess.DataAccessException;
import model.Game;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class GameDAOTest {
    private GameDAO gameDAO;
    private GameDAO gameDAO2;
    private Game model;
    private Game model2;

    @BeforeEach
    void setUp() {
        gameDAO = GameDAO.getInstance();
        //    public Game(int gameID, String whiteUsername, String blackUsername, String gameName, ChessGameImpl game) {
        model = new Game(1, "sam", "tony",
                "gameName", new ChessGameImpl(ChessGame.TeamColor.WHITE));
        model2 = new Game(2, "jack", "chalo",
                "myGame", new ChessGameImpl(ChessGame.TeamColor.BLACK));
    }

    @AfterEach
    void tearDown() {
        try {
            gameDAO.clear();
        } catch (DataAccessException e) {

        }
    }

    @Test
    void insert() throws DataAccessException {
        //insert one
        gameDAO.insert(model);
        assertTrue(GameDAO.getInstance().getGamesDB().contains(model));

        gameDAO.insert(model2);
        assertTrue(GameDAO.getInstance().getGamesDB().contains(model));
        assertTrue(GameDAO.getInstance().getGamesDB().contains(model2));

        //insert one that is already there
        assertThrows(DataAccessException.class, () -> {
            gameDAO.insert(model2);
        });
    }

    @Test
    void find() throws DataAccessException {
        //not the best, needs to improve this one, there was a bug and it didn't detect it.
        //game is in db
        gameDAO.insert(model);
        Game actual = gameDAO.find(model);
        Game expected = model;
        assertEquals(expected, actual);

        //user is not in db
        assertThrows(DataAccessException.class, () -> {
            gameDAO.find(model2);
        });
    }

    @Test
    void findAll() throws DataAccessException {
        //valid
        gameDAO.insert(model);
        gameDAO.insert(model2);
        HashSet<Game> actual = gameDAO.findAll();
        assertTrue(actual.contains(model));
        assertTrue(actual.contains(model2));

        //invalid - set is empty
        gameDAO.clear();
        assertThrows(DataAccessException.class, () -> {
            gameDAO.findAll();
        });
    }

    @Test
    void updateGame() throws DataAccessException {
        //    public Game(int gameID, String whiteUsername, String blackUsername, String gameName, ChessGameImpl game)

        Game update = new Game(model.getGameID(), "test1", "test2", "test3",
                new ChessGameImpl(ChessGame.TeamColor.BLACK));
        //valid
        gameDAO.insert(model);
        gameDAO.updateGame(model.getGameID(), update.getWhiteUsername(), update.getBlackUsername(), update.getGameName(),
                update.getGame());

        assertTrue(GameDAO.getInstance().getGamesDB().contains(update));
        assertFalse(GameDAO.getInstance().getGamesDB().contains(model));

        //invalid - trying to update something that is not there
        assertThrows(DataAccessException.class, () -> {
            gameDAO.updateGame(model2.getGameID(), "test", "test",
                    "test", new ChessGameImpl(ChessGame.TeamColor.WHITE));
        });
    }

    @Test
    void remove() throws DataAccessException {
        //valid
        gameDAO.insert(model);
        gameDAO.remove(model);
        assertTrue(GameDAO.getInstance().getGamesDB().isEmpty());

        //invalid - the user is not in the db
        assertThrows(DataAccessException.class, () -> {
            gameDAO.remove(model2);
        });

    }

    @Test
    void clear() throws DataAccessException {
        //valid
        gameDAO.insert(model);
        gameDAO.insert(model2);
        gameDAO.clear();
        assertTrue(GameDAO.getInstance().getGamesDB().isEmpty());

        //invalid - it was empty already
        assertThrows(DataAccessException.class, () -> {
            gameDAO.clear();
        });
    }
}