package net;

import webSocketMessages.serverMessages.ServerMessage;

public interface ServerMessageObserver {
    void notify(ServerMessage notification);
}
