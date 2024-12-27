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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ServerMC implements Server {

    // Simulation of a database using HashMaps
    public static Map<VehicleID, PMVehicle> vehicleAvailability = new HashMap<>(); // true if available, false if not
    public static Map<VehicleID, StationID> vehicleStationMap = new HashMap<>(); // Vehicle to station mapping
    public static Map<VehicleID, UserAccount> vehicleUserMap = new HashMap<>(); // Matched vehicle to user mapping

    private static Map<String,JourneyService> activeJourneyServices = new HashMap<>(); // Manage Multiple Journeys all the same time
    private static ArrayList<JourneyService> recordsJourneyServices = new ArrayList<>(); // Matched vehicle to user mapping

    public static ArrayList<String> paymentRecords = new ArrayList<>(); //

    public static boolean statusConnection = true;

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
        // System.out.println("Service completed: " + user + " - " + veh + " - " + st + " - " + loc + " - " + date + " - " + avSp + " - " + dist + " - " + dur + " - " + imp);

        // Mark the vehicle as available and remove the pairing
        vehicle.setAvailb();
        vehicleUserMap.remove(veh);
        registerLocation(veh, st);

        // Sets Server
        String serviceId = String.format("%s_%s_%s",user.getId(),veh,st); // same user with the same veh at the same station is unique
        JourneyService journeyService = activeJourneyServices.get(serviceId);
        activeJourneyServices.remove(serviceId);

        // Sets journey
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

    @Override
    public void setPairing(UserAccount user, VehicleID veh, StationID st, GeographicPoint loc, LocalDateTime date) {
        PMVehicle vehicle = vehicleAvailability.computeIfAbsent(veh, k -> new PMVehicle(
                PMVState.AVAILABLE, loc, 0.0
        ));
        vehicle.setId(veh);
        vehicle.setNotAvailb();
        vehicleUserMap.put(veh, user); // Assign user to the vehicle
        vehicleStationMap.put(veh, st); // Update the vehicle's station

        String serviceId = String.format("%s_%s_%s",user.getId(),veh,st); // same user with the same veh at the same station is unique
        JourneyService journeyService = new JourneyService(serviceId, loc);
        journeyService.setOriginPoint(vehicle.getLocation());
        journeyService.setInitDate(LocalDateTime.now());
        journeyService.setInitHour(LocalTime.now());
        journeyService.setServiceInit();
        activeJourneyServices.put(serviceId,journeyService);
    }

    @Override
    public void unPairRegisterService(JourneyService s) throws PairingNotFoundException {
        if (s == null) {
            throw new PairingNotFoundException("Journey service is null.");
        }

        s.setServiceFinish();
        recordsJourneyServices.add(s);
    }

    @Override
    public void registerLocation(VehicleID veh, StationID st) {
        if (veh != null && st != null) {
            vehicleStationMap.put(veh, st); // Update the vehicle's location
        }
    }

    @Override
    public void registerPayment(ServiceID servID, UserAccount user, BigDecimal imp, char payMeth) throws ConnectException {
        if(!statusConnection){
            throw new ConnectException("Connection error when register payment");
        }

        String paymentRegister = String.format("%s_%s_%s_%c",servID.toString(),user.getId(),imp.toString(),payMeth);
        paymentRecords.add(paymentRegister);
    }
}