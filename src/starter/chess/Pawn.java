package chess;

import java.util.HashSet;
import java.util.Set;

public class Pawn implements ChessPiece{
    private PieceType pieceType = PieceType.PAWN;
    private ChessGame.TeamColor teamColor;

    public boolean hasNotMoved(ChessPosition myPosition){
        if(this.teamColor == ChessGame.TeamColor.BLACK && myPosition.getRow() == 7){
            return true;
        } else if (this.teamColor == ChessGame.TeamColor.WHITE && myPosition.getRow() == 2){
            return true;
        }
        return false;
    }

    public Pawn(ChessGame.TeamColor teamColor) {
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

        int myPositionColumn = myPosition.getColumn();
        int forwardMove;
        int twoForwardMoves;

        if (this.teamColor == ChessGame.TeamColor.WHITE){
            forwardMove = myPosition.getRow() + 1;
            twoForwardMoves = myPosition.getRow() + 2;
        } else {
            forwardMove = myPosition.getRow() - 1;
            twoForwardMoves = myPosition.getRow() - 2;
        }


        ChessPositionImpl endPosition = new ChessPositionImpl(forwardMove, myPositionColumn);
        ChessMoveImpl move;

        if (board.getPiece(endPosition) == null && !oneMoveAwayFromEdge(myPosition)){
            //one forward
            move = new ChessMoveImpl(myPosition, endPosition);
            possibleMoves.add(move);

            //two forward
            endPosition = new ChessPositionImpl(twoForwardMoves, myPositionColumn);
            if(this.hasNotMoved(myPosition) && board.getPiece(endPosition) == null){ //there is no one in destination and I have not moved
                move = new ChessMoveImpl(myPosition, endPosition);
                possibleMoves.add(move);
            }
        }

        //CAPTURE
            //create 2 positions -> diagonal left and right and check if there is an enemy in those positions
                //if there is an enemy then take that guy and add that movement to the possibleMoves list.

        int columnToLeft = myPositionColumn - 1;
        int columnToRight = myPositionColumn + 1;

        ChessPositionImpl diagonalLeft = new ChessPositionImpl(forwardMove, columnToLeft);
        ChessPositionImpl diagonalRight = new ChessPositionImpl(forwardMove, columnToRight);

        if (thereIsAnEnemyIn(board, diagonalLeft)){
            if(!oneMoveAwayFromEdge(myPosition)) {
                move=new ChessMoveImpl(myPosition, diagonalLeft);
                possibleMoves.add(move);
            } else {
                move = new ChessMoveImpl(myPosition, diagonalLeft, PieceType.QUEEN);
                possibleMoves.add(move);
                move = new ChessMoveImpl(myPosition, diagonalLeft, PieceType.BISHOP);
                possibleMoves.add(move);
                move = new ChessMoveImpl(myPosition, diagonalLeft, PieceType.ROOK);
                possibleMoves.add(move);
                move = new ChessMoveImpl(myPosition, diagonalLeft, PieceType.KNIGHT);
                possibleMoves.add(move);
            }
        }

        if (thereIsAnEnemyIn(board, diagonalRight)){
            if(!oneMoveAwayFromEdge(myPosition)) {
                move = new ChessMoveImpl(myPosition, diagonalRight);
                possibleMoves.add(move);
            } else {
                move = new ChessMoveImpl(myPosition, diagonalRight, PieceType.QUEEN);
                possibleMoves.add(move);
                move = new ChessMoveImpl(myPosition, diagonalRight, PieceType.BISHOP);
                possibleMoves.add(move);
                move = new ChessMoveImpl(myPosition, diagonalRight, PieceType.ROOK);
                possibleMoves.add(move);
                move = new ChessMoveImpl(myPosition, diagonalRight, PieceType.KNIGHT);
                possibleMoves.add(move);
            }
        }


        //PROMOTE
            //if the piece is one forward away from the edge
                //if there is nothing in that position
                    //add that move
                //if there is an enemy in that position
                    //caputre
                //if there is an ally
                    //do not move
        endPosition = new ChessPositionImpl(forwardMove, myPositionColumn);
        if (oneMoveAwayFromEdge(myPosition)) {
            if (!thereIsAnAllyIn(board, endPosition)){ //the position is null or enemy
                move = new ChessMoveImpl(myPosition, endPosition, PieceType.QUEEN);
                possibleMoves.add(move);
                move = new ChessMoveImpl(myPosition, endPosition, PieceType.BISHOP);
                possibleMoves.add(move);
                move = new ChessMoveImpl(myPosition, endPosition, PieceType.ROOK);
                possibleMoves.add(move);
                move = new ChessMoveImpl(myPosition, endPosition, PieceType.KNIGHT);
                possibleMoves.add(move);
            }
        }

        return possibleMoves;
    }

    private boolean oneMoveAwayFromEdge(ChessPosition myPosition) {
        if(this.getTeamColor() == ChessGame.TeamColor.WHITE && myPosition.getRow() == 7){
            return true;
        } else if (this.getTeamColor() == ChessGame.TeamColor.BLACK && myPosition.getRow() == 2){
            return true;
        }
        return false;
    }




}
