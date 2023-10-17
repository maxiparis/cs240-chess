package services;

import responses.ListGamesResponse;

/**
 * This class represents an API that lists all the games.
 */
public class ListGamesService {

    /**
     * Lists all the games.
     * @return a response to the given action.
     */
    public ListGamesResponse listGames(){
        return new ListGamesResponse(null, null);
    }
}
