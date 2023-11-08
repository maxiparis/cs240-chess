package DAO;

import dataAccess.DataAccessException;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * A class used to insert Users into the Database
 */
public class UserDAO extends ClearDAO {
    static public UserDAO instance;
    private Database database = Database.getInstance();

    /**
     * Constructor. Initializes the HashSet of users.
     */
    private UserDAO() {
    }

    public static UserDAO getInstance(){
        if(instance == null){
            instance = new UserDAO();
        }
        return instance;
    }

    /**
     * Tries to insert a new User into the DB. If the User is already in the DB, DataAccessException will be thrown.
     * @param user the User to be inserted into the DB
     * @throws DataAccessException the exception to be thrown in case the User cannot be inserted.
     */
    public void insert(User user) throws DataAccessException {
        String sql = "insert into user (username, password, email) values (?, ?, ?)";

        Connection connection = database.getConnection();


        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getEmail());

            if (preparedStatement.executeUpdate() == 1) {
                System.out.println("Insert: Success!");
            } else {
                System.out.println("Insert: Something unexpected happened. :(");
            }
            database.closeConnection(connection);

        } catch (SQLException e) {
            //TODO here I am supposed to grab the exception and then send another exception with the correct
            //message.
            System.out.println(e.getMessage());
            if(e.getMessage().contains("Duplicate")){
                database.closeConnection(connection);

                throw new DataAccessException("Error: already taken");
            }
            database.closeConnection(connection);

            throw new DataAccessException("Error: bad request"); //just an example
        }
    }


    /**
     * Tries to find User in the DB. If the User is not in the DB, DataAccessException will be thrown.
     * @param user the User to be searched for.
     * @return a User model, in case it's found in the DB.
     * @throws DataAccessException the exception to be thrown in case the User cannot be found.
     */
    public User find(String usernameToFind) throws DataAccessException {
        String sql = "select * from user where username = ?";

        Connection connection = database.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, usernameToFind);
            List<User> usersReturned = new ArrayList<>();
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                String username = resultSet.getString(1);
                String password = resultSet.getString(2);
                String email = resultSet.getString(3);

                usersReturned.add(new User(username, password, email));
            }

            if(usersReturned.size() == 1){
                System.out.println("Find: success.");
            } else if (usersReturned.size() == 0) {
                database.closeConnection(connection);

                throw new DataAccessException("The user was not found in the DB.");
            } else {
                System.out.println("Find: too many rows returned. ");
            }
            database.closeConnection(connection);

            return usersReturned.get(0);
        } catch (SQLException e) {
            database.closeConnection(connection);

            throw new DataAccessException("Error: " + e.getMessage());
        }
    }


    /**
     * Tries to find a user in the DB by looking at the username and the password.
     * @param username of the user to look for.
     * @param password of the user we are looking for.
     */
    public User findWithUsernameAndPassword(String username, String password) throws DataAccessException {
        try {
            User foundUser = find(username);
            if(foundUser.getPassword().equals(password)){
                return foundUser;
            }
            throw new DataAccessException("Error: unauthorized");

        } catch (DataAccessException e) {
            throw new DataAccessException("Error: unauthorized");
        }
    }

    /**
     * Tries to return all Users in the DB. If the DB is emtpy, DataAccessException will be thrown.
     *
     * @return a set with a all the model Users, representing the found in the DB.
     * @throws DataAccessException the exception to be thrown in case the DB does not have any user.
     */
    public HashSet<User> findAll() throws DataAccessException {
        HashSet<User> usersInDB = new HashSet<>();
        String sql = "select username, password, email from user";

        Connection connection = database.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                String username = resultSet.getString(1);
                String password = resultSet.getString(2);
                String email = resultSet.getString(3);

                usersInDB.add(new User(username, password, email));
            }

            if (usersInDB.size() == 0){
                database.closeConnection(connection);
                throw new DataAccessException("Error: The Users DB is empty.");
            }
            database.closeConnection(connection);

            return usersInDB;
        } catch (SQLException e) {
            database.closeConnection(connection);

            throw new DataAccessException("Error: " + e.getMessage());
        }
    }


    /**
     * Tries to remove an User from the DB.
     * @param user the user to be deleted.
     * @throws DataAccessException in case the user is not found in the DB.
     */
    public void remove(String username) throws DataAccessException {
        String sql = "DELETE FROM user WHERE username = ?";

        Connection connection = database.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, username);

            if (preparedStatement.executeUpdate() == 1) {
                System.out.println("Remove: Success!");
            } else if (preparedStatement.executeUpdate() == 0) {
                database.closeConnection(connection);

                throw new DataAccessException("Error: the user was not in the DB.");
            } else {
                database.closeConnection(connection);

                throw new DataAccessException("Error: more than one row was affected.");
            }
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
        super.clear(dataBaseType.USER);
    }
}
