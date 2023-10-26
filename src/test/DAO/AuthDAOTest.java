package DAO;

import dataAccess.DataAccessException;
import model.AuthToken;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class AuthDAOTest {
    private AuthDAO authDAO;
    private AuthDAO authDAO2;
    private AuthDAO expected;
    private AuthToken model;
    private AuthToken model2;

    @BeforeEach
    public void setUp(){
        authDAO = new AuthDAO();
        authDAO2= new AuthDAO();
        model = new AuthToken("Alex", "434123412");
        model2 = new AuthToken("Martha", "d123123dasd34");
    }

    @AfterEach
    void tearDown() {
        AuthDAO.getAuthTokensDB().clear();
    }

    @Test
    void insert() throws DataAccessException {
    //valid
        authDAO.insert(model);
        Assertions.assertTrue(authDAO.getAuthTokensDB().size() == 1);
        Assertions.assertFalse(authDAO.getAuthTokensDB().size() == 0
                || authDAO.getAuthTokensDB().size() == 2
        );
        Assertions.assertTrue(authDAO.getAuthTokensDB().contains(model));

        //testing that all instances have same elements
        Assertions.assertTrue(authDAO2.getAuthTokensDB().size() == 1);
        Assertions.assertTrue(authDAO2.getAuthTokensDB().contains(model));

        //adding a second element
        authDAO.insert(model2);
        Assertions.assertTrue(authDAO.getAuthTokensDB().size() == 2);
        Assertions.assertTrue(authDAO.getAuthTokensDB().contains(model2));
    //invalid -> throws an exception
        assertThrows(DataAccessException.class, () -> {
            authDAO.insert(model);
            throw new DataAccessException("This is an exception.");
        });

    }

    @Test
    void find() throws DataAccessException {
    //valid
        authDAO.insert(model);
        AuthToken expected = model;
        AuthToken actual = authDAO.find(model);
        assertEquals(expected, actual);
    //invalid -> throws a new exception
        assertThrows(DataAccessException.class, ()-> {
            authDAO.find(model2);
        });
    }

    @Test
    void findAll() throws DataAccessException {
    //valid
        authDAO.insert(model);
        authDAO.insert(model2);
        HashSet<AuthToken> actual = authDAO.findAll();
        assertTrue(actual.contains(model));
        assertTrue(actual.contains(model2));

        authDAO.clear();
    //invalid
        assertThrows(DataAccessException.class, () -> {
            authDAO.clear();
        });
    }

    @Test
    void update() throws DataAccessException {
    //emtpy db
        assertThrows(DataAccessException.class, () -> {
            authDAO.update("Erick", "1238971945");
        });
    //non-emtpy db that does not have the username
        authDAO.insert(model);
        assertThrows(DataAccessException.class, () -> {
            authDAO.update("Alejandra", "123asda3");
        });
    //valid
        authDAO.update("Alex", "newToken");
        AuthToken expected = new AuthToken("Alex", "newToken");
        //contains the updated version
        AuthToken actual = authDAO.find(expected);
        assertEquals(expected, actual);
        //does not contain the old version
        assertFalse(authDAO.getAuthTokensDB().contains(model));
    }

    @Test
    void remove() throws DataAccessException {
        //remove something that does not exist
            assertThrows(DataAccessException.class, () -> {
                AuthToken invalidToken = new AuthToken("invalid", "invalid");
                authDAO.remove(invalidToken);
            });
        //valid
            authDAO.insert(model2);
            authDAO.insert(model);
            assertTrue(authDAO.getAuthTokensDB().size() == 2);

            authDAO.remove(model2);
            assertTrue(authDAO.getAuthTokensDB().size() == 1);
            assertTrue(authDAO.getAuthTokensDB().contains(model));
            assertFalse(authDAO.getAuthTokensDB().contains(model2));
    }

    @Test
    void clear() throws DataAccessException {
        //empty DB
        assertThrows(DataAccessException.class, () -> {
            authDAO.clear();
        });
        //valid
        authDAO.insert(model2);
        authDAO.insert(model);
        assertTrue(authDAO.getAuthTokensDB().size() == 2);

        authDAO.clear();
        assertTrue(authDAO.getAuthTokensDB().size() == 0);
    }
}