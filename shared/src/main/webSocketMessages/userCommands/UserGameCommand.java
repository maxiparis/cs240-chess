package webSocketMessages.userCommands;

import java.util.Objects;

/**
 * Represents a command a user can send the server over a websocket
 */
public class UserGameCommand {

    public UserGameCommand(String authToken) {
        this.authToken = authToken;
    }

    public enum CommandType {
        JOIN_PLAYER,
        JOIN_OBSERVER,
        MAKE_MOVE,
        LEAVE,
        RESIGN
    }

    protected CommandType commandType;

    private final String authToken;

    public String getAuthToken() {
        return authToken;
    }

    public CommandType getCommandType() { return this.commandType; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserGameCommand)) return false;
        UserGameCommand that = (UserGameCommand) o;
        return getCommandType() == that.getCommandType() && Objects.equals(getAuthToken(), that.getAuthToken());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCommandType(), getAuthToken());
    }

}
