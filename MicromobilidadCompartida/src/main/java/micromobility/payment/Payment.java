package micromobility.payment;

import exception.NotEnoughWalletException;

import java.math.BigDecimal;

/**
 * Represents an abstract base class for processing payments in the micromobility system.
 */
public abstract class Payment {

    /**
     * Default constructor for the {@code Payment} class.
     */
    public Payment() {
    }

    /**
     * Processes a payment with the specified amount.
     *
     * @param imp the amount to process as a {@code BigDecimal}.
     * @throws NotEnoughWalletException if there are insufficient funds in the wallet to complete the payment.
     */
    public abstract void processPayment(BigDecimal imp) throws NotEnoughWalletException;

}

