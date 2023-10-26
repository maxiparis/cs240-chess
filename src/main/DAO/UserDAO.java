package DAO;

import dataAccess.DataAccessException;
import model.User;

import java.util.HashSet;

/**
 * A class used to insert Users into the Database
 */
public class UserDAO extends ClearDAO {
    static public UserDAO instance;
    /**
     * A HashSet representing all users in the DB. Later on this will changed to an actual DB.
     */
    private HashSet<User> usersDB = new HashSet<>();

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
    public void insert(User user) throws DataAccessException{
        if(!isValid(user)){
            throw new DataAccessException("Error: bad request");
        }

        if(!userIsInDB(user)){
            usersDB.add(user);
        } else {
            throw new DataAccessException("Error: already taken");
        }
    }

    private boolean isValid(User user) {
        if((user.getUsername() == null) || (user.getUsername() == "") ||
            (user.getPassword() == null) || (user.getPassword() == "") ||
            (user.getEmail() == null) || (user.getEmail() == "") ){
            return false;
        }

        return true;
    }

    private boolean userIsInDB(User user) {
        try {
            if (find(user) != null){
                return true;
            }
        } catch (DataAccessException e) {
            return false;
        }
        return false;
    }

    /**
     * Tries to find User in the DB. If the User is not in the DB, DataAccessException will be thrown.
     * @param user the User to be searched for.
     * @return a User model, in case it's found in the DB.
     * @throws DataAccessException the exception to be thrown in case the User cannot be found.
     */
    public User find(User user) throws DataAccessException{
        if(usersDB.contains(user)){
            return user;
        } else {
            throw new DataAccessException("The user " + user.toString() + " was not found in the DB.");
        }
    }

    /**
     * Tries to find a user in the DB by looking at the username and the password.
     * @param username of the user to look for.
     * @param password of the user we are looking for.
     */
    public User findWithUsernameAndPassword(String username, String password) throws DataAccessException {
        if(usersDB.isEmpty()){
            throw new DataAccessException("Error: DB is empty."); //description 500
        }

        for (User user : usersDB) {
            if(user.getUsername() == username){
                if(user.getPassword() == password){
                    return user;
                } else {
                    //found a user but their password don't match
                    throw new DataAccessException("Error: unauthorized"); //401
                }
            }
            throw new DataAccessException("Error: the username is not in the DB."); //500
        }
        return null;
    }

    /**
     * Tries to return all Users in the DB. If the DB is emtpy, DataAccessException will be thrown.
     *
     * @return a set with a all the model Users, representing the found in the DB.
     * @throws DataAccessException the exception to be thrown in case the DB does not have any user.
     */
    public HashSet<User> findAll() throws DataAccessException {
        if(!usersDB.isEmpty()) {
            return usersDB;
        } else {
            throw new DataAccessException("The Users DB is empty.");
        }
    }

    /**
     * Tries to update a user already in the DB.
     * @param username the username of the User to be updated in the DB.
     * @param user the new User to be inserted into the DB, in the same spot than the user represented by the
     *             username.
     * @throws DataAccessException in case the username is not found in any User in the DB.
     */
    public void update(String username, String updatedPassword, String updatedEmail) throws DataAccessException {
        if(!usersDB.isEmpty()){
            for (User theUser : usersDB) {
                if(theUser.getUsername() == username){
                    remove(theUser);
                    insert(new User(username, updatedPassword, updatedEmail));
                    return;
                }
            }

            throw new DataAccessException("The DB did not contain a user with the username" +
                    username);
        } else {
            throw new DataAccessException("The DB is empty, therefore nothing can be updated");
        }

    }

    /**
     * Tries to remove an User from the DB.
     * @param user the user to be deleted.
     * @throws DataAccessException in case the user is not found in the DB.
     */
    public void remove(User user) throws DataAccessException{
        try {
            User tokenToRemove = find(user);
            usersDB.remove(tokenToRemove);
        } catch (DataAccessException e) {
            throw new DataAccessException("The user " + user.toString() + " could not be removed because it is not " +
                    "in the DB.");
        }
    }

    /**
     * tries to remove all elements from the DB.
     * @throws DataAccessException in case the DB is empty.
     */
    public void clear() throws DataAccessException {
        super.clear(usersDB);
    }

}
