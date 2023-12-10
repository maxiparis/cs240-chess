package handlers.websocket;

import model.AuthToken;
import org.eclipse.jetty.websocket.api.Session;

import java.io.IOException;

public class Connection {
    private AuthToken authToken;
    private Session session;

    public Connection(AuthToken authToken, Session session) {
        this.authToken = authToken;
        this.session = session;
    }

    public void send(String jsonMessage) throws IOException {
        session.getRemote().sendString(jsonMessage);
    }


    public AuthToken getAuthToken() {
        return authToken;
    }

    public Session getSession() {
        return session;
    }
}
