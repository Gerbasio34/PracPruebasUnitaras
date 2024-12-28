package micromobility.JourneyServiceTests;

import data.GeographicPoint;
import data.ServiceID;
import micromobility.JourneyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;

class JourneyServiceSuccessTest {

    private JourneyService journeyService;
    private GeographicPoint originPoint;

    @BeforeEach
    void setUp() {
        originPoint = new GeographicPoint(40.4168f, -3.7038f); // Madrid coordinates
        ServiceID serviceID = new ServiceID("UA-test-2367_VH-123456-Patinete_ST-12345-Lleida");
        journeyService = new JourneyService(serviceID, originPoint);
    }

    @Test
    @DisplayName("Test 1: Should initialize the service correctly")
    void testServiceInit() {
        assertFalse(journeyService.getInProgress(), "Service InProgress should be false initially.");
        journeyService.setServiceInit();
        assertTrue(journeyService.getInProgress(), "Service InProgress should be true");
    }

    @Test
    @DisplayName("Test 2: Should finish the service correctly")
    void testServiceFinish() {
        journeyService.setServiceInit(); // Start the service
        journeyService.setEndDate(LocalDateTime.now());
        journeyService.setEndHour(LocalTime.now());
        journeyService.setEndDate(LocalDateTime.now());
        journeyService.setServiceFinish(); // Finish the service
        assertFalse(journeyService.getEndDate() == null, "Service end date should be set after service finish.");
        assertFalse(journeyService.getEndHour() == null, "Service end time should be set after service finish.");
    }

    @Test
    @DisplayName("Test 3: Should set and get the distance correctly")
    void testSetDistance() {
        float distance = 10.5f;
        journeyService.setDistance(distance);
        assertEquals(distance, journeyService.getDistance(), "The distance should match the set value.");
    }

    @Test
    @DisplayName("Test 4: Should set and get the duration correctly")
    void testSetDuration() {
        int duration = 60; // Duration in minutes
        journeyService.setDuration(duration);
        assertEquals(duration, journeyService.getDuration(), "The duration should match the set value.");
    }

    @Test
    @DisplayName("Test 5: Should set and get the import cost correctly")
    void testSetImportCost() {
        BigDecimal cost = new BigDecimal("25.75");
        journeyService.setImportCost(cost);
        assertEquals(cost, journeyService.getImportCost(), "The import cost should match the set value.");
    }
}
