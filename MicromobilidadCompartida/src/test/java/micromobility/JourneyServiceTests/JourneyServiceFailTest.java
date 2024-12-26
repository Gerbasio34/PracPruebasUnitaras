package micromobility.JourneyServiceTests;

import data.GeographicPoint;
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
        originPoint = new GeographicPoint(40.4168f, -3.7038f); // Madrid coordinates
        journeyService = new JourneyService("Service123", originPoint);
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
