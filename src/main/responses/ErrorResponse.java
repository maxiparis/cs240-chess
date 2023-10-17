package responses;

/**
 * A super class that represents the JSON response for an error message.
 */
public class ErrorResponse {

    /**
     * Represents the message the client will receive.
     */
    private String message;

    /**
     * Constructs a new object, with the message passed as the parameter.
     * @param errorMessage
     */
    public ErrorResponse(String errorMessage) {
        this.message = errorMessage;
    }

    /**
     * @return the error message.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the message to the value of the parameter.
     * @param message is the value this.message will be set to.
     */
    public void setMessage(String message) {
        this.message = message;
    }
}
