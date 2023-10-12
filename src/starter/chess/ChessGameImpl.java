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
        Set<ChessMoveImpl> validMovesBeforeCheckFilter;

        ChessPiece piece = board.getPiece(startPosition);
        if(piece == null){
            return null;
        }
        validMovesBeforeCheckFilter = piece.pieceMoves(board, startPosition);

        //filter here
        Set<ChessMoveImpl> validMovesAfterCheckFilter = new HashSet<>();
        for (ChessMoveImpl move : validMovesBeforeCheckFilter){
            if (!wouldBeCheck(move, piece.getTeamColor())){
                validMovesAfterCheckFilter.add(move);
            }
        }



        return validMovesAfterCheckFilter;
    }

    public boolean wouldBeCheck(ChessMoveImpl move, TeamColor teamColor) {

        ChessPiece originalPiece = getBoard().getPiece(move.getStartPosition());
        ChessPiece pieceToBeReplaced;


        //make the move in testGame.board
        //****************
        if (originalPiece.getPieceType() == ChessPiece.PieceType.PAWN && move.getPromotionPiece() != null){ //promotion
            ChessPiece promotionPiece = null;
            switch (move.getPromotionPiece()){
                case QUEEN:
                    promotionPiece = new Queen(originalPiece.getTeamColor());
                    break;
                case BISHOP:
                    promotionPiece = new Bishop(originalPiece.getTeamColor());
                    break;
                case ROOK:
                    promotionPiece = new Rook(originalPiece.getTeamColor());
                    break;
                case KNIGHT:
                    promotionPiece = new Knight(originalPiece.getTeamColor());
                    break;
                default:
                    break;
            }
            pieceToBeReplaced = board.getPiece(move.getEndPosition());
            getBoard().addPiece(move.getEndPosition(), promotionPiece);
        } else {
            pieceToBeReplaced = board.getPiece(move.getEndPosition());
            getBoard().addPiece(move.getEndPosition(), originalPiece);
        }

        getBoard().removePiece(move.getStartPosition());
        //****************


        boolean wouldBeCheck = isInCheck(teamColor);

        // put pieces back
        getBoard().addPiece(move.getStartPosition(), originalPiece);
        getBoard().addPiece(move.getEndPosition(), pieceToBeReplaced); //adding the piece back in case there was something there

        return wouldBeCheck;
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

//        Set<ChessMoveImpl> pieceValidMoves = piece.pieceMoves(board, startPosition);
        Set<ChessMoveImpl> pieceValidMoves = validMoves(move.getStartPosition());

        if (piece.getTeamColor() != teamTurn){
            throw new InvalidMoveException("InvalidMoveException: is not the piece turn.");
        } else if (!pieceValidMoves.contains(move)) {
            throw new InvalidMoveException("InvalidMoveException: this move is not in validMoves");
        }

        if (piece.getPieceType() == ChessPiece.PieceType.PAWN && move.getPromotionPiece() != null){ //promotion
            ChessPiece promotionPiece= null;
            switch (move.getPromotionPiece()){
                case QUEEN:
                    promotionPiece = new Queen(piece.getTeamColor());
                    break;
                case BISHOP:
                    promotionPiece = new Bishop(piece.getTeamColor());
                    break;
                case ROOK:
                    promotionPiece = new Rook(piece.getTeamColor());
                    break;
                case KNIGHT:
                    promotionPiece = new Knight(piece.getTeamColor());
                    break;
                default:
                    break;
            }
            board.addPiece(endPosition, promotionPiece);
        } else {
            board.addPiece(endPosition, piece);
        }

        board.removePiece(startPosition);
        changeTurn(piece.getTeamColor());
    }

    public void changeTurn(TeamColor currentTurn) {
        if(currentTurn == TeamColor.WHITE){
            setTeamTurn(TeamColor.BLACK);
        } else {
            setTeamTurn(TeamColor.WHITE);
        }
    }

    @Override
    public boolean isInCheck(TeamColor teamColor) {
//        	○ Make a set with each possible move of each enemy piece on the board.
//  	    ○ If there is at least one move that would end in my kings position
//		        ○ Return true
//          Return false
        TeamColor enemyTeamColor = getOppositeTeamColor(teamColor);

        Set<ChessMove> enemyMoves = board.getMovesOfTeamColor(enemyTeamColor);
        //do I need all the moves or just the moves.endPosition??
        ChessPositionImpl myKingPosition = board.getKingPosition(teamColor);
        for (ChessMove move : enemyMoves) {
            if (move.getEndPosition().equals(myKingPosition)){
                return true;
            }
        }



        return false;
    }

    public static TeamColor getOppositeTeamColor(TeamColor teamColor) {
        TeamColor enemyTeamColor;
        if (teamColor == TeamColor.WHITE){
            enemyTeamColor = TeamColor.BLACK;
        } else {
            enemyTeamColor = TeamColor.WHITE;
        }
        return enemyTeamColor;
    }

    @Override
    public boolean isInCheckmate(TeamColor teamColor) {
        boolean inCheck = isInCheck(teamColor);

        boolean teamHasLegalMoves = teamHasLegalMove(teamColor);


        if(inCheck && !teamHasLegalMoves){
            return true;
        }
        return false;
    }

    public boolean teamHasLegalMove(TeamColor teamColor) {
        for (int row=1; row <= 8; row++) {
            for (int col=1; col <= 8; col++) {
                ChessPiece chessPiece = board.getPiece(new ChessPositionImpl(row,col));
                if (chessPiece != null){
                    if (chessPiece.getTeamColor() == teamColor){
                        ChessPositionImpl piecePosition = new ChessPositionImpl(row, col);
                        Set<ChessMoveImpl> pieceValidMoves = validMoves(piecePosition);
                        if (!pieceValidMoves.isEmpty()){
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public boolean isInStalemate(TeamColor teamColor) {
        boolean inCheck=isInCheck(teamColor);
        boolean teamHasLegalMoves = false;

        //for each of my pieces
            //if they have valid moves
                //set variable to teamHasLegalMoves
                //break

        teamHasLegalMoves = teamHasLegalMove(teamColor);


        if(!inCheck && !teamHasLegalMoves){
            return true;
        }
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
