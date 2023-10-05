package chess;

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
        return null;
    }
}
