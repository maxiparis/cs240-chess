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
    void setUp() {
        service = new ClearApplicationService();
        userDB = UserDAO.getInstance();
        authDB = AuthDAO.getInstance();
        gameDB = GameDAO.getInstance();
    }

    @Test
    void clearApplication() throws DataAccessException {
        //testing that after I call the clear application service, the all DB's are cleared.
        userDB.insert(new User("test", "test", "test"));
        authDB.insert(new AuthToken("test", "test"));
        gameDB.insert(new Game(1, "test", "test", "test",
                new ChessGameImpl(ChessGame.TeamColor.WHITE))
        );
        ClearApplicationResponse response = service.clearApplication();

        assertTrue(UserDAO.getInstance().getUsersDB().isEmpty());
        assertTrue(AuthDAO.getInstance().getAuthTokensDB().isEmpty());
        assertTrue(GameDAO.getInstance().getGamesDB().isEmpty());

        assertNull(response.getMessage());

        //calling for the second time
        response = service.clearApplication();

        assertEquals("Error: DB was empty", response.getMessage());


    }
}