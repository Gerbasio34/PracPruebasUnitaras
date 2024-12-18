package exception;

public class PMVNotAvailException extends Throwable {

    private static final String DEFAULT_MESSAGE = "PMV no disponible en este momento.";

    public PMVNotAvailException() {
        super(DEFAULT_MESSAGE);
    }

    public PMVNotAvailException(String message) {
        super(message);
    }

    public PMVNotAvailException(String message, Throwable cause) {
        super(message, cause);
    }

    public PMVNotAvailException(Throwable cause) {
        super(DEFAULT_MESSAGE, cause);
    }
}
