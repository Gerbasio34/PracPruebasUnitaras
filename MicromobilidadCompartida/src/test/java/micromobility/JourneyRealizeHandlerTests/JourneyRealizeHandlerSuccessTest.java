package micromobility.JourneyRealizeHandlerTests;

import data.GeographicPoint;
import data.StationID;
import data.UserAccount;
import data.VehicleID;
import exception.InvalidPairingArgsException;
import exception.PairingNotFoundException;
import exception.ProceduralException;
import micromobility.JourneyRealizeHandler;
import micromobility.JourneyService;
import micromobility.PMVState;
import micromobility.PMVehicle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import services.Server;
import exception.*;
import services.smartfeatures.ArduinoMicroController;
import services.smartfeatures.UnbondedBTSignalVMP;

import java.math.BigDecimal;
import java.net.ConnectException;

import static org.junit.jupiter.api.Assertions.*;

class JourneyRealizeHandlerSuccessTest {

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
    @DisplayName("Test 1: Start driving correctly initializes the journey")
    public void testStartDriving() throws ConnectException, ProceduralException, CorruptedImgException, InvalidPairingArgsException, PMVNotAvailException {
        unbondedBTSignal.BTbroadcast();
        journeyHandler.scanQR();
        journeyHandler.startDriving();

        assertTrue(vehicle.getState() == PMVState.UNDER_WAY);
        JourneyService localJourneyService = journeyHandler.getLocalJourneyService();
        assertNotNull(localJourneyService.getInitDate());
        assertNotNull(localJourneyService.getInitHour());
        assertNotNull(localJourneyService.getOriginPoint());
    }

    @Test
    @DisplayName("Test 2: Stop driving correctly updates the journey and vehicle")
    public void testStopDriving() throws ConnectException, ProceduralException, CorruptedImgException, InvalidPairingArgsException, PMVNotAvailException, PairingNotFoundException {
        unbondedBTSignal.BTbroadcast();
        journeyHandler.scanQR();
        journeyHandler.startDriving();
        //update location + stationID con el broadcast
        journeyHandler.stopDriving();
        journeyHandler.unPairVehicle();

        assertTrue(vehicle.getState() != PMVState.UNDER_WAY);
        JourneyService localJourneyService = journeyHandler.getLocalJourneyService();
        assertNotNull(localJourneyService.getEndDate());
        assertNotNull(localJourneyService.getEndHour());
        assertNotNull(localJourneyService.getDistance());
        assertNotNull(localJourneyService.getAvgSpeed());
    }


    @Test
    @DisplayName("Test 3: Calculate values correctly computes distance, duration, and average speed")
    public void testCalculateValues() throws ConnectException, CorruptedImgException, InvalidPairingArgsException, ProceduralException, PMVNotAvailException, PairingNotFoundException, InterruptedException {
        unbondedBTSignal.BTbroadcast();
        journeyHandler.scanQR();
        journeyHandler.startDriving();
        Thread.sleep(3000); // 3 minutes of journey duration (we use seconds as minutes)
        // update location + stationID
        StationID newLocationStation = new StationID("ST-12345-Madrid");
        unbondedBTSignal.setStationID(newLocationStation);
        unbondedBTSignal.BTbroadcast();
        journeyHandler.setGp(new GeographicPoint(41.614159f, -0.625800f)); //Lleida (from madrid to lleida)
        // end update location + stationID
        journeyHandler.stopDriving();
        journeyHandler.unPairVehicle();

        JourneyService localJourneyService = journeyHandler.getLocalJourneyService();
        assertNotEquals(0, localJourneyService.getDistance(), 0.1);
        assertTrue(localJourneyService.getDuration() > 0);
        assertTrue(localJourneyService.getAvgSpeed() >= 0);
    }

    @Test
    @DisplayName("Test 4: Calculate import computes the correct cost")
    public void testCalculateImport() throws ConnectException, InvalidPairingArgsException, PairingNotFoundException, ProceduralException, CorruptedImgException, PMVNotAvailException {
        unbondedBTSignal.BTbroadcast();
        journeyHandler.scanQR();
        journeyHandler.startDriving();
        // update location + stationID
        StationID newLocationStation = new StationID("ST-12345-Madrid");
        unbondedBTSignal.setStationID(newLocationStation);
        unbondedBTSignal.BTbroadcast();
        journeyHandler.setGp(new GeographicPoint(41.614159f, -0.625800f)); //Lleida (from madrid to lleida)
        // end update location + stationID
        journeyHandler.stopDriving();
        journeyHandler.unPairVehicle();

        assertTrue(vehicle.getState() == PMVState.AVAILABLE);
        JourneyService localJourneyService = journeyHandler.getLocalJourneyService();
        assertNotNull(localJourneyService.getEndDate());
        assertTrue(localJourneyService.getImportCost().compareTo(BigDecimal.ZERO) >= 0);
    }

    @Test
    @DisplayName("Test 5: QR Scanning correctly decodes vehicle ID and registers pairing")
    public void testScanQRSuccess() throws ConnectException, InvalidPairingArgsException, CorruptedImgException, PMVNotAvailException, ProceduralException {
        VehicleID vehicleID = new VehicleID("VH-123456-nissan");
        vehicle.setId(vehicleID);
        unbondedBTSignal.BTbroadcast();
        journeyHandler.scanQR(); //Image has another ID
        assertNotNull(journeyHandler.getStID());
        assertNotEquals(vehicle.getId(), vehicleID);
    }

    @Test
    @DisplayName("Test 6: QR Scanning fails when vehicle is not available")
    public void testScanQRFailure() throws ConnectException, InvalidPairingArgsException, CorruptedImgException, PMVNotAvailException, ProceduralException {
        unbondedBTSignal.BTbroadcast();
        journeyHandler.scanQR();
        journeyHandler.startDriving();

        assertThrows(PMVNotAvailException.class, () -> {
            journeyHandler.scanQR();
        });
    }



}
