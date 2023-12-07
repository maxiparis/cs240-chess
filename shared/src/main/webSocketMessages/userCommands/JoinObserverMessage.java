package webSocketMessages.userCommands;

public class JoinObserverMessage extends UserGameCommand {
    private int gameID;

    public JoinObserverMessage(String authToken, int gameID) {
        super(authToken);
        this.gameID = gameID;
        super.commandType = CommandType.JOIN_OBSERVER;
    }
}
