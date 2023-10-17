package responses;

import model.Game;

import java.util.Collection;

public class ListGamesResponse extends ErrorResponse {
    private Game[] games;

    public ListGamesResponse(String errorMessage, Game[] games) {
        super(errorMessage);
        this.games = games;
    }

    public Game[] getGames() {
        return games;
    }

    public void setGames(Game[] games) {
        this.games = games;
    }
}
