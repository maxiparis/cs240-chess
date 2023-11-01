package DAO;

import chess.ChessGameImpl;
import dataAccess.DataAccessException;
import model.Game;

import java.util.HashSet;

/**
 * A class used to do insert, remove, find or update Games in the DB.
 */
public class GameDAO extends ClearDAO {
    private static GameDAO instance;
    private HashSet<Game> gamesDB = new HashSet<>();

    public HashSet<Game> getGamesDB() {
        return gamesDB;
    }

    public void setGamesDB(HashSet<Game> gamesDB) {
        this.gamesDB = gamesDB;
    }

    /**
     * Constructs a new GameDAO object, and initializes the collection of games.
     */
    private GameDAO() {
    }

    public static GameDAO getInstance() {
        if (instance == null) {
            instance = new GameDAO();
        }
        return instance;
    }

    /**
     * Tries to insert a new game into the DB. If the game is already in the DB, DataAccessException will be thrown.
     * @param game the game to be inserted into the DB
     * @throws DataAccessException the exception to be thrown in case the game cannot be inserted.
     */
    public void insert(Game game) throws DataAccessException {
        if(findGameByName(game.getGameName()) != null){ //we found another game inside the db with the same name
            throw new DataAccessException("Error: there is another game with the same name");
        }
        if(!gameIsInDB(game)){
            gamesDB.add(game);
        } else {
            throw new DataAccessException("Error: game is already in DB");
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
    public Game find(Game game) throws DataAccessException {
        Game foundGame = findGameByName(game.getGameName());

        if(foundGame == null){
            throw new DataAccessException("The game was not found in the DB.");
        }

        if(game.getGameID() != foundGame.getGameID()){
            throw new DataAccessException("The game " + game.toString() + " was not found in the DB.");
        } else if (game.getWhiteUsername() != foundGame.getWhiteUsername()){
            throw new DataAccessException("The game " + game.toString() + " was not found in the DB.");
        } else if (game.getBlackUsername() != foundGame.getBlackUsername()) {
            throw new DataAccessException("The game " + game.toString() + " was not found in the DB.");
        } else if (!game.getGame().equals(foundGame.getGame())) { //failing here
            throw new DataAccessException("The game " + game.toString() + " was not found in the DB.");
        }
        return foundGame;
    }

    /**
     * Tries to return all Games in the DB. If the DB is emtpy, DataAccessException will be thrown.
     * @return a set with a all the Game found in the DB.
     * @throws DataAccessException the exception to be thrown in case the DB does not have any Game.
     */
    public HashSet<Game> findAll() throws DataAccessException{
        if(!gamesDB.isEmpty()) {
            return gamesDB;
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
        if(!gamesDB.isEmpty()){
            for (Game theGame : gamesDB) {
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
            gamesDB.remove(gameToRemove);
        } catch (DataAccessException e) {
            throw new DataAccessException("The game " + game.toString() + " could not be removed because it is not " +
                    "in the DB.");
        }
    }

    /**
     * tries to remove all elements from the DB.
     * @throws DataAccessException in case the DB is empty.
     */
    public void clear() throws DataAccessException {
        super.clear(dataBaseType.GAME);
    }

    public Game findGameByName(String gameName) {
        if(gamesDB.isEmpty()){
            return null;
        }

        Game toReturn = null;
        for (Game game : gamesDB) {
            if(game.getGameName().equals(gameName)){
                toReturn = game;
            }
        }

        return toReturn;
    }

    public Game findGameById(int gameID) throws DataAccessException {
        if(gamesDB.isEmpty()){
            throw new DataAccessException("Error: DB is empty");
        }


        Game toReturn = null;
        for (Game game : gamesDB) {
            if(game.getGameID() == gameID){
                toReturn = game;
            }
        }

        if(toReturn == null){
            throw new DataAccessException("Error: bad request");
        }

        return toReturn;
    }
}
