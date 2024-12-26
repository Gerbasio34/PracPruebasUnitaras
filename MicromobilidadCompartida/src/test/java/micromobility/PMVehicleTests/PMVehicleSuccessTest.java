package micromobility.PMVehicleTests;

import data.GeographicPoint;
import data.sensors.*;
import micromobility.PMVState;
import micromobility.PMVehicle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

class PMVehicleSuccessTest {

    private PMVehicle vehicle;
    private GeographicPoint location;
    private SensorData sensorMockA = new MockSensorDataA(true);
    private SensorData sensorMockB = new MockSensorDataB(10.2f);


    // Set up before each test
    @BeforeEach
    void setUp() {
        // Initialize objects required for the tests
        location = new GeographicPoint(40.4168f, -3.7038f); // Madrid coordinates
        ArrayList<SensorData> sensors = new ArrayList<>();
        sensors.add(sensorMockA);
        sensors.add(sensorMockB);

        // Create a PMVehicle instance
        vehicle = new PMVehicle(PMVState.AVAILABLE, location, 80.0, sensors);
    }

    @Test
    @DisplayName("Test1: Verify that the PMVehicle is created successfully with valid parameters")
    void testVehicleCreation() {
        // Check if the PMVehicle object is correctly initialized
        assertNull(vehicle.getId()); // By default, the ID is null until set explicitly
        assertEquals(PMVState.AVAILABLE, vehicle.getState());
        assertEquals(location, vehicle.getLocation());
        assertEquals(80.0, vehicle.getChargeLevel(), 0.01);
        assertNull(vehicle.getQRCode()); // QRCode should be null by default
    }

    @Test
    @DisplayName("Test2: Verify that setting a new location updates the location of the vehicle")
    void testSetLocation() {
        GeographicPoint newLocation = new GeographicPoint(41.3851f, 2.1734f); // Barcelona coordinates
        vehicle.setLocation(newLocation);
        assertEquals(newLocation, vehicle.getLocation()); // Check if location is updated
    }

    @Test
    @DisplayName("Test3: Verify that the charge level is set correctly and falls within the valid range")
    void testSetChargeLevel() {
        vehicle.setChargeLevel(50.0); // Set charge level to 50%
        assertEquals(50.0, vehicle.getChargeLevel(), 0.01); // Check if charge level is updated
    }

    @Test
    @DisplayName("Test4: Verify that the state transitions correctly when calling the set methods")
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

    @Test
    @DisplayName("Test7: Verify that the vehicle's sensors data is formatted correctly")
    void testGetSensorsData() {
        String expectedData = """
        Mock Sensor A: Mock sensor A is : ON
        Mock Sensor B: Mock sensor B : 10.2
        """.trim(); // Ajustar al formato real

        String actualData = vehicle.getSensorsData().trim(); // Aseguramos que eliminamos espacios extra
        assertEquals(expectedData, actualData); // Comparamos
    }

    @Test
    @DisplayName("Test8: Verify that the QRCode setter works correctly")
    void testSetQRCode() {
        BufferedImage newQRCode = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
        vehicle.setQRCode(newQRCode);
        assertEquals(newQRCode, vehicle.getQRCode()); // Check if QR code is updated
    }
}
