package data;

import micromobility.payment.Wallet;

import java.util.Objects;

final public class UserAccount {
    private final String id;
    private Wallet userWallet;

    public UserAccount(String id) {

        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null");
        }

        //Regex -> https://regex101.com/
        if (!id.matches("UA-[a-zA-Z]{1,10}-\\d{1,5}")) {
            throw new IllegalArgumentException("Invalid StationID format. Expected 'UA-username-max5numbers'");
        }
        this.id = id;
    }

    // Getters
    public String getId() {
        return id;
    }
    public Wallet getUserWallet() {
        return userWallet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserAccount that = (UserAccount) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "UserAccount{" + "id='" + id + '\'' + '}';
    }
}
