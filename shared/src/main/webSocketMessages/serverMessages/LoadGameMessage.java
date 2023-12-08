package webSocketMessages.serverMessages;

import chess.ChessGameImpl;
import model.Game;

public class LoadGameMessage extends ServerMessage{
    //TODO - should I use this game? or another one.
    private ChessGameImpl game;

    public ChessGameImpl getGame() {
        return game;
    }

    public LoadGameMessage(ChessGameImpl game) {
        super(ServerMessageType.LOAD_GAME);
        this.game = game;
    }
}
