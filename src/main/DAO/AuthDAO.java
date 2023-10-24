package DAO;

import dataAccess.DataAccessException;
import model.AuthToken;
import model.User;

import javax.xml.crypto.Data;
import java.util.Collection;
import java.util.HashSet;

/**
 * A class used to do insert, remove, find or update AuthTokens in the DB.
 */
public class AuthDAO {
    static private HashSet<AuthToken> authTokens;

    /**
     * Constructs a new AuthDAO object, and initializes the collection of authTokens.
     */
    public AuthDAO() {
        authTokens = new HashSet<>();
    }

    public static HashSet<AuthToken> getAuthTokens() {
        return authTokens;
    }

    public static void setAuthTokens(HashSet<AuthToken> authTokens) {
        AuthDAO.authTokens=authTokens;
    }

    /**
     * Tries to insert a new authToken into the DB. If the authToken is already in the DB, DataAccessException
     * will be thrown.
     * @param token the AuthToken to be inserted into the DB
     * @throws DataAccessException the exception to be thrown in case the authToken cannot be inserted.
     */
    public void insert(AuthToken token) throws DataAccessException {
            if(!tokenIsInDB(token)){
                authTokens.add(token);
            } else {
                throw new DataAccessException("The token " + token.toString() + " could not be " +
                        "added because it is already in the DB.");
            }
    }

    private boolean tokenIsInDB(AuthToken token) {
        try {
            if (find(token) != null){
                return true;
            }
        } catch (DataAccessException e) {
            return false;
        }
        return false;
    }

    /**
     * Tries to find an AuthToken in the DB. If the AuthToken is not in the DB, DataAccessException will be thrown.
     * @param token the AuthToken to be searched for.
     * @return a AuthToken object, in case it's found in the DB.
     * @throws DataAccessException the exception to be thrown in case the AuthToken cannot be found.
     */
    public AuthToken find(AuthToken token) throws DataAccessException{
        if(authTokens.contains(token)){
            return token;
        } else {
            throw new DataAccessException("The token " + token.toString() + " was not found in the DB.");
        }

    }

    /**
     * Tries to return all AuthToken in the DB. If the DB is emtpy, DataAccessException will be thrown.
     * @return a set with a all the AuthTokens found in the DB.
     * @throws DataAccessException the exception to be thrown in case the DB does not have any AuthToken.
     */
    public HashSet<AuthToken> findAll() throws DataAccessException{
        if(!authTokens.isEmpty()){
            return authTokens;
        } else {
            throw new DataAccessException("The AuthToken DB is empty.");
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
        if(!authTokens.isEmpty()){
            for (AuthToken authToken : authTokens) {
                if(authToken.getUsername() == username){
                    authToken.setToken(updatedToken);
                    return;
                }
            }

            throw new DataAccessException("The DB did not contain an AuthToken with the username" +
                    username);
        } else {
            throw new DataAccessException("The DB is empty, therefore nothing can be updated");
        }
    }

    /**
     * Tries to remove an AuthToken from the DB.
     * @param token the token to be deleted.
     * @throws DataAccessException in case the token is not found in the DB.
     */
    public void remove(AuthToken token) throws DataAccessException{
        try {
            AuthToken tokenToRemove = find(token);
            authTokens.remove(tokenToRemove);
        } catch (DataAccessException e) {
            System.out.println("The token " + token.toString() + " could not be removed because it is not " +
                    "in the DB.");
        }


    }

    /**
     * tries to remove all elements from the DB.
     * @throws DataAccessException in case the DB is empty.
     */
    public void clear() throws DataAccessException{
        if (!authTokens.isEmpty()) {
            authTokens = new HashSet<>();
        } else {
            throw new DataAccessException("The DB could not be cleared because it was empty.");
        }
    }
}
