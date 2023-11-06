package DAO;

import dataAccess.DataAccessException;
import model.AuthToken;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class AuthDAOTest {
    private AuthDAO authDAO;
    private UserDAO userDAO;
    private AuthDAO expected;
    private AuthToken model;
    private AuthToken model2;

    @BeforeEach
    public void setUp() throws DataAccessException {
        userDAO = UserDAO.getInstance();

        userDAO.insert(new User("john","asdfasdf--","ffsdf@hotmail.cl"));
        userDAO.insert(new User("alex","fsffsff335#","eerer@hotmail.cl"));
        userDAO.insert(new User("steve","ffeeffsd","steve@hotmail.cl"));
        userDAO.insert(new User("kate","fsd@#$@f","test2@hotmail.cl"));
        userDAO.insert(new User("connor","fsdf-sdfsd","test3@hotmail.cl"));

        authDAO = AuthDAO.getInstance();

        authDAO.insert(new AuthToken("john", UUID.randomUUID().toString()));
        authDAO.insert(new AuthToken("alex", UUID.randomUUID().toString()));
    }

    @AfterEach
    void tearDown() throws DataAccessException {
        authDAO.clear();
        userDAO.clear();
    }

    @Test
    void insert() throws DataAccessException {
        //valid
        String steveToken = UUID.randomUUID().toString();
        authDAO.insert(new AuthToken("steve", steveToken ));

        //adding a second element
        String kateToken =  UUID.randomUUID().toString();
        authDAO.insert(new AuthToken("kate", kateToken));

        AuthToken foundToken1 = authDAO.find("steve");
        AuthToken foundToken2 = authDAO.find("kate");

        assertEquals(steveToken, foundToken1.getToken());
        assertEquals(kateToken, foundToken2.getToken());

        assertEquals("steve", foundToken1.getUsername());
        assertEquals("kate", foundToken2.getUsername());

        //invalid -> try to insert a valid token for a user that does not exist
        assertThrows(DataAccessException.class, () -> {
            authDAO.insert(new AuthToken("invalidUser", "asdfasdfasdfsadf" ));
        });

        //invalid -> try to insert a valid token for a user that is already there
        assertThrows(DataAccessException.class, () -> {
            authDAO.insert(new AuthToken("steve", "asdfasdfasdfsadf" ));
        });
    }

    @Test
    void find() throws DataAccessException {
    //valid
        String steveToken = UUID.randomUUID().toString();
        authDAO.insert(new AuthToken("steve", steveToken ));

        AuthToken foundToken = authDAO.find("steve");

        assertEquals(steveToken, foundToken.getToken());

        assertEquals("steve", foundToken.getUsername());

    //invalid -> find with username that does not exist in the DB.
        assertThrows(DataAccessException.class, ()-> {
            authDAO.find("invalidUserName");
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
//        AuthToken actual = authDAO.find(expected);
//        assertEquals(expected, actual);
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
            authDAO.clear();
//        //empty DB
//        assertThrows(DataAccessException.class, () -> {
//            authDAO.clear();
//        });
//        //valid
//        authDAO.insert(model2);
//        authDAO.insert(model);
//        assertTrue(authDAO.getAuthTokensDB().size() == 2);
//
//        authDAO.clear();
//        assertTrue(authDAO.getAuthTokensDB().size() == 0);
    }
}