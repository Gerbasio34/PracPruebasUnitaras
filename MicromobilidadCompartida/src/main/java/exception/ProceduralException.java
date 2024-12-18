package exception;

public class ProceduralException extends Throwable {
    private static final String DEFAULT_MESSAGE = "Procedural error occurred, process cannot continue as expected.";

    public ProceduralException() {
        super(DEFAULT_MESSAGE);
    }

    public ProceduralException(String message) {
        super(message);
    }

    public ProceduralException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProceduralException(Throwable cause) {
        super(DEFAULT_MESSAGE, cause);
    }
}
