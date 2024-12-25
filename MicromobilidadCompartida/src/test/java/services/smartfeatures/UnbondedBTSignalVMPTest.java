package services.smartfeatures;

import static org.junit.jupiter.api.Assertions.*;

import data.GeographicPoint;
import data.UserAccount;
import data.VehicleID;
import micromobility.PMVState;
import micromobility.PMVehicle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.ConnectException;
import data.StationID;
import micromobility.JourneyRealizeHandler;

public class UnbondedBTSignalVMPTest {

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

    // Test1: Verify that BTbroadcast correctly sends the StationID
    @Test
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

    // Test2: Verify that a ConnectException is thrown in case of a connection error
    @Test
    public void testBTbroadcastThrowsConnectException() {
        // Simulate a connection error by modifying the behavior of the broadcastStationID method
        handler = new JourneyRealizeHandler(user, gp, vehicle) {
            @Override
            public void broadcastStationID(StationID stID) throws ConnectException {
                throw new ConnectException("Connection failed");
            }
        };

        unbondedBTSignal = new UnbondedBTSignalVMP(handler, stationID);

        // Verify that the ConnectException is correctly thrown
        assertThrows(ConnectException.class, () -> {
            unbondedBTSignal.BTbroadcast();
        });
    }

    // Test3: Verify that the StationID can be updated using the setter
    @Test
    public void testSetStationID() {
        // Create a new StationID
        StationID newStationID = new StationID("ST-12345-Barcelona");
        unbondedBTSignal.setStationID(newStationID);  // Update the StationID

        // Verify that the StationID has been updated correctly
        assertEquals(newStationID, unbondedBTSignal.getStationID(), "The StationID should be updated correctly.");
    }

    // Test4: Verify that the broadcast interval is respected
    @Test
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

    // Test5: Verify that the transmission is correctly interrupted
    @Test
    public void testBTbroadcastInterrupt() {
        // Create a new thread to simulate the behavior of BTbroadcast
        Thread broadcastThread = new Thread(() -> {
            try {
                unbondedBTSignal.BTbroadcast();
            } catch (ConnectException e) {
                fail("ConnectException should not be thrown.");
            }
        });

        broadcastThread.start();  // Start the thread

        // Interrupt the broadcast thread
        broadcastThread.interrupt();

        // Check that the thread was correctly interrupted
        assertTrue(broadcastThread.isInterrupted(), "The broadcast thread should have been interrupted.");
    }
}
