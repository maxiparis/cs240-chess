package model;

/**
 * A class that represents the AuthTokens in the server. Authtokens have a String authToken and they a String username
 * they are linked or generated for.
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

    /**
     * Gets the token member.
     * @return the token member of the object being called.
     */
    public String getToken() {
        return token;
    }

    /**
     * Sets the token member with the parameter being passed.
     * @param token will set the class member.
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * Gets the username member.
     * @return the username member of the object being called.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username member to the parameter being passed.
     * @param username string to be passed to the username of this object.
     */
    public void setUsername(String username) {
        this.username=username;
    }
}
