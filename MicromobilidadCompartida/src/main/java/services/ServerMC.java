package services;

import data.*;
import exception.InvalidPairingArgsException;
import exception.PMVNotAvailException;
import exception.PairingNotFoundException;
import micromobility.JourneyService;
import micromobility.PMVehicle;
import micromobility.PMVState;

import java.math.BigDecimal;
import java.net.ConnectException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

/**
 * Implementation of the server for managing PMVehicles, user pairing, and journey services.
 * Simulates database interactions using in-memory data structures like HashMaps and ArrayLists.
 */
public class ServerMC implements Server {

    // Simulated database
    public static Map<VehicleID, PMVehicle> vehicleAvailability = new HashMap<>();
    public static Map<VehicleID, StationID> vehicleStationMap = new HashMap<>();
    public static Map<VehicleID, UserAccount> vehicleUserMap = new HashMap<>();
    private static Map<String, JourneyService> activeJourneyServices = new HashMap<>();
    private static ArrayList<JourneyService> recordsJourneyServices = new ArrayList<>();
    public static HashMap<UserAccount, ArrayList<String>> paymentRecords = new HashMap<>();
    public static boolean statusConnection = true;

    /**
     * Verifies if a PMVehicle is available for pairing.
     *
     * @param vhID The ID of the vehicle to check.
     * @throws PMVNotAvailException if the vehicle is not available.
     * @throws ConnectException if the vehicle ID is null or not found.
     */
    @Override
    public void checkPMVAvail(VehicleID vhID) throws PMVNotAvailException, ConnectException {
        if (vhID == null) {
            throw new ConnectException("VehicleID is null, unable to connect to server.");
        }

        PMVehicle vehicle = vehicleAvailability.get(vhID);
        if (vehicle == null) {
            throw new ConnectException("VehicleID not found in the system.");
        }

        if (vehicle.getState() != PMVState.AVAILABLE) {
            throw new PMVNotAvailException("Vehicle is already paired with another user.");
        }
    }

    /**
     * Registers a pairing between a user and a PMVehicle at a specific station and location.
     *
     * @param user The user account.
     * @param veh The vehicle ID.
     * @param st The station ID.
     * @param loc The geographic location.
     * @param date The pairing date and time.
     * @throws InvalidPairingArgsException if any of the arguments are null.
     * @throws ConnectException if the vehicle is not available or not at the specified station.
     */
    @Override
    public void registerPairing(UserAccount user, VehicleID veh, StationID st, GeographicPoint loc, LocalDateTime date) throws InvalidPairingArgsException, ConnectException {
        if (user == null || veh == null || st == null || loc == null || date == null) {
            throw new InvalidPairingArgsException("One or more arguments are null.");
        }

        PMVehicle vehicle = vehicleAvailability.get(veh);
        if (vehicle == null) {
            throw new ConnectException("Vehicle is not available or does not exist.");
        }

        StationID currentStation = vehicleStationMap.get(veh);
        if (currentStation == null || !currentStation.equals(st)) {
            throw new ConnectException("Vehicle is not at the specified station.");
        }

        setPairing(user, veh, st, loc, date);
    }

