package data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StationIDTest {

    private StationID validStationID;

    // Set up before each test
    @BeforeEach
    void setUp() {
        validStationID = new StationID("ST-12345-Lleida"); // Common valid StationID for reuse
    }

    // Test1: Verify creation of a valid StationID
    @Test
    void testValidStationIDCreation() {
        assertEquals("ST-12345-Lleida", validStationID.getId()); // Verify the ID is set correctly
    }

    // Test2: Verify exception when StationID format is invalid (missing "ST-" prefix)
    @Test
    void testStationIDWithInvalidFormat() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new StationID("12345-Lleida")); // Missing "ST-"

        // Verify the exception message
        assertEquals("Invalid StationID format. Expected 'ST-12345-name'", exception.getMessage());
    }

    // Test3: Verify exception when StationID is null
    @Test
    void testStationIDWithNullInput() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new StationID(null));

        // Verify the exception message
        assertEquals("StationID cannot be null", exception.getMessage());
    }

    // Test4: Verify exception when StationID is an empty string
    @Test
    void testStationIDWithEmptyString() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new StationID(""));

        // Verify the exception message
        assertEquals("Invalid StationID format. Expected 'ST-12345-name'", exception.getMessage());
    }

    // Test5: Verify regex validation for various valid and invalid StationID formats
    @Test
    void testStationIDRegexValidation() {
        assertDoesNotThrow(() -> new StationID("ST-00001-Test")); // Valid
        assertThrows(IllegalArgumentException.class, () -> new StationID("ST-123-Test123")); // Invalid digits count
        assertThrows(IllegalArgumentException.class, () -> new StationID("ST12345Test")); // No dashes
        assertThrows(IllegalArgumentException.class, () -> new StationID("ST-ABCDE-Test")); // Non-numeric digits
    }

    // Test6: Verify that two StationID objects with the same ID are equal
    @Test
    void testEqualsForSameID() {
        StationID anotherStationID = new StationID("ST-12345-Lleida");
        assertEquals(validStationID, anotherStationID); // Verify they are equal
    }

    // Test7: Verify that two StationID objects with different IDs are not equal
    @Test
    void testEqualsForDifferentID() {
        StationID anotherStationID = new StationID("ST-54321-SecondaryStation");
        assertNotEquals(validStationID, anotherStationID); // Verify they are not equal
    }

    // Test8: Verify that two StationID objects with the same ID have the same hash code
    @Test
    void testHashCodeForSameID() {
        StationID anotherStationID = new StationID("ST-12345-Lleida");
        assertEquals(validStationID.hashCode(), anotherStationID.hashCode()); // Verify same hashCode
    }

    // Test9: Verify that two StationID objects with different IDs have different hash codes
    @Test
    void testHashCodeForDifferentID() {
        StationID anotherStationID = new StationID("ST-54321-SecondaryStation");
        assertNotEquals(validStationID.hashCode(), anotherStationID.hashCode()); // Verify different hashCodes
    }

    // Test10: Verify the correct string representation of a StationID object
    @Test
    void testToString() {
        String expected = "StationID{id='ST-12345-Lleida'}";
        assertEquals(expected, validStationID.toString()); // Verify the toString representation
    }
}
