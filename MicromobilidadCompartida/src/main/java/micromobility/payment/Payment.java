package micromobility.payment;

import exception.NotEnoughWalletException;

import java.math.BigDecimal;

public abstract class Payment {
    public Payment() {
    }

    public abstract void processPayment(BigDecimal imp) throws NotEnoughWalletException;

}
