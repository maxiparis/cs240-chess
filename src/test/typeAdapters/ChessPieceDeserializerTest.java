package typeAdapters;

import chess.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChessPieceDeserializerTest {

    @Test
    void deserialize() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(ChessPiece.class, new ChessPieceDeserializer())
                .create();

        //White Pawn
        Pawn toSerialize = new Pawn(ChessGame.TeamColor.WHITE);
        String jsonString = gson.toJson(toSerialize);

        ChessPiece actual = gson.fromJson(jsonString, ChessPiece.class);
        ChessPiece expected = new Pawn(ChessGame.TeamColor.WHITE);

        assertEquals(expected, actual);

        //Black King
        King toSerialize2 = new King(ChessGame.TeamColor.BLACK);
        String jsonString2 = gson.toJson(toSerialize2);

        ChessPiece actual2 = gson.fromJson(jsonString2, ChessPiece.class);
        ChessPiece expected2 = new King(ChessGame.TeamColor.BLACK);

        assertEquals(expected2, actual2);
    }
}