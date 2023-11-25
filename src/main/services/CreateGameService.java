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
public class CreateGameService extends AuthTokenValidator{
    private static int gameID = 1;


    /**
     * Creates a new game, using the specifications given by the parameter.
     * @param request includes all the specifications to create the new game.
     * @return a response to the given action.
     */
    public CreateGameResponse createGame (CreateGameRequest request, String authToken){
        try {
            validateAuthToken(authToken);
            Game gameToAdd = new Game();
            //gameToAdd will have ID, name, and a game, but no white/black usernames;
            gameToAdd.setGameName(request.getGameName());
            gameToAdd.setGameID(0);

            ChessGameImpl game = new ChessGameImpl(ChessGame.TeamColor.WHITE);
            game.getBoard().resetBoard();
            gameToAdd.setGame(game);

            GameDAO.getInstance().insert(gameToAdd);
            int gameIDofGameJustAdded = GameDAO.getInstance().find(request.getGameName()).getGameID();

            return new CreateGameResponse(null, gameIDofGameJustAdded);
        } catch (DataAccessException e) {
            gameID--;
            return new CreateGameResponse(e.getMessage(), null);
        }
    }


    public int generateNewGameID(){
        gameID++;
        return (gameID-1);
    }
}
