package exception;

public class CorruptedImgException extends Throwable {
    private static final String DEFAULT_MESSAGE = "The image file is corrupted and cannot be processed.";

    public CorruptedImgException() {
        super(DEFAULT_MESSAGE);
    }

    public CorruptedImgException(String message) {
        super(message);
    }

    public CorruptedImgException(String message, Throwable cause) {
        super(message, cause);
    }

    public CorruptedImgException(Throwable cause) {
        super(DEFAULT_MESSAGE, cause);
    }
}
