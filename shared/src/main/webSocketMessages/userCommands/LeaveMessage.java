package webSocketMessages.userCommands;

public class LeaveMessage extends UserGameCommand {
    private int gameID;

    public LeaveMessage(String authToken, int gameID) {
        super(authToken);
        this.gameID = gameID;
        super.commandType = CommandType.LEAVE;
    }

    public int getGameID() {
        return gameID;
    }
}
