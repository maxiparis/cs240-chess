package responses;

/**
 * A Class that represents the JSON response for JoinGameService. It extends ErrorResponse to
 * be able to return an error message.
 */
public class JoinGameResponse extends ErrorResponse {
    /**
     * Constructs a new object, using the parameter passed.
     * @param errorMessage is the value super.errorMessage will be set to.
     */
    public JoinGameResponse(String errorMessage) {
        super(errorMessage);
    }
}
