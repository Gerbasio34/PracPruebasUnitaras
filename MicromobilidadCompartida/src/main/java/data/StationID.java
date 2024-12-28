package data;

import java.util.Objects;

/**
 * Represents a unique identifier for a station, adhering to a specific format.
 * The expected format is 'ST-12345-name', where:
 * <ul>
 *   <li>ST is a fixed prefix.</li>
 *   <li>12345 is a 5-digit number.</li>
 *   <li>name is an alphanumeric string (1 to 30 characters).</li>
 * </ul>
 */
final public class StationID {

    private final String id;

    /**
     * Default constructor. Throws an exception because a StationID cannot be null.
     *
     * @throws IllegalArgumentException if no ID is provided.
     */
    public StationID() {
        throw new IllegalArgumentException("StationID cannot be null");
    }

    /**
     * Constructs a StationID with the specified ID string.
     *
     * @param id the ID string to assign to this StationID.
     * @throws IllegalArgumentException if the ID is null or does not match the required format.
     */
    public StationID(String id) {
        if (id == null) {
            throw new IllegalArgumentException("StationID cannot be null");
        }
        // Regex for validation: ST-12345-name
        if (!id.matches("ST-\\d{5}-[a-zA-Z]{1,30}")) {
            throw new IllegalArgumentException("Invalid StationID format. Expected 'ST-12345-name'");
        }
        this.id = id;
    }

    /**
     * Gets the ID of the station.
     *
     * @return the station ID as a string.
     */
    public String getId() {
        return id;
    }

    /**
     * Compares this StationID to another object for equality.
     * Two StationIDs are considered equal if their IDs are identical.
     *
     * @param o the object to compare to.
     * @return true if the other object is a StationID with the same ID, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StationID stationID = (StationID) o;
        return Objects.equals(id, stationID.id);
    }

    /**
     * Computes the hash code for this StationID based on its ID.
     *
     * @return the hash code as an integer.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /**
     * Returns a string representation of this StationID.
     *
     * @return a string in the format "StationID{id='ID'}".
     */
    @Override
    public String toString() {
        return "StationID{" + "id='" + id + '\'' + '}';
    }
}
