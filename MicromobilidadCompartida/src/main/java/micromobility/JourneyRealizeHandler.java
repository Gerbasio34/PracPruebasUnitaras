package micromobility;

import data.GeographicPoint;
import data.StationID;
import data.UserAccount;
import data.VehicleID;
import exception.*;
import services.ServerMC;
import services.smartfeatures.ArduinoMicroControllerVMP;
import services.smartfeatures.QRDecoderVMP;

import java.math.BigDecimal;
import java.net.ConnectException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

// Class that handles events and operations related to journey realization.
public class JourneyRealizeHandler {

    // Class members
    private StationID stID;
    private UserAccount user;
    private PMVehicle vehicle;
    private QRDecoderVMP qrDecoder;
    private ServerMC server;
    private ArduinoMicroControllerVMP arduino;
    private GeographicPoint gp;
    private LocalDateTime date;
    private JourneyService localJourneyService;

    // Constructors
    public JourneyRealizeHandler(UserAccount user, GeographicPoint gp, PMVehicle vehicle) {
        // Object initialization
        server = new ServerMC();
        arduino = new ArduinoMicroControllerVMP();
        this.gp = gp;
        this.user = user;
        this.vehicle = vehicle;
    }

    // Setter methods for injecting dependences
    public void setServer(ServerMC server) {
        this.server = server;
    }
    public void setArduino(ArduinoMicroControllerVMP arduino) {
        this.arduino = arduino;
    }
    public void setUser(UserAccount user) {
        this.user = user;
    }

    public void setGp(GeographicPoint gp) {
        this.gp = gp;
    }

    public void setVehicle(PMVehicle vehicle) {
        this.vehicle = vehicle;
    }

    public void setStID(StationID stID) {
        this.stID = stID;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    //GETTERS
    public StationID getStID() {
        return stID;
    }

    // User interface input events
    public void scanQR() throws ConnectException, InvalidPairingArgsException, CorruptedImgException, PMVNotAvailException, ProceduralException {
        // Initialize the QRDecoderVMP instance before each test
        qrDecoder = new QRDecoderVMP();

        //decode the QR to obtain the VehicleID
        VehicleID vehicleID = qrDecoder.getVehicleID(vehicle.getQRCode());

        //setVehicleId
        vehicle.setId(vehicleID);

        //check if the vehicle is available
        server.checkPMVAvail(vehicleID);

        // Establish Bluetooth connection
        arduino.setBTconnection();

        vehicle.setNotAvailb();

        String serviceId = String.format("%s_%s_%s",user.getId(),vehicleID,stID); // same user with the same veh at the same station is unique
        localJourneyService = new JourneyService(
                serviceId,
                this.gp
        );

        //try to register the pairing of the user with the vehicle
        server.registerPairing(user, vehicleID, stID, gp, date);
    }

    public void unPairVehicle() throws ConnectException, InvalidPairingArgsException, PairingNotFoundException, ProceduralException {
        localJourneyService.setEndPoint(vehicle.getLocation());
        localJourneyService.setEndDate(LocalDateTime.now());
        localJourneyService.setEndHour(LocalTime.now());
        calculateValues(vehicle.getLocation(), date);
        calculateImport(localJourneyService.getDistance(), localJourneyService.getDuration(), localJourneyService.getAvgSpeed(), localJourneyService.getEndDate());
        server.stopPairing(user, vehicle.getId(),stID, vehicle.getLocation(), localJourneyService.getEndDate(), localJourneyService.getAvgSpeed(), localJourneyService.getDistance(), localJourneyService.getDuration(), localJourneyService.getImportCost());
        vehicle.setAvailb();
        localJourneyService.setServiceFinish();
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

        try {
            arduino.startDriving();
            arduino.setVehicleBeingDriven(true);  // Vehicle is being driven
            arduino.setBraking(true);  // Driver is braking
            Thread.sleep(3000); // Simulate driver using the brake at least 3 seconds
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (PMVPhisicalException e) {
            throw new ProceduralException(e);
        }

        vehicle.setUnderWay();
        localJourneyService.setOriginPoint(vehicle.getLocation());
        localJourneyService.setInitDate(LocalDateTime.now());
        localJourneyService.setInitHour(LocalTime.now());
        localJourneyService.setServiceInit();
    }

    public void stopDriving()
            throws ConnectException, ProceduralException {
        // before needs to reconnect with UnbondedBTSignal
        try {
            arduino.stopDriving();
        } catch (PMVPhisicalException e) {
            throw new ProceduralException(e);
        }
    }

    // Internal operations
    private void calculateValues(GeographicPoint gP, LocalDateTime date) {
        localJourneyService.setDuration((int) Duration.between(localJourneyService.getInitHour(), date.toLocalTime()).toMinutes());

        GeographicPoint originPoint = localJourneyService.getOriginPoint();

        // Calculate distance between origin and destination
        localJourneyService.setDistance(originPoint.calculateDistance(gP));

        // Calculate average speed
        long durationInMinutes = localJourneyService.getDuration();
        if (durationInMinutes > 0) {
            localJourneyService.setAvgSpeed((localJourneyService.getDistance() / durationInMinutes) * 60);
        } else {
            localJourneyService.setAvgSpeed(0);
        }
    }

    private void calculateImport(float dis, int dur, float avSp, LocalDateTime date) {
        BigDecimal ratePerKm = new BigDecimal("1.5"); // Rate per kilometer
        BigDecimal ratePerMinute = new BigDecimal("0.5"); // Rate per minute
        float speedPenaltyThreshold = 25.0f; // Average speed limit
        BigDecimal speedPenaltyRate = new BigDecimal("0.2"); // 20% speed penalty
        BigDecimal weekendSurcharge = new BigDecimal("0.15"); // 15% surcharge on weekends

        boolean isWeekend = (date.getDayOfWeek().getValue() == 6 || date.getDayOfWeek().getValue() == 7);
        BigDecimal baseImport = ratePerKm.multiply(BigDecimal.valueOf(dis)).add(ratePerMinute.multiply(BigDecimal.valueOf(dur)));

        BigDecimal speedPenalty = BigDecimal.ZERO;
        if (avSp > speedPenaltyThreshold) {
            speedPenalty = baseImport.multiply(speedPenaltyRate);
        }

        BigDecimal surcharge = BigDecimal.ZERO;
        if (isWeekend) {
            surcharge = baseImport.multiply(weekendSurcharge);
        }

        localJourneyService.setImportCost(baseImport.add(speedPenalty).add(surcharge));
    }
}