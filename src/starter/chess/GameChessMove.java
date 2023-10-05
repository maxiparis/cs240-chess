package chess;

public class GameChessMove implements ChessMove{
    //This class represents a possible move a chess piece could make. It contains the starting and ending positions.
    // It also contains a field for the type of piece a pawn is being promoted to. If the move would not result in a
    // pawn being promoted, the promotion type field should be null.

    //Members
    private GameChessPosition startPosition;
    private GameChessPosition endPosition;
    private ChessPiece.PieceType promotionPiece;

    //Constructor
    public GameChessMove(GameChessPosition startPosition, GameChessPosition endPosition) {
        this.startPosition = startPosition;
        this.endPosition = endPosition;
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