    /**
     * Stops the pairing between a user and a PMVehicle, completing the journey service.
     *
     * @param user The user account.
     * @param veh The vehicle ID.
     * @param st The station ID.
     * @param loc The geographic location.
     * @param date The end date and time of the pairing.
     * @param avSp The average speed during the journey.
     * @param dist The distance traveled.
     * @param dur The duration of the journey.
     * @param imp The cost of the journey.
     * @throws InvalidPairingArgsException if any of the arguments are null.
     * @throws ConnectException if the vehicle is not paired with the user.
     */
    @Override
    public void stopPairing(UserAccount user, VehicleID veh, StationID st, GeographicPoint loc, LocalDateTime date, float avSp, float dist, int dur, BigDecimal imp) throws InvalidPairingArgsException, ConnectException {
        if (user == null || veh == null || st == null || loc == null || date == null || imp == null) {
            throw new InvalidPairingArgsException("One or more arguments are null.");
        }

        PMVehicle vehicle = vehicleAvailability.get(veh);

        UserAccount pairedUser = vehicleUserMap.get(veh);
        if (pairedUser == null || !pairedUser.equals(user)) {
            throw new ConnectException("Vehicle is not paired with the specified user.");
        }

        vehicle.setAvailb();
        vehicleUserMap.remove(veh);
        registerLocation(veh, st);

        ServiceID serviceId = new ServiceID(String.format("%s_%s_%s", user.getId(), veh.getId(), st.getId()));
        JourneyService journeyService = activeJourneyServices.get(serviceId.getId());
        activeJourneyServices.remove(serviceId.getId());

        journeyService.setEndPoint(loc);
        journeyService.setEndDate(date.toLocalDate().atStartOfDay());
        journeyService.setEndHour(date.toLocalTime());
        journeyService.setAvgSpeed(avSp);
        journeyService.setDistance(dist);
        journeyService.setDuration(dur);
        journeyService.setImportCost(imp);

        try {
            unPairRegisterService(journeyService);
        } catch (PairingNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Sets a pairing between a user and a PMVehicle.
     *
     * @param user The user account.
     * @param veh The vehicle ID.
     * @param st The station ID.
     * @param loc The geographic location.
     * @param date The pairing date and time.
     */
    @Override
    public void setPairing(UserAccount user, VehicleID veh, StationID st, GeographicPoint loc, LocalDateTime date) {
        PMVehicle vehicle = vehicleAvailability.computeIfAbsent(veh, k -> new PMVehicle(PMVState.AVAILABLE, loc, 0.0));
        vehicle.setId(veh);
        vehicle.setNotAvailb();
        vehicleUserMap.put(veh, user);
        vehicleStationMap.put(veh, st);

        ServiceID serviceId = new ServiceID(String.format("%s_%s_%s", user.getId(), veh.getId(), st.getId()));
        JourneyService journeyService = new JourneyService(serviceId, loc);
        journeyService.setOriginPoint(vehicle.getLocation());
        journeyService.setInitDate(LocalDateTime.now());
        journeyService.setInitHour(LocalTime.now());
        journeyService.setServiceInit();
        activeJourneyServices.put(serviceId.getId(), journeyService);
    }

    /**
     * Registers the completion of a journey service.
     *
     * @param s The journey service to register.
     * @throws PairingNotFoundException if the service is null.
     */
    @Override
    public void unPairRegisterService(JourneyService s) throws PairingNotFoundException {
        if (s == null) {
            throw new PairingNotFoundException("Journey service is null.");
        }
        s.setServiceFinish();
        recordsJourneyServices.add(s);
    }

    /**
     * Updates the location of a PMVehicle at a specific station.
     *
     * @param veh The vehicle ID.
     * @param st The station ID.
     */
    @Override
    public void registerLocation(VehicleID veh, StationID st) {
        if (veh != null && st != null) {
            vehicleStationMap.put(veh, st);
        }
    }

    /**
     * Registers a payment for a journey service.
     *
     * @param servID The service ID.
     * @param user The user account.
     * @param imp The payment amount.
     * @param payMeth The payment method.
     * @throws ConnectException if the server connection fails.
     */
    @Override
    public void registerPayment(ServiceID servID, UserAccount user, BigDecimal imp, char payMeth) throws ConnectException {
        if (!statusConnection) {
            throw new ConnectException("Connection error when registering payment.");
        }

        String paymentRegister = String.format("%s_%s_%c", servID.getId(), imp.toString(), payMeth);
        ArrayList<String> list = paymentRecords.computeIfAbsent(user, k -> new ArrayList<>());
        list.add(paymentRegister);
    }
}
