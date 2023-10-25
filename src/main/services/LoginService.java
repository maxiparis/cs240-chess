package services;

import dataAccess.DataAccessException;
import requests.LoginRequest;
import responses.LoginResponse;

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
//        try {
//
//        } catch (DataAccessException e){
//
//        }
        return new LoginResponse(null, null, null);
    }
}
