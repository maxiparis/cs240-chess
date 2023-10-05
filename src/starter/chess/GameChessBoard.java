package chess;

public class GameChessBoard implements ChessBoard {
    //members
        //2d array of ChessPieces
    private ChessPiece board[][]; //[row][column]

    //constructor
    public GameChessBoard() {
        resetBoard();
    }

    @Override
    public void addPiece(ChessPosition position, ChessPiece piece) {
        board[position.getRow()][position.getColumn()] = piece;
    }

    @Override
    public ChessPiece getPiece(ChessPosition position) {
        if(board[position.getRow()][position.getColumn()] != null){
            return board[position.getRow()][position.getColumn()];
        }
        return null;
    }

    /**
    |r|n|b|q|k|b|n|r|
    |p|p|p|p|p|p|p|p|
    | | | | | | | | |
    | | | | | | | | |
    | | | | | | | | |
    |P|P|P|P|P|P|P|P|
    |R|N|B|Q|K|B|N|R|
     **/

    @Override
    public void resetBoard() {

    }
}
