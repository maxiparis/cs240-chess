package ui;

import chess.ChessBoard;
import chess.ChessBoardImpl;
import chess.ChessPiece;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import static ui.EscapeSequences.*;

public class BoardDrawer {
    private ChessBoardImpl board;
    //define board sizes and stuff
        //example
         private static final int SQUARE_SIZE_IN_CHAR = 3; //" R "
         private static final int TABLE_ROWS_IN_SQUARES = 8; //only the table
         private static final int TABLE_COLUMNS_IN_SQUARES = 8; //only the table
         private static final int BOARD_HEADERS_ROWS_IN_SQUARES = 10; //board plus headers
         private static final int BOARD_HEADERS_COLUMNS_IN_SQUARES = 10; //board plus headers
         private static final String ROOK = " R ";
         private static final String KNIGHT = " N ";
         private static final String KING = " K ";
         private static final String QUEEN = " Q ";
         private static final String BISHOP = " B ";
         private static final String PAWN = " P ";
         private static final String EMPTY = "   ";



    public BoardDrawer(ChessBoardImpl board) {
        this.board = board;
    }

    public void drawBoardWhite() {
        PrintStream out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        out.print(ERASE_SCREEN);
        drawHeadersWhite(out);
        drawTableWhite(out);
        drawHeadersWhite(out);
    }

    private void drawHeadersWhite(PrintStream out) {
        setGrey(out);

        for (int col = 0; col < BOARD_HEADERS_COLUMNS_IN_SQUARES; col++) {
               switch (col){
                   case 1:
                       printHeaderSquare(out, " a ");
                       break;
                   case 2:
                       printHeaderSquare(out, " b ");
                       break;
                   case 3:
                       printHeaderSquare(out, " c ");
                       break;
                   case 4:
                       printHeaderSquare(out, " d ");
                       break;
                   case 5:
                       printHeaderSquare(out, " e ");
                       break;
                   case 6:
                       printHeaderSquare(out, " f ");
                       break;
                   case 7:
                       printHeaderSquare(out, " g ");
                       break;
                   case 8:
                       printHeaderSquare(out, " h ");
                       break;
                   default:
                       printHeaderSquare(out, EMPTY);
                       break;
               }
               if(col == BOARD_HEADERS_COLUMNS_IN_SQUARES-1){
                   setBlack(out);
                   out.println();
               }
        }
    }

    private void printHeaderSquare(PrintStream out, String toPrint) {
        out.print(SET_BG_COLOR_LIGHT_GREY);
        out.print(SET_TEXT_COLOR_BLACK);
        out.print(toPrint);
    }

    private void drawTableWhite(PrintStream out) {
        for (int row = TABLE_ROWS_IN_SQUARES; row > 0; row--) {
            printHeaderSquare(out, " " + row + " ");
            //ChessPiece[] chessPiecesRow = board.getBoardTable()[row];
            out.println();
        }

    }

    private void setGrey(PrintStream out) {
        out.print(SET_BG_COLOR_LIGHT_GREY);
        out.print(SET_TEXT_COLOR_LIGHT_GREY);
    }


    private void setBlack(PrintStream out) {
        out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_BLACK);
    }
}
