package DAO;

import dataAccess.DataAccessException;
import model.User;

import java.util.Collection;
import java.util.HashSet;

/**
 * A class used to insert Users into the Database
 */
public class UserDAO {
    /**
     * A HashSet representing all users in the DB. Later on this will changed to an actual DB.
     */
    static public HashSet<User> users;

    /**
     * Constructor. Initializes the HashSet of users.
     */
    public UserDAO() {
        users = new HashSet<>();
    }

    public static HashSet<User> getUsers() {
        return users;
    }

    public static void setUsers(HashSet<User> users) {
        UserDAO.users = users;
    }

    /**
     * Tries to insert a new User into the DB. If the User is already in the DB, DataAccessException will be thrown.
     * @param user the User to be inserted into the DB
     * @throws DataAccessException the exception to be thrown in case the User cannot be inserted.
     */
    public void insert(User user) throws DataAccessException{
        if(!userIsInDB(user)){
            users.add(user);
        } else {
            throw new DataAccessException("The user " + user.toString() + " could not be " +
                    "added because it is already in the DB."
            );
        }
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
        if(users.contains(user)){
            return user;
        } else {
            throw new DataAccessException("The user " + user.toString() + " was not found in the DB.");
        }
    }

    /**
     * Tries to return all Users in the DB. If the DB is emtpy, DataAccessException will be thrown.
     * @return a set with a all the model Users, representing the found in the DB.
     * @throws DataAccessException the exception to be thrown in case the DB does not have any user.
     */
    public Collection<User> findAll() throws DataAccessException {
        if(!users.isEmpty()){
            return users;
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
    public void update(String username, User user) throws DataAccessException{
        // ------------------
        // WORK HERE
        // ------------------



    }

    /**
     * Tries to remove an User from the DB.
     * @param user the user to be deleted.
     * @throws DataAccessException in case the user is not found in the DB.
     */
    public void remove(User user) throws DataAccessException{

    }

    /**
     * tries to remove all elements from the DB.
     * @throws DataAccessException in case the DB is empty.
     */
    public void clear() throws DataAccessException{

    }


}
