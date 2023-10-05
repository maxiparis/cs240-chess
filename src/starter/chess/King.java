package chess;

import java.util.HashSet;
import java.util.Set;

public class King implements ChessPiece{
    private PieceType pieceType = PieceType.KING;
    private ChessGame.TeamColor teamColor;

    public King(ChessGame.TeamColor teamColor) {
        this.teamColor=teamColor;
    }

    @Override
    public ChessGame.TeamColor getTeamColor() {
        return teamColor;
    }

    @Override
    public PieceType getPieceType() {
        return pieceType;
    }

    @Override
    public Set<ChessMoveImpl> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Set<ChessMoveImpl> possibleMoves = new HashSet<>();

        //emptyboard
        /*
        | | | | | | | | |
		| | | | | | | | |
		| | | | | | | | |
		| | | | | | | | |
		| | | | |x|x|x| |
		| | | | |x|K|x| |
		| | | | |x|x|x| |
		| | | | | | | | |
         */

        /*
        0: =,+
        1: =,-
        2: -,-
        3: -,=
        4: -,+
        5: +,-
        6: +,=
        7: +,+

        check in each case:
            - if is out of table
            - if there as an allied in that position
            - if there is an enemy -> eat it
         */

        int totalPossibleMovements = 8;

        int myRowPosition = myPosition.getRow(); //(3)
        int myColPosition = myPosition.getColumn(); //(6)

        int rowUp = myRowPosition+1; //3+1=4
        int rowDown = myRowPosition-1; //3-1=2
        int colToRight = myColPosition+1; //6+1=7
        int colToLeft = myColPosition-1; //6-1=5


        //more efficient
        for (int row = -1; row < 2; row++) {//each row starting from bottom
            for (int col = -1; col < 2; col++) {//each column, starting from left
                //check if end position would be the same than myPosition
                if(row == 0 && col == 0){
                    continue;
                }

                int rowMove = myRowPosition + row;
                int colMove = myColPosition + col;

                ChessPositionImpl endPosition = new ChessPositionImpl(rowMove, colMove);
                if(!pieceWouldGoOutOfBoard(endPosition)){
                    ChessMoveImpl move = new ChessMoveImpl(myPosition, endPosition);


                    if(board.getPiece(endPosition).getTeamColor() == this.getTeamColor()){ //friend
                        continue; //do not add it to the possible moves
                    } else { //null or enemy
                        possibleMoves.add(move);
                    }


                }
            }
        }


//        for (int i = 0; i < totalPossibleMovements; i++) {
//            switch (i){
//                case 0:
//                    ChessPositionImpl endPosition=new ChessPositionImpl(myRowPosition, colToRight);
//                    ChessMoveImpl sameRight = new ChessMoveImpl(myPosition, endPosition);
//                    if(!pieceWouldGoOutOfBoard(sameRight)){
//                        if(board.getPiece(endPosition).getTeamColor() == this.getTeamColor()){ //friend
//                            break; //do not add it to the possible moves
//                        } else if (board.getPiece(endPosition).getTeamColor() != this.getTeamColor()){ //enemy
//                            possibleMoves.add(sameRight); //what do I do if is an enemy??
//                            break;
//                        } else { //null -> there is nothing in that position
//                            possibleMoves.add(sameRight);
//                            break;
//                        }
//
//                    }
//                    break;
//                case 1:
//                    ChessMoveImpl sameLeft = new ChessMoveImpl(myPosition, new ChessPositionImpl(myRowPosition,colToLeft));
//                    break;
//                case 2:
//                    ChessMoveImpl downLeft = new ChessMoveImpl(myPosition, new ChessPositionImpl(rowDown,colToLeft));
//                    break;
//                case 3:
//                    ChessMoveImpl downSame = new ChessMoveImpl(myPosition, new ChessPositionImpl(rowDown,myColPosition));
//                    break;
//                case 4:
//                    ChessMoveImpl downRight = new ChessMoveImpl(myPosition, new ChessPositionImpl(rowDown,colToRight));
//                    break;
//                case 5:
//                    ChessMoveImpl upLeft = new ChessMoveImpl(myPosition, new ChessPositionImpl(rowUp,colToLeft));
//                    break;
//                case 6:
//                    ChessMoveImpl upSame = new ChessMoveImpl(myPosition, new ChessPositionImpl(rowUp,myColPosition));
//                    break;
//                case 7:
//                    ChessMoveImpl upRight = new ChessMoveImpl(myPosition, new ChessPositionImpl(rowUp,colToRight));
//                    break;
//            }
//        }
        return possibleMoves;
    }

    public boolean pieceWouldGoOutOfBoard(ChessPosition endPosition) {
        if (endPosition.getRow() > 8
            || endPosition.getRow() < 1
            || endPosition.getColumn() > 8
            || endPosition.getColumn() < 1){
            return true;
        }

        return false;
    }
}
