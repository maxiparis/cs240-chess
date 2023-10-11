package chess;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChessPieceTest {

    private static ChessBoardImpl testBoard;

    @BeforeEach
    public void setUp(){
        testBoard = new ChessBoardImpl();
        testBoard.resetBoard();
    }
    @Test
    void thereIsAnEnemyIn() {
        //8 |r|n|b|q|k|b|n|r|
        //7 |p|p|p|p|p|p|p|p|
        //6 | | | | | | | | |
        //5 | | | | | | | | |
        //4 | | | | | | | | |
        //3 | | | | | | | | |
        //2 |P|P|P|P|P|P|P|P|
        //1 |R|N|B|Q|K|B|N|R|
        //   1 2 3 4 5 6 7 8

        //testing with white positions
        //true
        Queen whiteQueen = new Queen(ChessGame.TeamColor.WHITE);
        ChessPositionImpl blackRook = new ChessPositionImpl(8,1);
        boolean actual = whiteQueen.thereIsAnEnemyIn(testBoard, blackRook);
        Assertions.assertTrue(actual);


        //false
        ChessPositionImpl allyPiece = new ChessPositionImpl(1,7);
        actual = whiteQueen.thereIsAnEnemyIn(testBoard, allyPiece);
        Assertions.assertFalse(actual);

    }

    @Test
    void moveWouldGoOutOfBoard() {
        //8 |r|n|b|q|k|b|n|r|
        //7 |p|p|p|p|p|p|p|p|
        //6 | | | | | | | | |
        //5 | | | | | | | | |
        //4 | | | | | | | | |
        //3 | | | | | | | | |
        //2 |P|P|P|P|P|P|P|P|
        //1 |R|N|B|Q|K|B|N|R|
        //   1 2 3 4 5 6 7 8

        //true
        Queen whiteQueen = new Queen(ChessGame.TeamColor.WHITE);
        ChessPositionImpl endPosition = new ChessPositionImpl(0, 4);
        boolean actual = whiteQueen.moveWouldGoOutOfBoard(endPosition);
        Assertions.assertTrue(actual);

        //false
        endPosition = new ChessPositionImpl(4, 4);
        actual = whiteQueen.moveWouldGoOutOfBoard(endPosition);
        Assertions.assertFalse(actual);


        //true column > 8
        ChessPiece pawn = testBoard.getPiece(new ChessPositionImpl(7, 8));
        endPosition = new ChessPositionImpl(7,9);
        actual = pawn.moveWouldGoOutOfBoard(endPosition);
        assertTrue(actual);

        //true column < 1
        endPosition = new ChessPositionImpl(7,0);
        actual = pawn.moveWouldGoOutOfBoard(endPosition);
        assertTrue(actual);

        //false
        endPosition = new ChessPositionImpl(8,8);
        actual = pawn.moveWouldGoOutOfBoard(endPosition);
        assertFalse(actual);

        endPosition = new ChessPositionImpl(1,1);
        actual = pawn.moveWouldGoOutOfBoard(endPosition);
        assertFalse(actual);


    }
}