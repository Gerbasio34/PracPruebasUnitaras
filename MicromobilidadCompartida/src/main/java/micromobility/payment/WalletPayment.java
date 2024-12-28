package micromobility.payment;

import data.UserAccount;
import exception.NotEnoughWalletException;
import micromobility.JourneyService;

import java.math.BigDecimal;

public class WalletPayment extends Payment {
    private Wallet wallet;

    public WalletPayment(Wallet wallet) {
        super();
        if (wallet == null) {
            throw new IllegalArgumentException("Wallet cannot be null");
        }
        this.wallet = wallet;
    }

    @Override
    public void processPayment(BigDecimal imp) throws NotEnoughWalletException {
        wallet.deduct(imp);
    }

    public Wallet getWallet() {
        return wallet;
    }
}
