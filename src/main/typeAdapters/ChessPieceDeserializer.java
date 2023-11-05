package typeAdapters;

import chess.*;
import com.google.gson.JsonDeserializer;
import com.google.gson.*;

import java.lang.reflect.Type;

public class ChessPieceDeserializer implements JsonDeserializer {
    @Override
    public ChessPiece deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context)
            throws JsonParseException {
//        {
//          "pieceType": "ROOK",
//          "teamColor": "WHITE"
//        }


        ChessPiece piece = null;

        JsonObject object = jsonElement.getAsJsonObject();
        ChessPiece.PieceType pieceType = context.deserialize(object.get("pieceType"), ChessPiece.PieceType.class);
        ChessGame.TeamColor teamColor = context.deserialize(object.get("teamColor"), ChessGame.TeamColor.class);

        switch (pieceType) {
            case KING -> {
                piece = new King(teamColor);
            }
            case QUEEN -> {
                piece = new Queen(teamColor);
            }
            case ROOK -> {
                piece = new Rook(teamColor);
            }
            case BISHOP -> {
                piece = new Bishop(teamColor);
            }
            case KNIGHT -> {
                piece = new Knight(teamColor);
            }
            case PAWN -> {
                piece = new Pawn(teamColor);
            }
        }

        return piece;
    }
}
