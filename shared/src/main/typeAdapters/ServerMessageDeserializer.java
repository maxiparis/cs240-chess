package typeAdapters;

import chess.ChessGameImpl;
import com.google.gson.*;
import model.Game;
import webSocketMessages.serverMessages.ErrorMessage;
import webSocketMessages.serverMessages.LoadGameMessage;
import webSocketMessages.serverMessages.NotificationMessage;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.userCommands.UserGameCommand;

import java.lang.reflect.Type;

public class ServerMessageDeserializer implements JsonDeserializer {
    @Override
    public ServerMessage deserialize(JsonElement jsonElement, Type type,
                                     JsonDeserializationContext context) throws JsonParseException {

        JsonObject jsonObject = jsonElement.getAsJsonObject();
        ServerMessage.ServerMessageType messageType = context.deserialize(jsonObject.get("serverMessageType"),
                ServerMessage.ServerMessageType.class);


        switch (messageType) {
            case LOAD_GAME -> {
                Gson gson = new GsonBuilder()
                        .registerTypeAdapter(ChessGameImpl.class, new ChessGameDeserializer())
                        .create();
                JsonElement chessGameAsElement = jsonObject.get("game");
                ChessGameImpl deserializedChessGame = gson.fromJson(chessGameAsElement, ChessGameImpl.class);
                return new LoadGameMessage(deserializedChessGame);
            }
            case ERROR -> {
                String message = context.deserialize(jsonObject.get("errorMessage"), String.class);
                return new ErrorMessage(message);
            }
            case NOTIFICATION -> {
                String message = context.deserialize(jsonObject.get("message"), String.class);
                return new NotificationMessage(message);
            }
        }
        return null;
    }
}
