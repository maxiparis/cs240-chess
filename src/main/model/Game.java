package model;

import chess.ChessGameImpl;

/**
 * A class that represents the Games in the server.
 */
public class Game {

    /**
     * gameID will be used to identify each game in the DB.
     */
    private int gameID;

    /**
     * the username of the white player.
     */
    private String whiteUsername;

    /**
     * the username of the black player.
     */
    private String blackUsername;

    /**
     * the name of the game
     */
    private String gameName;

    /**
     * the ChessGame object that represents the actual game (position, board, turn)
     */
    private ChessGameImpl game;

    /**
     * Constructs a new Game object.
     * @param gameID value to initialize this.gameID
     * @param whiteUsername value to initialize this.whiteUsername
     * @param blackUsername value to initialize this.blackUsername
     * @param gameName value to initialize this.gameName
     * @param game value to initialize this.game
     */
    public Game(int gameID, String whiteUsername, String blackUsername, String gameName, ChessGameImpl game) {
        this.gameID = gameID;
        this.whiteUsername = whiteUsername;
        this.blackUsername = blackUsername;
        this.gameName = gameName;
        this.game = game;
    }

    /**
     * Constructs an empy game object for compiling purposes.
     */
    public Game() {
    }

    /**
     * Returns the gameID
     * @return gameID member
     */
    public int getGameID() {
        return gameID;
    }

    /**
     * Sets the gameID with the parameter passed.
     * @param gameID to initialize this.gameID with.
     */
    public void setGameID(int gameID) {
        this.gameID=gameID;
    }

    /**
     * Returns the whiteUsername
     * @return whiteUsername member
     */
    public String getWhiteUsername() {
        return whiteUsername;
    }

    /**
     * Sets the whiteUsername with the parameter passed.
     * @param whiteUsername to initialize this.whiteUsername with.
     */
    public void setWhiteUsername(String whiteUsername) {
        this.whiteUsername=whiteUsername;
    }

    /**
     * Returns the blackUsername
     * @return blackUsername member
     */
    public String getBlackUsername() {
        return blackUsername;
    }

    /**
     * Sets the blackUsername with the parameter passed.
     * @param blackUsername to initialize this.blackUsername with.
     */
    public void setBlackUsername(String blackUsername) {
        this.blackUsername=blackUsername;
    }

    /**
     * Returns the gameName
     * @return gameName member
     */
    public String getGameName() {
        return gameName;
    }

    /**
     * Sets the gameName with the parameter passed.
     * @param gameName to initialize this.gameName with.
     */
    public void setGameName(String gameName) {
        this.gameName=gameName;
    }

    /**
     * Returns the game.
     * @return game member.
     */
    public ChessGameImpl getGame() {
        return game;
    }

    /**
     * Sets the game with the parameter passed.
     * @param game to initialize this.game with.
     */
    public void setGame(ChessGameImpl game) {
        this.game=game;
    }
}
