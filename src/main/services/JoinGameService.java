package services;

import requests.JoinGameRequest;
import responses.JoinGameResponse;

public class JoinGameService {
    public JoinGameResponse joinGame (JoinGameRequest request){
        return new JoinGameResponse(null);
    }
}
