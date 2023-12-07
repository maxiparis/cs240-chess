package services;

import DAO.AuthDAO;
import dataAccess.DataAccessException;

public class AuthTokenValidator {

    public static boolean validateAuthToken(String authToken) throws DataAccessException {
        try {
            AuthDAO.getInstance().findWithToken(authToken);
            return true;
        } catch (DataAccessException e) {
            throw new DataAccessException("Error: unauthorized"); //the authToken does not exist
        }


    }
}
