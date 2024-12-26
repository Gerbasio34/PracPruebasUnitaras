package micromobility.JourneyRealizeHandlerTests;

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
import services.Server;

import java.math.BigDecimal;
import java.net.ConnectException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class MockServer implements Server {

    private Map<VehicleID, Boolean> vehicleAvailability = new HashMap<>();
    private Map<VehicleID, Boolean> pairingRegistry = new HashMap<>();

    @Override
    public void checkPMVAvail(VehicleID vhID) throws PMVNotAvailException, ConnectException {
        if (vhID == null) {
            throw new ConnectException("VehicleID is null, unable to connect to server.");
        }

        if (!vehicleAvailability.containsKey(vhID) || !vehicleAvailability.get(vhID)) {
            throw new PMVNotAvailException("Vehicle is not available.");
        }
    }

    @Override
    public void registerPairing(UserAccount user, VehicleID veh, StationID st, GeographicPoint loc, LocalDateTime date) throws InvalidPairingArgsException, ConnectException {

        if (user == null || veh == null || st == null || loc == null || date == null) {
            throw new InvalidPairingArgsException("One or more arguments are null.");
        }

        vehicleAvailability.put(veh, false);
        pairingRegistry.put(veh, true);
    }

    @Override
    public void stopPairing(UserAccount user, VehicleID veh, StationID st, GeographicPoint loc, LocalDateTime date, float avSp, float dist, int dur, BigDecimal imp) throws InvalidPairingArgsException, ConnectException {
        if (user == null || veh == null || st == null || loc == null || date == null) {
            throw new InvalidPairingArgsException("One or more arguments are null.");
        }
        vehicleAvailability.put(veh, true);
        pairingRegistry.put(veh, false);
    }

    @Override
    public void setPairing(UserAccount user, VehicleID veh, StationID st, GeographicPoint loc, LocalDateTime date) {

    }

    @Override
    public void unPairRegisterService(JourneyService s) throws PairingNotFoundException {

    }

    @Override
    public void registerLocation(VehicleID veh, StationID st) {

    }

}
