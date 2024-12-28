package micromobility.payment;

import exception.NotEnoughWalletException;

import java.math.BigDecimal;

public class Wallet {
    private BigDecimal balance;

    public Wallet(BigDecimal initialBalance) {
        if (initialBalance == null || initialBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Initial balance cannot be null or negative");
        }
        this.balance = initialBalance;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void addFunds(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount to add must be greater than zero");
        }
        balance = balance.add(amount);
    }

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