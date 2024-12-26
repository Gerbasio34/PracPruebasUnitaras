package services.smartfeatures.ServerTests;

import data.GeographicPoint;
import data.StationID;
import data.UserAccount;
import data.VehicleID;
import exception.InvalidPairingArgsException;
import exception.PMVNotAvailException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import services.ServerMC;

import java.math.BigDecimal;
import java.net.ConnectException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class ServerFailTest {

    private ServerMC server;
    private VehicleID vehicleID;
    private StationID stationID;
    private UserAccount userAccount;
    private GeographicPoint location;

    @BeforeEach
    public void setUp() {
        // Initialize the server and mock data before each test
        server = new ServerMC();
        vehicleID = new VehicleID("VH-123456-Patinete");
        stationID = new StationID("ST-12345-Madrid");
        userAccount = new UserAccount("UA-Romero-2344");
        location = new GeographicPoint(40.4168f, -3.7038f); // Example coordinates (Madrid)
    }

    @Test
    @DisplayName("Test vehicle availability check failure for unavailable vehicle")
    public void testCheckPMVAvail_Fail_VehicleNotAvailable() {
        // Ensure exception is thrown when vehicle is not available
        assertThrows(ConnectException.class, () -> server.checkPMVAvail(vehicleID));
    }

    @Test
    @DisplayName("Test pairing registration failure with invalid arguments")
    public void testRegisterPairing_Fail_InvalidArguments() {
        // Ensure exception is thrown for null user argument
        LocalDateTime now = LocalDateTime.now();
        assertThrows(InvalidPairingArgsException.class, () -> server.registerPairing(null, vehicleID, stationID, location, now));
    }

    @Test
    @DisplayName("Test unpairing failure for non-paired vehicle")
    public void testStopPairing_Fail_NotPaired() {
        // Ensure exception is thrown when trying to unpair a vehicle not paired to a user
        LocalDateTime now = LocalDateTime.now();
        BigDecimal cost = BigDecimal.valueOf(10.5);
        assertThrows(ConnectException.class, () -> server.stopPairing(userAccount, vehicleID, stationID, location, now, 15.0f, 5.0f, 10, cost));
    }

    @Test
    @DisplayName("Test stop pairing failure due to invalid arguments")
    public void testStopPairing_Fail_InvalidArguments() {
        // Ensure exception is thrown for null cost argument
        LocalDateTime now = LocalDateTime.now();
        BigDecimal cost = null;
        assertThrows(InvalidPairingArgsException.class, () -> server.stopPairing(userAccount, vehicleID, stationID, location, now, 15.0f, 5.0f, 10, cost));
    }
}
