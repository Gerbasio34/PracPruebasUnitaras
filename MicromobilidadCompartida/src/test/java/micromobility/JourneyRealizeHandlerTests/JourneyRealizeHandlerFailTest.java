package micromobility.JourneyRealizeHandlerTests;

import data.GeographicPoint;
import data.StationID;
import data.UserAccount;
import data.VehicleID;
import exception.*;
import micromobility.JourneyRealizeHandler;
import micromobility.PMVState;
import micromobility.PMVehicle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import services.Server;
import services.smartfeatures.ArduinoMicroController;
import services.smartfeatures.UnbondedBTSignalVMP;

import java.net.ConnectException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class JourneyRealizeHandlerFailTest {

    private JourneyRealizeHandler journeyHandler;
    private ArduinoMicroController arduinoMock;
    private Server serverMock;
    private PMVehicle vehicle;
    private UserAccount user;
    private GeographicPoint gp;
    private StationID stID;
    private UnbondedBTSignalVMP unbondedBTSignal;

    @BeforeEach
    void setUp() {
        arduinoMock = new MockArduinoMicroController();
        serverMock = new MockServer();
        gp = new GeographicPoint(40.4168f, -3.7038f); // Madrid coordinates
        vehicle = new PMVehicle(PMVState.AVAILABLE, gp, 80.0);
        vehicle.setQRCode("qrcode-dummy.png");
        user = new UserAccount("UA-johnsmith-12345");

        journeyHandler = new JourneyRealizeHandler(user, gp, vehicle);
        journeyHandler.setArduino(arduinoMock);
        journeyHandler.setServer(serverMock);

        unbondedBTSignal = new UnbondedBTSignalVMP(journeyHandler, stID);
        stID = new StationID("ST-12345-Madrid");
        unbondedBTSignal.setStationID(stID);
    }

    @Test
    @DisplayName("Test 1: Scan QR fails for unavailable vehicle")
    public void testScanQRUnavailableVehicle() throws CorruptedImgException, InvalidPairingArgsException, ProceduralException, PMVNotAvailException, ConnectException {
        unbondedBTSignal.BTbroadcast();
        journeyHandler.scanQR();

        assertThrows(PMVNotAvailException.class, () -> {
            journeyHandler.scanQR();
        });
    }

    @Test
    @DisplayName("Test 2: Start driving fails when vehicle is not paired")
    public void testStartDrivingWithoutPairing() throws ProceduralException, ConnectException {
        assertDoesNotThrow(() -> unbondedBTSignal.BTbroadcast());
        assertThrows(ConnectException.class, () -> {
            journeyHandler.startDriving();
        });
    }

    @Test
    @DisplayName("Test 3: Stop driving fails when journey is not started")
    public void testStopDrivingWithoutStarting() {
        assertDoesNotThrow(() -> unbondedBTSignal.BTbroadcast());
        assertDoesNotThrow(() -> journeyHandler.scanQR());
        assertThrows(ProceduralException.class, () -> {
            journeyHandler.stopDriving();
        });
    }

    @Test
    @DisplayName("Test 4: Unpairing fails if not paired")
    public void testUnpairWithoutPairing() {
        assertThrows(PairingNotFoundException.class, () -> {
            journeyHandler.unPairVehicle();
        });
    }

}
