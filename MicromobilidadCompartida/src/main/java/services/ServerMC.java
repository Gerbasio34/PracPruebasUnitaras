package services;

import data.GeographicPoint;
import data.StationID;
import data.UserAccount;
import data.VehicleID;

import java.math.BigDecimal;
import java.net.ConnectException;
import java.time.LocalDateTime;

public class ServerMC implements Server{

    @Override
    public void checkPMVAvail(VehicleID vhID) throws PMVNotAvailException, ConnectException {

    }

    @Override
    public void registerPairing(UserAccount user, VehicleID veh, StationID st, GeographicPoint loc, LocalDateTime date) throws InvalidPairingArgsException, ConnectException {

    }

    @Override
    public void stopPairing(UserAccount user, VehicleID veh, StationID st, GeographicPoint loc, LocalDateTime date, float avSp, float dist, int dur, BigDecimal imp) throws InvalidPairingArgsException, ConnectException {

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
