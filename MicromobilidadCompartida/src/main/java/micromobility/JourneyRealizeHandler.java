package micromobility;

import data.GeographicPoint;
import data.StationID;
import exception.*;

import java.net.ConnectException;
import java.time.LocalDateTime;

// Class that handles events and operations related to journey realization.
public class JourneyRealizeHandler {

    // Class members

    // Constructors
    public JourneyRealizeHandler() {
        // Object initialization
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
            throw new ConnectException("No se recibió ningún ID de estación. Verifique la conexión Bluetooth.");
        }

        try {
            // Simula la recepción del ID desde el canal Bluetooth
            System.out.println("ID de estación recibido: " + stID.getId());
            // Aquí podrías almacenar el ID para su uso posterior
        } catch (Exception e) {
            throw new ConnectException("Error al procesar el ID de la estación: " + e.getMessage());
        }
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
