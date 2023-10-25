package services;

import DAO.UserDAO;
import dataAccess.DataAccessException;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import requests.LoginRequest;
import requests.RegisterRequest;
import responses.LoginResponse;

import java.awt.image.RescaleOp;

import static org.junit.jupiter.api.Assertions.*;

class LoginServiceTest {
    private LoginService loginService;
    private LoginRequest request;
    private UserDAO userDB;

    @BeforeEach
    void setUp() {
        loginService = new LoginService();
        request = new LoginRequest("Jason", "1234*");
        userDB = new UserDAO();
    }

    @Test
    void login() throws DataAccessException {
        //valid - the user is not in the db
        User user = new User("Jason", "1234*", "email");
        userDB.insert(user);
        LoginResponse response = loginService.login(request);
        assertNotNull(response);

        assertSame(user.getUsername(), response.getUsername());



        //invalid
        String actualErrorMessage = "";
            //unauthorized - password do not match the username
            response = loginService.login(new LoginRequest("Jason", "wrongPassword"));
            actualErrorMessage = response.getMessage();
            assertSame("Error: unauthorized", actualErrorMessage);

            //username is not in db
            response = loginService.login(new LoginRequest("wrongUserName", "wrongPassword"));
            actualErrorMessage = response.getMessage();
            assertSame("Error: the username is not in the DB.", actualErrorMessage);

            //empty
            userDB.clear();
            response = loginService.login(new LoginRequest("wrongUserName", "wrongPassword"));
            actualErrorMessage = response.getMessage();
            assertSame("Error: DB is empty.", actualErrorMessage);
    }
}