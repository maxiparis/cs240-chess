package services;

import DAO.AuthDAO;
import DAO.GameDAO;
import DAO.UserDAO;
import dataAccess.DataAccessException;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import requests.LoginRequest;
import responses.LoginResponse;

import static org.junit.jupiter.api.Assertions.*;

class LoginServiceTest {
    private LoginService loginService;
    private LoginRequest request;
    private UserDAO userDB;

    @BeforeEach
    void setUp() throws DataAccessException {
        loginService = new LoginService();
        request = new LoginRequest("Jason", "1234*");
        userDB = UserDAO.getInstance();
        userDB.clear();
        AuthDAO.getInstance().clear();
        GameDAO.getInstance().clear();
    }

    @Test
    void login_Valid() throws DataAccessException {
        //valid - the user is not in the db
        User user = new User("Jason", "1234*", "email");
        userDB.insert(user);
        LoginResponse response = loginService.login(request);
        assertNotNull(response);

        assertEquals(user.getUsername(), response.getUsername());
    }

    @Test
    void login_Invalid() throws DataAccessException {
        User user = new User("Jason", "1234*", "email");
        userDB.insert(user);
        LoginResponse response = loginService.login(request);

        //invalid
        String actualErrorMessage = "";
        //unauthorized - password do not match the username
        response = loginService.login(new LoginRequest("Jason", "wrongPassword"));
        actualErrorMessage = response.getMessage();
        assertSame("Error: unauthorized", actualErrorMessage);

        //username is not in db
        response = loginService.login(new LoginRequest("wrongUserName", "wrongPassword"));
        actualErrorMessage = response.getMessage();
        assertSame("Error: unauthorized", actualErrorMessage);

        //empty
        userDB.clear();
        response = loginService.login(new LoginRequest("wrongUserName", "wrongPassword"));
        actualErrorMessage = response.getMessage();
        assertSame("Error: unauthorized", actualErrorMessage);
    }
}