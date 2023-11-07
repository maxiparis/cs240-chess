package services;

import DAO.AuthDAO;
import dataAccess.DataAccessException;
import model.AuthToken;

import java.util.HashSet;

public class AuthTokenValidator {

    public static boolean validateAuthToken(String authToken) throws DataAccessException {
        try {
            AuthDAO.getInstance().findWithToken(authToken);
            return true;
        } catch (DataAccessException e) {
            throw new DataAccessException("Error: unauthorized"); //the authToken does not exist
        }

//        HashSet<AuthToken> authTokensDB = AuthDAO.getInstance().getAuthTokensDB();
//
//        for (AuthToken token : authTokensDB) {
//            if(token.getToken().equals(authToken)){
//                return true;
//            }
//        }
//
//        throw new DataAccessException("Error: unauthorized"); //the authToken does not exist
    }


//    public static void tryToValidateAuthToken(String authToken) throws DataAccessException {
//        if(!AuthDAO.getInstance().getAuthTokensDB().isEmpty()){
//            validateAuthToken(authToken);
//        } else {
//            throw new DataAccessException("Error: unauthorized"); //empty db
//        }
//    }
}
