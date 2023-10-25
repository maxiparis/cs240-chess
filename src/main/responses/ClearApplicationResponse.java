package responses;

/**
 * A Class that represents the JSON response for ClearApplicationService. It extends ErrorResponse to
 * be able to return an error message.
 */
public class ClearApplicationResponse extends ErrorResponse {

    /**
     * Constructs a new object with the parameter passed.
     * @param errorMessage initializes super(errorMessage)
     */
    public ClearApplicationResponse(String errorMessage) {
        super(errorMessage);
    }


}
