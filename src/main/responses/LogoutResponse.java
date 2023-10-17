package responses;

/**
 * A Class that represents the JSON response for LogoutService. It extends ErrorResponse to
 * be able to return an error message.
 */
public class LogoutResponse extends ErrorResponse {

    /**
     * Constructs a new object with the errorMessage passed.
     * @param errorMessage is the value super.errorMessage will be set to.
     */
    public LogoutResponse(String errorMessage) {
        super(errorMessage);
    }
}
