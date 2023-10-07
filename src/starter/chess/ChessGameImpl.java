package chess;

import java.util.HashSet;
import java.util.Set;

public class ChessGameImpl implements ChessGame{
    private TeamColor teamTurn;
    private ChessBoard board;

    public ChessGameImpl(TeamColor teamTurn) {
        board = new ChessBoardImpl();
        this.teamTurn=teamTurn;
    }

    @Override
    public TeamColor getTeamTurn() {
        return teamTurn;
    }

    @Override
    public void setTeamTurn(TeamColor team) {
        teamTurn = team;
    }

    @Override
    public Set<ChessMoveImpl> validMoves(ChessPosition startPosition) {
        Set<ChessMoveImpl> validMoves;
        if(board.getPiece(startPosition) == null){
            return null;
        }
        validMoves = board.getPiece(startPosition).pieceMoves(board, startPosition);
        return validMoves;
    }

    @Override
    public void makeMove(ChessMove move) throws InvalidMoveException {
        // Receives a given move and executes it, provided it is a legal move.
        // If the move is illegal, it throws a InvalidMoveException.
        // A move is illegal
            // if the chess piece cannot move there,
            // if the move leaves the team’s king in danger, or
            // if it’s not the corresponding teams turn.


        ChessPosition startPosition = move.getStartPosition();
        ChessPosition endPosition = move.getEndPosition();

        ChessPiece piece = board.getPiece(startPosition);

        if (piece == null) {
            throw new InvalidMoveException("InvalidMoveException: piece in start position is null");
        }

        Set<ChessMoveImpl> pieceValidMoves = piece.pieceMoves(board, startPosition);

        if (piece.getTeamColor() != teamTurn){
            throw new InvalidMoveException("InvalidMoveException: is not the piece turn.");
        } else if (!pieceValidMoves.contains(move)){
            throw new InvalidMoveException("InvalidMoveException: this move is not in validMoves");
        } else if (isInCheck(piece.getTeamColor())){
            throw new InvalidMoveException("InvalidMoveException: would leave the king in check");
        }

//        if (piece.getPieceType() == ChessPiece.PieceType.PAWN){ //promotion
//
//        }

        board.addPiece(endPosition, piece);
        board.removePiece(startPosition);
        changeTurn(piece.getTeamColor());
    }

    private void changeTurn(TeamColor currentTurn) {
        if(currentTurn == TeamColor.WHITE){
            setTeamTurn(TeamColor.BLACK);
        } else {
            setTeamTurn(TeamColor.WHITE);
        }
    }

    @Override
    public boolean isInCheck(TeamColor teamColor) {
        return false;
    }

    @Override
    public boolean isInCheckmate(TeamColor teamColor) {
        return false;
    }

    @Override
    public boolean isInStalemate(TeamColor teamColor) {
        return false;
    }

    @Override
    public void setBoard(ChessBoard board) {
        this.board = board;
    }

    @Override
    public ChessBoard getBoard() {
        return board;
    }
}
