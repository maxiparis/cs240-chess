package services;

import DAO.UserDAO;
import dataAccess.DataAccessException;
import model.User;
import requests.LoginRequest;
import responses.LoginResponse;

import java.util.UUID;

/**
 * This class represents an API that clears the logins a user.
 */
public class LoginService {

    /**
     * Logins a user.
     * @param request includes all the information to perform the action (username, password)
     * @return a response to the given action.
     */
    public LoginResponse login(LoginRequest request) {
        //returns response with username and authtoken
        //request = username and password
        try {

            System.out.println("LoginRequest request: username = " + request.getUsername() + " | password = " + request.getPassword());
            System.out.println("UserDB = " + UserDAO.getInstance().findAll().toString());
            User found = UserDAO.getInstance().findWithUsernameAndPassword(request.getUsername(), request.getPassword());

            String authToken = UUID.randomUUID().toString();

            LoginResponse response = new LoginResponse(null, found.getUsername(), authToken);
            return response;
        } catch (DataAccessException e){
            return new LoginResponse(e.getMessage(), null, null);
        }
    }
}
