package chess;

public class ChessBoardImpl implements ChessBoard {
    //members
        //2d array of ChessPieces
    private ChessPiece boardTable[][]; //[row][column]

    //constructor
    public ChessBoardImpl() {
        boardTable= new ChessPiece[8][8]; //0-7
//        resetBoard();
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
        if(boardTable[position.getRow()-1][position.getColumn()-1] != null){
            return boardTable[position.getRow()-1][position.getColumn()-1];
        }
        return null;
    }

    /**
     * (row, column)
    row
    8       |r|n|b|q|k|b|n|r|
    7       |p|p|p|p|p|p|p|p|
    6       | | | | | | | | |
    5       | | | | | | | | |
    4       | | | | | | | | |
    3       | | | | | | | | |
    2       |P|P|P|P|P|P|P|P|
    1       |R|N|B|Q|K|B|N|R|

     column |1|2|3|4|5|6|7|8|
     **/

    @Override
    public void resetBoard() {
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
}
