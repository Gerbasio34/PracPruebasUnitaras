package services;

import data.GeographicPoint;
import data.StationID;
import data.UserAccount;
import data.VehicleID;
import exception.InvalidPairingArgsException;
import exception.PMVNotAvailException;
import exception.PairingNotFoundException;
import micromobility.JourneyService;
import micromobility.PMVehicle;
import micromobility.PMVState;

import java.math.BigDecimal;
import java.net.ConnectException;
import java.time.LocalDateTime;

import java.util.HashMap;
import java.util.Map;

public class ServerMC implements Server {

    // Simulation of a database using HashMaps
    private Map<VehicleID, PMVehicle> vehicleAvailability = new HashMap<>(); // true if available, false if not
    private Map<VehicleID, StationID> vehicleStationMap = new HashMap<>(); // Vehicle to station mapping
    private Map<VehicleID, UserAccount> vehicleUserMap = new HashMap<>(); // Matched vehicle to user mapping

    @Override
    public void checkPMVAvail(VehicleID vhID) throws PMVNotAvailException, ConnectException {
        if (vhID == null) {
            throw new ConnectException("VehicleID is null, unable to connect to server.");
        }

        // Verify if the vehicle is available
        PMVehicle vehicle = vehicleAvailability.get(vhID);
        if (vehicle == null) {
            throw new ConnectException("VehicleID not found in the system.");
        }

        if (vehicle.getState() != PMVState.AVAILABLE) {
            throw new PMVNotAvailException("Vehicle is already paired with another user.");
        }
    }

    @Override
    public void registerPairing(UserAccount user, VehicleID veh, StationID st, GeographicPoint loc, LocalDateTime date) throws InvalidPairingArgsException, ConnectException {
        if (user == null || veh == null || st == null || loc == null || date == null) {
            throw new InvalidPairingArgsException("One or more arguments are null.");
        }

        // Verify that the vehicle is available
        PMVehicle vehicle = vehicleAvailability.get(veh);
        if (vehicle == null) {
            throw new ConnectException("Vehicle is not available or does not exist.");
        }

        // Verify that the vehicle is at the specified station
        StationID currentStation = vehicleStationMap.get(veh);
        if (currentStation == null || !currentStation.equals(st)) {
            throw new ConnectException("Vehicle is not at the specified station.");
        }


        setPairing(user, veh, st, loc, date);
    }

    @Override
    public void stopPairing(UserAccount user, VehicleID veh, StationID st, GeographicPoint loc, LocalDateTime date, float avSp, float dist, int dur, BigDecimal imp) throws InvalidPairingArgsException, ConnectException {
        if (user == null || veh == null || st == null || loc == null || date == null || imp == null) {
            throw new InvalidPairingArgsException("One or more arguments are null.");
        }

        PMVehicle vehicle = vehicleAvailability.get(veh);

        // Verify that the vehicle was paired with the user
        UserAccount pairedUser = vehicleUserMap.get(veh);
        if (pairedUser == null || !pairedUser.equals(user)) {
            throw new ConnectException("Vehicle is not paired with the specified user.");
        }

        // Register the service completion (persistent record simulation)
        System.out.println("Service completed: " + user + " - " + veh + " - " + st + " - " + loc + " - " + date + " - " + avSp + " - " + dist + " - " + dur + " - " + imp);

        // Mark the vehicle as available and remove the pairing
        vehicle.setAvailb();
        vehicleUserMap.remove(veh);
        registerLocation(veh, st);
    }

    @Override
    public void setPairing(UserAccount user, VehicleID veh, StationID st, GeographicPoint loc, LocalDateTime date) {
        PMVehicle vehicle = vehicleAvailability.get(veh);
        vehicle.setNotAvailb();
        vehicleUserMap.put(veh, user); // Assign user to the vehicle
        vehicleStationMap.put(veh, st); // Update the vehicle's station
    }

    @Override
    public void unPairRegisterService(JourneyService s) throws PairingNotFoundException {
        if (s == null) {
            throw new PairingNotFoundException("Journey service is null.");
        }

        // Simulation of service unregistration
        System.out.println("Unregistering journey service: " + s);
    }

    @Override
    public void registerLocation(VehicleID veh, StationID st) {
        if (veh != null && st != null) {
            vehicleStationMap.put(veh, st); // Update the vehicle's location
        }
    }
}

