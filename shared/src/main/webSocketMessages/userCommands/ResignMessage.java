package webSocketMessages.userCommands;

public class ResignMessage extends UserGameCommand {
    private int gameID;

    public ResignMessage(String authToken, int gameID) {
        super(authToken);
        this.gameID = gameID;
        super.commandType = CommandType.RESIGN;
    }
}
