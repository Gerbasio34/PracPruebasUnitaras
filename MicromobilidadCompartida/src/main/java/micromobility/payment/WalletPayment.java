package micromobility.payment;

import data.UserAccount;
import exception.NotEnoughWalletException;
import micromobility.JourneyService;

import java.math.BigDecimal;

public class WalletPayment extends Payment {
    private Wallet wallet;

    public WalletPayment(JourneyService service, UserAccount user, Wallet wallet) {
        super(service, user);
        if (wallet == null) {
            throw new IllegalArgumentException("Wallet cannot be null");
        }
        this.wallet = wallet;
    }

    @Override
    public void processPayment() throws NotEnoughWalletException {
        BigDecimal serviceCost = service.getImportCost();
        wallet.deduct(serviceCost);
    }

    public Wallet getWallet() {
        return wallet;
    }
}
