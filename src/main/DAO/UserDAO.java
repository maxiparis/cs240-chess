package DAO;

import dataAccess.DataAccessException;
import model.User;

import java.util.Collection;
import java.util.HashSet;

public class UserDAO {
    private HashSet<User> users;

    public UserDAO() {
        users = new HashSet<>();
    }

    public void insert(User user) throws DataAccessException{

    }

    public User find() throws DataAccessException{
        return new User();
    }

    public Collection<User> findAll() throws DataAccessException{
        return new HashSet<>();
    }


    public void updateUsername(String username, String newUsername) throws DataAccessException{

    }

    public void updatePassword(String username, String newPassword) throws DataAccessException{

    }

    public void updateEmail(String username, String newEmail) throws DataAccessException{

    }

    public void remove(String username) throws DataAccessException{

    }

    public void clear() throws DataAccessException{

    }


}
