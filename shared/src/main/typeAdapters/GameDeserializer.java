package typeAdapters;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessGameImpl;
import com.google.gson.*;
import model.Game;

import java.lang.reflect.Type;

public class GameDeserializer implements JsonDeserializer {
    @Override
    public Game deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context)
            throws JsonParseException {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(ChessGameImpl.class, new ChessGameDeserializer())
                .create();
        Game toReturn = new Game();
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        // {
        //      "gameID": 1638,
        //      "blackUsername": "tom",
        //      "whiteUsername" : "max",
        //      "gameName": "testst",
        //      "game":  {
        //          "teamTurn": "WHITE",
        //          "board": { boardTable [...][...] }
        //      }


        int gameID = context.deserialize(jsonObject.get("gameID"), int.class);
        String blackUsername;
        String whiteUsername;
        try {
            blackUsername = context.deserialize(jsonObject.get("blackUsername"), String.class);
        } catch (Exception e){
            System.out.println(e.getMessage());
            blackUsername = null;
        }

        try {
            whiteUsername = context.deserialize(jsonObject.get("whiteUsername"), String.class);
        } catch (Exception e){
            System.out.println(e.getMessage());
            whiteUsername = null;
        }

        String gameName = context.deserialize(jsonObject.get("gameName"), String.class);
        JsonElement chessGameAsElement = jsonObject.get("game");
        ChessGameImpl deserializedChessGame = gson.fromJson(chessGameAsElement, ChessGameImpl.class);

        // {
        //      "gameID": 1638,
        //      "blackUsername": "tom",
        //      "whiteUsername" : "max",
        //      "gameName": "testst",
        //      "game":  {
        //          "teamTurn": "WHITE",
        //          "board": { boardTable [...][...] }
        //      }

        //set here all the attributes of game, before returning it
        toReturn.setGame(deserializedChessGame);
        toReturn.setGameID(gameID);
        toReturn.setBlackUsername(blackUsername);
        toReturn.setWhiteUsername(whiteUsername);
        toReturn.setGameName(gameName);

        return toReturn;
    }
}
