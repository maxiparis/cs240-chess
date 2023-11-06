package DAO;

import chess.ChessGame;
import chess.ChessGameImpl;
import chess.ChessPiece;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dataAccess.DataAccessException;
import model.Game;
import model.User;
import requests.CreateGameRequest;
import typeAdapters.ChessGameDeserializer;
import typeAdapters.ChessPieceDeserializer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * A class used to do insert, remove, find or update Games in the DB.
 */
public class GameDAO extends ClearDAO {
    private static GameDAO instance;
    private HashSet<Game> gamesDB = new HashSet<>();
    private Database database = Database.getInstance();


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
        String sql = "insert into game(gameName, whiteUsername, blackUsername, game) values (?,?,?,?)";
        Gson gson = new Gson();
        String serializedGame = gson.toJson(game.getGame());
        Connection connection = database.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, game.getGameName());
            preparedStatement.setString(2, game.getWhiteUsername());
            preparedStatement.setString(3, game.getBlackUsername());
            preparedStatement.setString(4, serializedGame);

            if (preparedStatement.executeUpdate() == 1) {
                System.out.println("Insert: Success!");
            } else {
                System.out.println("Insert: Something unexpected happened. :(");
            }
        } catch (SQLException e) {
            //TODO here I am supposed to grab the exception and then send another exception with the correct
            //message.
            System.out.println(e.getMessage());
            throw new DataAccessException("Error: bad request"); //just an example
        }


//        if(findGameByName(game.getGameName()) != null){ //we found another game inside the db with the same name
//            throw new DataAccessException("Error: there is another game with the same name");
//        }
//        if(!gameIsInDB(game)){
//            gamesDB.add(game);
//        } else {
//            throw new DataAccessException("Error: game is already in DB");
//        }
    }

    /**
     * Tries to find a Game in the DB. If the Game is not in the DB, DataAccessException will be thrown.
     * @param game the Game to be looked for.
     * @return a Game object, in case it's found in the DB.
     * @throws DataAccessException the exception to be thrown in case the Game cannot be found.
     */
    public Game find(String gameName) throws DataAccessException {
        //searching with the gameName
        String sql = "select * from game where gameName = ?";
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(ChessGame.class, new ChessGameDeserializer())
                .create();
        Connection connection = database.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, gameName);
            List<Game> gamesReturned = new ArrayList<>();

            ResultSet resultSet = preparedStatement.executeQuery();


            while (resultSet.next()){
                int gameID = resultSet.getInt(1);
                String game_Name = resultSet.getString(2);
                String whiteUsername = resultSet.getString(3);
                String blackUsername = resultSet.getString(4);
                String gameChess = resultSet.getString(5);

                ChessGameImpl serializedChessGame = (ChessGameImpl) gson.fromJson(gameChess, ChessGame.class);
                gamesReturned.add(new Game(gameID, whiteUsername, blackUsername, game_Name, serializedChessGame));
            }

            if(gamesReturned.size() == 1){
                System.out.println("Find: success.");
            } else if (gamesReturned.size() == 0) {
                throw new DataAccessException("The game was not found in the DB");
            } else {
                throw new DataAccessException("Error: More than one game found.");
            }

            return gamesReturned.get(0);
        } catch (SQLException e) {
            throw new DataAccessException("Error: " + e.getMessage());
        }


//        Game foundGame = findGameByName(game.getGameName());
//
//        if(foundGame == null){
//            throw new DataAccessException("The game was not found in the DB.");
//        }
//
//        if(game.getGameID() != foundGame.getGameID()){
//            throw new DataAccessException("The game " + game.toString() + " was not found in the DB.");
//        } else if (game.getWhiteUsername() != foundGame.getWhiteUsername()){
//            throw new DataAccessException("The game " + game.toString() + " was not found in the DB.");
//        } else if (game.getBlackUsername() != foundGame.getBlackUsername()) {
//            throw new DataAccessException("The game " + game.toString() + " was not found in the DB.");
//        } else if (!game.getGame().equals(foundGame.getGame())) { //failing here
//            throw new DataAccessException("The game " + game.toString() + " was not found in the DB.");
//        }
//        return foundGame;
    }

    /**
     * Tries to return all Games in the DB. If the DB is emtpy, DataAccessException will be thrown.
     * @return a set with a all the Game found in the DB.
     * @throws DataAccessException the exception to be thrown in case the DB does not have any Game.
     */
    public HashSet<Game> findAll() throws DataAccessException{
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(ChessGame.class, new ChessGameDeserializer())
                .create();

        HashSet<Game> gamesInDB = new HashSet<>();
        String sql = "select * from game";

        Connection connection = database.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                int gameID = resultSet.getInt(1);
                String game_Name = resultSet.getString(2);
                String whiteUsername = resultSet.getString(3);
                String blackUsername = resultSet.getString(4);
                String gameChess = resultSet.getString(5);

                ChessGameImpl serializedChessGame = (ChessGameImpl) gson.fromJson(gameChess, ChessGame.class);
                gamesInDB.add(new Game(gameID, whiteUsername, blackUsername, game_Name, serializedChessGame));
            }

            if (gamesInDB.size() == 0){
                throw new DataAccessException("Error: The Game DB is empty.");
            }

            return gamesInDB;
        } catch (SQLException e) {
            throw new DataAccessException("Error: " + e.getMessage());
        }
