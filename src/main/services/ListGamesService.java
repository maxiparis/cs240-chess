package services;

import DAO.GameDAO;
import dataAccess.DataAccessException;
import model.Game;
import responses.ListGamesResponse;

import java.util.HashSet;

/**
 * This class represents an API that lists all the games.
 */
public class ListGamesService extends AuthTokenValidator {

    /**
     * Lists all the games.
     * @return a response to the given action.
     */
    public ListGamesResponse listGames(String authToken) {
        try {
            validateAuthToken(authToken);
            HashSet<Game> gamesHashset = GameDAO.getInstance().findAll();

            return new ListGamesResponse(null, gamesHashset);
        } catch (DataAccessException e) {
            return new ListGamesResponse(e.getMessage(), null);
        }
    }
}
