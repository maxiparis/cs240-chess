package DAO;

import com.google.gson.Gson;
import dataAccess.DataAccessException;
import model.AuthToken;
import model.Game;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * A class used to do insert, remove, find or update AuthTokens in the DB.
 */
public class AuthDAO extends ClearDAO {
    private static AuthDAO instance;
    private HashSet<AuthToken> authTokensDB = new HashSet<>();
    private Database database = Database.getInstance();


    /**
     * Constructs a new AuthDAO object, and initializes the collection of authTokens.
     */
    private AuthDAO() {
    }

    public static AuthDAO getInstance() {
        if(instance == null){
            instance = new AuthDAO();
        }
        return instance;
    }

    public HashSet<AuthToken> getAuthTokensDB() {
        return authTokensDB;
    }

    public void setAuthTokensDB(HashSet<AuthToken> authTokensDB) {
        this.authTokensDB=authTokensDB;
    }

    /**
     * Tries to insert a new authToken into the DB. If the authToken is already in the DB, DataAccessException
     * will be thrown.
     * @param token the AuthToken to be inserted into the DB
     * @throws DataAccessException the exception to be thrown in case the authToken cannot be inserted.
     */
    public void insert(AuthToken token) throws DataAccessException {
        String sql = "insert into authToken (username, token) values (?, ?)";

        Connection connection = database.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, token.getUsername());
            preparedStatement.setString(2, token.getToken());

            if (preparedStatement.executeUpdate() == 1) {
                System.out.println("Insert: Success!");
            } else {
                System.out.println("Insert: Something unexpected happened. :(");
            }
            database.closeConnection(connection);
        } catch (SQLException e) {
            //TODO here I am supposed to grab the exception and then send another exception with the correct
            //message.
            database.closeConnection(connection);

            System.out.println(e.getMessage());
            throw new DataAccessException("Error: bad request"); //just an example
        }
//            if(!tokenIsInDB(token)){
//                authTokensDB.add(token);
//            } else {
//                throw new DataAccessException("The token could not be added because it is already in the DB");
//            }
    }

//    private boolean tokenIsInDB(AuthToken token) {
////        try {
////            if (find(token) != null){
////                return true;
////            }
////        } catch (DataAccessException e) {
////            return false;
////        }
//        return false;
//    }

    /**
     * Tries to find an AuthToken in the DB. If the AuthToken is not in the DB, DataAccessException will be thrown.
     * @param token the AuthToken to be searched for.
     * @return a AuthToken object, in case it's found in the DB.
     * @throws DataAccessException the exception to be thrown in case the AuthToken cannot be found.
     */
    public AuthToken find(String username) throws DataAccessException {
            String sql = "select * from authToken where username = ?";

            Connection connection = database.getConnection();

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
                preparedStatement.setString(1, username);
                List<AuthToken> authTokensReturned = new ArrayList<>();
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()){
                    String _username = resultSet.getString(1);
                    String token = resultSet.getString(2);

                    authTokensReturned.add(new AuthToken(_username, token));
                }

                if(authTokensReturned.size() == 1){
                    System.out.println("Find: success.");
                } else if (authTokensReturned.size() == 0) {
                    database.closeConnection(connection);

                    throw new DataAccessException("The user was not found in the DB.");
                } else {
                    System.out.println("Find: too many rows returned. ");
                }
                database.closeConnection(connection);

                return authTokensReturned.get(0);
            } catch (SQLException e) {
                throw new DataAccessException("Error: " + e.getMessage());
            }

//        if(authTokensDB.contains(token)){
//            return token;
//        } else {
//            throw new DataAccessException("The token was not found in the DB.");
//        }
    }


    public AuthToken findWithToken(String token) throws DataAccessException {
        String sql = "select * from authToken where token = ?";

        Connection connection = database.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, token);
            List<AuthToken> authTokensReturned = new ArrayList<>();
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                String _username = resultSet.getString(1);
                String _token = resultSet.getString(2);

                authTokensReturned.add(new AuthToken(_username, _token));
            }

            if(authTokensReturned.size() == 1){
                System.out.println("Find: success.");
            } else if (authTokensReturned.size() == 0) {
                database.closeConnection(connection);

                throw new DataAccessException("The user was not found in the DB.");
            } else {
                System.out.println("Find: too many rows returned. ");
            }
            database.closeConnection(connection);

            return authTokensReturned.get(0);
        } catch (SQLException e) {
            database.closeConnection(connection);

            throw new DataAccessException("Error: " + e.getMessage());
        }

