package data;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Random;

/**
 * Essential data classes
 */
final public class UserAccount {
    private final String id;
    private String username;
    private String password;
    private final LocalDate dateCreation;

    public UserAccount(String username, String password) {

        // HACER SISTEMA DE HASH PARA LA PASSWORD
        if (username == null) {
            throw new IllegalArgumentException("Username cannot be null");
        }
        if (password == null) {
            throw new IllegalArgumentException("Password cannot be null");
        }

        int randomSixDigitNumber = 100000 + new Random().nextInt(900000);
        this.id = String.format("UA-%s-%d", username, randomSixDigitNumber);;
        this.username = username;
        this.password = password;
        this.dateCreation = LocalDate.now();
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public LocalDate getDateCreation() {
        return dateCreation;
    }

    // Setters
    public void setUsername(String username) {
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        this.username = username;
    }

    public void setPassword(String password) {
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        this.password = password;
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
        return "UserAccount{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", dateCreation=" + dateCreation +
                '}';
    }
}
