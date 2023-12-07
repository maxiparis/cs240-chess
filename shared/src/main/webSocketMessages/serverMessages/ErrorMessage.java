package webSocketMessages.serverMessages;

public class ErrorMessage extends ServerMessage {
    private String  errorMessage;

    public ErrorMessage(String errorMessage) {
        super(ServerMessageType.ERROR);

        String[] split = errorMessage.split(" ");
        if(split[0].equals("Error:")){
            this.errorMessage = errorMessage;
        } else {
            this.errorMessage = "Error: " + errorMessage;
        }
    }
}
