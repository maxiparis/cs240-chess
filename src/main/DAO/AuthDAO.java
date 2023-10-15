package DAO;

import dataAccess.DataAccessException;
import model.AuthToken;
import model.User;

import java.util.Collection;
import java.util.HashSet;

public class AuthDAO {
    private HashSet<AuthToken> authTokens;

    public AuthDAO() {
        authTokens = new HashSet<>();
    }

    public void insert(AuthToken token) throws DataAccessException {

    }

    public AuthToken find() throws DataAccessException{
        return new AuthToken();
    }

    public Collection<AuthToken> findAll() throws DataAccessException{
        return new HashSet<>();
    }


    public void updateUsername(String username, String newUsername) throws DataAccessException{

    }

    public void updateAuthToken(String username, String newAuthToken) throws DataAccessException{

    }

    public void remove(String username) throws DataAccessException{

    }

    public void clear() throws DataAccessException{

    }
}
