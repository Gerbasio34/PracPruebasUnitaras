package micromobility.payment;

import exception.NotEnoughWalletException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class WalletTest {

    private Wallet wallet;

    @BeforeEach
    void setUp() {
        wallet = new Wallet(BigDecimal.valueOf(100));
    }

    @Test
    @DisplayName("Test1: Validate that constructor throws IllegalArgumentException if the initial balance is null or negative")
    void testConstructorWithInvalidInitialBalance() {
        assertThrows(IllegalArgumentException.class, () -> new Wallet(null));
        assertThrows(IllegalArgumentException.class, () -> new Wallet(BigDecimal.valueOf(-10)));
    }

    @Test
    @DisplayName("Test2: Validate that getBalance returns the correct balance")
    void testGetBalance() {
        assertEquals(BigDecimal.valueOf(100), wallet.getBalance());
    }

    @Test
    @DisplayName("Test3: Validate that addFunds increases the balance correctly")
    void testAddFunds() {
        wallet.addFunds(BigDecimal.valueOf(50));
        assertEquals(BigDecimal.valueOf(150), wallet.getBalance());
    }

    @Test
    @DisplayName("Test4: Validate that addFunds throws IllegalArgumentException if the amount is null or non-positive")
    void testAddFundsWithInvalidAmount() {
        assertThrows(IllegalArgumentException.class, () -> wallet.addFunds(null));
        assertThrows(IllegalArgumentException.class, () -> wallet.addFunds(BigDecimal.valueOf(0)));
        assertThrows(IllegalArgumentException.class, () -> wallet.addFunds(BigDecimal.valueOf(-10)));
    }

    @Test
    @DisplayName("Test5: Validate that deduct reduces the balance correctly if there are sufficient funds")
    void testDeductValidAmount() throws NotEnoughWalletException {
        wallet.deduct(BigDecimal.valueOf(50));
        assertEquals(BigDecimal.valueOf(50), wallet.getBalance());
    }

    @Test
    @DisplayName("Test6: Validate that deduct throws NotEnoughWalletException if there are not enough funds")
    void testDeductInsufficientFunds() {
        assertThrows(NotEnoughWalletException.class, () -> wallet.deduct(BigDecimal.valueOf(150)));
        assertEquals(BigDecimal.valueOf(100), wallet.getBalance());
    }

    @Test
    @DisplayName("Test7: Validate that deduct throws IllegalArgumentException if the amount is null or non-positive")
    void testDeductWithInvalidAmount() {
        assertThrows(IllegalArgumentException.class, () -> wallet.deduct(null));
        assertThrows(IllegalArgumentException.class, () -> wallet.deduct(BigDecimal.valueOf(0)));
        assertThrows(IllegalArgumentException.class, () -> wallet.deduct(BigDecimal.valueOf(-10)));
    }
}