package services.smartfeatures;

import data.StationID;
import micromobility.JourneyRealizeHandler;

import java.net.ConnectException;

/**
 * Represents a Bluetooth signal handler that broadcasts station ID to an unbonded Bluetooth channel.
 * Implements the {@link UnbondedBTSignal} interface to provide Bluetooth signaling functionality.
 */
public class UnbondedBTSignalVMP implements UnbondedBTSignal {

    private JourneyRealizeHandler handler;
    private StationID stationID;
    private static final int BROADCAST_INTERVAL = 1000; // Interval in milliseconds

    /**
     * Constructs an UnbondedBTSignalVMP object with the specified handler and station ID.
     *
     * @param handler The {@link JourneyRealizeHandler} object used for handling the journey.
     * @param stationID The {@link StationID} to be broadcast.
     */
    public UnbondedBTSignalVMP(JourneyRealizeHandler handler, StationID stationID) {
        this.handler = handler;
        this.stationID = stationID;
    }

    /**
     * Sets the station ID to be broadcasted by the Bluetooth signal.
     *
     * @param stationID The {@link StationID} to be set.
     */
    public void setStationID(StationID stationID) {
        this.stationID = stationID;
    }

    /**
     * Retrieves the current station ID that will be broadcasted.
     *
     * @return The current {@link StationID} being broadcasted.
     */
    public StationID getStationID() {
        return stationID;
    }

    /**
     * Simulates broadcasting the station ID over Bluetooth at regular intervals.
     * The broadcast occurs in a loop and waits for a specified interval before repeating.
     *
     * @throws ConnectException If there is an error during the connection or broadcasting process.
     */
    @Override
    public void BTbroadcast() throws ConnectException {
        // Simulating the behavior of broadcasting at regular intervals
        while (true) {
            try {
                // Simulate the broadcasting of the station ID
                handler.broadcastStationID(this.stationID);
                // Wait for the next broadcast (simulate the interval)
                Thread.sleep(BROADCAST_INTERVAL);
                break; // In real scenarios, this break will not exist, and it will run in a parallel thread
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}
