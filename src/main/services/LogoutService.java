package services;

import responses.LogoutResponse;

/**
 * This class represents an API that logs out the user.
 */
public class LogoutService {

    /**
     * Logs out the user.
     * @return a reponse to the action.
     */
    public LogoutResponse logout () {
        try {
            //TODO

        } catch (Exception e) {
            return new LogoutResponse(null);
        }
        return new LogoutResponse(null);
    }
}
