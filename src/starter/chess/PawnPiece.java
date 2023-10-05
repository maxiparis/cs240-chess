package chess;

import java.util.Collection;

public class PawnPiece implements ChessPiece{
    private PieceType pieceType = PieceType.PAWN;
    private ChessGame.TeamColor teamColor;

    public PawnPiece(ChessGame.TeamColor teamColor) {
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
