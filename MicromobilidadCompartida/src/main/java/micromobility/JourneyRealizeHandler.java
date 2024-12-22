package micromobility;

import data.GeographicPoint;
import data.StationID;
import exception.*;

import java.net.ConnectException;
import java.time.LocalDateTime;

// Class that handles events and operations related to journey realization.
public class JourneyRealizeHandler {

    // Class members
    StationID stID;

    // Constructors
    public JourneyRealizeHandler() {
        // Object initialization
    }

    //GETTERS

    public StationID getStID() {
        return stID;
    }

    // User interface input events
    public void scanQR()
            throws ConnectException, InvalidPairingArgsException,
            CorruptedImgException, PMVNotAvailException,
            ProceduralException {
        // Implementation
    }

    public void unPairVehicle()
            throws ConnectException, InvalidPairingArgsException,
            PairingNotFoundException, ProceduralException {
        // Implementation
    }

    // Input events from the unbonded Bluetooth channel
    public void broadcastStationID(StationID stID) throws ConnectException {
        if (stID == null) {
            throw new ConnectException("Null Station ID received.");
        }
        this.stID = stID;
    }

    // Input events from the Arduino microcontroller channel
    public void startDriving()
            throws ConnectException, ProceduralException {
        // Implementation
    }

    public void stopDriving()
            throws ConnectException, ProceduralException {
        // Implementation
    }

    // Internal operations
    private void calculateValues(GeographicPoint gP, LocalDateTime date) {
        // Implementation
    }

    private void calculateImport(float dis, int dur, float avSp, LocalDateTime date) {
        // Implementation
    }

    // Setter methods for injecting dependences

}
