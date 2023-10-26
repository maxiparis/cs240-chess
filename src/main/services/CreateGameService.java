package services;

import DAO.GameDAO;
import chess.ChessGame;
import chess.ChessGameImpl;
import dataAccess.DataAccessException;
import model.Game;
import requests.CreateGameRequest;
import responses.CreateGameResponse;

/**
 * This class represents an API that creates a new game.
 */
public class CreateGameService {
    private static int gameID = 0;

    /**
     * Creates a new game, using the specifications given by the parameter.
     * @param request includes all the specifications to create the new game.
     * @return a response to the given action.
     */
    public CreateGameResponse createGame (CreateGameRequest request){
        try {
            GameDAO gameDB = new GameDAO();
            Game gameToAdd = new Game();
            //gameToAdd will have ID, name, and a game, but no white/black usernames;
            gameToAdd.setGameName(request.getGameName());
            gameToAdd.setGameID(generateNewGameID());
            gameToAdd.setGame(new ChessGameImpl(ChessGame.TeamColor.WHITE));

            gameDB.insert(gameToAdd);
            return new CreateGameResponse(null, gameToAdd.getGameID());
        } catch (DataAccessException e) {
            return new CreateGameResponse(e.getMessage(), null);
        }
    }


    public int generateNewGameID(){
        gameID++;
        return gameID-1;
    }
}
