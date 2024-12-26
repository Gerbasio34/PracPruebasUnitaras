package services.smartfeatures.ArduinoMicroControllerTests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.net.ConnectException;
import exception.PMVPhisicalException;
import exception.ProceduralException;
import services.smartfeatures.ArduinoMicroControllerVMP;

public class ArduinoMicroControllerSuccessTest {

    private ArduinoMicroControllerVMP controller;

    @BeforeEach
    public void setUp() {
        // Initialize the controller before each test
        controller = new ArduinoMicroControllerVMP();
    }

    @Test
    @DisplayName("Test1: Should establish a Bluetooth connection successfully")
    public void testSetBTconnectionValid() throws ConnectException {
        controller.setBTconnection();
        // Assert that Bluetooth is connected
        assertTrue(controller.getBtConnected());
    }

    @Test
    @DisplayName("Test2: Should not throw any Exception it's a valid journey")
    public void testValidJourney() throws ConnectException, ProceduralException, InterruptedException, PMVPhisicalException {
        controller.setBTconnection();  // Establish Bluetooth connection
        controller.setVehicleBeingDriven(true);  // Vehicle is being driven
        controller.startDriving();
        try {
            controller.stopDriving();
        } catch (Exception e) {
            fail(String.format("Exception %s",e.getMessage()));
        }
    }

    @Test
    @DisplayName("Test3: Should reset all states when undoing the Bluetooth connection")
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
