package micromobility.payment;

import data.UserAccount;
import exception.NotEnoughWalletException;
import micromobility.JourneyService;

public abstract class Payment {
    protected JourneyService service;
    protected UserAccount user;

    public Payment(JourneyService service, UserAccount user) {
        if (service == null || user == null) {
            throw new IllegalArgumentException("Service and User cannot be null");
        }
        this.service = service;
        this.user = user;
    }

    public abstract void processPayment() throws NotEnoughWalletException;

}
