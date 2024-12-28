package services.smartfeatures.ServerTests;

import data.*;
import micromobility.PMVehicle;
import micromobility.PMVState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import services.Server;
import services.ServerMC;

import java.math.BigDecimal;
import java.net.ConnectException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class ServerSuccessTest {

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
        stationID = new StationID("ST-12345-Lleida");
        userAccount = new UserAccount("UA-test-2367");
        location = new GeographicPoint(40.4168f, -3.7038f); // Example coordinates (Madrid)

        // Simulate a vehicle available in the system
        PMVehicle vehicle = new PMVehicle(PMVState.AVAILABLE, location, 0.0);
        ServerMC.vehicleAvailability.put(vehicleID, vehicle);
        ServerMC.vehicleStationMap.put(vehicleID, stationID);
    }

    @Test
    @DisplayName("Test1: successful vehicle availability check")
    public void testCheckPMVAvail_Success() throws Exception {
        // Ensure the vehicle availability check works without exceptions
        assertDoesNotThrow(() -> server.checkPMVAvail(vehicleID));
    }

    @Test
    @DisplayName("Test2: successful pairing registration")
    public void testRegisterPairing_Success() throws Exception {
        // Ensure pairing registration works without exceptions
        LocalDateTime now = LocalDateTime.now();
        assertDoesNotThrow(() -> server.registerPairing(userAccount, vehicleID, stationID, location, now));

        // Verify that the vehicle is paired to the user
        assertEquals(userAccount, ServerMC.vehicleUserMap.get(vehicleID));
    }

    @Test
    @DisplayName("Test3: successful unpairing and service completion")
    public void testStopPairing_Success() throws Exception {
        // Simulate pairing before testing unpairing
        LocalDateTime now = LocalDateTime.now();
        assertDoesNotThrow(() -> server.registerPairing(userAccount, vehicleID, stationID, location, now ));

        // Test successful stop pairing
        BigDecimal cost = BigDecimal.valueOf(10.5);
        assertDoesNotThrow(() ->server.stopPairing(userAccount, vehicleID, stationID, location, now, 15.0f, 5.0f, 10, cost));

        // Verify that the vehicle is unpaired and marked as available
        assertNull(ServerMC.vehicleUserMap.get(vehicleID));
        assertTrue(ServerMC.vehicleAvailability.get(vehicleID).getState() == PMVState.AVAILABLE);
    }

    @Test
    @DisplayName("Test4: successful payment registration")
    public void testRegisterPayment_Success() throws Exception {
        ServiceID serviceID = new ServiceID(String.format("%s_%s_%s",userAccount.getId(),vehicleID.getId(),stationID.getId()));
        BigDecimal amount = new BigDecimal("50.0");
        char payMeth = 'C';

        assertDoesNotThrow(() -> server.registerPayment(serviceID, userAccount, amount, payMeth));

        String expectedPayment = "UA-test-2367_VH-123456-Patinete_ST-12345-Lleida_50.0_C";
        assertTrue(ServerMC.paymentRecords.get(userAccount).contains(expectedPayment));
    }
}
