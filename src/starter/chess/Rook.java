package chess;

import java.util.HashSet;
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
        Set<ChessMoveImpl> possibleMoves = new HashSet<>();
           /*
        | | | | | | | | |
		| | | | | | | | |
		| | | | | | | | |
		|N| | | | | | | |
		|r| | | | |B| | |
		| | | | | | | | |
		|q| | | | | | | |
		| | | | | | | | |
         */

    //STRAIGHT LINE
        //RIGHT

        ChessPositionImpl right = toRightOf(myPosition);
        ChessMoveImpl move;

        while(!moveWouldGoOutOfBoard(right) && !thereIsAnAllyIn(board, right)){
            move = new ChessMoveImpl(myPosition, right);
            possibleMoves.add(move);
            if (thereIsAnEnemyIn(board, right)){
                break;
            }
            right = toRightOf(right);
        }
        
        //LEFT
        ChessPositionImpl left = toLeftOf(myPosition);

        while(!moveWouldGoOutOfBoard(left) && !thereIsAnAllyIn(board, left)){
            move = new ChessMoveImpl(myPosition, left);
            possibleMoves.add(move);
            if (thereIsAnEnemyIn(board, left)){
                break;
            }
            left = toLeftOf(left);
        }
        
        //UP
        ChessPositionImpl up = goUpOf(myPosition);

        while(!moveWouldGoOutOfBoard(up) && !thereIsAnAllyIn(board, up)){
            move = new ChessMoveImpl(myPosition, up);
            possibleMoves.add(move);
            if (thereIsAnEnemyIn(board, up)){
                break;
            }
            up = goUpOf(up);
        }

        //DOWN

        ChessPositionImpl down = goDownOf(myPosition);

        while(!moveWouldGoOutOfBoard(down) && !thereIsAnAllyIn(board, down)){
            move = new ChessMoveImpl(myPosition, down);
            possibleMoves.add(move);
            if (thereIsAnEnemyIn(board, down)){
                break;
            }
            down = goDownOf(down);
        }

        return possibleMoves;
    }
}
