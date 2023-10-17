package requests;

/**
 * A class to represent the JSON model passed to the CreateGameService
 */
public class CreateGameRequest {
    /**
     * Represents the name of the game to be created.
     */
    private String gameName;

    /**
     * Constructs a new object.
     * @param gameName initializes this.game to variable.
     */
    public CreateGameRequest(String gameName) {
        this.gameName = gameName;
    }

    /**
     * @return the gameName.
     */
    public String getGameName() {
        return gameName;
    }

    /**
     * Sets this.gameName to the parameter.
     * @param gameName is the value that this.gameName will be assigned to.
     */
    public void setGameName(String gameName) {
        this.gameName = gameName;
    }
}
