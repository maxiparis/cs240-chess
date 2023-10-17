package services;

import requests.CreateGameRequest;
import responses.CreateGameResponse;

/**
 * This class represents an API that creates a new game.
 */
public class CreateGameService {

    /**
     * Creates a new game, using the specifications given by the parameter.
     * @param request includes all the specifications to create the new game.
     * @return a response to the given action.
     */
    public CreateGameResponse createGame (CreateGameRequest request){
        return new CreateGameResponse(null, null);
    }
}
