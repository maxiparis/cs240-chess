package typeAdapters;
import chess.*;
import com.google.gson.*;

import java.lang.reflect.Type;

public class ChessBoardDeserializer implements JsonDeserializer{
    @Override
    public ChessBoard deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context)
            throws JsonParseException {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(ChessPiece.class, new ChessPieceDeserializer())
                .create();

        ChessBoardImpl chessBoard = new ChessBoardImpl();

        // {"boardTable":[[{"pieceType":"ROOK"},{"pieceType":"PAWN"}],[{"pieceType":"KING"},{"pieceType":"QUEEN"}]}

        JsonObject jsonObject = jsonElement.getAsJsonObject();
        // {"boardTable":[[{"pieceType":"ROOK"},{"pieceType":"PAWN"}],[{"pieceType":"KING"},{"pieceType":"QUEEN"}]}
        JsonArray boardTable = jsonObject.getAsJsonArray("boardTable");
        // [[{"pieceType":"ROOK"},{"pieceType":"PAWN"}]     ,     [{"pieceType":"KING"},{"pieceType":"QUEEN"}]

        for (int row = 0; row < boardTable.size(); row++) { //outer loop
            JsonElement columns = boardTable.get(row);
            //[{"pieceType":"ROOK"},{"pieceType":"PAWN"}]
            JsonArray columnsArray = columns.getAsJsonArray();
            for (int column = 0; column < columnsArray.size(); column++) { //inner loop
                JsonElement singlePiece = columnsArray.get(column);
                ChessPiece deserializedPiece = gson.fromJson(singlePiece, ChessPiece.class);
                chessBoard.addPiece(new ChessPositionImpl(row+1, column+1), deserializedPiece);
            }
        }


        return chessBoard;
    }
}
