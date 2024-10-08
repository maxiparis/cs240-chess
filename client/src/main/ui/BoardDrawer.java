package ui;

import chess.*;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Set;

import static ui.EscapeSequences.*;

public class BoardDrawer {
    private ChessBoardImpl board;
    //define board sizes and stuff
        //example
         private static final int TABLE_ROWS_IN_SQUARES = 8; //only the table
         private static final int TABLE_COLUMNS_IN_SQUARES = 8; //only the table
         private static final int BOARD_HEADERS_COLUMNS_IN_SQUARES = 10; //board plus headers
         private static final String ROOK = " R ";
         private static final String KNIGHT = " N ";
         private static final String KING = " K ";
         private static final String QUEEN = " Q ";
         private static final String BISHOP = " B ";
         private static final String PAWN = " P ";
         private static final String EMPTY = "   ";

    public void drawBoardWhiteHighlighting(ChessPositionImpl positionToCheck, Set<ChessMoveImpl> validMoves) {
        System.out.println();
        PrintStream out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        out.print(ERASE_SCREEN);
        drawHeadersWhite(out);
        drawTableWhiteHighlighting(out, positionToCheck, validMoves);
        drawHeadersWhite(out);
        resetColors(out);
        out.println();
    }

    private void drawTableWhiteHighlighting(PrintStream out, ChessPositionImpl positionToCheck,
                                            Set<ChessMoveImpl> validMoves) {

        for (int row = TABLE_ROWS_IN_SQUARES; row > 0; row--) {
            drawHeaderSquare(out, " " + row + " ");
            ChessPiece[] chessPiecesRow = board.getBoardTable()[row-1]; //if row = 8 then get board.getBoardTable()[7]
            SquareColor firstRowColor =
                    (row == 8 || row == 6 || row == 4 || row == 2) ? SquareColor.LIGHT : SquareColor.DARK;
            drawTableRowHighlighting(out, chessPiecesRow, firstRowColor, row, positionToCheck, validMoves);
            drawHeaderSquare(out, " " + row + " ");
            resetColors(out);
            out.println();
        }
    }

    private void drawTableRowHighlighting(PrintStream out, ChessPiece[] chessPiecesRow, SquareColor firstRowColor,
                                          int rowNumber, ChessPositionImpl positionToCheck,
                                          Set<ChessMoveImpl> validMoves) {

        boolean nextBGColorIsLight = (firstRowColor.equals(SquareColor.LIGHT));

        for (int column = 0; column < TABLE_COLUMNS_IN_SQUARES; column++) {

            final int COLUMN_1BASED = column + 1; //the column number but in 1 based. To be used by ChessPositionImpl

            ChessPositionImpl currentPosition = new ChessPositionImpl(rowNumber, COLUMN_1BASED);

            ChessPiece piece = chessPiecesRow[column];
            SquareColor teamColor = (nextBGColorIsLight) ? SquareColor.LIGHT : SquareColor.DARK;


            boolean highlightPositionToCheck = false;
            boolean highlightValidMove = false;
            //checking if the current position is the position of my position to check
            if(positionToCheck.equals(currentPosition)){
                highlightPositionToCheck = true;
            }

            if(validMoves != null){
                for (ChessMoveImpl validMove : validMoves) {
                    if(validMove.getEndPosition().equals(currentPosition)   ){
                        highlightValidMove = true;
                    }
                }
            }

            if(piece == null){
                drawTableSquareHighlighting(out, EMPTY, ChessGame.TeamColor.WHITE, teamColor, highlightPositionToCheck,
                        highlightValidMove); //team color does not matter
                nextBGColorIsLight = !nextBGColorIsLight; //changing the color
                continue;
            }

            switch (piece.getPieceType()){
                case KING:
                    drawTableSquareHighlighting(out, KING, piece.getTeamColor(), teamColor, highlightPositionToCheck,
                            highlightValidMove);
                    break;
                case QUEEN:
                    drawTableSquareHighlighting(out, QUEEN, piece.getTeamColor(), teamColor, highlightPositionToCheck,
                            highlightValidMove);
                    break;
                case BISHOP:
                    drawTableSquareHighlighting(out, BISHOP, piece.getTeamColor(), teamColor, highlightPositionToCheck,
                            highlightValidMove );
                    break;
                case KNIGHT:
                    drawTableSquareHighlighting(out, KNIGHT, piece.getTeamColor(), teamColor, highlightPositionToCheck,
                            highlightValidMove );
                    break;
                case ROOK:
                    drawTableSquareHighlighting(out, ROOK, piece.getTeamColor(), teamColor, highlightPositionToCheck,
                            highlightValidMove );
                    break;
                case PAWN:
                    drawTableSquareHighlighting(out, PAWN, piece.getTeamColor(), teamColor, highlightPositionToCheck,
                            highlightValidMove );
                    break;
                default:
                    break;
            }
            nextBGColorIsLight = !nextBGColorIsLight; //changing the color
        }

    }

