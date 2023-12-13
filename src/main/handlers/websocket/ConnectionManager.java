package handlers.websocket;

import model.AuthToken;
import org.eclipse.jetty.client.AuthenticationProtocolHandler;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.common.WebSocketSession;

import java.io.IOException;
import java.util.*;

//This class is in charge of updating and sending messages to the connections
public class ConnectionManager {
    //TODO consider removing byAuthToken because I am never using it. I am only adding and
    //removing, but never accessing it.
    Map<AuthToken, Connection> byAuthToken = new HashMap<>();
    Map<Integer, Set<Connection>> byGameID = new HashMap<>();

    //********************* connectionsByGameId **************************//

    public void addByGameID(int gameID, Connection connection){
        //if there is a game
            //get the value (list)
            //add to the list a new connection
        //else
            //create a new one

        if(byGameID.containsKey(gameID)){
            byGameID.get(gameID).add(connection); //adding a new element to the list
            //TODO I need to make sure that here is adding to the list inside the map
        } else { //that gameID has not been created yet in the conns byGameID
            Set<Connection> connectionsSet = new HashSet<>();
            connectionsSet.add(connection);
            byGameID.put(gameID, connectionsSet);
        }
    }

    public Connection getByGameID(int gameID){
        return null;
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


    public void removeByGameID(int gameID){

    }

    //********************* connectionsByAuthToken **************************//

    public Connection addByAuthToken(AuthToken authToken, Session session){
        Connection connection = new Connection(authToken, session);
        byAuthToken.put(authToken, connection);
        return connection;
    }

    public Connection getByAuthToken(String authToken){
        return null;
    }




    //********************* both **************************//

    /**
     * Removes that authoken from both collections of connections.
     * @param authToken to be removed
     * @param gameID where the authoken will be in the ByGameID map.
     */
    public void removeByAuthToken(AuthToken authToken, int gameID){

        byAuthToken.remove(authToken);

        //1. copy all the connections from the original hashset, where the connection.authToken.username is not
        //equal to the authToken parameter.
        //2. I am gonna have a new hashset. replace that hashset into the gameID

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
