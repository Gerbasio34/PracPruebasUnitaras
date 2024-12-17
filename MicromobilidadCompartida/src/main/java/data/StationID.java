package data;

import java.util.Objects;

/**
 * Essential data classes
 */
final public class StationID {

    private final String id;
    private final GeographicPoint location;

    public StationID() {
        throw new IllegalArgumentException("StationID cannot be null");
    }

    public StationID(String id, GeographicPoint location) {
        if (id == null) {
            throw new IllegalArgumentException("StationID cannot be null");
        }
        //Regex -> https://regex101.com/
        if (!id.matches("ST-\\d{5}-[a-zA-Z]{1,10}")) {
            throw new IllegalArgumentException("Invalid StationID format. Expected 'ST-12345-name'");
        }
        this.id = id;
        this.location = location;
    }

    // Getters
    public GeographicPoint getLocation() {
        return location;
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StationID stationID = (StationID) o;
        return Objects.equals(id, stationID.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "StationID{" + "id='" + id + '\'' + '}';
    }
}