    private void drawTableSquareHighlighting
            (PrintStream out, String toPrint, ChessGame.TeamColor teamColor, SquareColor bgColor,
             boolean highlightPositionToCheck, boolean highlightValidMove) {

        if(highlightPositionToCheck){
            if(teamColor.equals(ChessGame.TeamColor.WHITE)){
                out.print(SET_BG_COLOR_MAGENTA);
            } else { //BLACK
                out.print(SET_BG_COLOR_YELLOW);
//            out.print(SET_TEXT_COLOR_BLACK);
//            out.print(SET_TEXT_BOLD);
//            out.print(toPrint);
//            return;
            }
        } else {
            if (bgColor.equals(SquareColor.LIGHT)) { //bg
                if (highlightValidMove) {
                    out.print(SET_BG_COLOR_GREEN);
                } else {
                    out.print(SET_BG_COLOR_LIGHT_GREEN_RGB);
                }
            } else { //dark
                if (highlightValidMove) {
                    out.print(SET_BG_COLOR_DARK_GREEN);
                } else {
                    out.print(SET_BG_COLOR_DARK_GREEN_RGB);
                }
            }
        }

        if(teamColor.equals(ChessGame.TeamColor.WHITE)){ //fg
            out.print(SET_TEXT_COLOR_WHITE_RGB);
            out.print(SET_TEXT_BOLD);

        } else {
            out.print(SET_TEXT_COLOR_BLACK);
            out.print(SET_TEXT_BOLD);
        }
        out.print(toPrint); //text to print
    }

    public void drawBoardBlackHighlighting(ChessPositionImpl positionToCheck, Set<ChessMoveImpl> validMoves) {
        System.out.println();
        PrintStream out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        out.print(ERASE_SCREEN);
        drawHeadersBlack(out);
        drawTableBlackHighlighting(out, positionToCheck, validMoves);
        drawHeadersBlack(out);
        resetColors(out);
        out.println();
    }

    private void drawTableBlackHighlighting(PrintStream out, ChessPositionImpl positionToCheck, Set<ChessMoveImpl> validMoves) {
        for (int row = 0; row < TABLE_ROWS_IN_SQUARES; row++) {
            drawHeaderSquare(out, " " + (row+1) + " ");
            ChessPiece[] chessPiecesRow = board.getBoardTable()[row]; //if row = 8 then get board.getBoardTable()[7]
            SquareColor firstRowColor =
                    ((row+1) == 8 || (row+1) == 6 || (row+1) == 4 || (row+1) == 2) ? SquareColor.DARK : SquareColor.LIGHT;
            drawTableRowReversedHighlighting(out, chessPiecesRow, firstRowColor, row+1, positionToCheck, validMoves);
            drawHeaderSquare(out, " " + (row+1) + " ");
            resetColors(out);
            out.println();
        }
    }

