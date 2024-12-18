package data;

import java.util.Objects;

/**
 * Essential data classes
 */
final public class VehicleID {
    private final String id;
    public VehicleID () {
        throw new IllegalArgumentException("VehicleID cannot be null");
    }

    public VehicleID(String id, GeographicPoint location) {
        if (id == null) {
            throw new IllegalArgumentException("VehicleID cannot be null");
        }
        if (!id.matches("VH-\\d{6}-[a-zA-Z]{1,10}")) { // Ejemplo: formato "VH-123456"
            throw new IllegalArgumentException("Invalid VehicleID format. Expected 'VH-123456-name'");
        }
        this.id = id;
    }

    // Getters
    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VehicleID vehicleID = (VehicleID) o;
        return Objects.equals(id, vehicleID.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "VehicleID{" + "id='" + id + '\'' + '}';
    }

}
