package DAO;

import chess.ChessGameImpl;
import dataAccess.DataAccessException;
import model.AuthToken;
import model.Game;

import java.util.Collection;
import java.util.HashSet;

/**
 * A class used to do insert, remove, find or update Games in the DB.
 */
public class GameDAO extends ClearDAO {
    public static HashSet<Game> games;

    public static HashSet<Game> getGames() {
        return games;
    }

    public static void setGames(HashSet<Game> games) {
        GameDAO.games = games;
    }



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
        if(!gameIsInDB(game)){
            games.add(game);
        } else {
            throw new DataAccessException("The game " + game.toString() + " could not be " +
                    "added because it is already in the DB."
            );
        }
    }

    private boolean gameIsInDB(Game game) {
        try {
            if (find(game) != null){
                return true;
            }
        } catch (DataAccessException e) {
            return false;
        }
        return false;
    }

    /**
     * Tries to find a Game in the DB. If the Game is not in the DB, DataAccessException will be thrown.
     * @param game the Game to be looked for.
     * @return a Game object, in case it's found in the DB.
     * @throws DataAccessException the exception to be thrown in case the Game cannot be found.
     */
    public Game find(Game game) throws DataAccessException{
        if(games.contains(game)){
            return game;
        } else {
            throw new DataAccessException("The game " + game.toString() + " was not found in the DB.");
        }
    }

    /**
     * Tries to return all Games in the DB. If the DB is emtpy, DataAccessException will be thrown.
     * @return a set with a all the Game found in the DB.
     * @throws DataAccessException the exception to be thrown in case the DB does not have any Game.
     */
    public HashSet<Game> findAll() throws DataAccessException{
        if(!games.isEmpty()) {
            return games;
        } else {
            throw new DataAccessException("The Games DB is empty.");
        }
    }


    /**
     * Tries to update a Game already in the DB.
     * @param gameID the gameID of the Game to be updated in the DB.
     * @param updatedGame the new Game to be inserted into the DB.
     * @throws DataAccessException in case the gameID is not found in the DB.
     */
    public void updateGame(int gameID, String newWhiteUsername, String newBlackUsername,
                           String newGameName, ChessGameImpl newGame) throws DataAccessException {
        if(!games.isEmpty()){
            for (Game theGame : games) {
                if(theGame.getGameID() == gameID){
                    remove(theGame);
                    insert(new Game(gameID, newWhiteUsername, newBlackUsername, newGameName, newGame));
                    return;
                }
            }

            throw new DataAccessException("The DB did not contain a game with the gameID " +
                    gameID);
        } else {
            throw new DataAccessException("The Games DB is empty, therefore nothing can be updated");
        }

    }

    /**
     * Tries to remove a Game from the DB.
     * @param game the Game to be deleted.
     * @throws DataAccessException in case the Game is not found in the DB.
     */
    public void remove(Game game) throws DataAccessException{
        try {
            Game gameToRemove = find(game);
            games.remove(gameToRemove);
        } catch (DataAccessException e) {
            throw new DataAccessException("The game " + game.toString() + " could not be removed because it is not " +
                    "in the DB.");
        }
    }

    /**
     * tries to remove all elements from the DB.
     * @throws DataAccessException in case the DB is empty.
     */
    public void clear() throws DataAccessException{
        super.clear(games);
    }
}