    private void drawTableRowReversedHighlighting(PrintStream out, ChessPiece[] chessPiecesRow, SquareColor firstRowColor, int rowNumber, ChessPositionImpl positionToCheck, Set<ChessMoveImpl> validMoves) {

        boolean nextBGColorIsLight = (firstRowColor.equals(SquareColor.LIGHT));

        for (int column = TABLE_COLUMNS_IN_SQUARES; column > 0; column--) {

            ChessPositionImpl currentPosition = new ChessPositionImpl(rowNumber, column);

            ChessPiece piece = chessPiecesRow[column-1];
            SquareColor teamColor = (nextBGColorIsLight) ? SquareColor.LIGHT : SquareColor.DARK;


            boolean highlightPositionToCheck = false;
            boolean highlightValidMove = false;
            //checking if the current position is the position of my position to check
            if(positionToCheck.equals(currentPosition)){
                highlightPositionToCheck = true;
            }

            if(validMoves != null){
                for (ChessMoveImpl validMove : validMoves) {
                    if(validMove.getEndPosition().equals(currentPosition)   ){
                        highlightValidMove = true;
                    }
                }
            }


            if(piece == null){
                drawTableSquareHighlighting(out, EMPTY, ChessGame.TeamColor.WHITE, teamColor, highlightPositionToCheck, highlightValidMove); //team color does not matter
                nextBGColorIsLight = !nextBGColorIsLight; //changing the color
                continue;
            }
            switch (piece.getPieceType()){
                case KING:
                    drawTableSquareHighlighting(out, KING, piece.getTeamColor(), teamColor, highlightPositionToCheck, highlightValidMove );
                    break;
                case QUEEN:
                    drawTableSquareHighlighting(out, QUEEN, piece.getTeamColor(), teamColor, highlightPositionToCheck, highlightValidMove );
                    break;
                case BISHOP:
                    drawTableSquareHighlighting(out, BISHOP, piece.getTeamColor(), teamColor, highlightPositionToCheck, highlightValidMove );
                    break;
                case KNIGHT:
                    drawTableSquareHighlighting(out, KNIGHT, piece.getTeamColor(), teamColor, highlightPositionToCheck, highlightValidMove );
                    break;
                case ROOK:
                    drawTableSquareHighlighting(out, ROOK, piece.getTeamColor(), teamColor, highlightPositionToCheck, highlightValidMove );
                    break;
                case PAWN:
                    drawTableSquareHighlighting(out, PAWN, piece.getTeamColor(), teamColor, highlightPositionToCheck, highlightValidMove );
                    break;
                default:
                    break;
            }
            nextBGColorIsLight = !nextBGColorIsLight; //changing the color
        }
    }


    private enum SquareColor{
             LIGHT,
             DARK;
    }
    public BoardDrawer(ChessBoardImpl board) {
        this.board = board;
    }

    public void drawBoardBlack() {
        System.out.println();
        PrintStream out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        out.print(ERASE_SCREEN);
        drawHeadersBlack(out);
        drawTableBlack(out);
        drawHeadersBlack(out);
        resetColors(out);
        out.println();
    }

    public void drawBoardWhite() {
        System.out.println();
        PrintStream out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        out.print(ERASE_SCREEN);
        drawHeadersWhite(out);
        drawTableWhite(out);
        drawHeadersWhite(out);
        resetColors(out);
        out.println();
    }

    private void drawHeadersBlack(PrintStream out) {
        setGrey(out);
        for (int col = 0; col < BOARD_HEADERS_COLUMNS_IN_SQUARES; col++) {
            switch (col){
                case 1:
                    drawHeaderSquare(out, " h ");
                    break;
                case 2:
                    drawHeaderSquare(out, " g ");
                    break;
                case 3:
                    drawHeaderSquare(out, " f ");
                    break;
                case 4:
                    drawHeaderSquare(out, " e ");
                    break;
                case 5:
                    drawHeaderSquare(out, " d ");
                    break;
                case 6:
                    drawHeaderSquare(out, " c ");
                    break;
                case 7:
                    drawHeaderSquare(out, " b ");
                    break;
                case 8:
                    drawHeaderSquare(out, " a ");
                    break;
                default:
                    drawHeaderSquare(out, EMPTY);
                    break;
            }
            if(col == BOARD_HEADERS_COLUMNS_IN_SQUARES-1){
                resetColors(out);
                out.println();
            }
        }
    }

