package services.smartfeatures;

import static org.junit.jupiter.api.Assertions.*;

import data.UserAccount;
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
    private UserAccount user;

    @BeforeEach
    public void setUp() {
        // Setup before each test: create the necessary objects
        user = new UserAccount("johnsmith-1234");
        handler = new JourneyRealizeHandler(user);
        stationID = new StationID("ST-12345-Lleida");
        unbondedBTSignal = new UnbondedBTSignalVMP(handler, stationID);
    }

    // Test1: Verify that BT broadcast sends the station ID correctly
    @Test
    public void testBTbroadcastSendsStationID() {
        try {
            unbondedBTSignal.setStationID(stationID);
            unbondedBTSignal.BTbroadcast();
            assertEquals(unbondedBTSignal.getStationID(),handler.getStID());
        } catch (ConnectException e) {
            fail("ConnectException should not be thrown during broadcasting: " + e.getMessage());
        }
    }

    // Test2: Verify that ConnectException is thrown if there is a connection error during broadcast
    @Test
    public void testBTbroadcastThrowsConnectException() {
        // Simulate a connection error by setting the connection state to false manually
        handler = new JourneyRealizeHandler(user) {
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
        StationID newStationID = new StationID("ST-12345-Barcelona");
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
        assertTrue(elapsedTime >= 1000, "The broadcast interval should be at least 1000 milliseconds.");
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
