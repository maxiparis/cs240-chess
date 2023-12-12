package ui;

import chess.ChessPiece;
import chess.ChessPositionImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClientTest {
    Client client = new Client();

    @Test
    void stringToChessPosition() {
        ChessPositionImpl position = client.StringToChessPosition("a5");
        assertEquals(1, position.getColumn());
        assertEquals(5, position.getRow());

        ChessPositionImpl invalid = client.StringToChessPosition("a524");
        assertNull(invalid);

        ChessPositionImpl invalid2 = client.StringToChessPosition("b9");
        assertNull(invalid2);

        ChessPositionImpl invalid3 = client.StringToChessPosition("n2");
        assertNull(invalid);
    }

    @Test
    void StringToPromotionPiece() {
        ChessPiece.PieceType queen = client.StringToPromotionPiece("queen");
        assertEquals(ChessPiece.PieceType.QUEEN, queen);

        ChessPiece.PieceType queen2 = client.StringToPromotionPiece("QUEEN");
        assertEquals(ChessPiece.PieceType.QUEEN, queen2);

        ChessPiece.PieceType rook = client.StringToPromotionPiece("rOOk");
        assertEquals(ChessPiece.PieceType.ROOK, rook);

        ChessPiece.PieceType invalid = client.StringToPromotionPiece("");
        assertNull(invalid);

        ChessPiece.PieceType invalid2 = client.StringToPromotionPiece(null);
        assertNull(invalid2);

        ChessPiece.PieceType invalid3 = client.StringToPromotionPiece("invalid");
        assertNull(invalid3);
    }
}