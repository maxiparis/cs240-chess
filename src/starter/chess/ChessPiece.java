package chess;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents a single chess piece
 */
public interface ChessPiece {

    /**
     * The various different chess piece options
     */
    enum PieceType{
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    ChessGame.TeamColor getTeamColor();

    /**
     * @return which type of chess piece this piece is
     */
    PieceType getPieceType();

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in danger
     * <p>
     * <p>
     * it does not check the turn or Check (king safety) constraints.
     * This method does account for enemy and friendly pieces blocking movement paths.
     *
     * @return Collection of valid moves
     */
    Set<ChessMoveImpl> pieceMoves(ChessBoard board, ChessPosition myPosition);


    default boolean thereIsAnEnemyIn(ChessBoard board, ChessPosition positionToCheck) {
        //if the position at board is not null and is not my dataType -> (is my enemy)
        //return true

        if (board.getPiece(positionToCheck) != null
                && board.getPiece(positionToCheck).getTeamColor() != this.getTeamColor()){
            return true;
        }

        return false;
    }

    default boolean thereIsAnAllyIn(ChessBoard board, ChessPosition positionToCheck) {
        //if the position at board is not null and is not my dataType -> (is my enemy)
        //return true

        if (board.getPiece(positionToCheck) != null
                && board.getPiece(positionToCheck).getTeamColor() == this.getTeamColor()){
            return true;
        }

        return false;
    }

    default boolean thereIsANullIn(ChessBoard board, ChessPosition positionToCheck) {
        if (board.getPiece(positionToCheck) == null){
            return true;
        }

        return false;
    }

    default boolean moveWouldGoOutOfBoard(ChessPosition endPosition) {
        if (endPosition.getRow() > 8
                || endPosition.getRow() < 1
                || endPosition.getColumn() > 8
                || endPosition.getColumn() < 1){
            return true;
        }

        return false;
    }

    default ChessPositionImpl goLeftOf(ChessPosition myPosition){
        ChessPositionImpl endDestination = new ChessPositionImpl(myPosition.getRow(), myPosition.getColumn()-1);
        return endDestination;
    }

    default ChessPositionImpl goRightOf(ChessPosition myPosition){
        ChessPositionImpl endDestination = new ChessPositionImpl(myPosition.getRow(), myPosition.getColumn()+1);
        return endDestination;
    }

    default ChessPositionImpl goUpOf(ChessPosition myPosition){
        ChessPositionImpl endDestination = new ChessPositionImpl(myPosition.getRow()+1, myPosition.getColumn());
        return endDestination;
    }

    default ChessPositionImpl goDownOf(ChessPosition myPosition){
        ChessPositionImpl endDestination = new ChessPositionImpl(myPosition.getRow()-1, myPosition.getColumn());
        return endDestination;
    }




//    - goUpRightOf
    default ChessPositionImpl goUpRightOf(ChessPosition myPosition){
        ChessPositionImpl endDestination = goUpOf(myPosition);
        endDestination = goRightOf(endDestination);
        return endDestination;
    }
//    - goUpLeftOf
    default ChessPositionImpl goUpLeftOf(ChessPosition myPosition){
        ChessPositionImpl endDestination = goUpOf(myPosition);
        endDestination = goLeftOf(endDestination);
        return endDestination;
    }
//    - goDownRightOf
    default ChessPositionImpl goDownRightOf(ChessPosition myPosition){
        ChessPositionImpl endDestination = goDownOf(myPosition);
        endDestination = goRightOf(endDestination);
        return endDestination;
    }
//    goDownLeftOf
    default ChessPositionImpl goDownLeftOf(ChessPosition myPosition){
        ChessPositionImpl endDestination = goDownOf(myPosition);
        endDestination = goLeftOf(endDestination);
        return endDestination;
    }



