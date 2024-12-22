package services.smartfeatures;

import data.StationID;
import micromobility.JourneyRealizeHandler;

import java.net.ConnectException;

public class UnbondedBTSignalVMP implements UnbondedBTSignal {

    private JourneyRealizeHandler handler;

    private StationID stationID;
    private static final int BROADCAST_INTERVAL = 1000;

    public UnbondedBTSignalVMP(JourneyRealizeHandler handler, StationID stationID) {
        this.handler = handler;
        this.stationID = stationID;
    }

    // SETTERS
    public void setStationID(StationID stationID) {
        this.stationID = stationID;
    }
    //GETTERS
    public StationID getStationID() {
        return stationID;
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
                break; // in real scenarios this break will not exists and this must be a parallel thread
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}
