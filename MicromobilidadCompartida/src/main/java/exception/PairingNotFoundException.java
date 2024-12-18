package exception;

public class PairingNotFoundException extends Throwable {
    private static final String DEFAULT_MESSAGE = "The information associated with that pairing is not available on the server.";

    public PairingNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

    public PairingNotFoundException(String message) {
        super(message);
    }

    public PairingNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public PairingNotFoundException(Throwable cause) {
        super(DEFAULT_MESSAGE, cause);
    }
}
