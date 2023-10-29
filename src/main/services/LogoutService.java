package services;

import DAO.AuthDAO;
import model.AuthToken;
import responses.LogoutResponse;

/**
 * This class represents an API that logs out the user.
 */
public class LogoutService extends AuthTokenValidator{

    /**
     * Logs out the user.
     * @param authToken will be the authToken the request sens to the handler, and the handler sends it to the service.
     *                  The service checks that it is valid.
     * @return a reponse to the action.
     */
    public LogoutResponse logout (String authToken) {
        try {
            tryToValidateAuthToken(authToken);
            AuthToken token = AuthDAO.getInstance().findWithAuthToken(authToken);
            AuthDAO.getInstance().remove(token);
            return new LogoutResponse(null);
        } catch (Exception e) {
            return new LogoutResponse(e.getMessage());
        }
    }
}
