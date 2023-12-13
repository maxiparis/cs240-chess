package ui;

import chess.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import webSocketMessages.userCommands.MakeMoveMessage;

import javax.swing.*;
import java.net.SocketTimeoutException;
import java.util.Set;

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

    @Test
    void drawBoardWhiteHighlighting() throws InvalidMoveException {
        ChessGameImpl game = new ChessGameImpl(ChessGame.TeamColor.WHITE);
        board = new ChessBoardImpl();
        board.resetBoard();
        game.setBoard(board);

        ChessPositionImpl positionToCheck = new ChessPositionImpl(2, 4);
        Set<ChessMoveImpl> validMoves = game.validMoves(positionToCheck);

        drawer = new BoardDrawer(board);
        drawer.drawBoardWhiteHighlighting(positionToCheck, validMoves);

        game.makeMove(new ChessMoveImpl(new ChessPositionImpl(2, 5), new ChessPositionImpl(3,5)));

        drawer = new BoardDrawer(board);
        positionToCheck = new ChessPositionImpl(1, 4);
        validMoves = game.validMoves(positionToCheck);
        drawer.drawBoardWhiteHighlighting(positionToCheck, validMoves);

        game.setTeamTurn(ChessGame.TeamColor.WHITE);
        game.makeMove(new ChessMoveImpl(positionToCheck, new ChessPositionImpl(4, 7)));
        drawer = new BoardDrawer(board);
        positionToCheck = new ChessPositionImpl(4, 7);
        validMoves = game.validMoves(positionToCheck);
        drawer.drawBoardWhiteHighlighting(positionToCheck, validMoves);
    }
}
