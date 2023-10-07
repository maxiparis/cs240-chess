package chess;

import java.util.HashSet;
import java.util.Set;

public class Knight implements ChessPiece{
    private PieceType pieceType = PieceType.KNIGHT;
    private ChessGame.TeamColor teamColor;

    public Knight(ChessGame.TeamColor teamColor) {
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
        Set<ChessMoveImpl> possibleMoves = new HashSet<>();
        /*
        | | | | | | | | |
		| | | | | | | | |
		| | | | | | | | |
		| | | | |n| | | |
		| | |N| | | | | |
		| | | |P| |R| | |
		| | | | | | | | |
		| | | | | | | | |
         */
        ChessMoveImpl move;
        ChessPositionImpl endPosition;


        //UP-UP-LEFT
        endPosition = goUpOf(goUpLeftOf(myPosition));
        if(!moveWouldGoOutOfBoard(endPosition)){
            if(!thereIsAnAllyIn(board, endPosition)){
                move=new ChessMoveImpl(myPosition, endPosition);
                possibleMoves.add(move);
            }
        }


        //UP-UP-RIGHT
        endPosition = goUpOf(goUpRightOf(myPosition));
        if(!moveWouldGoOutOfBoard(endPosition)){
            if(!thereIsAnAllyIn(board, endPosition)){
                move=new ChessMoveImpl(myPosition, endPosition);
                possibleMoves.add(move);
            }
        }

        //DOWN-DOWN-LEFT
        endPosition = goDownOf(goDownLeftOf(myPosition));
        if(!moveWouldGoOutOfBoard(endPosition)){
            if(!thereIsAnAllyIn(board, endPosition)){
                move=new ChessMoveImpl(myPosition, endPosition);
                possibleMoves.add(move);
            }
        }


        //DOWN-DOWN-RIGHT
        endPosition = goDownOf(goDownRightOf(myPosition));
        if(!moveWouldGoOutOfBoard(endPosition)){
            if(!thereIsAnAllyIn(board, endPosition)){
                move=new ChessMoveImpl(myPosition, endPosition);
                possibleMoves.add(move);
            }
        }

        //RIGHT-RIGHT-UP
        endPosition = goRightOf(goUpRightOf(myPosition));
        if(!moveWouldGoOutOfBoard(endPosition)){
            if(!thereIsAnAllyIn(board, endPosition)){
                move=new ChessMoveImpl(myPosition, endPosition);
                possibleMoves.add(move);
            }
        }

        //RIGHT-RIGHT-DOWN
        endPosition = goRightOf(goDownRightOf(myPosition));
        if(!moveWouldGoOutOfBoard(endPosition)){
            if(!thereIsAnAllyIn(board, endPosition)){
                move=new ChessMoveImpl(myPosition, endPosition);
                possibleMoves.add(move);
            }
        }


        //LEFT-LEFT-UP
        endPosition = goLeftOf(goUpLeftOf(myPosition));
        if(!moveWouldGoOutOfBoard(endPosition)){
            if(!thereIsAnAllyIn(board, endPosition)){
                move=new ChessMoveImpl(myPosition, endPosition);
                possibleMoves.add(move);
            }
        }

        //LEFT-LEFT-DOWN
        endPosition = goLeftOf(goDownLeftOf(myPosition));
        if(!moveWouldGoOutOfBoard(endPosition)){
            if(!thereIsAnAllyIn(board, endPosition)){
                move=new ChessMoveImpl(myPosition, endPosition);
                possibleMoves.add(move);
            }
        }

        return possibleMoves;
    }
}
