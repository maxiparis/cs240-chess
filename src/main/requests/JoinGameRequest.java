package requests;

import chess.ChessPiece;

public class JoinGameRequest {
    private ChessPiece.PieceType playerColor;
    private int gameID;

    public JoinGameRequest(ChessPiece.PieceType playerColor, int gameID) {
        this.playerColor = playerColor;
        this.gameID = gameID;
    }

    public ChessPiece.PieceType getPlayerColor() {
        return playerColor;
    }

    public void setPlayerColor(ChessPiece.PieceType playerColor) {
        this.playerColor = playerColor;
    }

    public int getGameID() {
        return gameID;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }
}
