package net;

import chess.ChessGame;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import requests.CreateGameRequest;
import requests.JoinGameRequest;
import requests.LoginRequest;
import requests.RegisterRequest;
import responses.*;
import typeAdapters.ChessGameDeserializer;
import typeAdapters.ListGamesResponseDeserializer;

import java.io.InputStreamReader;

public class ServerFacade {
    private ClientCommunicator communicator = new ClientCommunicator();

    public LoginResponse login (LoginRequest request){
        //convert request to JSON
        String requestAsJson = new Gson().toJson(request);
        try {
            InputStreamReader jsonResponse = communicator.post(requestAsJson, null, "session");
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
            InputStreamReader jsonResponse = communicator.post(requestAsJson, null, "user");
            RegisterResponse response = new Gson().fromJson(jsonResponse, RegisterResponse.class);
            return response;
        } catch (Exception e) {
            RegisterResponse response = new RegisterResponse(e.getMessage(), null, null);
            return response;
        }
    }

    public LogoutResponse logout(String authTokenLoggedIn) {
        try {
            InputStreamReader jsonResponse = communicator.delete(authTokenLoggedIn, "session");
            LogoutResponse response = new Gson().fromJson(jsonResponse, LogoutResponse.class);
            return response;
        } catch (Exception e) {
            LogoutResponse response = new LogoutResponse(e.getMessage());
            return response;
        }
    }

    public CreateGameResponse createGame(CreateGameRequest request, String tokenToAuthorize) {
        String requestAsJson = new Gson().toJson(request);
        try {
            InputStreamReader jsonResponse = communicator.post(requestAsJson, tokenToAuthorize, "game");
            CreateGameResponse response = new Gson().fromJson(jsonResponse, CreateGameResponse.class);
            return response;
        } catch (Exception e) {
            CreateGameResponse response = new CreateGameResponse(e.getMessage(), null);
            return response;
        }

    }

    public ListGamesResponse listGames(String tokenToAuthorize) {
        try {
            InputStreamReader jsonResponse = communicator.get(tokenToAuthorize, "game");
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(ListGamesResponse.class, new ListGamesResponseDeserializer())
                    .create();

            ListGamesResponse response = gson.fromJson(jsonResponse, ListGamesResponse.class);
            return response;
        } catch (Exception e) {
            return new ListGamesResponse(e.getMessage(), null);
        }
    }

    public JoinGameResponse joinGame(JoinGameRequest request, String tokenToAuthorize) {
        String requestAsJson = new Gson().toJson(request);
        try {
            InputStreamReader jsonResponse = communicator.put(requestAsJson, tokenToAuthorize, "game");
            JoinGameResponse response = new Gson().fromJson(jsonResponse, JoinGameResponse.class);
            return response;
        } catch (Exception e) {
            JoinGameResponse response = new JoinGameResponse(e.getMessage());
            return response;
        }
    }
}
