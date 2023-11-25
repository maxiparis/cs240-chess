package ui;

import chess.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.net.SocketTimeoutException;

import static org.junit.jupiter.api.Assertions.*;

class BoardDrawerTest {
    ChessBoardImpl board;
    BoardDrawer drawer;
    @BeforeEach
    void setUp() {
        board = new ChessBoardImpl();
        board.resetBoard();
        board.addPiece(new ChessPositionImpl(5, 7), new Pawn(ChessGame.TeamColor.WHITE));
        board.addPiece(new ChessPositionImpl(5, 2), new Rook(ChessGame.TeamColor.BLACK));
        drawer = new BoardDrawer(board);
    }

    @Test
    void drawBoardWhite() {
        drawer.drawBoardWhite();
        drawer.drawBoardBlack();
        System.out.println("This is a text to test the colors after printing the board");
    }
}
