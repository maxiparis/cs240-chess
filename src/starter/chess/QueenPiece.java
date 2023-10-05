package chess;

import java.util.Collection;

public class QueenPiece implements ChessPiece{
    private PieceType pieceType = PieceType.QUEEN;
    private ChessGame.TeamColor teamColor;

    public QueenPiece(ChessGame.TeamColor teamColor) {
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
