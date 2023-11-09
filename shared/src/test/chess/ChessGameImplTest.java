package chess;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChessGameImplTest {
    public static ChessGameImpl game;
    public static ChessGameImpl game2;

    @BeforeEach
    public void setUp(){
        game = new ChessGameImpl(ChessGame.TeamColor.WHITE);
        game2 = new ChessGameImpl(ChessGame.TeamColor.WHITE);
    }

    @Test
    void changeTurn() {
        //white
        assertTrue(game.getTeamTurn().equals(ChessGame.TeamColor.WHITE));

        //change turn
        //expecting black
        game.changeTurn(game.getTeamTurn());
        assertTrue(game.getTeamTurn().equals(ChessGame.TeamColor.BLACK));
        assertFalse(game.getTeamTurn().equals(ChessGame.TeamColor.WHITE));


    }

    @Test
    void testEquals() {
        //all null
        assertTrue(game.equals(game2));

        game.getBoard().addPiece(new ChessPositionImpl(2,2),
                new Pawn(ChessGame.TeamColor.WHITE) );
        game2.getBoard().addPiece(new ChessPositionImpl(2,2),
                new Pawn(ChessGame.TeamColor.WHITE) );
        //with pieces
        assertTrue(game.equals(game2));

        //different. cases = game has more pieces than game2 and viceversa
        //game2 has more pieces
        game2.getBoard().addPiece(new ChessPositionImpl(4,2),
                new Pawn(ChessGame.TeamColor.WHITE) );
        assertFalse(game.equals(game2));

        //game1 has more pieces
        game.getBoard().addPiece(new ChessPositionImpl(4,2),
                new Pawn(ChessGame.TeamColor.WHITE) );
        game.getBoard().addPiece(new ChessPositionImpl(6,2),
                new Pawn(ChessGame.TeamColor.BLACK) );
        assertFalse(game.equals(game2));

        //full boards
        game.getBoard().resetBoard();
        game2.getBoard().resetBoard();
        assertTrue(game.equals(game2));



    }
}