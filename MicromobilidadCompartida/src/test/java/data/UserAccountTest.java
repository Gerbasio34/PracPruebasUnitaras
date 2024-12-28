package data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserAccountTest {

    private UserAccount validUserAccount;

    // Set up before each test
    @BeforeEach
    void setUp() {
        validUserAccount = new UserAccount("UA-johnsmith-12345"); // Common valid UserAccount for reuse
    }

    @Test
    @DisplayName("Test1: Verify creation of a valid UserAccount")
    void testValidUserAccountCreation() {
        assertEquals("UA-johnsmith-12345", validUserAccount.getId()); // Verify the ID is set correctly
    }

    @Test
    @DisplayName("Test2: Verify exception when UserAccount format is invalid (missing \"UA-\" prefix)")
    void testUserAccountWithInvalidFormat() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new UserAccount("johnsmith-12345")); // Missing "UA-"

        // Verify the exception message
        assertEquals("Invalid StationID format. Expected 'UA-username-max5numbers'", exception.getMessage());
    }

    @Test
    @DisplayName("Test3: Verify exception when UserAccount ID is null")
    void testUserAccountWithNullInput() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new UserAccount(null));

        // Verify the exception message
        assertEquals("Id cannot be null", exception.getMessage());
    }

    @Test
    @DisplayName("Test4: Verify exception when UserAccount ID is an empty string")
    void testUserAccountWithEmptyString() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new UserAccount(""));

        // Verify the exception message
        assertEquals("Invalid StationID format. Expected 'UA-username-max5numbers'", exception.getMessage());
    }

    @Test
    @DisplayName("Test5: Verify regex validation for various valid and invalid UserAccount formats")
    void testUserAccountRegexValidation() {
        assertDoesNotThrow(() -> new UserAccount("UA-john-1"));       // Valid
        assertDoesNotThrow(() -> new UserAccount("UA-user-54321")); // Valid
        assertThrows(IllegalArgumentException.class, () -> new UserAccount("UA-username-123456")); // Numbers > 5
        assertThrows(IllegalArgumentException.class, () -> new UserAccount("UA-username123-123")); // Alphanumeric username
        assertThrows(IllegalArgumentException.class, () -> new UserAccount("UA-username-"));       // Missing numbers
        assertThrows(IllegalArgumentException.class, () -> new UserAccount("UA--12345"));          // Missing username
    }

    @Test
    @DisplayName("Test6: Verify that two UserAccount objects with the same ID are equal")
    void testEqualsForSameID() {
        UserAccount user2 = new UserAccount("UA-johnsmith-12345");
        assertEquals(validUserAccount, user2); // Verify they are equal
    }

    @Test
    @DisplayName("Test7: Verify that two UserAccount objects with different IDs are not equal")
    void testEqualsForDifferentID() {
        UserAccount user2 = new UserAccount("UA-janedoe-54321");
        assertNotEquals(validUserAccount, user2); // Verify they are not equal
    }

    @Test
    @DisplayName("Test8: Verify that two UserAccount objects with the same ID have the same hash code")
    void testHashCodeForSameID() {
        UserAccount user2 = new UserAccount("UA-johnsmith-12345");
        assertEquals(validUserAccount.hashCode(), user2.hashCode()); // Verify same hashCode
    }

    @Test
    @DisplayName("Test9: Verify that two UserAccount objects with different IDs have different hash codes")
    void testHashCodeForDifferentID() {
        UserAccount user2 = new UserAccount("UA-janedoe-54321");
        assertNotEquals(validUserAccount.hashCode(), user2.hashCode()); // Verify different hashCodes
    }

    @Test
    @DisplayName("Test10: Verify the correct string representation of a UserAccount object")
    void testToString() {
        String expected = "UserAccount{id='UA-johnsmith-12345'}";
        assertEquals(expected, validUserAccount.toString()); // Verify the toString representation
    }
}
