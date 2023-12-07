package webSocketMessages.serverMessages;

import chess.ChessGameImpl;
import model.Game;

public class LoadGameMessage extends ServerMessage{
    //TODO - should I use this game? or another one.
    private ChessGameImpl game;

    public LoadGameMessage(ServerMessageType type, ChessGameImpl game) {
        super(type);
        this.game = game;
        super.serverMessageType = ServerMessageType.LOAD_GAME;
    }
}
