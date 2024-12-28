package data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class VehicleIDTest {

    private VehicleID validVehicleID;

    // Before each test, create a valid VehicleID instance
    @BeforeEach
    void setUp() {
        validVehicleID = new VehicleID("VH-123456-Auto");
    }

    @Test
    @DisplayName("Test1: Constructor with a valid ID")
    void testValidVehicleID() {
        assertNotNull(validVehicleID); // Ensure object is created
        assertEquals("VH-123456-Auto", validVehicleID.getId()); // Check that the ID is correct
    }

    @Test
    @DisplayName("Test2: Constructor with a null ID (should throw IllegalArgumentException)")
    void testNullID() {
        assertThrows(IllegalArgumentException.class, () -> {
            new VehicleID(null); // Should throw an exception because ID is null
        });
    }

    @Test
    @DisplayName("Test3: Constructor with an invalid format (should throw IllegalArgumentException)")
    void testInvalidIDFormat() {
        assertThrows(IllegalArgumentException.class, () -> {
            new VehicleID("123456-Auto"); // Invalid format (no "VH-" prefix)
        });
    }

    @Test
    @DisplayName("Test4: Constructor with an invalid format (wrong number of digits)")
    void testInvalidIDWrongNumberOfDigits() {
        assertThrows(IllegalArgumentException.class, () -> {
            new VehicleID("VH-12345-Auto"); // Invalid format (only 5 digits)
        });
    }

    @Test
    @DisplayName("Test5: Constructor with a valid ID and a long name part (upper limit)")
    void testValidVehicleIDLongName() {
        VehicleID vehicleID = new VehicleID("VH-123456-ABCDEFGHJK"); // Valid ID with long name part
        assertNotNull(vehicleID); // Ensure object is created
        assertEquals("VH-123456-ABCDEFGHJK", vehicleID.getId());
    }

    @Test
    @DisplayName("Test6: Equals method with equal VehicleIDs")
    void testEqualsWithEqualVehicleIDs() {
        VehicleID vehicleID2 = new VehicleID("VH-123456-Auto");
        assertTrue(validVehicleID.equals(vehicleID2)); // IDs should be equal
    }

    @Test
    @DisplayName("Test7: Equals method with different VehicleIDs")
    void testEqualsWithDifferentVehicleIDs() {
        VehicleID vehicleID2 = new VehicleID("VH-654321-Truck");
        assertFalse(validVehicleID.equals(vehicleID2)); // IDs should not be equal
    }

    @Test
    @DisplayName("Test8: Equals method with null object")
    void testEqualsWithNull() {
        assertFalse(validVehicleID.equals(null)); // Should return false when compared with null
    }

    @Test
    @DisplayName("Test9: Equals method with object of different class")
    void testEqualsWithDifferentClass() {
        String otherObject = "Not a VehicleID";
        assertFalse(validVehicleID.equals(otherObject)); // Should return false when compared with an object of different class
    }

    @Test
    @DisplayName("Test10: HashCode method with equal VehicleIDs")
    void testHashCodeWithEqualVehicleIDs() {
        VehicleID vehicleID2 = new VehicleID("VH-123456-Auto");
        assertEquals(validVehicleID.hashCode(), vehicleID2.hashCode()); // Same IDs should have same hashCode
    }

    @Test
    @DisplayName("Test11: HashCode method with different VehicleIDs")
    void testHashCodeWithDifferentVehicleIDs() {
        VehicleID vehicleID2 = new VehicleID("VH-654321-Truck");
        assertNotEquals(validVehicleID.hashCode(), vehicleID2.hashCode()); // Different IDs should have different hashCodes
    }

    @Test
    @DisplayName("Test12: toString method")
    void testToString() {
        assertEquals("VehicleID{id='VH-123456-Auto'}", validVehicleID.toString()); // Verify string representation
    }
}
