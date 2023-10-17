package services;

import requests.JoinGameRequest;
import responses.JoinGameResponse;

/**
 * This class represents an API that joins a player to a game.
 */
public class JoinGameService {

    /**
     * Joins a player to the game
     * @param request includes the what team and game to join.
     * @return a response to the given action.
     */
    public JoinGameResponse joinGame (JoinGameRequest request){
        return new JoinGameResponse(null);
    }
}
