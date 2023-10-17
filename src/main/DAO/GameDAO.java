package DAO;

import dataAccess.DataAccessException;
import model.AuthToken;
import model.Game;

import java.util.Collection;
import java.util.HashSet;

/**
 * A class used to do insert, remove, find or update Games in the DB.
 */
public class GameDAO {
    private HashSet<AuthToken> games;

    /**
     * Constructs a new GameDAO object, and initializes the collection of games.
     */
    public GameDAO() {
        games = new HashSet<>();
    }

    /**
     * Tries to insert a new game into the DB. If the game is already in the DB, DataAccessException will be thrown.
     * @param game the game to be inserted into the DB
     * @throws DataAccessException the exception to be thrown in case the game cannot be inserted.
     */
    public void insert(Game game) throws DataAccessException {

    }

    /**
     * Tries to find a Game in the DB. If the Game is not in the DB, DataAccessException will be thrown.
     * @param game the Game to be looked for.
     * @return a Game object, in case it's found in the DB.
     * @throws DataAccessException the exception to be thrown in case the Game cannot be found.
     */
    public Game find(Game game) throws DataAccessException{
        return new Game();
    }

    /**
     * Tries to return all Games in the DB. If the DB is emtpy, DataAccessException will be thrown.
     * @return a set with a all the Game found in the DB.
     * @throws DataAccessException the exception to be thrown in case the DB does not have any Game.
     */
    public Collection<Game> findAll() throws DataAccessException{
        return new HashSet<>();
    }


    /**
     * Tries to update a Game already in the DB.
     * @param gameID the gameID of the Game to be updated in the DB.
     * @param updatedGame the new Game to be inserted into the DB.
     * @throws DataAccessException in case the gameID is not found in the DB.
     */
    public void updateGame(int gameID, Game updatedGame) throws DataAccessException{

    }

    /**
     * Tries to remove a Game from the DB.
     * @param game the Game to be deleted.
     * @throws DataAccessException in case the Game is not found in the DB.
     */
    public void remove(Game game) throws DataAccessException{

    }

    /**
     * tries to remove all elements from the DB.
     * @throws DataAccessException in case the DB is empty.
     */
    public void clear() throws DataAccessException{

    }


}
