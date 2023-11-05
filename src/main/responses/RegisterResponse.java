package responses;

/**
 * A Class that represents the JSON response for RegisterService. It extends ErrorResponse to
 * be able to return an error message.
 */
public class RegisterResponse extends ErrorResponse {
    /**
     * the username of the registerd user.
     */
    private String username;

    /**
     * the authToken of the registerd user.
     */
    private String authToken;

    /**
     * Constructs a new object with the parameters passed.
     * @param errorMessage is the value this.errorMessage will be set to.
     * @param username is the value this.username will be set to.
     * @param authToken is the value this.authToken will be set to.
     */
    public RegisterResponse(String errorMessage, String username, String authToken) {
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
        this.username=username;
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
        this.authToken=authToken;
    }
}
