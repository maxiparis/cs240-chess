package requests;

/**
 * A class to represent the JSON model passed to the RegisterService
 */
public class RegisterRequest {
    /**
     * the username of the user trying to register.
     */
    private String username;

    /**
     * the password of the user trying to register.
     */
    private String password;

    /**
     * the email of the user trying to register.
     */
    private String email;

    /**
     * Constructs a new object using the parameters passed.
     * @param username is the value this.username will be initialized to.
     * @param password is the value this.password will be initialized to.
     * @param email is the value this.email will be initialized to.
     */
    public RegisterRequest(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
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
        this.username = username;
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

    /**
     * @return the email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email to the parameter value.
     * @param email is the value this.email will be assigned to.
     */
    public void setEmail(String email) {
        this.email=email;
    }
}
