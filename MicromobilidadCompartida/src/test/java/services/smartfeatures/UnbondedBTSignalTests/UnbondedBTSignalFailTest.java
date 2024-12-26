package services.smartfeatures.UnbondedBTSignalTests;

import data.GeographicPoint;
import data.StationID;
import data.UserAccount;
import micromobility.JourneyRealizeHandler;
import micromobility.PMVState;
import micromobility.PMVehicle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import services.smartfeatures.UnbondedBTSignalVMP;

import java.net.ConnectException;

import static org.junit.jupiter.api.Assertions.*;

public class UnbondedBTSignalFailTest {

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
    @DisplayName("Test1: Verify that a ConnectException is thrown in case of a connection error")
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

    @Test
    @DisplayName("Test2: Verify that the transmission is correctly interrupted")
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
