package handlers.websocket;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.common.WebSocketSession;

import java.io.IOException;
import java.util.*;

//This class is in charge of updating and sending messages to the connections
public class ConnectionManager {
    Map<String, Connection> byAuthToken = new HashMap<>();
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


    public void broadcastToGame(int gameID, String authTokenToExclude, String jsonMessage) throws IOException {
        Set<Connection> connectionS = byGameID.get(gameID);

        for (Connection connection : connectionS) {
            if(!connection.getAuthToken().equals(authTokenToExclude)){
                connection.send(jsonMessage);

                //testing
                System.out.println("Message sent to " + connection.getAuthToken() + ": " + jsonMessage);
            }
        }
    }


    public void removeByGameID(int gameID){

    }

    //********************* connectionsByAuthToken **************************//

    public Connection addByAuthToken(String authToken, Session session){
        Connection connection = new Connection(authToken, session);
        byAuthToken.put(authToken, connection);
        return connection;
    }

    public Connection getByAuthToken(String authToken){
        return null;
    }


    public void removeByAuthToken(String authToken, int gameID){
        byAuthToken.remove(authToken);

        Set<Connection> connectionsCopy = byGameID.get(gameID);
//        for (Connection connection : byGameID.get(gameID)) {
//            if(connection.getAuthToken().equals(authToken)){
//                connectionsCopy.remove();
//            }
//        }
    }


    //********************* both **************************//


}
