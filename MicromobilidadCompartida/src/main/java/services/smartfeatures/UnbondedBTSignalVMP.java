package services.smartfeatures;

import data.StationID;
import micromobility.JourneyRealizeHandler;

import java.net.ConnectException;

public class UnbondedBTSignalVMP implements UnbondedBTSignal {

    private JourneyRealizeHandler handler;

    private StationID stationID;
    private static final int BROADCAST_INTERVAL = 3000;

    public UnbondedBTSignalVMP(JourneyRealizeHandler handler, StationID stationID) {
        this.handler = handler;
        this.stationID = stationID;
    }

    // SETTERS
    public void setStationID(StationID stationID) {
        this.stationID = stationID;
    }

    @Override
    public void BTbroadcast() throws ConnectException {
        // Simulating the behavior of broadcasting at regular intervals
        while (true) {
            try {
                // Simulate the broadcasting of the station ID
                handler.broadcastStationID(this.stationID);
                // Wait for the next broadcast (simulate the interval)
                Thread.sleep(BROADCAST_INTERVAL);

            } catch (InterruptedException e) {
                System.out.println("Broadcast interrupted.");
                break;
            }
        }
    }
}
