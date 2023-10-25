package services;

import DAO.UserDAO;
import dataAccess.DataAccessException;
import model.User;
import requests.RegisterRequest;
import responses.RegisterResponse;

import java.util.UUID;

/**
 * This class represents an API that register a user.
 */
public class RegisterService {

    /**
     * Registers a user.
     * @param request includes all the information to register a user (username, password, email)
     * @return a response to the registration.
     */
    public RegisterResponse register (RegisterRequest request) {
        try {
            User newUser = new User(request.getUsername(), request.getPassword(), request.getEmail());
            UserDAO userDB = new UserDAO();
            userDB.insert(newUser);

            String authToken = UUID.randomUUID().toString();

            RegisterResponse response = new RegisterResponse(null, request.getUsername(), authToken);
            return response;
        } catch (DataAccessException e) {
            return new RegisterResponse(e.getMessage(), null, null);
        }
    }
}
