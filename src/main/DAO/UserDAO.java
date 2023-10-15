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

    public User find(User user) throws DataAccessException{
        return new User();
    }

    public Collection<User> findAll() throws DataAccessException{
        return new HashSet<>();
    }


    public void update(String username, User user) throws DataAccessException{

    }

    public void remove(User user) throws DataAccessException{

    }

    public void clear() throws DataAccessException{

    }


}
