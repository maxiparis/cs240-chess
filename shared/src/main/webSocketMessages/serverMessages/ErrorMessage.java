package webSocketMessages.serverMessages;

public class ErrorMessage extends ServerMessage {
    private String  errorMessage;

    public ErrorMessage(ServerMessageType type, String errorMessage) {
        super(type);
        this.errorMessage = errorMessage;
        super.serverMessageType = ServerMessageType.ERROR;
    }
}
