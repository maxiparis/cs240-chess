package chess;

import java.util.HashSet;
import java.util.Set;

public class Bishop implements ChessPiece{
    private PieceType pieceType = PieceType.BISHOP;
    private ChessGame.TeamColor teamColor;

    public Bishop(ChessGame.TeamColor teamColor) {
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

        possibleMoves.addAll(getsPossibleDiagonalMoves(board, myPosition));

        return possibleMoves;
    }
}
