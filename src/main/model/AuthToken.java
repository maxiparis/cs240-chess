package model;

/**
 * A class that represents the AuthTokens in the server. Authtokens have a String authToken and they a
 * String usernameb they are linked or generated for.
 */
public class AuthToken {

    /**
     * the token that will be linked to the user
     */
    private String token;

    /**
     * the username linked to the token
     */
    private String username;

    /**
     * Constructs a new AuthToken object, initializing the token and the username.
     * @param token the token to be set.
     * @param username the username linked to the token generated.
     */
    public AuthToken(String token, String username) {
        this.token = token;
        this.username = username;
    }

    /**
     * Constructs a new AuthToken object. Used for compiling purposes.
     */
    public AuthToken() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username=username;
    }
}
