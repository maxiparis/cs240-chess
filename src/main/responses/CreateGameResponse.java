package responses;

/**
 * A Class that represents the JSON response for CreateGameService. It extends ErrorResponse to
 * be able to return an error message.
 */
public class CreateGameResponse extends ErrorResponse {

    /**
     * the gameID of the game created.
     */
    private int gameID;

    /**
     * Constructs a new object with the parameter passed.
     * @param errorMessage initializes super(errorMessage)
     * @param gameID initializes this.gameID to this value
     *
     */
    public CreateGameResponse(String errorMessage, Integer gameID) {
        super(errorMessage);
        this.gameID = gameID;
    }

    /**
     * @return the gameID of the created game.
     */
    public int getGameID() {
        return gameID;
    }

    /**
     * Sets the gameID with the value of the parameter.
     * @param gameID sets this.gameID to its value.
     */
    public void setGameID(int gameID) {
        this.gameID=gameID;
    }
}
