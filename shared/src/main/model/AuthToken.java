package model;

import java.util.Objects;

/**
 * A class that represents the AuthTokens in the server. Authtokens have a String authToken and they a
 * String username they are linked or generated for.
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
    public AuthToken(String username, String token) {
        this.token = token;
        this.username = username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthToken authToken = (AuthToken) o;
        return token.equals(authToken.token) && username.equals(authToken.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(token, username);
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

    @Override
    public String toString() {
        return "AuthToken{" +
                "token='" + token + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
