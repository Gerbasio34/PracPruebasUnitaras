package micromobility.payment;

import exception.NotEnoughWalletException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class WalletPaymentTest {

    private Wallet wallet;
    private WalletPayment walletPayment;

    @BeforeEach
    void setUp() {
        wallet = new Wallet(BigDecimal.valueOf(100));
        walletPayment = new WalletPayment(wallet);
    }

    @Test
    @DisplayName("Test1: Validate that constructor throws IllegalArgumentException if the wallet is null")
    void testConstructorWithNullWallet() {
        assertThrows(IllegalArgumentException.class, () -> new WalletPayment(null));
    }

    @Test
    @DisplayName("Test2: Validate that processPayment deducts the amount correctly from wallet")
    void testProcessPaymentValidAmount() throws NotEnoughWalletException {
        BigDecimal paymentAmount = BigDecimal.valueOf(50);
        walletPayment.processPayment(paymentAmount);
        assertEquals(BigDecimal.valueOf(50), wallet.getBalance());
    }

    @Test
    @DisplayName("Test3: Validate that processPayment throws NotEnoughWalletException if there are not enough funds")
    void testProcessPaymentNotEnoughFunds() {
        BigDecimal paymentAmount = BigDecimal.valueOf(150);
        assertThrows(NotEnoughWalletException.class, () -> walletPayment.processPayment(paymentAmount));
        assertEquals(BigDecimal.valueOf(100), wallet.getBalance());
    }

    @Test
    @DisplayName("Test4: Validate that getWallet returns the correct wallet")
    void testGetWallet() {
        assertEquals(wallet, walletPayment.getWallet());
    }
}