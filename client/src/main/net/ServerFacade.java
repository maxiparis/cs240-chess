package net;

import com.google.gson.Gson;
import requests.LoginRequest;
import requests.RegisterRequest;
import responses.LoginResponse;
import responses.LogoutResponse;
import responses.RegisterResponse;

import java.io.InputStreamReader;

public class ServerFacade {
    private ClientCommunicator communicator = new ClientCommunicator();

    public LoginResponse login (LoginRequest request){
        //convert request to JSON
        String requestAsJson = new Gson().toJson(request);
        try {
            InputStreamReader jsonResponse = communicator.post(requestAsJson, "session");
            LoginResponse response = new Gson().fromJson(jsonResponse, LoginResponse.class);
            return response;
        } catch (Exception e) {
            LoginResponse response = new LoginResponse(e.getMessage(), null, null);
            return response;
        }
    }

    public RegisterResponse register(RegisterRequest request) {
        String requestAsJson = new Gson().toJson(request);
        try {
            InputStreamReader jsonResponse = communicator.post(requestAsJson, "user");
            RegisterResponse response = new Gson().fromJson(jsonResponse, RegisterResponse.class);
            return response;
        } catch (Exception e) {
            RegisterResponse response = new RegisterResponse(e.getMessage(), null, null);
            return response;
        }
    }

    public LogoutResponse logout(String authTokenLoggedIn) {
        //should I conver the authToken to json?? or should I just pass it as a string?
        try {
            InputStreamReader jsonResponse = communicator.delete(authTokenLoggedIn, "session");
            LogoutResponse response = new Gson().fromJson(jsonResponse, LogoutResponse.class);
            return response;
        } catch (Exception e) {
            LogoutResponse response = new LogoutResponse(e.getMessage());
            return response;
        }
    }
}
