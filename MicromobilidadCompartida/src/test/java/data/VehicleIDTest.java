package data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class VehicleIDTest {

    private VehicleID validVehicleID;

    // Before each test, create a valid VehicleID instance
    @BeforeEach
    void setUp() {
        validVehicleID = new VehicleID("VH-123456-Auto");
    }

    // Test 1: Constructor with a valid ID
    @Test
    void testValidVehicleID() {
        assertNotNull(validVehicleID); // Ensure object is created
        assertEquals("VH-123456-Auto", validVehicleID.getId()); // Check that the ID is correct
    }

    // Test 2: Constructor with a null ID (should throw IllegalArgumentException)
    @Test
    void testNullID() {
        assertThrows(IllegalArgumentException.class, () -> {
            new VehicleID(null); // Should throw an exception because ID is null
        });
    }

    // Test 3: Constructor with an invalid format (should throw IllegalArgumentException)
    @Test
    void testInvalidIDFormat() {
        assertThrows(IllegalArgumentException.class, () -> {
            new VehicleID("123456-Auto"); // Invalid format (no "VH-" prefix)
        });
    }

    // Test 4: Constructor with an invalid format (wrong number of digits)
    @Test
    void testInvalidIDWrongNumberOfDigits() {
        assertThrows(IllegalArgumentException.class, () -> {
            new VehicleID("VH-12345-Auto"); // Invalid format (only 5 digits)
        });
    }

    // Test 5: Constructor with a valid ID and a long name part (upper limit)
    @Test
    void testValidVehicleIDLongName() {
        VehicleID vehicleID = new VehicleID("VH-123456-ABCDEFGHJK"); // Valid ID with long name part
        assertNotNull(vehicleID); // Ensure object is created
        assertEquals("VH-123456-ABCDEFGHJK", vehicleID.getId());
    }

    // Test 6: Equals method with equal VehicleIDs
    @Test
    void testEqualsWithEqualVehicleIDs() {
        VehicleID vehicleID2 = new VehicleID("VH-123456-Auto");
        assertTrue(validVehicleID.equals(vehicleID2)); // IDs should be equal
    }

    // Test 7: Equals method with different VehicleIDs
    @Test
    void testEqualsWithDifferentVehicleIDs() {
        VehicleID vehicleID2 = new VehicleID("VH-654321-Truck");
        assertFalse(validVehicleID.equals(vehicleID2)); // IDs should not be equal
    }

    // Test 8: Equals method with null object
    @Test
    void testEqualsWithNull() {
        assertFalse(validVehicleID.equals(null)); // Should return false when compared with null
    }

    // Test 9: Equals method with object of different class
    @Test
    void testEqualsWithDifferentClass() {
        String otherObject = "Not a VehicleID";
        assertFalse(validVehicleID.equals(otherObject)); // Should return false when compared with an object of different class
    }

    // Test 10: HashCode method with equal VehicleIDs
    @Test
    void testHashCodeWithEqualVehicleIDs() {
        VehicleID vehicleID2 = new VehicleID("VH-123456-Auto");
        assertEquals(validVehicleID.hashCode(), vehicleID2.hashCode()); // Same IDs should have same hashCode
    }

    // Test 11: HashCode method with different VehicleIDs
    @Test
    void testHashCodeWithDifferentVehicleIDs() {
        VehicleID vehicleID2 = new VehicleID("VH-654321-Truck");
        assertNotEquals(validVehicleID.hashCode(), vehicleID2.hashCode()); // Different IDs should have different hashCodes
    }

    // Test 12: toString method
    @Test
    void testToString() {
        assertEquals("VehicleID{id='VH-123456-Auto'}", validVehicleID.toString()); // Verify string representation
    }
}
