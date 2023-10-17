package services;

import requests.RegisterRequest;
import responses.RegisterResponse;

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
        return new RegisterResponse(null, null, null);
    }
}
