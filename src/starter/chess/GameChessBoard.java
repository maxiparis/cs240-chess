package chess;

public class GameChessBoard implements ChessBoard {
    //members
        //2d array of ChessPieces
    private ChessPiece board[][]; //[row][column]

    //constructor
    public GameChessBoard() {
        board = new ChessPiece[8][8]; //0-7
        resetBoard();
    }

    @Override
    public void addPiece(ChessPosition position, ChessPiece piece) {
        board[position.getRow()-1][position.getColumn()-1] = piece;
    }

    @Override
    public ChessPiece getPiece(ChessPosition position) {

        if(board[position.getRow()-1][position.getColumn()-1] != null){
            return board[position.getRow()-1][position.getColumn()-1];
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
        /**1,1 R**/ addPiece(new GameChessPosition(1,1),new RookPiece(ChessGame.TeamColor.WHITE));
        /**1,2 N**/ addPiece(new GameChessPosition(1,2),new KnightPiece(ChessGame.TeamColor.WHITE));
        /**1,3 B**/ addPiece(new GameChessPosition(1,3),new BishopPiece(ChessGame.TeamColor.WHITE));
        /**1,4 Q**/ addPiece(new GameChessPosition(1,4),new QueenPiece(ChessGame.TeamColor.WHITE));
        /**1,5 K**/ addPiece(new GameChessPosition(1,5),new KingPiece(ChessGame.TeamColor.WHITE));
        /**1,6 B**/ addPiece(new GameChessPosition(1,6),new BishopPiece(ChessGame.TeamColor.WHITE));
        /**1,7 N**/ addPiece(new GameChessPosition(1,7),new KnightPiece(ChessGame.TeamColor.WHITE));
        /**1,8 R**/ addPiece(new GameChessPosition(1,8),new RookPiece(ChessGame.TeamColor.WHITE));

        /**2,1-8 P**/
        for (int column = 1; column <= 8; column++) {
            addPiece(new GameChessPosition(2,column),new PawnPiece(ChessGame.TeamColor.WHITE));
        }


        //black
        /**8,1 r**/ addPiece(new GameChessPosition(8,1),new RookPiece(ChessGame.TeamColor.BLACK));
        /**8,2 n**/ addPiece(new GameChessPosition(8,2),new KnightPiece(ChessGame.TeamColor.BLACK));
        /**8,3 b**/ addPiece(new GameChessPosition(8,3),new BishopPiece(ChessGame.TeamColor.BLACK));
        /**8,4 q**/ addPiece(new GameChessPosition(8,4),new QueenPiece(ChessGame.TeamColor.BLACK));
        /**8,5 k**/ addPiece(new GameChessPosition(8,5),new KingPiece(ChessGame.TeamColor.BLACK));
        /**8,6 b**/ addPiece(new GameChessPosition(8,6),new BishopPiece(ChessGame.TeamColor.BLACK));
        /**8,7 n**/ addPiece(new GameChessPosition(8,7),new KnightPiece(ChessGame.TeamColor.BLACK));
        /**8,8 r**/ addPiece(new GameChessPosition(8,8),new RookPiece(ChessGame.TeamColor.BLACK));

        /**7,1-8 P**/
        for (int column = 1; column <= 8; column++) {
            addPiece(new GameChessPosition(7,column),new PawnPiece(ChessGame.TeamColor.BLACK));
        }
    }
}
