package typeAdapters;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessGameImpl;
import com.google.gson.*;

import java.lang.reflect.Type;

public class ChessGameDeserializer implements JsonDeserializer {
    @Override
    public ChessGame deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context)
            throws JsonParseException {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(ChessBoard.class, new ChessBoardDeserializer())
                .create();

        JsonObject jsonObject = jsonElement.getAsJsonObject();

        ChessGame.TeamColor teamTurn = context.deserialize(jsonObject.get("teamTurn"), ChessGame.TeamColor.class);
        JsonElement boardAsElement = jsonObject.get("board");
        ChessBoard deserializedBoard = gson.fromJson(boardAsElement, ChessBoard.class);

        ChessGameImpl chessGame = new ChessGameImpl(teamTurn);
        chessGame.setBoard(deserializedBoard);

        return chessGame;
    }
}
