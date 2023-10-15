package DAO;

import dataAccess.DataAccessException;
import model.AuthToken;
import model.Game;

import java.util.Collection;
import java.util.HashSet;

public class GameDAO {
    private HashSet<AuthToken> games;

    public GameDAO() {
        games = new HashSet<>();
    }

    public void insert(Game game) throws DataAccessException {

    }

    public Game find(Game game) throws DataAccessException{
        return new Game();
    }

    public Collection<Game> findAll() throws DataAccessException{
        return new HashSet<>();
    }


    public void updateGame(int gameID, Game updatedGame) throws DataAccessException{

    }

    public void remove(Game game) throws DataAccessException{

    }

    public void clear() throws DataAccessException{

    }


}
