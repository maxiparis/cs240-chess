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
import responses.ClearApplicationResponse;

import java.util.ServiceConfigurationError;

import static org.junit.jupiter.api.Assertions.*;

class ClearApplicationServiceTest {
    private ClearApplicationService service;
    private UserDAO userDB;
    private AuthDAO authDB;
    private GameDAO gameDB;

    @BeforeEach
    void setUp() throws DataAccessException {
        service = new ClearApplicationService();
        userDB = UserDAO.getInstance();
        authDB = AuthDAO.getInstance();
        gameDB = GameDAO.getInstance();
        gameDB.clear();
        authDB.clear();
        userDB.clear();
    }

    @Test
    void clearApplication() throws DataAccessException {
        //testing that after I call the clear application service, the all DB's are cleared.
        userDB.insert(new User("test1", "test1", "test1"));
        userDB.insert(new User("test2", "test2", "test2"));
        userDB.insert(new User("test3", "test3", "test3"));
        userDB.insert(new User("test4", "test4", "test4"));
        userDB.insert(new User("test5", "test5", "test5"));

        authDB.insert(new AuthToken("test1", "test1"));
        authDB.insert(new AuthToken("test2", "test2"));
        authDB.insert(new AuthToken("test3", "test3"));
        authDB.insert(new AuthToken("test4", "test4"));
        authDB.insert(new AuthToken("test5", "test5"));


        gameDB.insert(new Game(1, "test1", "test1", "test1",
                new ChessGameImpl(ChessGame.TeamColor.WHITE)));
        gameDB.insert(new Game(2, "test2", "test2", "test2",
                new ChessGameImpl(ChessGame.TeamColor.WHITE)));
        gameDB.insert(new Game(3, "test3", "test3", "test3",
                new ChessGameImpl(ChessGame.TeamColor.WHITE)));
        gameDB.insert(new Game(4, "test4", "test4", "test4",
                new ChessGameImpl(ChessGame.TeamColor.WHITE)));
        gameDB.insert(new Game(5, "test5", "test5", "test5",
                new ChessGameImpl(ChessGame.TeamColor.WHITE)));

        ClearApplicationResponse response = service.clearApplication();

        assertNull(response.getMessage());

        //calling for the second time
        response = service.clearApplication();

        assertNull(response.getMessage());


    }
}