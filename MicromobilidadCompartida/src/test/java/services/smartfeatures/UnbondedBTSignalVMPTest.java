package services.smartfeatures;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.net.ConnectException;
import data.StationID;
import micromobility.JourneyRealizeHandler;
import services.smartfeatures.UnbondedBTSignalVMP;

public class UnbondedBTSignalVMPTest {

    private UnbondedBTSignalVMP unbondedBTSignal;
    private JourneyRealizeHandler handler;
    private StationID stationID;

    @BeforeEach
    public void setUp() {
        // Setup before each test: create the necessary objects
        handler = new JourneyRealizeHandler();  // Real handler
        stationID = new StationID("Station123");  // Assuming StationID constructor takes a string
        unbondedBTSignal = new UnbondedBTSignalVMP(handler, stationID);
    }

    // Test1: Verify that BT broadcast sends the station ID correctly
    @Test
    public void testBTbroadcastSendsStationID() {
        // We simulate the broadcast process here
        try {
            // Normally, we would verify the output of handler.broadcastStationID() in real scenarios.
            // Since the function is void and prints internally, we can't directly check the result,
            // but we can assert that no exceptions are thrown, meaning the process ran correctly.
            unbondedBTSignal.BTbroadcast();
        } catch (ConnectException e) {
            fail("ConnectException should not be thrown during broadcasting: " + e.getMessage());
        }
    }

    // Test2: Verify that ConnectException is thrown if there is a connection error during broadcast
    @Test
    public void testBTbroadcastThrowsConnectException() {
        // Simulate a connection error by setting the connection state to false manually
        handler = new JourneyRealizeHandler() {
            @Override
            public void broadcastStationID(StationID stID) throws ConnectException {
                throw new ConnectException("Connection failed");
            }
        };

        unbondedBTSignal = new UnbondedBTSignalVMP(handler, stationID);

        // We expect a ConnectException to be thrown when calling BTbroadcast
        assertThrows(ConnectException.class, () -> {
            unbondedBTSignal.BTbroadcast();
        });
    }

    // Test3: Verify that the station ID can be updated via the setter method
    @Test
    public void testSetStationID() {
        // Verify that the setter updates the stationID correctly
        StationID newStationID = new StationID("Station456");
        unbondedBTSignal.setStationID(newStationID);

        // Assert that the station ID has been updated
        assertEquals(newStationID, unbondedBTSignal.getStationID(), "StationID should be updated.");
    }

    // Test4: Verify that the broadcast interval time works as expected (simulate time)
    @Test
    public void testBroadcastInterval() {
        long startTime = System.currentTimeMillis();
        try {
            // Simulate the broadcasting of the station ID
            unbondedBTSignal.BTbroadcast();
        } catch (ConnectException e) {
            fail("ConnectException should not be thrown during broadcasting: " + e.getMessage());
        }
        long elapsedTime = System.currentTimeMillis() - startTime;

        // Assert that the broadcast occurs after the interval
        assertTrue(elapsedTime >= 3000, "The broadcast interval should be at least 3000 milliseconds.");
    }

    // Test5: Verify that the broadcast is interrupted correctly
    @Test
    public void testBTbroadcastInterrupt() {
        // Create a separate thread to simulate broadcast behavior
        Thread broadcastThread = new Thread(() -> {
            try {
                unbondedBTSignal.BTbroadcast();
            } catch (ConnectException e) {
                fail("Broadcast threw ConnectException.");
            }
        });

        broadcastThread.start();

        // Interrupt the broadcast thread
        broadcastThread.interrupt();

        // Check that the thread was interrupted successfully
        assertTrue(broadcastThread.isInterrupted(), "Broadcast thread should be interrupted.");
    }
}
