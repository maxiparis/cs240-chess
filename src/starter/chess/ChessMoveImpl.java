package chess;

public class ChessMoveImpl implements ChessMove{
    //This class represents a possible move a chess piece could make. It contains the starting and ending positions.
    // It also contains a field for the type of piece a pawn is being promoted to. If the move would not result in a
    // pawn being promoted, the promotion type field should be null.

    //Members
    private ChessPositionImpl startPosition;
    private ChessPositionImpl endPosition;
    private ChessPiece.PieceType promotionPiece;

    //Constructor
    public ChessMoveImpl(ChessPosition startPosition, ChessPosition endPosition) {
        this.startPosition =(ChessPositionImpl) startPosition;
        this.endPosition =(ChessPositionImpl) endPosition;
        promotionPiece = null;
    }

    public void setPromotionPiece(ChessPiece.PieceType promotionPiece) {
        this.promotionPiece = promotionPiece;
    }

    @Override
    public ChessPosition getStartPosition() {
        return startPosition;
    }

    @Override
    public ChessPosition getEndPosition() {
        return endPosition;
    }

    @Override
    public ChessPiece.PieceType getPromotionPiece() {
        return promotionPiece;
    }
}
