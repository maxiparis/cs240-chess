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
        return new LogoutResponse(null);
    }
}
