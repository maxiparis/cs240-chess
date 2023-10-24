package DAO;

import dataAccess.DataAccessException;
import model.AuthToken;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;
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
        authDAO= new AuthDAO();
        authDAO2= new AuthDAO();
        model = new AuthToken("434123412", "Alex");
        model2 = new AuthToken("d123123dasd34", "Martha");
    }

    @Test
    void insert() throws DataAccessException {
    //valid
        authDAO.insert(model);
        Assertions.assertTrue(authDAO.getAuthTokens().size() == 1);
        Assertions.assertFalse(authDAO.getAuthTokens().size() == 0
                || authDAO.getAuthTokens().size() == 2
        );
        Assertions.assertTrue(authDAO.getAuthTokens().contains(model));

        //testing that all instances have same elements
        Assertions.assertTrue(authDAO2.getAuthTokens().size() == 1);
        Assertions.assertTrue(authDAO2.getAuthTokens().contains(model));

        //adding a second element
        authDAO.insert(model2);
        Assertions.assertTrue(authDAO.getAuthTokens().size() == 2);
        Assertions.assertTrue(authDAO.getAuthTokens().contains(model2));
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
    void update() {
    }

    @Test
    void remove() {
    }

    @Test
    void clear() {
    }
}