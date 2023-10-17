package responses;

public class LoginResponse extends ErrorResponse {
    private String username;
    private String authToken;

    public LoginResponse(String errorMessage, String username, String authToken) {
        super(errorMessage);
        this.username = username;
        this.authToken = authToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}