    /**
     * Calculates all the possible moves for a specific position, going up, down, left or
     * right until the edge or an ally.
     *
     * Made for rook movements.
     *
     * @param board board to use and calculate different positions
     * @param myPosition position from where we will calculate the moves
     * @return a set with all the possible moves
     */
    default Set<ChessMoveImpl> getsPossibleLineMoves(ChessBoard board, ChessPosition myPosition) {
        Set<ChessMoveImpl> possibleMoves = new HashSet<>();
        ChessPositionImpl right = goRightOf(myPosition);
        ChessMoveImpl move;

        while(!moveWouldGoOutOfBoard(right) && !thereIsAnAllyIn(board, right)){
            move = new ChessMoveImpl(myPosition, right);
            possibleMoves.add(move);
            if (thereIsAnEnemyIn(board, right)){
                break;
            }
            right = goRightOf(right);
        }

        //LEFT
        ChessPositionImpl left = goLeftOf(myPosition);

        while(!moveWouldGoOutOfBoard(left) && !thereIsAnAllyIn(board, left)){
            move = new ChessMoveImpl(myPosition, left);
            possibleMoves.add(move);
            if (thereIsAnEnemyIn(board, left)){
                break;
            }
            left = goLeftOf(left);
        }

        //UP
        ChessPositionImpl up = goUpOf(myPosition);

        while(!moveWouldGoOutOfBoard(up) && !thereIsAnAllyIn(board, up)){
            move = new ChessMoveImpl(myPosition, up);
            possibleMoves.add(move);
            if (thereIsAnEnemyIn(board, up)){
                break;
            }
            up = goUpOf(up);
        }

        //DOWN

        ChessPositionImpl down = goDownOf(myPosition);

        while(!moveWouldGoOutOfBoard(down) && !thereIsAnAllyIn(board, down)){
            move = new ChessMoveImpl(myPosition, down);
            possibleMoves.add(move);
            if (thereIsAnEnemyIn(board, down)){
                break;
            }
            down = goDownOf(down);
        }
        return possibleMoves;
    }


    default Set<ChessMoveImpl> getsPossibleDiagonalMoves(ChessBoard board, ChessPosition myPosition) {
        //UP-RIGHT
        Set<ChessMoveImpl> possibleMoves = new HashSet<>();
        ChessPositionImpl upRight = goUpRightOf(myPosition);
        ChessMoveImpl move;

        while(!moveWouldGoOutOfBoard(upRight) && !thereIsAnAllyIn(board, upRight)){
            move = new ChessMoveImpl(myPosition, upRight);
            possibleMoves.add(move);
            if (thereIsAnEnemyIn(board, upRight)){
                break;
            }
            upRight = goUpRightOf(upRight);
        }

        //UP-LEFT
        ChessPositionImpl upLeft = goUpLeftOf(myPosition);

        while(!moveWouldGoOutOfBoard(upLeft) && !thereIsAnAllyIn(board, upLeft)){
            move = new ChessMoveImpl(myPosition, upLeft);
            possibleMoves.add(move);
            if (thereIsAnEnemyIn(board, upLeft)){
                break;
            }
            upLeft = goUpLeftOf(upLeft);
        }

        //DOWN-RIGHT
        ChessPositionImpl downRight= goDownRightOf(myPosition);

        while(!moveWouldGoOutOfBoard(downRight) && !thereIsAnAllyIn(board, downRight)){
            move = new ChessMoveImpl(myPosition, downRight);
            possibleMoves.add(move);
            if (thereIsAnEnemyIn(board, downRight)){
                break;
            }
            downRight = goDownRightOf(downRight);
        }

        //DOWN-LEFT
        ChessPositionImpl downLeft = goDownLeftOf(myPosition);

        while(!moveWouldGoOutOfBoard(downLeft) && !thereIsAnAllyIn(board, downLeft)){
            move = new ChessMoveImpl(myPosition, downLeft);
            possibleMoves.add(move);
            if (thereIsAnEnemyIn(board, downLeft)){
                break;
            }
            downLeft = goDownLeftOf(downLeft);
        }
        return possibleMoves;
    }
}
