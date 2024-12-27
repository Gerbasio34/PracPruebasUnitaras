package exception;

public class NotEnoughWalletException extends Throwable {
    private static final String DEFAULT_MESSAGE = "Insufficient funds in the wallet to complete the transaction.";

    public NotEnoughWalletException() {
        super(DEFAULT_MESSAGE);
    }

    public NotEnoughWalletException(String message) {
        super(message);
    }

    public NotEnoughWalletException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotEnoughWalletException(Throwable cause) {
        super(DEFAULT_MESSAGE, cause);
    }
}
