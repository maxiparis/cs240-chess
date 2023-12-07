package webSocketMessages.serverMessages;

public class NotificationMessage extends ServerMessage {

    private String message;

    public NotificationMessage(ServerMessageType type, String message) {
        super(type);
        this.message = message;
        super.serverMessageType = ServerMessageType.NOTIFICATION;
    }
}
