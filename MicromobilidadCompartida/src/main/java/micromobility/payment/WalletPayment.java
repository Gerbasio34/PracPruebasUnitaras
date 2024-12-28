package micromobility.payment;

import data.UserAccount;
import exception.NotEnoughWalletException;
import micromobility.JourneyService;

import java.math.BigDecimal;

/**
 * Represents a payment method that uses a user's wallet balance to process transactions.
 */
public class WalletPayment extends Payment {

    private Wallet wallet;

    /**
     * Constructs a WalletPayment object using the provided wallet.
     *
     * @param wallet the wallet to be associated with this payment method.
     * @throws IllegalArgumentException if the wallet is null.
     */
    public WalletPayment(Wallet wallet) {
        super();
        if (wallet == null) {
            throw new IllegalArgumentException("Wallet cannot be null");
        }
        this.wallet = wallet;
    }

    /**
     * Processes a payment by deducting the specified amount from the wallet.
     *
     * @param imp the amount to be deducted from the wallet.
     * @throws NotEnoughWalletException if the wallet balance is insufficient to cover the payment.
     */
    @Override
    public void processPayment(BigDecimal imp) throws NotEnoughWalletException {
        wallet.deduct(imp);
    }

    /**
     * Gets the wallet associated with this payment method.
     *
     * @return the wallet object.
     */
    public Wallet getWallet() {
        return wallet;
    }
}
