package DAO;

import dataAccess.DataAccessException;
import model.AuthToken;
import model.User;

import java.util.Collection;
import java.util.HashSet;

/**
 * A class used to do insert, remove, find or update AuthTokens in the DB.
 */
public class AuthDAO {
    private HashSet<AuthToken> authTokens;

    /**
     * Constructs a new AuthDAO object, and initializes the collection of authTokens.
     */
    public AuthDAO() {
        authTokens = new HashSet<>();
    }

    /**
     * Tries to insert a new authToken into the DB. If the authToken is already in the DB, DataAccessException will be thrown.
     * @param token the AuthToken to be inserted into the DB
     * @throws DataAccessException the exception to be thrown in case the authToken cannot be inserted.
     */
    public void insert(AuthToken token) throws DataAccessException {

    }

    /**
     * Tries to find an AuthToken in the DB. If the AuthToken is not in the DB, DataAccessException will be thrown.
     * @param token the AuthToken to be searched for.
     * @return a AuthToken object, in case it's found in the DB.
     * @throws DataAccessException the exception to be thrown in case the AuthToken cannot be found.
     */
    public AuthToken find(AuthToken token) throws DataAccessException{
        return new AuthToken();
    }

    /**
     * Tries to return all AuthToken in the DB. If the DB is emtpy, DataAccessException will be thrown.
     * @return a set with a all the AuthTokens found in the DB.
     * @throws DataAccessException the exception to be thrown in case the DB does not have any AuthToken.
     */
    public Collection<AuthToken> findAll() throws DataAccessException{
        return new HashSet<>();
    }

    /**
     * Tries to update an AuthToken already in the DB.
     * @param username the username of the AuthToken to be updated in the DB.
     * @param updatedToken the new AuthToken to be inserted into the DB, in the same spot than the AuthToken represented by the username.
     * @throws DataAccessException in case the username is not found in any User in the DB.
     */
    public void update(String username, AuthToken updatedToken) throws DataAccessException{

    }

    /**
     * Tries to remove an AuthToken from the DB.
     * @param token the token to be deleted.
     * @throws DataAccessException in case the token is not found in the DB.
     */
    public void remove(AuthToken token) throws DataAccessException{

    }

    /**
     * tries to remove all elements from the DB.
     * @throws DataAccessException in case the DB is empty.
     */
    public void clear() throws DataAccessException{

    }
}
