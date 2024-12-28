package micromobility.JourneyServiceTests;

import data.GeographicPoint;
import data.ServiceID;
import micromobility.JourneyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

class JourneyServiceFailTest {

    private JourneyService journeyService;
    private GeographicPoint originPoint;

    @BeforeEach
    void setUp() {
        originPoint = new GeographicPoint(40.4168f, -3.7038f);// Madrid coordinates
        ServiceID serviceID = new ServiceID("UA-test-2367_VH-123456-Patinete_ST-12345-Lleida");

        journeyService = new JourneyService(serviceID, originPoint);
    }

    @Test
    @DisplayName("Test 1: Should throw exception when trying to start an already in-progress service")
    void testServiceInitWhenInProgress() {
        journeyService.setServiceInit(); // Start the service
        assertThrows(IllegalStateException.class, journeyService::setServiceInit, "Should throw exception when trying to start an already in-progress service.");
    }

    @Test
    @DisplayName("Test 2: Should throw exception when trying to finish a service that is not in progress")
    void testServiceFinishWhenNotInProgress() {
        assertThrows(IllegalStateException.class, journeyService::setServiceFinish, "Should throw exception when trying to finish a service that is not in progress.");
    }

    @Test
    @DisplayName("Test 3: Should throw exception when setting a negative distance")
    void testSetDistanceWithNegativeValue() {
        assertThrows(IllegalArgumentException.class, () -> journeyService.setDistance(-5f), "Should throw exception when setting a negative distance.");
    }

    @Test
    @DisplayName("Test 4: Should throw exception when setting a negative duration")
    void testSetDurationWithNegativeValue() {
        assertThrows(IllegalArgumentException.class, () -> journeyService.setDuration(-10), "Should throw exception when setting a negative duration.");
    }
}
