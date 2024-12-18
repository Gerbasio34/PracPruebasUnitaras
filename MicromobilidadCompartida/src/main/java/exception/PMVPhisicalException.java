package exception;

public class PMVPhisicalException extends Throwable {
    private static final String DEFAULT_MESSAGE = "Technical issue in the vehicle preventing it from starting.";

    public PMVPhisicalException() {
        super(DEFAULT_MESSAGE);
    }

    public PMVPhisicalException(String message) {
        super(message);
    }

    public PMVPhisicalException(String message, Throwable cause) {
        super(message, cause);
    }

    public PMVPhisicalException(Throwable cause) {
        super(DEFAULT_MESSAGE, cause);
    }
}
