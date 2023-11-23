package ui;

import chess.ChessBoard;
import chess.ChessBoardImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardDrawerTest {
    ChessBoardImpl board;
    BoardDrawer drawer;
    @BeforeEach
    void setUp() {
        board = new ChessBoardImpl();
        board.resetBoard();
        drawer = new BoardDrawer(board);
    }

    @Test
    void drawBoardWhite() {
        drawer.drawBoardWhite();
    }
}