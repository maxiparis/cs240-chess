package services;

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
    void setUp() {
        registerService = new RegisterService();
        request = new RegisterRequest("Tom", "password", "email");
        userDB = new UserDAO();
    }

    @Test
    void register() throws DataAccessException {
        //valid
        RegisterResponse response = registerService.register(request);
        User requestToUser = new User(request.getUsername(), request.getPassword(), request.getEmail());

        assertSame(userDB.find(requestToUser).getUsername(), request.getUsername());
        assertSame(userDB.find(requestToUser).getPassword(), request.getPassword());
        assertSame(userDB.find(requestToUser).getEmail(), request.getEmail());
        assertNull(response.getMessage());

        assertSame(response.getUsername(), request.getUsername());
        assertNotSame(response.getAuthToken(), "");
        System.out.println(response.getAuthToken());


        //invalid
        RegisterResponse invalidResponse = registerService.register(request); //same request than before
        assertNull(invalidResponse.getUsername());
        assertNull(invalidResponse.getAuthToken());
        assertNotNull(invalidResponse.getMessage());

    }
}