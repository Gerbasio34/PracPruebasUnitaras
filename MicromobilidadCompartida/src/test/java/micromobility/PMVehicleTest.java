package micromobility;

import data.GeographicPoint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;


class PMVehicleTest {

    private PMVehicle vehicle;
    private GeographicPoint location;
    private BufferedImage QRCode;

    // Set up before each test
    @BeforeEach
    void setUp() throws IOException {
        // Initialize objects required for the tests
        location = new GeographicPoint(40.4168f, -3.7038f); // Madrid coordinates
        // Load QR
        vehicle = new PMVehicle("VH-123456-TestVehicle", PMVState.AVAILABLE, location, 80.0, "qrcode-dummy.png");
    }

    // Test1: Verify that the PMVehicle is created successfully with valid parameters
    @Test
    void testVehicleCreation() {
        // Check if the PMVehicle object is correctly initialized
        assertEquals("VehicleID{id='VH-123456-TestVehicle'}", vehicle.getId());
        assertEquals(PMVState.AVAILABLE, vehicle.getState());
        assertEquals(location, vehicle.getLocation());
        assertEquals(80.0, vehicle.getChargeLevel());
        assertNotNull(vehicle.getQRCode());
    }

    // Test2: Verify that setting a new location updates the location of the vehicle
    @Test
    void testSetLocation() {
        GeographicPoint newLocation = new GeographicPoint(41.3851f, 2.1734f); // Barcelona coordinates
        vehicle.setLocation(newLocation);
        assertEquals(newLocation, vehicle.getLocation()); // Check if location is updated
    }

    // Test3: Verify that setting a null location throws an exception
    @Test
    void testSetLocationWithNull() {
        Exception exception = assertThrows(NullPointerException.class, () -> vehicle.setLocation(null));
        assertEquals("Location must be defined cannot be null", exception.getMessage()); // Verify exception message
    }

    // Test4: Verify that the charge level is set correctly and falls within the valid range
    @Test
    void testSetChargeLevel() {
        vehicle.setChargeLevel(50.0); // Set charge level to 50%
        assertEquals(50.0, vehicle.getChargeLevel()); // Check if charge level is updated
    }

    // Test5: Verify that setting an invalid charge level (greater than 100) throws an exception
    @Test
    void testSetChargeLevelInvalid() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> vehicle.setChargeLevel(150.0));
        assertEquals("Charge level must be between 0.0 and 100.0", exception.getMessage()); // Verify exception message
    }

    // Test6: Verify that the state transitions correctly when calling the set methods
    @Test
    void testStateTransitions() {
        vehicle.setNotAvailb();
        assertEquals(PMVState.NOT_AVAILABLE, vehicle.getState()); // Check state transition to NOT_AVAILABLE

        vehicle.setUnderWay();
        assertEquals(PMVState.UNDER_WAY, vehicle.getState()); // Check state transition to UNDER_WAY

        vehicle.setAvailb();
        assertEquals(PMVState.AVAILABLE, vehicle.getState()); // Check state transition to AVAILABLE

        vehicle.setTemporaryParking();
        assertEquals(PMVState.TEMPORARY_PARKING, vehicle.getState()); // Check state transition to TEMPORARY_PARKING
    }

    // Test7: Verify that the vehicle's sensors data is formatted correctly
    @Test
    void testGetSensorsData() {
        String expectedData = """
        Sensor type: Light Sensor: Current light is : OFF
        Sensor type: Temperature Sensor: Current temperature: 20.0°C
        Sensor type: Light Sensor: Current brake is : OFF
        Sensor type: Speed Sensor: Current speed: 0.0km/h
        """;
        assertEquals(expectedData, vehicle.getSensorsData()); // Check if sensors data is returned correctly
    }

    // Test8: Verify that the QRCode setter works correctly
    @Test
    void testSetQRCode() {
        BufferedImage newQRCode = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
        vehicle.setQRCode(newQRCode);
        assertEquals(newQRCode, vehicle.getQRCode()); // Check if QR code is updated
    }
}
