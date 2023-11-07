package services;

import DAO.AuthDAO;
import DAO.GameDAO;
import chess.ChessGame;
import chess.ChessPiece;
import dataAccess.DataAccessException;
import model.AuthToken;
import model.Game;
import requests.JoinGameRequest;
import responses.JoinGameResponse;

import static chess.ChessGame.TeamColor.BLACK;
import static chess.ChessGame.TeamColor.WHITE;

/**
 * This class represents an API that joins a player to a game.
 */
public class JoinGameService extends AuthTokenValidator {

    /**
     * Joins a player to the game
     * @param request includes the what team and game to join.
     * @return a response to the given action.
     */
    public JoinGameResponse joinGame (JoinGameRequest request, String authToken){
        try {
            validateAuthToken(authToken);
            Game foundGame = GameDAO.getInstance().findGameById(request.getGameID());

            ChessGame.TeamColor callerColorRequest = request.getPlayerColor();

            if(callerColorRequest == null){ //if the caller did not specify a color, then is spectator
                return new JoinGameResponse(null); //sending back a valid response
            }

            AuthToken callerToken = AuthDAO.getInstance().findWithToken(authToken);

            if (callerColorRequest.equals(WHITE)) {
                if(foundGame.getWhiteUsername() != null){
//                if(foundGame.getWhiteUsername() != null || foundGame.getWhiteUsername() != ""){
                    return new JoinGameResponse("Error: already taken");
                } else {
                    foundGame.setWhiteUsername(callerToken.getUsername());
                }
            } else if(callerColorRequest.equals(BLACK)){
                if(foundGame.getBlackUsername() != null){
//                if(foundGame.getBlackUsername() != null || foundGame.getBlackUsername() != ""){
                    return new JoinGameResponse("Error: already taken");
                } else {
                    foundGame.setBlackUsername(callerToken.getUsername());
                }
            }
            return new JoinGameResponse(null);
        } catch (DataAccessException e) {
            return new JoinGameResponse(e.getMessage());
        }
    }
}
