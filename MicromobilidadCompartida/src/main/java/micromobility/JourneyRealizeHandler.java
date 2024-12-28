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

/**
 * Handles events and operations related to the realization of a journey,
 * including QR scanning, pairing and unpairing vehicles, driving operations,
 * and payment processing.
 */
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

    /**
     * Constructs a JourneyRealizeHandler with the specified user, geographic point, and vehicle.
     *
     * @param user The user associated with the journey.
     * @param gp The geographic point representing the current location of the journey.
     * @param vehicle The vehicle involved in the journey.
     */
    public JourneyRealizeHandler(UserAccount user, GeographicPoint gp, PMVehicle vehicle) {
        server = new ServerMC();
        arduino = new ArduinoMicroControllerVMP();
        this.gp = gp;
        this.user = user;
        this.vehicle = vehicle;
    }

    // Setter methods for injecting dependencies

    /**
     * Sets the server for the journey.
     *
     * @param server The server to set.
     */
    public void setServer(Server server) {
        this.server = server;
    }

    /**
     * Sets the Arduino microcontroller for the journey.
     *
     * @param arduino The Arduino microcontroller to set.
     */
    public void setArduino(ArduinoMicroController arduino) {
        this.arduino = arduino;
    }

    /**
     * Sets the user associated with the journey.
     *
     * @param user The user to set.
     */
    public void setUser(UserAccount user) {
        this.user = user;
    }

    /**
     * Sets the geographic point representing the current location of the journey.
     *
     * @param gp The geographic point to set.
     */
    public void setGp(GeographicPoint gp) {
        this.gp = gp;
    }

    /**
     * Sets the vehicle involved in the journey.
     *
     * @param vehicle The vehicle to set.
     */
    public void setVehicle(PMVehicle vehicle) {
        this.vehicle = vehicle;
    }

    // Getter methods

    /**
     * Gets the station ID.
     *
     * @return The station ID.
     */
    public StationID getStID() {
        return stID;
    }

    /**
     * Gets the geographic point representing the current location of the journey.
     *
     * @return The geographic point.
     */
    public GeographicPoint getGp() {
        return gp;
    }

    /**
     * Gets the local journey service.
     *
     * @return The local journey service.
     */
    public JourneyService getLocalJourneyService() {
        return localJourneyService;
    }

    /**
     * Scans the QR code, retrieves the vehicle ID, and performs necessary pairing operations.
     *
     * @throws ConnectException If there is an issue with the connection.
     * @throws InvalidPairingArgsException If the pairing arguments are invalid.
     * @throws CorruptedImgException If the QR code image is corrupted.
     * @throws PMVNotAvailException If the vehicle is not available.
     * @throws ProceduralException If a procedural issue occurs during the process.
     */
    public void scanQR() throws ConnectException, InvalidPairingArgsException, CorruptedImgException, PMVNotAvailException, ProceduralException {
        qrDecoder = new QRDecoderVMP();
        VehicleID vehicleID = qrDecoder.getVehicleID(vehicle.getQRCode());
        vehicle.setId(vehicleID);
        server.checkPMVAvail(vehicleID);
        arduino.setBTconnection();

        if (stID == null) {
            throw new ProceduralException("Bluetooth connection could not be completed");
        }

        ServiceID serviceId = new ServiceID(String.format("%s_%s_%s", user.getId(), vehicleID.getId(), stID.getId()));
        localJourneyService = new JourneyService(serviceId, this.gp);

        server.registerPairing(user, vehicleID, stID, gp, LocalDateTime.now());
        vehicle.setNotAvailb();
    }

    /**
     * Unpairs the vehicle and ends the journey, updating relevant details.
     *
     * @throws ConnectException If there is an issue with the connection.
     * @throws InvalidPairingArgsException If the pairing arguments are invalid.
     * @throws PairingNotFoundException If the pairing is not found.
     * @throws ProceduralException If a procedural issue occurs during the process.
     */
    public void unPairVehicle() throws ConnectException, InvalidPairingArgsException, PairingNotFoundException, ProceduralException {
        if (vehicle.getState() == PMVState.AVAILABLE) {
            throw new PairingNotFoundException("This vehicle is not paired");
        }

        if (stID == null) {
            throw new ProceduralException("Bluetooth connection could not be completed");
        }

        localJourneyService.setEndPoint(vehicle.getLocation());
        localJourneyService.setEndDate(LocalDateTime.now());
        localJourneyService.setEndHour(LocalTime.now());
        vehicle.setLocation(gp);
        calculateValues(vehicle.getLocation(), LocalDateTime.now());
        calculateImport(localJourneyService.getDistance(), localJourneyService.getDuration(), localJourneyService.getAvgSpeed(), localJourneyService.getEndDate());
        server.stopPairing(user, vehicle.getId(), stID, vehicle.getLocation(), localJourneyService.getEndDate(), localJourneyService.getAvgSpeed(), localJourneyService.getDistance(), localJourneyService.getDuration(), localJourneyService.getImportCost());
        vehicle.setAvailb();
        localJourneyService.setServiceFinish();
    }

    /**
     * Broadcasts the station ID received via Bluetooth.
     *
     * @param stID The station ID to broadcast.
     * @throws ConnectException If the Station ID is null or there is a connection issue.
     */
    public void broadcastStationID(StationID stID) throws ConnectException {
        if (stID == null) {
            throw new ConnectException("Null Station ID received.");
        }
        this.stID = stID;
    }

    /**
     * Starts the vehicle driving session, marking the vehicle as "under way".
     *
     * @throws ConnectException If there is a connection issue.
     * @throws ProceduralException If a procedural issue occurs during the process.
     */
    public void startDriving() throws ConnectException, ProceduralException {
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

    /**
     * Stops the vehicle driving session.
     *
     * @throws ConnectException If there is a connection issue.
     * @throws ProceduralException If a procedural issue occurs during the process.
     */
    public void stopDriving() throws ConnectException, ProceduralException {
        if (vehicle.getState() != PMVState.UNDER_WAY) {
            throw new ProceduralException("Vehicle is not being driven");
        }

        try {
            arduino.stopDriving();
        } catch (PMVPhisicalException e) {
            throw new ProceduralException(e);
        }
    }

    /**
     * Selects the payment method based on the user's input and processes the payment.
     *
     * @param opt The selected payment option: 'C' for Credit, 'B' for Bizum, 'P' for PayPal, 'W' for Wallet.
     * @throws ProceduralException If the payment method is not valid or developed.
     * @throws NotEnoughWalletException If there is not enough balance in the wallet.
     * @throws ConnectException If there is a connection issue.
     */
    public void selectPaymentMethod(char opt) throws ProceduralException, NotEnoughWalletException, ConnectException {
        switch (opt) {
            case 'C':
                throw new ProceduralException("Pay method is not developed yet");
            case 'B':
                throw new ProceduralException("Pay method is not developed yet");
            case 'P':
                throw new ProceduralException("Pay method is not developed yet");
            case 'W':
                wallet = user.getUserWallet();
                payment = new WalletPayment(wallet);
                break;
            default:
                throw new ProceduralException("Pay method not valid. Only C, B, P, or W");
        }

        realizePayment(localJourneyService.getImportCost());
    }

    // Internal operations

    /**
     * Calculates the journey values such as duration, distance, and average speed.
     *
     * @param gP The geographic point representing the destination.
     * @param date The end date of the journey.
     */
    private void calculateValues(GeographicPoint gP, LocalDateTime date) {
        localJourneyService.setDuration((int) Duration.between(localJourneyService.getInitHour(), date.toLocalTime()).toSeconds());

        GeographicPoint originPoint = localJourneyService.getOriginPoint();
        localJourneyService.setDistance(originPoint.calculateDistance(gP));

        long durationInMinutes = localJourneyService.getDuration();
        if (durationInMinutes > 0) {
            localJourneyService.setAvgSpeed((localJourneyService.getDistance() / durationInMinutes) * 60);
        } else {
            localJourneyService.setAvgSpeed(0);
        }
    }

    /**
     * Calculates the import (cost) of the journey based on distance, duration, speed, and time of day.
     *
     * @param dis The distance covered during the journey.
     * @param dur The duration of the journey in seconds.
     * @param avSp The average speed of the vehicle.
     * @param date The end date of the journey.
     */
    private void calculateImport(float dis, int dur, float avSp, LocalDateTime date) {
        BigDecimal ratePerKm = new BigDecimal("1.5");
        BigDecimal ratePerMinute = new BigDecimal("0.5");
        float speedPenaltyThreshold = 25.0f;
        BigDecimal speedPenaltyRate = new BigDecimal("0.2");
        BigDecimal weekendSurcharge = new BigDecimal("0.15");

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

    /**
     * Processes the payment based on the calculated cost.
     *
     * @param imp The import (cost) of the journey.
     * @throws NotEnoughWalletException If there is insufficient funds in the wallet.
     */
    private void realizePayment(BigDecimal imp) throws NotEnoughWalletException {
        try {
            payment.processPayment(imp);
        } catch (NotEnoughWalletException e) {
            throw new NotEnoughWalletException("Wallet payment failed: " + e.getMessage());
        }
    }
}
