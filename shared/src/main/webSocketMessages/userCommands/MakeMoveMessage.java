package webSocketMessages.userCommands;

import chess.ChessMoveImpl;

public class MakeMoveMessage extends UserGameCommand {
    private int gameID;
    ChessMoveImpl move;

    public MakeMoveMessage(String authToken, int gameID, ChessMoveImpl chessMove) {
        super(authToken);
        this.gameID = gameID;
        this.move = chessMove;
        super.commandType = CommandType.MAKE_MOVE;
    }
}
