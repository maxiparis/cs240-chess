package typeAdapters;

import chess.ChessGame;
import chess.ChessMoveImpl;
import com.google.gson.*;
import model.User;
import webSocketMessages.userCommands.*;

import java.lang.reflect.Type;

public class UserGameCommandDeserializer implements JsonDeserializer {

    @Override
    public UserGameCommand deserialize
            (JsonElement jsonElement, Type type, JsonDeserializationContext context)
            throws JsonParseException {

        JsonObject jsonObject = jsonElement.getAsJsonObject();
        String authToken = context.deserialize(jsonObject.get("authToken"), String.class);
        int gameID = context.deserialize(jsonObject.get("gameID"), int.class);

        UserGameCommand.CommandType commandType = context.deserialize(jsonObject.get("commandType"),
                UserGameCommand.CommandType.class);

        switch (commandType){
            case JOIN_PLAYER -> {
                ChessGame.TeamColor playerColor = context.deserialize(jsonObject.get("playerColor"),
                        ChessGame.TeamColor.class);
                return new JoinPlayerMessage(authToken, gameID, playerColor);
            }
            case JOIN_OBSERVER -> {
                return new JoinObserverMessage(authToken, gameID);
            }
            case MAKE_MOVE -> {
                ChessMoveImpl move = context.deserialize(jsonObject.get("move"),
                        ChessMoveImpl.class);
                return new MakeMoveMessage(authToken, gameID, move);
            }
            case LEAVE -> {
                return new LeaveMessage(authToken, gameID);
            }
            case RESIGN -> {
                return new ResignMessage(authToken, gameID);
            }
        }
        return null;
    }
}
