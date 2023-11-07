package services;

import DAO.AuthDAO;
import DAO.UserDAO;
import dataAccess.DataAccessException;
import model.AuthToken;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class AuthTokenValidatorTest {
    private AuthTokenValidator validator;
    private AuthDAO authDAO;
    private UserDAO userDAO;
    private AuthToken token1;
    private AuthToken token2;

    @BeforeEach
    void setUp() throws DataAccessException {
        validator = new AuthTokenValidator();
        userDAO = UserDAO.getInstance();
        authDAO = AuthDAO.getInstance();
        authDAO.clear();
        userDAO.clear();

        userDAO.insert(new User("john","asdfasdf--","ffsdf@hotmail.cl"));
        userDAO.insert(new User("alex","fsffsff335#","eerer@hotmail.cl"));
        userDAO.insert(new User("steve","ffeeffsd","steve@hotmail.cl"));
        userDAO.insert(new User("kate","fsd@#$@f","test2@hotmail.cl"));
        userDAO.insert(new User("connor","fsdf-sdfsd","test3@hotmail.cl"));

        token1 = new AuthToken("john", UUID.randomUUID().toString());
        token2 = new AuthToken("alex", UUID.randomUUID().toString());

        authDAO.insert(token1);
        authDAO.insert(token2);

    }

    @AfterEach
    void tearDown() throws DataAccessException {
        authDAO.clear();
    }

    @Test
    void validate_Valid() throws DataAccessException {
        String authToken1 = token1.getToken();
        boolean validToken = validator.validateAuthToken(authToken1); //valid
        assertTrue(validToken, "validToken was not validated correctly.");
    }

    @Test
    void validate_Invalid() throws DataAccessException {
        assertThrows(DataAccessException.class, () -> {
            boolean invalidToken = validator.validateAuthToken("235234");
        });

        assertThrows(DataAccessException.class, () -> {
            boolean invalidToken = validator.validateAuthToken("invalidToken!");
        });
    }
}