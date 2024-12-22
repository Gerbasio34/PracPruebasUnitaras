package services.smartfeatures;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.net.ConnectException;
import exception.PMVPhisicalException;
import exception.ProceduralException;

public class ArduinoMicroControllerVMPTest {

    private ArduinoMicroControllerVMP controller;

    @BeforeEach
    public void setUp() {
        // Initialize the controller before each test
        controller = new ArduinoMicroControllerVMP();
    }

    @Test
    // Test1: Should establish a Bluetooth connection successfully
    public void testSetBTconnectionValid() throws ConnectException {
        controller.setBTconnection();
        // Assert that Bluetooth is connected
        assertTrue(controller.getIsBtConnected());
    }

    @Test
    // Test2: Should throw ConnectException if Bluetooth is already connected
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
    // Test3: Should throw ConnectException if Bluetooth is not connected
    public void testStartDrivingNoConnection() {
        controller.setIsVehicleBeingDriven(true);  // Vehicle is ready to start
        try {
            controller.startDriving();
            fail("Expected ConnectException, but none was thrown");
        } catch (ConnectException e) {
            // Assert that the exception message matches
            assertEquals("Bluetooth connection is not established.", e.getMessage());
        }
    }

    @Test
    // Test4: Should throw ProceduralException if the vehicle is already in use
    public void testStartDrivingWithVehicleInUse() throws ConnectException, PMVPhisicalException, ProceduralException {
        controller.setBTconnection();  // Establish Bluetooth connection
        controller.setIsVehicleBeingDriven(true);  // Ready to start driving
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
    // Test5: Should throw ConnectException if Bluetooth is not connected when stopping
    public void testStopDrivingNoConnection() {
        controller.setIsBraking(true);  // Driver is braking
        try {
            controller.stopDriving();
            fail("Expected ConnectException, but none was thrown");
        } catch (ConnectException e) {
            // Assert that the exception message matches
            assertEquals("Bluetooth connection is not established.", e.getMessage());
        }
    }

    @Test
    // Test6: Should throw ProceduralException if the vehicle is not in use
    public void testStopDrivingVehicleNotInUse() throws ConnectException {
        controller.setBTconnection();  // Establish Bluetooth connection
        controller.setIsBraking(true);  // Driver is braking
        try {
            controller.stopDriving();  // Vehicle is not in use yet
            fail("Expected ProceduralException, but none was thrown");
        } catch (ProceduralException e) {
            // Assert that the exception message matches
            assertEquals("The vehicle is not in use.", e.getMessage());
        }
    }

    @Test
    // Test7: Should throw PMVPhisicalException if there is a technical failure when stopping
    public void testStopDrivingWithTechnicalFailure() throws ConnectException, ProceduralException {
        controller.setBTconnection();  // Establish Bluetooth connection
        controller.setIsVehicleBeingDriven(true);  // Vehicle is in use
        controller.setIsBraking(true);  // Driver is braking
        controller.setIsTechnicalFailure(true);  // Simulate a technical failure (e.g., brake failure)
        try {
            controller.stopDriving();
            fail("Expected PMVPhisicalException, but none was thrown");
        } catch (PMVPhisicalException e) {
            // Assert that the exception message matches
            assertEquals("Technical issue with the brakes, cannot stop the vehicle.", e.getMessage());
        }
    }

    @Test
    // Test8: Should reset all states when undoing the Bluetooth connection
    public void testUndoBTconnection() {
        controller.setBTconnection();  // Establish Bluetooth connection
        controller.setIsVehicleBeingDriven(true);  // Start vehicle usage
        controller.undoBTconnection();  // Undo the connection
        // Assert that all states are reset
        assertFalse(controller.getIsBtConnected());
        assertFalse(controller.getIsVehicleInUse());
        assertFalse(controller.getIsVehicleBeingDriven());
        assertFalse(controller.getIsTechnicalFailure());
        assertFalse(controller.getIsBraking());
    }
}

