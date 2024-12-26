package micromobility.PMVehicleTests;

import data.GeographicPoint;
import data.sensors.MockSensorDataA;
import data.sensors.MockSensorDataB;
import data.sensors.SensorData;
import micromobility.PMVState;
import micromobility.PMVehicle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PMVehicleFailTest {

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

    //
    @Test
    @DisplayName("Test1: Verify that setting a null location throws an exception")
    void testSetLocationWithNull() {
        Exception exception = assertThrows(NullPointerException.class, () -> vehicle.setLocation(null));
        assertEquals("Location must be defined cannot be null", exception.getMessage()); // Verify exception message
    }

    @Test
    @DisplayName("Test2: Verify that setting an invalid charge level throws an exception")
    void testSetChargeLevelInvalid() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> vehicle.setChargeLevel(150.0));
        assertEquals("Charge level must be between 0.0 and 100.0", exception.getMessage()); // Verify exception message
    }


}
