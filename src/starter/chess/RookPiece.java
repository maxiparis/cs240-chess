package chess;

import java.util.Collection;

public class RookPiece implements ChessPiece{
    private PieceType pieceType = PieceType.ROOK;
    private ChessGame.TeamColor teamColor;

    public RookPiece(ChessGame.TeamColor teamColor) {
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
