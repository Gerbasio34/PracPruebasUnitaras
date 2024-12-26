package micromobility.JourneyRealizeHandlerTests;

import exception.PMVPhisicalException;
import exception.ProceduralException;
import services.smartfeatures.ArduinoMicroController;

import java.net.ConnectException;

public class MockArduinoMicroController implements ArduinoMicroController {

    private boolean btConnected = false;

    @Override
    public void setBTconnection() throws ConnectException {
        if (btConnected) {
            throw new ConnectException("Bluetooth connection already established.");
        }
        btConnected = true;
    }

    @Override
    public void startDriving() throws PMVPhisicalException, ConnectException, ProceduralException {
        if (!btConnected) {
            throw new ConnectException("Bluetooth connection is not established.");
        }
    }

    @Override
    public void stopDriving() throws PMVPhisicalException, ConnectException, ProceduralException {
        if (!btConnected) {
            throw new ConnectException("Bluetooth connection is not established.");
        }
    }

    @Override
    public void undoBTconnection() {
        btConnected = false;
    }
}