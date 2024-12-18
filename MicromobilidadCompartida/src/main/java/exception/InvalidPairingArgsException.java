package exception;

public class InvalidPairingArgsException extends Throwable {
    private static final String DEFAULT_MESSAGE = "Some of the arguments are incorrect.";

    public InvalidPairingArgsException() {
        super(DEFAULT_MESSAGE);
    }

    public InvalidPairingArgsException(String message) {
        super(message);
    }

    public InvalidPairingArgsException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidPairingArgsException(Throwable cause) {
        super(DEFAULT_MESSAGE, cause);
    }
}