    private void drawHeadersWhite(PrintStream out) {
        setGrey(out);
        for (int col = 0; col < BOARD_HEADERS_COLUMNS_IN_SQUARES; col++) {
               switch (col){
                   case 1:
                       drawHeaderSquare(out, " a ");
                       break;
                   case 2:
                       drawHeaderSquare(out, " b ");
                       break;
                   case 3:
                       drawHeaderSquare(out, " c ");
                       break;
                   case 4:
                       drawHeaderSquare(out, " d ");
                       break;
                   case 5:
                       drawHeaderSquare(out, " e ");
                       break;
                   case 6:
                       drawHeaderSquare(out, " f ");
                       break;
                   case 7:
                       drawHeaderSquare(out, " g ");
                       break;
                   case 8:
                       drawHeaderSquare(out, " h ");
                       break;
                   default:
                       drawHeaderSquare(out, EMPTY);
                       break;
               }
               if(col == BOARD_HEADERS_COLUMNS_IN_SQUARES-1){
                   resetColors(out);
                   out.println();
               }
        }
    }

    private void drawHeaderSquare(PrintStream out, String toPrint) {
        out.print(SET_BG_COLOR_LIGHT_GREY);
        out.print(SET_TEXT_COLOR_BLACK);
        out.print(SET_TEXT_BOLD);
        out.print(toPrint);
    }

    private void drawTableWhite(PrintStream out) {
        for (int row = TABLE_ROWS_IN_SQUARES; row > 0; row--) {
            drawHeaderSquare(out, " " + row + " ");
            ChessPiece[] chessPiecesRow = board.getBoardTable()[row-1]; //if row = 8 then get board.getBoardTable()[7]
            SquareColor firstRowColor =
                    (row == 8 || row == 6 || row == 4 || row == 2) ? SquareColor.LIGHT : SquareColor.DARK;
            drawTableRow(out, chessPiecesRow, firstRowColor);
            drawHeaderSquare(out, " " + row + " ");
            resetColors(out);
            out.println();
        }
    }

    private void drawTableBlack(PrintStream out) {
        for (int row = 0; row < TABLE_ROWS_IN_SQUARES; row++) {
            drawHeaderSquare(out, " " + (row+1) + " ");
            ChessPiece[] chessPiecesRow = board.getBoardTable()[row]; //if row = 8 then get board.getBoardTable()[7]
            SquareColor firstRowColor =
                    ((row+1) == 8 || (row+1) == 6 || (row+1) == 4 || (row+1) == 2) ? SquareColor.DARK : SquareColor.LIGHT;
            drawTableRowReversed(out, chessPiecesRow, firstRowColor);
            drawHeaderSquare(out, " " + (row+1) + " ");
            resetColors(out);
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

    private void drawTableRowReversed(PrintStream out, ChessPiece[] chessPiecesRow, SquareColor firstRowColor) {

        boolean nextBGColorIsLight = (firstRowColor.equals(SquareColor.LIGHT));

        for (int column = TABLE_COLUMNS_IN_SQUARES; column > 0; column--) {
            ChessPiece piece = chessPiecesRow[column-1];
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

    private void drawTableSquare(PrintStream out, String toPrint, ChessGame.TeamColor teamColor, SquareColor bgColor) {
        if (bgColor.equals(SquareColor.LIGHT)) { //bg
            out.print(SET_BG_COLOR_LIGHT_GREEN_RGB);
        } else { //dark
            out.print(SET_BG_COLOR_DARK_GREEN_RGB);
        }

        if(teamColor.equals(ChessGame.TeamColor.WHITE)){ //fg
            out.print(SET_TEXT_COLOR_WHITE_RGB);
            out.print(SET_TEXT_BOLD);
        } else {
            out.print(SET_TEXT_COLOR_BLACK);
            out.print(SET_TEXT_BOLD);
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

    private static void resetColors(PrintStream out) {
        out.print(RESET_BG_COLOR);
        out.print(RESET_TEXT_COLOR);
    }
}
