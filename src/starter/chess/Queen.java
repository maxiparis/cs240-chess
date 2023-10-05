package chess;

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
        return null;
    }
}
