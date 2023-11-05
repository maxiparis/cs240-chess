package chess;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ChessBoardImpl implements ChessBoard {
    //members
        //2d array of ChessPieces
    private ChessPiece boardTable[][]; //[row][column]

    //constructor
    public ChessBoardImpl() {
        boardTable = new ChessPiece[8][8]; //0-7
//        resetBoard();
    }

    public void setBoardTable(ChessPiece[][] boardTable) {
        this.boardTable = boardTable;
    }

    public ChessPiece[][] getBoardTable() {
        return boardTable;
    }

    @Override
    public void addPiece(ChessPosition position, ChessPiece piece) {
        boardTable[position.getRow()-1][position.getColumn()-1] = piece;
    }

    @Override
    public ChessPiece getPiece(ChessPosition position) {
        //start fix
        if ((position.getRow() <= 0) || (position.getColumn() <= 0) || (position.getRow() >= 9) || (position.getColumn() >= 9)){
            return null;
        }
        //end fix
        if(boardTable[position.getRow()-1][position.getColumn()-1] != null){
            return boardTable[position.getRow()-1][position.getColumn()-1];
        }
        return null;
    }


    @Override
    public void resetBoard() {
        clearBoard();
        //WHITE
        /**1,1 R**/ addPiece(new ChessPositionImpl(1,1),new Rook(ChessGame.TeamColor.WHITE));
        /**1,2 N**/ addPiece(new ChessPositionImpl(1,2),new Knight(ChessGame.TeamColor.WHITE));
        /**1,3 B**/ addPiece(new ChessPositionImpl(1,3),new Bishop(ChessGame.TeamColor.WHITE));
        /**1,4 Q**/ addPiece(new ChessPositionImpl(1,4),new Queen(ChessGame.TeamColor.WHITE));
        /**1,5 K**/ addPiece(new ChessPositionImpl(1,5),new King(ChessGame.TeamColor.WHITE));
        /**1,6 B**/ addPiece(new ChessPositionImpl(1,6),new Bishop(ChessGame.TeamColor.WHITE));
        /**1,7 N**/ addPiece(new ChessPositionImpl(1,7),new Knight(ChessGame.TeamColor.WHITE));
        /**1,8 R**/ addPiece(new ChessPositionImpl(1,8),new Rook(ChessGame.TeamColor.WHITE));

        /**2,1-8 P**/
        for (int column = 1; column <= 8; column++) {
            addPiece(new ChessPositionImpl(2,column),new Pawn(ChessGame.TeamColor.WHITE));
        }


        //black
        /**8,1 r**/ addPiece(new ChessPositionImpl(8,1),new Rook(ChessGame.TeamColor.BLACK));
        /**8,2 n**/ addPiece(new ChessPositionImpl(8,2),new Knight(ChessGame.TeamColor.BLACK));
        /**8,3 b**/ addPiece(new ChessPositionImpl(8,3),new Bishop(ChessGame.TeamColor.BLACK));
        /**8,4 q**/ addPiece(new ChessPositionImpl(8,4),new Queen(ChessGame.TeamColor.BLACK));
        /**8,5 k**/ addPiece(new ChessPositionImpl(8,5),new King(ChessGame.TeamColor.BLACK));
        /**8,6 b**/ addPiece(new ChessPositionImpl(8,6),new Bishop(ChessGame.TeamColor.BLACK));
        /**8,7 n**/ addPiece(new ChessPositionImpl(8,7),new Knight(ChessGame.TeamColor.BLACK));
        /**8,8 r**/ addPiece(new ChessPositionImpl(8,8),new Rook(ChessGame.TeamColor.BLACK));

        /**7,1-8 P**/
        for (int column = 1; column <= 8; column++) {
            addPiece(new ChessPositionImpl(7,column),new Pawn(ChessGame.TeamColor.BLACK));
        }
    }

    private void clearBoard() {
        boardTable = new ChessPiece[8][8];
    }

    @Override
    public void removePiece(ChessPosition position) {
        boardTable[position.getRow()-1][position.getColumn()-1] = null;
    }

    /**
     * Creates a set with all the moves the teamColor could do.
     * Traverse through all the board
     *
     * @param teamColor
     * @return a set with all the moves that the teamColor could do
     */
    @Override
    public Set<ChessMove> getMovesOfTeamColor(ChessGame.TeamColor teamColor) {
        //set<ChessMove> toReturn;
        //for i 0 ... 8
            //for j 0...8
                //if the piece is not null & is the teamColor
                    //piecePosition = new ChessPosition(row = i+1, col= j+1)
                    //toReturn.addAll(piece.pieceMoves(ChessBoard board = this.board, ChessPosition piecePosition));

        Set<ChessMove> movesOfTeam = new HashSet<>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessPiece chessPiece = boardTable[i][j];
                if (chessPiece != null && chessPiece.getTeamColor() == teamColor){
                    ChessPositionImpl piecePosition = new ChessPositionImpl(i+1, j+1);
                    movesOfTeam.addAll(chessPiece.pieceMoves(this, piecePosition));
                }
            }
        }


        return movesOfTeam;
    }

    @Override
    public ChessPositionImpl getKingPosition(ChessGame.TeamColor teamColor) {
        for (int row=1; row <= 8; row++) {
            for (int col=1; col <= 8; col++) {
                ChessPiece chessPiece = getPiece(new ChessPositionImpl(row,col));
                if (chessPiece != null){
                    if (chessPiece.getPieceType() == ChessPiece.PieceType.KING
                            && chessPiece.getTeamColor() == teamColor){
                        return new ChessPositionImpl(row, col); //King's team position
                    }
                }
            }
        }
        return null;
    }

    @Override
    public String toString() {
//      | | | | | | |q| |
//		| | | | | | | | |
//		| | |n| | | |p| |
//		| | | | | | | | |
//		| | | | | | | | |
//		| | | | | | | | |
//		| | |B| | | | | |
//		|K| | | | | | |R|
        StringBuilder toReturn = new StringBuilder();
        for (int row=7; row >= 0; row--) {
            for (int col=0; col < 8; col++) {
                String s = "";
                if (col == 0){
                    toReturn.append((row+1)+ " |");
                }
                ChessPiece piece = boardTable[row][col];
                if (piece == null){
                    toReturn.append(" |");
                    continue;
                } else if (piece.getPieceType() == ChessPiece.PieceType.PAWN){
                    s = "p";
                } else if (piece.getPieceType() == ChessPiece.PieceType.BISHOP) {
                    s ="b";
                } else if (piece.getPieceType() == ChessPiece.PieceType.ROOK) {
                    s ="r";
                } else if (piece.getPieceType() == ChessPiece.PieceType.KNIGHT) {
                    s ="n";
                } else if (piece.getPieceType() == ChessPiece.PieceType.KING) {
                    s ="k";
                } else if (piece.getPieceType() == ChessPiece.PieceType.QUEEN) {
                    s ="q";
                }

                if (piece.getTeamColor() == ChessGame.TeamColor.WHITE) {
                    toReturn.append(s.toUpperCase() + "|");
                } else {
                    toReturn.append(s + "|");
                }

            }
            toReturn.append("\n");
        }
        toReturn.append("   1 2 3 4 5 6 7 8 ");

        return toReturn.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessBoardImpl that=(ChessBoardImpl) o;
        boolean BoardsAreEqual = boardsAreEqual(this.getBoardTable(), that.getBoardTable());
        return BoardsAreEqual;
    }

    private boolean boardsAreEqual(ChessPiece[][] board1, ChessPiece[][] board2) {
        for (int row = 0; row < 8; row++) {
          for (int column = 0; column < 8; column++) {
              ChessPiece board1Piece = board1[row][column];
              ChessPiece board2Piece = board2[row][column];
              if(board1Piece == null && board2Piece != null){
                  return false;
              } else if (board2Piece == null && board1Piece != null){
                  return false;
              }
              if (board1Piece != null) {
                  if(!board1Piece.equals(board2Piece)){
                    return false;
                  }
              }
          }
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(boardTable);
    }
}