//        if(!gamesDB.isEmpty()) {
//            return gamesDB;
//        } else {
//            throw new DataAccessException("The Games DB is empty.");
//        }
    }


    /**
     * Tries to update a Game already in the DB.
     * @param gameID the gameID of the Game to be updated in the DB.
     * @param updatedGame the new Game to be inserted into the DB.
     * @throws DataAccessException in case the gameID is not found in the DB.
     */
    public void updateGame(String gameName, String newWhiteUsername, String newBlackUsername, ChessGameImpl newGame)
            throws DataAccessException {

        String sql = "UPDATE game SET whiteUsername=?, blackUsername=?, game=? WHERE gameName=?";
        Gson gson = new Gson();
        String serializedGame = gson.toJson(newGame);
        Connection connection = database.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, newWhiteUsername);
            preparedStatement.setString(2, newBlackUsername);
            preparedStatement.setString(3, serializedGame);
            preparedStatement.setString(4, gameName);

            if (preparedStatement.executeUpdate() == 1) {
                System.out.println("Insert: Success!");
            } else if (preparedStatement.executeUpdate() == 0) {
                System.out.println("Update: nothing was updated.");
                throw new DataAccessException("Error: nothing was updated.");
            } else {
                System.out.println("Insert: Something unexpected happened. :(");
            }
        } catch (SQLException e) {
            //TODO here I am supposed to grab the exception and then send another exception with the correct message.
            System.out.println(e.getMessage());
            throw new DataAccessException("Error: " + e.getMessage()); //just an example
        }


//        if(!gamesDB.isEmpty()){
//            for (Game theGame : gamesDB) {
//                if(theGame.getGameID() == gameID){
//                    remove(theGame);
//                    insert(new Game(gameID, newWhiteUsername, newBlackUsername, newGameName, newGame));
//                    return;
//                }
//            }
//
//            throw new DataAccessException("The DB did not contain a game with the gameID " +
//                    gameID);
//        } else {
//            throw new DataAccessException("The Games DB is empty, therefore nothing can be updated");
//        }

    }

    /**
     * Tries to remove a Game from the DB.
     * @param game the Game to be deleted.
     * @throws DataAccessException in case the Game is not found in the DB.
     */
    public void remove(String gameName) throws DataAccessException{
        String sql = "DELETE FROM game WHERE gameName = ?";

        Connection connection = database.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, gameName);

            if (preparedStatement.executeUpdate() == 1) {
                System.out.println("Remove: Success!");
            } else if (preparedStatement.executeUpdate() == 0) {
                throw new DataAccessException("Error: the user was not in the DB.");
            } else {
                throw new DataAccessException("Error: more than one row was affected.");
            }
        } catch (SQLException e) {
            //TODO here I am supposed to grab the exception and then send another exception with the correct
            //message.
            System.out.println(e.getMessage());
            throw new DataAccessException("Error: " + e.getMessage()); //just an example
        }



//        try {
//            Game gameToRemove = find(game.getGameName());
//            gamesDB.remove(gameToRemove);
//        } catch (DataAccessException e) {
//            throw new DataAccessException("The game " + game.toString() + " could not be removed because it is not " +
//                    "in the DB.");
//        }
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

//    private boolean gameIsInDB(Game game) {
//        try {
//            if (find(game.getGameName()) != null){
//                return true;
//            }
//        } catch (DataAccessException e) {
//            return false;
//        }
//        return false;
//    }
}
