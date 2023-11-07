package services;

import DAO.AuthDAO;
import DAO.UserDAO;
import dataAccess.DataAccessException;
import model.AuthToken;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import responses.LogoutResponse;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class LogoutServiceTest {
    private LogoutService service;
    private AuthDAO authDAO;
    private UserDAO userDAO;

    @BeforeEach
    void setUp() throws DataAccessException {
        service = new LogoutService();

        userDAO = UserDAO.getInstance();
        authDAO = AuthDAO.getInstance();
        authDAO.clear();
        userDAO.clear();

        userDAO.insert(new User("john","asdfasdf--","ffsdf@hotmail.cl"));
        userDAO.insert(new User("alex","fsffsff335#","eerer@hotmail.cl"));
        userDAO.insert(new User("steve","ffeeffsd","steve@hotmail.cl"));
        userDAO.insert(new User("kate","fsd@#$@f","test2@hotmail.cl"));
        userDAO.insert(new User("connor","fsdf-sdfsd","test3@hotmail.cl"));


        authDAO.insert(new AuthToken("john", "12345"));
        authDAO.insert(new AuthToken("alex", "67890"));
    }

    @AfterEach
    void tearDown() throws DataAccessException {
        authDAO.clear();
        userDAO.clear();
    }

    @Test
    void logoutValid() throws DataAccessException {
        //Valid - logging out a user correctly. The authToken is correctly removed from the DB.
        assertTrue(authDAO.findAll().size() == 2);
        LogoutResponse response = service.logout("12345");
        assertTrue(authDAO.findAll().size() == 1, "The DB did not REMOVE correctly the token.");
        assertTrue(response.getMessage() == null, "The response message was not set to null.");

    }

    @Test
    void logoutInvalidAuthTokenNotFound() throws DataAccessException {
        assertTrue(authDAO.findAll().size() == 2);

        LogoutResponse response = service.logout("wrongAuthToken");
        assertEquals("Error: unauthorized", response.getMessage(),
                "The error messages are not the same.");
        assertTrue(authDAO.findAll().size() == 2);

    }
}