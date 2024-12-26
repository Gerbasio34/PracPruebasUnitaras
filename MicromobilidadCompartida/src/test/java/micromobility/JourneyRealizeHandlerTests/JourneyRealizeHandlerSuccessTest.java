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

import java.math.BigDecimal;
import java.net.ConnectException;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class JourneyRealizeHandlerSuccessTest {

    private JourneyRealizeHandler journeyHandler;
    private ArduinoMicroController arduinoMock;
    private Server serverMock;
    private PMVehicle vehicle;
    private UserAccount user;
    private GeographicPoint gp;
    private StationID stID;

    @BeforeEach
    void setUp() {
        arduinoMock = new MockArduinoMicroController();
        serverMock = new MockServer();
        gp = new GeographicPoint(40.4168f, -3.7038f); // Madrid coordinates
        vehicle = new PMVehicle(PMVState.AVAILABLE, gp, 80.0);
        vehicle.setQRCode("qrcode-dummy.png");

        user = new UserAccount("UA-johnsmith-12345");
        stID = new StationID("ST-12345-Madrid");

        journeyHandler = new JourneyRealizeHandler(user, gp, vehicle);
        journeyHandler.setArduino(arduinoMock);
        journeyHandler.setServer(serverMock);
        journeyHandler.setStID(stID);
        journeyHandler.setDate(LocalDateTime.now());

    }

    @Test
    @DisplayName("Test 1: Start driving correctly initializes the journey")
    public void testStartDriving() throws ConnectException, ProceduralException, CorruptedImgException, InvalidPairingArgsException, PMVNotAvailException {
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

    /*
    @Test
    @DisplayName("Test 3: Calculate values correctly computes distance, duration, and average speed")
    public void testCalculateValues() {
        GeographicPoint origin = new GeographicPoint(40.748817f, -73.985428f);
        GeographicPoint destination = new GeographicPoint(40.748200f, -73.985500f);

        localJourneyService.setOriginPoint(origin);
        localJourneyService.setInitDate(LocalDateTime.now());
        localJourneyService.setInitHour(LocalTime.now());
        //Crear un lastCalculatedValue y hacer un getter para JourneyRealizeHandler
        journeyHandler.calculateValues(destination, LocalDateTime.now().plusMinutes(10));

        assertNotEquals(0, localJourneyService.getDistance(), 0.1);
        assertTrue(localJourneyService.getDuration() > 0);
        assertTrue(localJourneyService.getAvgSpeed() >= 0);
    }

    @Test
    @DisplayName("Test 4: Calculate import computes the correct cost")
    public void testCalculateImport() throws ProceduralException, ConnectException, PairingNotFoundException, InvalidPairingArgsException {
        float distance = 10.0f;
        int duration = 20;
        float avgSpeed = 30.0f;
        LocalDateTime journeyDate = LocalDateTime.now();

        journeyHandler.startDriving();
        journeyHandler.unPairVehicle();

        journeyHandler.calculateImport(distance, duration, avgSpeed, journeyDate);

        assertTrue(localJourneyService.getImportCost().compareTo(BigDecimal.ZERO) > 0);
    }
    */

    @Test
    @DisplayName("Test 5: Unpairing the vehicle correctly finalizes the journey and updates vehicle availability")
    public void testUnPairVehicle() throws ConnectException, InvalidPairingArgsException, PairingNotFoundException, ProceduralException, CorruptedImgException, PMVNotAvailException {
        journeyHandler.scanQR();
        journeyHandler.startDriving();
        //update location + stationID con el broadcast
        journeyHandler.stopDriving();
        journeyHandler.unPairVehicle();

        assertTrue(vehicle.getState() == PMVState.AVAILABLE);
        JourneyService localJourneyService = journeyHandler.getLocalJourneyService();
        assertNotNull(localJourneyService.getEndDate());
        assertTrue(localJourneyService.getImportCost().compareTo(BigDecimal.ZERO) >= 0);
    }

    @Test
    @DisplayName("Test 6: QR Scanning correctly decodes vehicle ID and registers pairing")
    public void testScanQRSuccess() throws ConnectException, InvalidPairingArgsException, CorruptedImgException, PMVNotAvailException, ProceduralException {
        VehicleID vehicleID = new VehicleID("VH-123456-nissan");
        vehicle.setId(vehicleID);

        journeyHandler.scanQR(); //Image has another ID
        assertNotNull(journeyHandler.getStID());
        assertNotEquals(vehicle.getId(), vehicleID);
    }

    @Test
    @DisplayName("Test 7: QR Scanning fails when vehicle is not available")
    public void testScanQRFailure() throws ConnectException, InvalidPairingArgsException, CorruptedImgException, PMVNotAvailException, ProceduralException {
        journeyHandler.scanQR();
        journeyHandler.startDriving();

        assertThrows(PMVNotAvailException.class, () -> {
            journeyHandler.scanQR();
        });
    }



}
