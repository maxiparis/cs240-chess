package typeAdapters;

import chess.ChessGame;
import chess.ChessGameImpl;
import com.google.gson.*;

import java.lang.reflect.Type;

public class ChessGameDeserializer implements JsonDeserializer {
    @Override
    public ChessGame deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context)
            throws JsonParseException {

        JsonObject jsonObject = jsonElement.getAsJsonObject();

        ChessGame.TeamColor teamTurn = context.deserialize(jsonObject.get("teamTurn"), ChessGame.TeamColor.class);
        ChessGame board = context.deserialize(jsonObject.get("board"), ChessGame.class);

        ChessGameImpl chessGame = new ChessGameImpl(teamTurn);
        chessGame.setBoard(board.getBoard());

        return chessGame;
    }
}
