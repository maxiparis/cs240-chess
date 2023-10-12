package chess;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChessGameImplTest {
    public static ChessGameImpl game;

    @BeforeAll
    public static void setUp(){
        game = new ChessGameImpl(ChessGame.TeamColor.WHITE);
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
}