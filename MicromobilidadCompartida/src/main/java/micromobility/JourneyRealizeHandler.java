package micromobility;

import data.*;
import exception.*;
import micromobility.payment.Payment;
import micromobility.payment.Wallet;
import micromobility.payment.WalletPayment;
import services.Server;
import services.ServerMC;
import services.smartfeatures.ArduinoMicroController;
import services.smartfeatures.ArduinoMicroControllerVMP;
import services.smartfeatures.QRDecoder;
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
    private QRDecoder qrDecoder;
    private Server server;
    private ArduinoMicroController arduino;
    private GeographicPoint gp;
    private JourneyService localJourneyService;
    private Wallet wallet;
    private Payment payment;

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
    public void setServer(Server server) {
        this.server = server;
    }
    public void setArduino(ArduinoMicroController arduino) {
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

    //GETTERS
    public StationID getStID() {
        return stID;
    }

    public GeographicPoint getGp() { return gp;}

    public JourneyService getLocalJourneyService(){
        return localJourneyService;
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

        if (stID == null){
            throw new ProceduralException("Bluetooth connection could not be completed");
        }
        ServiceID serviceId = new ServiceID(String.format("%s_%s_%s",user.getId(),vehicleID.getId(),stID.getId())); // same user with the same veh at the same station is unique
        localJourneyService = new JourneyService(
                serviceId.getId(),
                this.gp
        );

        //try to register the pairing of the user with the vehicle
        server.registerPairing(user, vehicleID, stID, gp, LocalDateTime.now());

        vehicle.setNotAvailb();
    }

    public void unPairVehicle() throws ConnectException, InvalidPairingArgsException, PairingNotFoundException, ProceduralException {
        if (vehicle.getState() == PMVState.AVAILABLE) {
            throw new PairingNotFoundException("This vehicle it's not paired");
        }

        if (stID == null){
            throw new ProceduralException("Bluetooth connection could not be completed");
        }

        localJourneyService.setEndPoint(vehicle.getLocation());
        localJourneyService.setEndDate(LocalDateTime.now());
        localJourneyService.setEndHour(LocalTime.now());
        vehicle.setLocation(gp);
        calculateValues(vehicle.getLocation(), LocalDateTime.now());
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
        if (vehicle.getState() == PMVState.UNDER_WAY) {
            throw new ProceduralException("Vehicle is already being driven");
        }

        try {
            arduino.startDriving();
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

        if (vehicle.getState() != PMVState.UNDER_WAY) {
            throw new ProceduralException("Vehicle is not being driven");
        }
        // before needs to reconnect with UnbondedBTSignal
        try {
            arduino.stopDriving();
        } catch (PMVPhisicalException e) {
            throw new ProceduralException(e);
        }
    }

    public void selectPaymentMethod (char opt) throws ProceduralException, NotEnoughWalletException, ConnectException {
        // Implementation
        switch (opt) {
            case 'C': // Credit
                throw new ProceduralException("Pay method is not developed yet");

            case 'B': // Bizum
                throw new ProceduralException("Pay method is not developed yet");
                //payment = new BizumPayment(localJourneyService, user, wallet); //example

            case 'P': // PayPal
                throw new ProceduralException("Pay method is not developed yet");

            case 'W': // Wallet
                wallet = user.getUserWallet();
                payment = new WalletPayment(localJourneyService, user, wallet);
                break;
            default:
                throw new ProceduralException("Pay method not valid. Only C, B, P or W");
        }
    }
        // Internal operations
    private void calculateValues(GeographicPoint gP, LocalDateTime date) {
        //Each second will represent minutes, this way we don't need to wait too much (in tests)
        //instead of toMinutes() we use toSeconds()
        localJourneyService.setDuration((int) Duration.between(localJourneyService.getInitHour(), date.toLocalTime()).toSeconds()); // change this toMinutes()

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

        localJourneyService.setImportCost(baseImport.add(speedPenalty).add(surcharge).setScale(2, BigDecimal.ROUND_HALF_UP));
    }

    private void realizePayment (BigDecimal imp) throws NotEnoughWalletException {
        try {
            payment.processPayment();
        } catch (NotEnoughWalletException e) {
            throw new NotEnoughWalletException("Wallet payment failed: " + e.getMessage());
        }
    }
}