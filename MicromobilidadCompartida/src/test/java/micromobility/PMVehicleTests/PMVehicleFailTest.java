package micromobility.PMVehicleTests;

import data.GeographicPoint;
import data.sensors.MockSensorDataA;
import data.sensors.MockSensorDataB;
import data.sensors.SensorData;
import micromobility.PMVState;
import micromobility.PMVehicle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PMVehicleFailTest {

    private PMVehicle vehicle;
    private GeographicPoint location;
    private SensorData sensorMockA = new MockSensorDataA(true);
    private SensorData sensorMockB = new MockSensorDataB(10.2f);
    private

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
        assertEquals(50.0, vehicle.getChargeLevel(), 0.01); // Check if charge level is updated
    }

    // Test5: Verify that setting an invalid charge level throws an exception
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
        """.trim(); // Ajustar al formato real

        String actualData = vehicle.getSensorsData().trim(); // Aseguramos que eliminamos espacios extra
        assertEquals(expectedData, actualData); // Comparamos
    }

    // Test8: Verify that the QRCode setter works correctly
    @Test
    void testSetQRCode() {
        BufferedImage newQRCode = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
        vehicle.setQRCode(newQRCode);
        assertEquals(newQRCode, vehicle.getQRCode()); // Check if QR code is updated
    }
}
