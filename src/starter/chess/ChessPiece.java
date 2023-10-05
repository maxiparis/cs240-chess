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
}
