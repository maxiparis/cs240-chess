package responses;

public class ErrorResponse {
    private String message;

    public ErrorResponse(String errorMessage) {
        this.message = errorMessage;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
