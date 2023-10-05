package chess;

import java.util.Collection;

public class KingPiece implements ChessPiece{
    private PieceType pieceType = PieceType.KING;
    private ChessGame.TeamColor teamColor;

    public KingPiece(ChessGame.TeamColor teamColor) {
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
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        return null;
    }
}
