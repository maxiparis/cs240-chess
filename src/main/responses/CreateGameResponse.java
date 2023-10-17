package responses;

public class CreateGameResponse extends ErrorResponse {

    private int gameID;

    public CreateGameResponse(String errorMessage, Integer gameID) {
        super(errorMessage);
        this.gameID = gameID;
    }

    public int getGameID() {
        return gameID;
    }

    public void setGameID(int gameID) {
        this.gameID=gameID;
    }
}
