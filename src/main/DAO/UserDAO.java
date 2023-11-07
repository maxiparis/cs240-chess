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
    /**
     * A HashSet representing all users in the DB. Later on this will changed to an actual DB.
     */
    private HashSet<User> usersDB = new HashSet<>();

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

    public HashSet<User> getUsersDB() {
        return usersDB;
    }

    public void setUsersDB(HashSet<User> usersDB) {
        this.usersDB=usersDB;
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


//        if(!isValid(user)){
//            throw new DataAccessException("Error: bad request");
//        }
//
//        if(!userIsInDB(user)){
//            usersDB.add(user);
//        } else {
//            throw new DataAccessException("Error: already taken");
//        }
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

//        if(usersDB.contains(user)){
//            return user;
//        } else {
//            throw new DataAccessException("The user was not found in the DB.");
//        }
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


//        if(usersDB.isEmpty()){
//            throw new DataAccessException("Error: unauthorized"); //description 500
//        }
//
//        for (User user : usersDB) {
//            if(user.getUsername().equals(username)){
//                if(user.getPassword().equals(password)){
//                    return user;
//                } else {
//                    throw new DataAccessException("Error: unauthorized"); //401
//                }
//            }
//        }
//        throw new DataAccessException("Error: the username is not in the DB."); //500
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
//        if(!usersDB.isEmpty()) {
//            return usersDB;
//        } else {
//            throw new DataAccessException("Error: The Users DB is empty.");
//        }
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


//        try {
//            User tokenToRemove = find(user.getUsername());
//            usersDB.remove(tokenToRemove);
//        } catch (DataAccessException e) {
//            throw new DataAccessException("The user " + user.toString() + " could not be removed because it is not " +
//                    "in the DB.");
//        }
    }

    /**
     * tries to remove all elements from the DB.
     * @throws DataAccessException in case the DB is empty.
     */
    public void clear() throws DataAccessException {
        super.clear(dataBaseType.USER);
    }

//    /**
//     * Tries to update a user already in the DB.
//     * @param username the username of the User to be updated in the DB.
//     * @param user the new User to be inserted into the DB, in the same spot than the user represented by the
//     *             username.
//     * @throws DataAccessException in case the username is not found in any User in the DB.
//     */
//    public void update(String username, String updatedPassword, String updatedEmail) throws DataAccessException {
//        String sql = "UPDATE user SET password = WHERE username = ";
//
//        Connection connection = database.getConnection();
//
//
//        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
//            preparedStatement.setString(1, user.getUsername());
//            preparedStatement.setString(2, user.getPassword());
//            preparedStatement.setString(3, user.getEmail());
//
//            if (preparedStatement.executeUpdate() == 1) {
//                System.out.println("Insert: Success!");
//            } else {
//                System.out.println("Insert: Something unexpected happened. :(");
//            }
//        } catch (SQLException e) {
//            //TODO here I am supposed to grab the exception and then send another exception with the correct
//            //message.
//            System.out.println(e.getMessage());
//            throw new DataAccessException("Error: bad request"); //just an example
//        }
//
//
////        if(!usersDB.isEmpty()){
////            for (User theUser : usersDB) {
////                if(theUser.getUsername() == username){
////                    remove(theUser);
////                    insert(new User(username, updatedPassword, updatedEmail));
////                    return;
////                }
////            }
////
////            throw new DataAccessException("The DB did not contain a user with the username" + username);
////        } else {
////            throw new DataAccessException("The DB is empty, therefore nothing can be updated");
////        }
//
//    }


//    private boolean isValid(User user) {
//        if((user.getUsername() == null) || (user.getUsername() == "") ||
//            (user.getPassword() == null) || (user.getPassword() == "") ||
//            (user.getEmail() == null) || (user.getEmail() == "") ){
//            return false;
//        }
//        return true;
//    }
//
//    private boolean userIsInDB(User user) {
//        try {
//            if (find(user.getUsername()) != null){
//                return true;
//            }
//        } catch (DataAccessException e) {
//            return false;
//        }
//        return false;
//    }
}
