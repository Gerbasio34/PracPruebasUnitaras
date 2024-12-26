package services.smartfeatures.UnbondedBTSignalTests;

import static org.junit.jupiter.api.Assertions.*;

import data.GeographicPoint;
import data.UserAccount;
import micromobility.PMVState;
import micromobility.PMVehicle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.net.ConnectException;
import data.StationID;
import micromobility.JourneyRealizeHandler;
import services.smartfeatures.UnbondedBTSignalVMP;

public class UnbondedBTSignalSuccesTest {

    private UnbondedBTSignalVMP unbondedBTSignal;
    private JourneyRealizeHandler handler;
    private StationID stationID;
    private UserAccount user;
    private GeographicPoint gp;
    private PMVehicle vehicle;

    @BeforeEach
    public void setUp() {
        // Setup before each test: create the necessary objects
        user = new UserAccount("UA-johnsmith-1234");
        gp = new GeographicPoint(40.4168f, -3.7038f);  // Example geographic location
        vehicle = new PMVehicle(PMVState.AVAILABLE, new GeographicPoint(40.4168f, -3.7038f), 75);
        handler = new JourneyRealizeHandler(user, gp, vehicle); // Initializing the JourneyRealizeHandler with the user account and location
        stationID = new StationID("ST-12345-Lleida"); // Test station
        unbondedBTSignal = new UnbondedBTSignalVMP(handler, stationID); // Instantiate UnbondedBTSignalVMP
    }

    @Test
    @DisplayName("Test1: Verify that BTbroadcast correctly sends the StationID")
    public void testBTbroadcastSendsStationID() {
        try {
            // Call the BTbroadcast method
            unbondedBTSignal.BTbroadcast();

            // Verify that the handler correctly called the method with the stationID
            assertEquals(stationID, handler.getStID(), "The StationID sent by the broadcast does not match.");

        } catch (ConnectException e) {
            fail("ConnectException should not be thrown: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Test2: Verify that the StationID can be updated using the setter")
    public void testSetStationID() {
        // Create a new StationID
        StationID newStationID = new StationID("ST-12345-Barcelona");
        unbondedBTSignal.setStationID(newStationID);  // Update the StationID

        // Verify that the StationID has been updated correctly
        assertEquals(newStationID, unbondedBTSignal.getStationID(), "The StationID should be updated correctly.");
    }

    @Test
    @DisplayName("Test3: Verify that the broadcast interval is respected")
    public void testBroadcastInterval() {
        // Measure the time before executing the broadcast
        long startTime = System.currentTimeMillis();

        try {
            // Execute the BTbroadcast method
            unbondedBTSignal.BTbroadcast();

        } catch (ConnectException e) {
            fail("ConnectException should not be thrown: " + e.getMessage());
        }

        // Measure the elapsed time
        long elapsedTime = System.currentTimeMillis() - startTime;

        // Verify that the broadcast time is at least 1000ms
        assertTrue(elapsedTime >= 1000, "The broadcast interval should be at least 1000ms.");
    }


}
