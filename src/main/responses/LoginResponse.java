package responses;

/**
 * A Class that represents the JSON response for LoginService. It extends ErrorResponse to
 * be able to return an error message.
 */
public class LoginResponse extends ErrorResponse {
    /**
     * the username of the user trying to log in.
     */
    private String username;

    /**
     * the authToken of the user trying to log in.
     */
    private String authToken;

    /**
     * Constructs a new object with the parameters passed.
     * @param errorMessage intializes this.errorMessage to this value
     * @param username intializes this.username to this value
     * @param authToken intializes this.authToken to this value
     */
    public LoginResponse(String errorMessage, String username, String authToken) {
        super(errorMessage);
        this.username = username;
        this.authToken = authToken;
    }

    /**
     * @return the username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username to the value passed in the parameter.
     * @param username is the value this.username will be set to.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the authToken.
     */
    public String getAuthToken() {
        return authToken;
    }

    /**
     * Sets the authToken member to the value passed in the parameter.
     * @param authToken is the value this.authToken will be set to.
     */
    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}
