package services;

import requests.LoginRequest;
import responses.LoginResponse;

public class LoginService {
    public LoginResponse login(LoginRequest request) {
        return new LoginResponse(null, null, null);
    }
}
