package services;

import data.GeographicPoint;
import data.ServiceID;
import data.StationID;
import data.UserAccount;
import data.VehicleID;
import exception.InvalidPairingArgsException;
import exception.PMVNotAvailException;
import exception.PairingNotFoundException;
import micromobility.JourneyService;

import java.math.BigDecimal;
import java.net.ConnectException;
import java.time.LocalDateTime;

public interface Server { // External service for the persistent storage

    // To be invoked by the use case controller
    void checkPMVAvail(VehicleID vhID)
            throws PMVNotAvailException, ConnectException;

    void registerPairing(UserAccount user, VehicleID veh, StationID st, GeographicPoint loc, LocalDateTime date)
            throws InvalidPairingArgsException, ConnectException;

    void stopPairing(UserAccount user, VehicleID veh, StationID st,
             GeographicPoint loc, LocalDateTime date, float avSp, float dist,
             int dur, BigDecimal imp)
                        throws InvalidPairingArgsException, ConnectException;

    // Internal operations
    void setPairing(UserAccount user, VehicleID veh, StationID st, GeographicPoint loc, LocalDateTime date);
    void unPairRegisterService(JourneyService s)
            throws PairingNotFoundException;
    void registerLocation(VehicleID veh, StationID st);

    void registerPayment(ServiceID servID, UserAccount user, BigDecimal imp,
                         char payMeth) throws ConnectException;
}