package data;

import micromobility.payment.Wallet;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Represents a user account in the system, identified by a unique ID and associated with a wallet.
 */
final public class UserAccount {

    /**
     * The unique identifier for the user account.
     */
    private final String id;

    /**
     * The wallet associated with the user account for managing funds.
     */
    private Wallet userWallet;

    /**
     * Constructs a {@code UserAccount} with the specified unique ID.
     *
     * @param id the unique identifier for the user account, which must follow the format "UA-username-max5numbers".
     * @throws IllegalArgumentException if the {@code id} is null or does not match the required format.
     */
    public UserAccount(String id) {

        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null");
        }

        // Regex -> https://regex101.com/
        if (!id.matches("UA-[a-zA-Z]{1,10}-\\d{1,5}")) {
            throw new IllegalArgumentException("Invalid StationID format. Expected 'UA-username-max5numbers'");
        }
        this.id = id;
        this.userWallet = new Wallet(new BigDecimal(0));
    }

    /**
     * Returns the unique identifier of the user account.
     *
     * @return the user account ID.
     */
    public String getId() {
        return id;
    }

    /**
     * Returns the wallet associated with the user account.
     *
     * @return the user's wallet.
     */
    public Wallet getUserWallet() {
        return userWallet;
    }

    /**
     * Compares this {@code UserAccount} to the specified object for equality.
     *
     * @param o the object to compare with.
     * @return {@code true} if the specified object is equal to this {@code UserAccount}; {@code false} otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserAccount that = (UserAccount) o;
        return Objects.equals(id, that.id);
    }

    /**
     * Returns the hash code for this {@code UserAccount}.
     *
     * @return the hash code.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /**
     * Returns a string representation of this {@code UserAccount}.
     *
     * @return a string representation in the format "UserAccount{id='identifier'}".
     */
    @Override
    public String toString() {
        return "UserAccount{" + "id='" + id + '\'' + '}';
    }
}
