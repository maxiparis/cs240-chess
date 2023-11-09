package responses;

import model.Game;

import java.util.HashSet;

/**
 * A Class that represents the JSON response for ListGamesService. It extends ErrorResponse to
 * be able to return an error message.
 */
public class ListGamesResponse extends ErrorResponse {
    /**
     * An array with all the games passed as response.
     */
    private HashSet<Game> games;

    /**
     * Constructs a new object, with an errorMessage and an array of games.
     * @param errorMessage is the value super.errorMessage will be set to.
     * @param games is the value this.games will be set to.
     */
    public ListGamesResponse(String errorMessage, HashSet<Game> games) {
        super(errorMessage);
        this.games = games;
    }

    /**
     * @return the games array.
     */
    public HashSet<Game> getGames() {
        return games;
    }

    /**
     * Set this.games to the value passed by the parameter.
     * @param games is the array this.games will be set to.
     */
    public void setGames(HashSet<Game> games) {
        this.games = games;
    }
}
