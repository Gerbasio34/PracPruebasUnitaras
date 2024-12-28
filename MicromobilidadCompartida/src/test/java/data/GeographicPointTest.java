package data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GeographicPointTest {

    private GeographicPoint madridPoint;
    private GeographicPoint barcelonaPoint;

    // Set up before each test
    @BeforeEach
    void setUp() {
        madridPoint = new GeographicPoint(40.4168f, -3.7038f); // Madrid coordinates
        barcelonaPoint = new GeographicPoint(41.3851f, 2.1734f); // Barcelona coordinates
    }

    @Test
    @DisplayName("Test1: Verify creation of a GeographicPoint and the correct getters for latitude and longitude")
    void testCreationAndGetters() {
        assertEquals(40.4168f, madridPoint.getLatitude()); // Check latitude
        assertEquals(-3.7038f, madridPoint.getLongitude()); // Check longitude
    }

    @Test
    @DisplayName("Test2: Verify that two GeographicPoint objects with the same values are considered equal")
    void testEqualsForSameValues() {
        GeographicPoint point2 = new GeographicPoint(40.4168f, -3.7038f); // Madrid
        assertEquals(madridPoint, point2); // Verify they are equal
    }

    @Test
    @DisplayName("Test3: Verify that two GeographicPoint objects with different values are not considered equal")
    void testEqualsForDifferentValues() {
        assertNotEquals(madridPoint, barcelonaPoint); // Verify they are not equal
    }

    @Test
    @DisplayName("Test4: Verify that two GeographicPoint objects with the same values have the same hash code")
    void testHashCodeForSameValues() {
        GeographicPoint point2 = new GeographicPoint(40.4168f, -3.7038f); // Madrid
        assertEquals(madridPoint.hashCode(), point2.hashCode()); // Verify they have the same hash code
    }

    @Test
    @DisplayName("Test5: Verify that two GeographicPoint objects with different values have different hash codes")
    void testHashCodeForDifferentValues() {
        assertNotEquals(madridPoint.hashCode(), barcelonaPoint.hashCode()); // Verify they have different hash codes
    }

    @Test
    @DisplayName("Test6: Verify the toString method of a GeographicPoint object")
    void testToString() {
        String expected = "Geographic point {latitude='40.4168'longitude='-3.7038'}";
        assertEquals(expected, madridPoint.toString()); // Verify the correct string format
    }
}
