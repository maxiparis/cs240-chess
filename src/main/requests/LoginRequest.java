package requests;

/**
 * A class to represent the JSON model passed to the LoginService
 */
public class LoginRequest {
    /**
     * the username of the user trying to log in
     */
    private String username;

    /**
     * the password of the user trying to log in
     */
    private String password;

    /**
     * Constructs a new objecti with the parameters passed.
     * @param username is the value this.username will be initialized to.
     * @param password is the value this.password will be initialized to.
     */
    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * @return the username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username to the parameter value.
     * @param username is the value this.username will be assigned to.
     */
    public void setUsername(String username) {
        this.username=username;
    }

    /**
     * @return the password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password to the parameter value.
     * @param password is the value this.password will be assigned to.
     */
    public void setPassword(String password) {
        this.password=password;
    }
}
