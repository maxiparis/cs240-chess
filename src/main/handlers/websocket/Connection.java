package handlers.websocket;

import org.eclipse.jetty.websocket.api.Session;

import java.io.IOException;

public class Connection {
    private String authToken;
    private Session session;

    public Connection(String authToken, Session session) {
        this.authToken = authToken;
        this.session = session;
    }

    public void send(String jsonMessage) throws IOException {
        session.getRemote().sendString(jsonMessage);
    }


    public String getAuthToken() {
        return authToken;
    }

    public Session getSession() {
        return session;
    }
}
