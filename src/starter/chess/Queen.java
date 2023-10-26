package chess;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Queen implements ChessPiece{
    private PieceType pieceType = PieceType.QUEEN;
    private ChessGame.TeamColor teamColor;

    public Queen(ChessGame.TeamColor teamColor) {
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

        possibleMoves.addAll(getsPossibleLineMoves(board, myPosition));
        possibleMoves.addAll(getsPossibleDiagonalMoves(board, myPosition));

        return possibleMoves;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Queen queen=(Queen) o;
        return pieceType == queen.pieceType && teamColor == queen.teamColor;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieceType, teamColor);
    }
}
