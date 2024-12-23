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
        assertTrue(controller.getBtConnected());
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
    // Test3: Should not throw any Exception it's a valid journey
    public void testValidJourney() throws ConnectException, ProceduralException, InterruptedException, PMVPhisicalException {
        controller.setBTconnection();  // Establish Bluetooth connection
        controller.setVehicleBeingDriven(true);  // Vehicle is being driven
        controller.startDriving();
        controller.setBraking(true);  // Driver is braking
        Thread.sleep(3000);
        try {
            controller.stopDriving();
        } catch (Exception e) {
            fail(String.format("Exception %s",e.getMessage()));
        }
    }

    @Test
    // Test4: Should throw ConnectException if Bluetooth is not connected
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
    // Test5: Should throw ConnectException if Bluetooth is not connected
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
    // Test6: Should throw ProceduralException if the vehicle is already in use
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
    // Test7: Should throw ConnectException if Bluetooth is not connected when stopping
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
    // Test8: Should throw ProceduralException if the vehicle is not being driven
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
    // Test9: Should throw PMVPhisicalException if there is a technical failure when stopping
    public void testStopDrivingWithTechnicalFailure() throws ConnectException, ProceduralException, InterruptedException, PMVPhisicalException {
        controller.setBTconnection();  // Establish Bluetooth connection
        controller.setVehicleBeingDriven(true);  // Vehicle is being driven
        controller.startDriving();
        controller.setBraking(true);  // Driver is braking
        controller.setTechnicalFailure(true);  // Simulate a technical failure (e.g., brake failure)
        Thread.sleep(3000);
        try {
            controller.stopDriving();
            fail("Expected PMVPhisicalException, but none was thrown");
        } catch (PMVPhisicalException e) {
            // Assert that the exception message matches
            assertEquals("Technical issue with the brakes, cannot stop the vehicle.", e.getMessage());
        }
    }

    @Test
    // Test10: Should throw ProceduralException if it's in movement
    public void testStopDrivingWhileMoving() throws ConnectException, ProceduralException, PMVPhisicalException {
        controller.setBTconnection();  // Establish Bluetooth connection
        controller.setVehicleBeingDriven(true);  // Vehicle is being driven
        controller.startDriving();
        controller.setBraking(true);  // Driver is braking
        //no wait
        try {
            controller.stopDriving();
            fail("Expected ProceduralException, but none was thrown");
        } catch (ProceduralException e) {
            // Assert that the exception message matches
            assertEquals("The vehicle is moving, cannot stop right now.", e.getMessage());
        }
    }

    @Test
    // Test11: Should reset all states when undoing the Bluetooth connection
    public void testUndoBTconnection() throws ConnectException {
        controller.setBTconnection();  // Establish Bluetooth connection
        controller.setVehicleBeingDriven(true);  // Start vehicle usage
        controller.undoBTconnection();  // Undo the connection
        // Assert that all states are reset
        assertFalse(controller.getBtConnected());
        assertFalse(controller.getVehicleInUse());
        assertFalse(controller.getVehicleBeingDriven());
        assertFalse(controller.getTechnicalFailure());
        assertFalse(controller.getBraking());
    }
}
