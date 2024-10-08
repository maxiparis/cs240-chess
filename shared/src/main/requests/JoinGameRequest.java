package requests;

import chess.ChessGame;

/**
 * A class to represent the JSON model passed to the JoinGameService
 */
public class JoinGameRequest {
    /**
     * the color of the player that will join the game
     */
    private ChessGame.TeamColor playerColor;

    /**
     * the gameID of the game the player is trying to join.
     */
    private int gameID;

    /**
     * Constructs a new object, with the parameters passed.
     * @param playerColor is the value this.playerColor will be initialized to.
     * @param gameID is the value this.gameID will be initialized to.
     */
    public JoinGameRequest(ChessGame.TeamColor playerColor, int gameID) {
        this.playerColor = playerColor;
        this.gameID = gameID;
    }

    /**
     * @return the playerColor.
     */
    public ChessGame.TeamColor getPlayerColor() {
        return playerColor;
    }

    /**
     * Sets the playerColor to the parameter value.
     * @param playerColor is the value this.playerColor will be assigned to.
     */
    public void setPlayerColor(ChessGame.TeamColor playerColor) {
        this.playerColor = playerColor;
    }

    /**
     * @return the gameID.
     */
    public int getGameID() {
        return gameID;
    }

    /**
     * Sets the gameID to the parameter value.
     * @param gameID is the value this.gameID will be assigned to.
     */
    public void setGameID(int gameID) {
        this.gameID = gameID;
    }
}
