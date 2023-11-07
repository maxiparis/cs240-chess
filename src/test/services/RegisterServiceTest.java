package services;

import DAO.AuthDAO;
import DAO.UserDAO;
import dataAccess.DataAccessException;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import requests.RegisterRequest;
import responses.RegisterResponse;

import static org.junit.jupiter.api.Assertions.*;

class RegisterServiceTest {
    private RegisterService registerService;
    private RegisterRequest request;
    private UserDAO userDB;


    @BeforeEach
    void setUp() throws DataAccessException {
        registerService = new RegisterService();
        userDB = UserDAO.getInstance();
        AuthDAO.getInstance().clear();
        userDB.clear();
        request = new RegisterRequest("Tom", "password", "email");
    }

    @Test
    void register_Valid() throws DataAccessException {
        //valid
        RegisterResponse response = registerService.register(request);
        User requestToUser = new User(request.getUsername(), request.getPassword(), request.getEmail());

        assertEquals(userDB.find(requestToUser.getUsername()).getUsername(), request.getUsername());
        assertEquals(userDB.find(requestToUser.getUsername()).getPassword(), request.getPassword());
        assertEquals(userDB.find(requestToUser.getUsername()).getEmail(), request.getEmail());
        assertNull(response.getMessage());

        assertEquals(response.getUsername(), request.getUsername());
        assertNotSame(response.getAuthToken(), "");
    }

    @Test
    void register_Invalid() throws DataAccessException {
        RegisterResponse response = registerService.register(request);
        User requestToUser = new User(request.getUsername(), request.getPassword(), request.getEmail());

        //invalid - already taken
        RegisterResponse invalidResponse = registerService.register(request);
        assertNull(invalidResponse.getUsername());
        assertNull(invalidResponse.getAuthToken());
        assertNotNull(invalidResponse.getMessage());

        //invalid - bad request
        RegisterRequest badRequest = new RegisterRequest(null, "pass", "email");
        RegisterRequest badRequest2 = new RegisterRequest("username", null, "email");

        invalidResponse = registerService.register(badRequest);
        assertEquals("Error: bad request", invalidResponse.getMessage());
        assertNull(invalidResponse.getUsername());
        assertNull(invalidResponse.getAuthToken());
        assertNotNull(invalidResponse.getMessage());

        invalidResponse = registerService.register(badRequest2);
        assertEquals("Error: bad request", invalidResponse.getMessage());
        assertNull(invalidResponse.getUsername());
        assertNull(invalidResponse.getAuthToken());
        assertNotNull(invalidResponse.getMessage());

    }
}