//        if(authTokensDB.contains(token)){
//            return token;
//        } else {
//            throw new DataAccessException("The token was not found in the DB.");
//        }
    }

    /**
     * Tries to return all AuthToken in the DB. If the DB is emtpy, DataAccessException will be thrown.
     * @return a set with a all the AuthTokens found in the DB.
     * @throws DataAccessException the exception to be thrown in case the DB does not have any AuthToken.
     */
    public HashSet<AuthToken> findAll() throws DataAccessException {
        HashSet<AuthToken> authsInDB = new HashSet<>();
        String sql = "select username, token from authToken";

        Connection connection = database.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                String username = resultSet.getString(1);
                String token = resultSet.getString(2);

                authsInDB.add(new AuthToken(username, token));
            }

            if (authsInDB.size() == 0){
                database.closeConnection(connection);
                throw new DataAccessException("Error: The Users DB is empty.");
            }
            database.closeConnection(connection);

            return authsInDB;
        } catch (SQLException e) {
            database.closeConnection(connection);
            throw new DataAccessException("Error: " + e.getMessage());
        }
    }

    /**
     * Tries to update an AuthToken already in the DB.
     * @param username the username of the AuthToken to be updated in the DB.
     * @param updatedToken the new AuthToken to be inserted into the DB, in the same spot than the AuthToken
     *                     represented by the username.
     * @throws DataAccessException in case the username is not found in any User in the DB.
     */
    public void update(String username, String updatedToken) throws DataAccessException{
        String sql = "UPDATE authToken SET token=? WHERE username=?";
        Gson gson = new Gson();
        Connection connection = database.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, updatedToken);
            preparedStatement.setString(2, username);

            if (preparedStatement.executeUpdate() == 1) {
                System.out.println("Update: Success!");
            } else if (preparedStatement.executeUpdate() == 0) {
                System.out.println("Update: nothing was updated.");
                throw new DataAccessException("Error: nothing was updated.");
            } else {
                System.out.println("Insert: Something unexpected happened. :(");
            }
            database.closeConnection(connection);
        } catch (SQLException e) {
            //TODO here I am supposed to grab the exception and then send another exception with the correct message.
            System.out.println(e.getMessage());
            database.closeConnection(connection);

            throw new DataAccessException("Error: " + e.getMessage()); //just an example
        }



//        if(!authTokensDB.isEmpty()){
//            for (AuthToken authToken : authTokensDB) {
//                if(authToken.getUsername() == username){
//                    remove(authToken);
//                    insert(new AuthToken(username, updatedToken));
////                    authToken.setToken(updatedToken);
//                    return;
//                }
//            }
//
//            throw new DataAccessException("The DB did not contain an AuthToken with the username" +
//                    username);
//        } else {
//            throw new DataAccessException("The DB is empty, therefore nothing can be updated");
//        }
    }

    /**
     * Tries to remove an AuthToken from the DB.
     * @param token the token to be deleted.
     * @throws DataAccessException in case the token is not found in the DB.
     */
    public void remove(String username) throws DataAccessException{
        String sql = "delete from authToken where username = ?";

        Connection connection = database.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, username);

            if (preparedStatement.executeUpdate() == 1) {
                System.out.println("Remove: Success!");
            } else if (preparedStatement.executeUpdate() == 0) {
                throw new DataAccessException("Error: the authToken was not in the DB.");
            } else {
                throw new DataAccessException("Error: more than one row was affected.");
            }
            database.closeConnection(connection);

        } catch (SQLException e) {
            //TODO here I am supposed to grab the exception and then send another exception with the correct
            //message.
            System.out.println(e.getMessage());
            database.closeConnection(connection);

            throw new DataAccessException("Error: " + e.getMessage()); //just an example
        }
    }

    /**
     * tries to remove all elements from the DB.
     * @throws DataAccessException in case the DB is empty.
     */
    public void clear() throws DataAccessException {
        super.clear(dataBaseType.AUTHTOKEN);
    }

//    public AuthToken findWithAuthToken(String theToken) throws DataAccessException {
//        if(authTokensDB.isEmpty()){
//            throw new DataAccessException("Error: DB is empty");
//        }
//
//        AuthToken toReturn = null;
//        for (AuthToken token : authTokensDB) {
//            if(token.getToken().equals(theToken)){
//                return token;
//            }
//        }
//
//        throw new DataAccessException("Error: the token was not found in DB.");
//
//    }
}
