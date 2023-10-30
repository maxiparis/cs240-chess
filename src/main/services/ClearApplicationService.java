package services;

import DAO.AuthDAO;
import DAO.GameDAO;
import DAO.UserDAO;
import dataAccess.DataAccessException;
import responses.ClearApplicationResponse;

/**
 * This class represents an API that clears the database. Removes all users, games, and authTokens.
 */
public class ClearApplicationService {
    /**
     * Removes all users, games and authokens from the DB.
     * @return a response sent from the ClearApplicationResponse class.
     */
    public ClearApplicationResponse clearApplication() {
            UserDAO.getInstance().clear();

            AuthDAO.getInstance().clear();

            GameDAO.getInstance().clear();
            return new ClearApplicationResponse(null);

    }
}
