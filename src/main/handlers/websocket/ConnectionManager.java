package handlers.websocket;

import model.AuthToken;

import java.io.IOException;
import java.util.*;

//This class is in charge of updating and sending messages to the connections
public class ConnectionManager {
    Map<Integer, Set<Connection>> byGameID = new HashMap<>();

    public void add(int gameID, Connection connection){
        if(byGameID.containsKey(gameID)){
            byGameID.get(gameID).add(connection); //adding a new element to the list
        } else { //that gameID has not been created yet in the conns byGameID
            Set<Connection> connectionsSet = new HashSet<>();
            connectionsSet.add(connection);
            byGameID.put(gameID, connectionsSet);
        }
    }


    public void broadcastToGame(int gameID, AuthToken authTokenToExclude, String jsonMessage) throws IOException {
        Set<Connection> connectionS = byGameID.get(gameID);

        if (connectionS != null) {
            for (Connection connection : connectionS) {
                if(authTokenToExclude == null || !connection.getAuthToken().equals(authTokenToExclude)){
                    connection.send(jsonMessage);

                    //testing
                    System.out.println("Message sent to " + connection.getAuthToken() + ": " + jsonMessage);
                }
            }
        }
    }


    /**
     * Removes that authoken from both collections of connections.
     * @param authToken to be removed
     * @param gameID where the authoken will be in the ByGameID map.
     */
    public void remove(AuthToken authToken, int gameID){
        Set<Connection> connectionsCopy = new HashSet<>();

        if (byGameID.get(gameID) != null) {
            for (Connection connection : byGameID.get(gameID)) {
                if(!connection.getAuthToken().equals(authToken)){
                    connectionsCopy.add(connection);
                } else {
                    connection.getSession().close();
                }
            }
        }

        byGameID.put(gameID, connectionsCopy);

    }


}
