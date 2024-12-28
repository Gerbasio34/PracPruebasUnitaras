package data;

import java.util.Objects;

/**
 * Represents a unique vehicle identifier.
 * The identifier follows a specific format: "VH-123456-name", where the first part is numeric,
 * followed by a dash and a string representing the name or type of the vehicle.
 * This class is immutable, ensuring that the identifier can never be null or have an incorrect format.
 */
final public class VehicleID {

    private final String id;

    /**
     * Default constructor that throws an exception when called.
     * This constructor is invalid as a vehicle identifier cannot be null.
     *
     * @throws IllegalArgumentException always, indicating that the VehicleID cannot be null.
     */
    public VehicleID() {
        throw new IllegalArgumentException("VehicleID cannot be null");
    }

    /**
     * Constructor that creates a VehicleID object with the given identifier.
     * The identifier cannot be null and must follow the format "VH-123456-name".
     *
     * @param id The vehicle identifier. Must adhere to the format "VH-123456-name".
     * @throws IllegalArgumentException If the identifier is null or does not match the expected format.
     */
    public VehicleID(String id) {
        if (id == null) {
            throw new IllegalArgumentException("VehicleID cannot be null");
        }
        if (!id.matches("VH-\\d{6}-[a-zA-Z]{1,30}")) { // Example format "VH-123456"
            throw new IllegalArgumentException("Invalid VehicleID format. Expected 'VH-123456-name'");
        }
        this.id = id;
    }

    /**
     * Gets the vehicle identifier.
     *
     * @return The vehicle identifier.
     */
    public String getId() {
        return id;
    }

    /**
     * Compares this object with another to check if they are equal. Two VehicleID objects are equal
     * if they have the same identifier.
     *
     * @param o The object to compare with this VehicleID.
     * @return {@code true} if the object is a VehicleID and has the same identifier; {@code false} otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VehicleID vehicleID = (VehicleID) o;
        return Objects.equals(id, vehicleID.id);
    }

    /**
     * Returns the hash code value for this object. The vehicle identifier is used to generate the hash.
     *
     * @return The hash code value of the VehicleID.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /**
     * Returns a string representation of the VehicleID object.
     * The string format is: "VehicleID{id='id'}".
     *
     * @return A string representing the VehicleID.
     */
    @Override
    public String toString() {
        return "VehicleID{" + "id='" + id + '\'' + '}';
    }
}
