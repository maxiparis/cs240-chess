package chess;

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

    default ChessPositionImpl toLeftOf(ChessPosition myPosition){
        ChessPositionImpl endDestination = new ChessPositionImpl(myPosition.getRow(), myPosition.getColumn()-1);
        return endDestination;
    }

    default ChessPositionImpl toRightOf(ChessPosition myPosition){
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
}
