package data.sensors;

import micromobility.PMVehicle;
import data.GeographicPoint;
import micromobility.PMVState;

import java.util.ArrayList;

public class PMVehicle_sensorsTest {
    public static void main(String[] args) {
        // Create mock sensors
        MockSensorBrake mockBrake = new MockSensorBrake(true); // Mock for brake sensor
        MockSensorLight mockLight = new MockSensorLight(false); // Mock for light sensor
        MockSensorSpeed mockSpeed = new MockSensorSpeed(25.5); // Mock for speed sensor
        MockSensorTemperature mockTemperature = new MockSensorTemperature(22.5); // Mock for temperature sensor

        // Add mock sensors to a list
        ArrayList<SensorData> mockSensors = new ArrayList<>();
        mockSensors.add(mockBrake);
        mockSensors.add(mockLight);
        mockSensors.add(mockSpeed);
        mockSensors.add(mockTemperature);

        // Create a PMVehicle instance with mock sensors
        PMVehicle vehicle = new PMVehicle(
                "VH-123456-gerbasiou",
                PMVState.AVAILABLE,
                new GeographicPoint(40.7128f, -74.0060f), // Updated constructor with float values
                75.0,
                null,
                mockSensors
        );

        // Test sensor data output
        System.out.println(vehicle.getSensorsData());
    }
}
