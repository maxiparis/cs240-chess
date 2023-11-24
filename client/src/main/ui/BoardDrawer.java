package ui;

import chess.*;

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

         private enum SquareColor{
             LIGHT,
             DARK
         }

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
            ChessPiece[] chessPiecesRow = board.getBoardTable()[row-1]; //if row = 8 then get board.getBoardTable()[7]
            SquareColor firstRowColor =
                    (row == 8 || row == 6 || row == 4 || row == 2 || row == 8) ? SquareColor.LIGHT : SquareColor.DARK;
            drawTableRow(out, chessPiecesRow, firstRowColor);
            printHeaderSquare(out, " " + row + " ");
            setBlack(out);
            out.println();
        }
    }

    private void drawTableRow(PrintStream out, ChessPiece[] chessPiecesRow, SquareColor firstRowColor) {

        boolean nextBGColorIsLight = (firstRowColor.equals(SquareColor.LIGHT));

        for (int column = 0; column < TABLE_COLUMNS_IN_SQUARES; column++) {
            ChessPiece piece = chessPiecesRow[column];
                SquareColor teamColor = (nextBGColorIsLight) ? SquareColor.LIGHT : SquareColor.DARK;

            if(piece == null){
                drawTableSquare(out, EMPTY, ChessGame.TeamColor.WHITE, teamColor ); //team color does not matter
                nextBGColorIsLight = !nextBGColorIsLight; //changing the color
                continue;
            }
            switch (piece.getPieceType()){
                case KING:
                    drawTableSquare(out, KING, piece.getTeamColor(), teamColor );
                    break;
                case QUEEN:
                    drawTableSquare(out, QUEEN, piece.getTeamColor(), teamColor );
                    break;
                case BISHOP:
                    drawTableSquare(out, BISHOP, piece.getTeamColor(), teamColor );
                    break;
                case KNIGHT:
                    drawTableSquare(out, KNIGHT, piece.getTeamColor(), teamColor );
                    break;
                case ROOK:
                    drawTableSquare(out, ROOK, piece.getTeamColor(), teamColor );
                    break;
                case PAWN:
                    drawTableSquare(out, PAWN, piece.getTeamColor(), teamColor );
                    break;
                default:
                    break;
            }
            nextBGColorIsLight = !nextBGColorIsLight; //changing the color
        }
    }

    private void drawDarkSquare(PrintStream out, String toPrint, ChessGame.TeamColor teamColor) {
        out.print(SET_BG_COLOR_DARK_GREEN); //bg
        if(teamColor.equals(ChessGame.TeamColor.WHITE)){
            out.print(SET_TEXT_COLOR_WHITE); //foreground
        } else {
            out.print(SET_TEXT_COLOR_BLACK); //foreground
        }
        out.print(toPrint); //text to print
    }

    private void drawTableSquare(PrintStream out, String toPrint, ChessGame.TeamColor teamColor, SquareColor bgColor) {
        if (bgColor.equals(SquareColor.LIGHT)) { //bg
            out.print(SET_BG_COLOR_GREEN);
        } else { //dark
            out.print(SET_BG_COLOR_DARK_GREEN);
        }

        if(teamColor.equals(ChessGame.TeamColor.WHITE)){ //fg
            out.print(SET_TEXT_COLOR_WHITE);
        } else {
            out.print(SET_TEXT_COLOR_BLACK);
        }
        out.print(toPrint); //text to print
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
