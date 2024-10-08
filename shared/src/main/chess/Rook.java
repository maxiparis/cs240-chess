package chess;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Rook implements ChessPiece{
    private PieceType pieceType = PieceType.ROOK;
    private ChessGame.TeamColor teamColor;

    public Rook(ChessGame.TeamColor teamColor) {
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

        return possibleMoves;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rook rook=(Rook) o;
        return pieceType == rook.pieceType && teamColor == rook.teamColor;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieceType, teamColor);
    }
}
