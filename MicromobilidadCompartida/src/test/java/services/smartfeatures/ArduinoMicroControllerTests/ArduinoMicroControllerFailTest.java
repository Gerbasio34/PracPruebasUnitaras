package services.smartfeatures.ArduinoMicroControllerTests;

import exception.PMVPhisicalException;
import exception.ProceduralException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import services.smartfeatures.ArduinoMicroControllerVMP;

import java.net.ConnectException;

import static org.junit.jupiter.api.Assertions.*;

public class ArduinoMicroControllerFailTest {

    private ArduinoMicroControllerVMP controller;

    @BeforeEach
    public void setUp() {
        // Initialize the controller before each test
        controller = new ArduinoMicroControllerVMP();
    }

    @Test
    @DisplayName("Test1: Should throw ConnectException if Bluetooth is already connected")
    public void testSetBTconnectionAlreadyConnected() {
        try {
            controller.setBTconnection();
            controller.setBTconnection();  // Trying to connect again
            fail("Expected ConnectException, but none was thrown");
        } catch (ConnectException e) {
            // Assert that the exception message matches
            assertEquals("Bluetooth connection is already established.", e.getMessage());
        }
    }

    @Test
    @DisplayName("Test2: Should throw ConnectException if Bluetooth is not connected")
    public void testSetBeingDrivenNoConnection() {
        try {
            controller.setVehicleBeingDriven(true);
            fail("Expected ConnectException, but none was thrown");
        } catch (ConnectException e) {
            // Assert that the exception message matches
            assertEquals("Bluetooth connection is not established.", e.getMessage());
        }
    }

    @Test
    @DisplayName("Test3: Should throw ConnectException if Bluetooth is not connected")
    public void testSetBrakingNoConnection() {
        try {
            controller.setBraking(true);  // Driver is braking
            fail("Expected ConnectException, but none was thrown");
        } catch (ConnectException e) {
            // Assert that the exception message matches
            assertEquals("Bluetooth connection is not established.", e.getMessage());
        }
    }
    @Test
    @DisplayName("Test4: Should throw ProceduralException if the vehicle is already in use")
    public void testStartDrivingWithVehicleInUse() throws ConnectException, PMVPhisicalException, ProceduralException {
        controller.setBTconnection();  // Establish Bluetooth connection
        controller.setVehicleBeingDriven(true);  // Ready to start driving
        controller.startDriving();  // First start
        try {
            controller.startDriving();  // Trying to start again
            fail("Expected ProceduralException, but none was thrown");
        } catch (ProceduralException e) {
            // Assert that the exception message matches
            assertEquals("The vehicle is already in use.", e.getMessage());
        }
    }

    @Test
    @DisplayName("Test5: Should throw ConnectException if Bluetooth is not connected when stopping")
    public void testStopDrivingNoConnection() throws ProceduralException, PMVPhisicalException{
        try {
            controller.stopDriving();
            fail("Expected ConnectException, but none was thrown");
        } catch (ConnectException e) {
            // Assert that the exception message matches
            assertEquals("Bluetooth connection is not established.", e.getMessage());
        }
    }

    @Test
    @DisplayName("Test6: Should throw ProceduralException if the vehicle is not being driven")
    public void testStopDrivingVehicleNotBeingDriven() throws ConnectException, PMVPhisicalException {
        controller.setBTconnection();  // Establish Bluetooth connection
        controller.setBraking(true);  // Driver is braking
        try {
            controller.stopDriving();  // Vehicle is not in use yet
            fail("Expected ProceduralException, but none was thrown");
        } catch (ProceduralException e) {
            // Assert that the exception message matches
            assertEquals("The vehicle is not being driven.", e.getMessage());
        }
    }

    @Test
    @DisplayName("Test7: Should throw PMVPhisicalException if there is a technical failure when stopping")
    public void testStopDrivingWithTechnicalFailure() throws ConnectException, ProceduralException, InterruptedException, PMVPhisicalException {
        controller.setBTconnection();  // Establish Bluetooth connection
        controller.setVehicleBeingDriven(true);  // Vehicle is being driven
        controller.startDriving();
        controller.setTechnicalFailure(true);  // Simulate a technical failure (e.g., brake failure)
        try {
            controller.stopDriving();
            fail("Expected PMVPhisicalException, but none was thrown");
        } catch (PMVPhisicalException e) {
            // Assert that the exception message matches
            assertEquals("Technical issue with the brakes, cannot stop the vehicle.", e.getMessage());
        }
    }
}
