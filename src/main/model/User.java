package model;

/**
 * A class that represents the Users in the server.
 */
public class User {

    /**
     * the User's username
     */
    private String username;

    /**
     * the User's password
     */
    private String password;

    /**
     * the User's email
     */
    private String email;

    /**
     * Constructs a new User object with the parameters passed.
     * @param username value to initialize this.username
     * @param password value to initialize this.password
     * @param email value to initialize this.email
     */
    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    /**
     * Constructs an empy User object for compiling purposes.
     */
    public User(){
    }


    //methods


    /**
     * Returns the username
     * @return username member
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username with the parameter passed.
     * @param username to initialize this.username with.
     */
    public void setUsername(String username) {
        this.username=username;
    }

    /**
     * Returns the password
     * @return password member
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password with the parameter passed.
     * @param password to initialize this.password with.
     */
    public void setPassword(String password) {
        this.password=password;
    }

    /**
     * Returns the email
     * @return email member
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email with the parameter passed.
     * @param email to initialize this.email with.
     */
    public void setEmail(String email) {
        this.email=email;
    }
}
