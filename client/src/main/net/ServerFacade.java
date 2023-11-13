package net;

import com.google.gson.Gson;
import requests.LoginRequest;
import responses.LoginResponse;

public class ServerFacade {
    private ClientCommunicator communicator = new ClientCommunicator();

    public LoginResponse login (LoginRequest request){
        //convert request to JSON
        String requestAsJson = new Gson().toJson(request);
        try {
            return communicator.post(requestAsJson, "session");
        } catch (Exception e) {
            System.out.println("Login had an error: " + e.getMessage());
        }
        return null;
    }
}
