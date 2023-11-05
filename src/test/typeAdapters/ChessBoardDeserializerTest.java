package typeAdapters;

import chess.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChessBoardDeserializerTest {
    ChessBoardDeserializer deserializer = new ChessBoardDeserializer();

    @Test
    void deserialize() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(ChessBoard.class, new ChessBoardDeserializer())
                .create();

        ChessBoardImpl board = new ChessBoardImpl();
        board.resetBoard();
        String serializedBoard = gson.toJson(board);

        ChessBoard actual = gson.fromJson(serializedBoard, ChessBoard.class);

        ChessBoardImpl expected = new ChessBoardImpl();
        expected.resetBoard();

        assertEquals(expected, actual);

        expected.addPiece(new ChessPositionImpl(4,4), new Pawn(ChessGame.TeamColor.WHITE));

        assertNotEquals(expected, actual);
    }
}