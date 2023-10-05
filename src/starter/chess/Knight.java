package chess;

import java.util.Set;

public class Knight implements ChessPiece{
    private PieceType pieceType = PieceType.KNIGHT;
    private ChessGame.TeamColor teamColor;

    public Knight(ChessGame.TeamColor teamColor) {
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
        return null;
    }
}
