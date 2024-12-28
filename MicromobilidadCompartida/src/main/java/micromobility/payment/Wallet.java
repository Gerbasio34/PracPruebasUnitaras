package micromobility.payment;

import exception.NotEnoughWalletException;

import java.math.BigDecimal;

/**
 * Represents a wallet for managing funds in the micromobility system.
 */
public class Wallet {

    /**
     * The current balance of the wallet.
     */
    private BigDecimal balance;

    /**
     * Constructs a {@code Wallet} with the specified initial balance.
     *
     * @param initialBalance the initial balance of the wallet.
     * @throws IllegalArgumentException if the initial balance is null or negative.
     */
    public Wallet(BigDecimal initialBalance) {
        if (initialBalance == null || initialBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Initial balance cannot be null or negative");
        }
        this.balance = initialBalance;
    }

    /**
     * Returns the current balance of the wallet.
     *
     * @return the current balance as a {@code BigDecimal}.
     */
    public BigDecimal getBalance() {
        return balance;
    }

    /**
     * Adds funds to the wallet.
     *
     * @param amount the amount to add.
     * @throws IllegalArgumentException if the amount is null or less than or equal to zero.
     */
    public void addFunds(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount to add must be greater than zero");
        }
        balance = balance.add(amount);
    }

    /**
     * Deducts a specified amount from the wallet.
     *
     * @param amount the amount to deduct.
     * @throws IllegalArgumentException if the amount is null or less than or equal to zero.
     * @throws NotEnoughWalletException if there are insufficient funds in the wallet to complete the deduction.
     */
    public void deduct(BigDecimal amount) throws NotEnoughWalletException {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount to deduct must be greater than zero");
        }
        if (balance.compareTo(amount) < 0) {
            throw new NotEnoughWalletException("Insufficient wallet balance");
        }
        balance = balance.subtract(amount);
    }
}
