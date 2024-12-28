package data;

import java.util.Objects;

/**
 * Represents a unique identifier for a service, following a specific format.
 */
final public class ServiceID {

    /**
     * The identifier string for the service.
     */
    private final String id;

    /**
     * Default constructor is disabled to prevent null ServiceID creation.
     *
     * @throws IllegalArgumentException always, as a ServiceID must not be null.
     */
    public ServiceID() {
        throw new IllegalArgumentException("ServiceID cannot be null");
    }

    /**
     * Constructs a {@code ServiceID} with the specified identifier string.
     *
     * @param id the identifier string, which must follow the format:
     *           "UA-username-max5numbers_VH-123456-name_ST-12345-name".
     * @throws IllegalArgumentException if the {@code id} is null or does not match the required format.
     */
    public ServiceID(String id) {
        if (id == null) {
            throw new IllegalArgumentException("ServiceID cannot be null");
        }
        if (!id.matches("UA-[a-zA-Z]+-\\d{1,5}_VH-\\d{6}-[a-zA-Z]+_ST-\\d{5}-[a-zA-Z]+")) {
            throw new IllegalArgumentException("Invalid ServiceID format. Expected 'UA-username-max5numbers_VH-123456-name_ST-12345-name'");
        }
        this.id = id;
    }

    /**
     * Returns the identifier string of this {@code ServiceID}.
     *
     * @return the identifier string.
     */
    public String getId() {
        return id;
    }

    /**
     * Compares this {@code ServiceID} to the specified object.
     *
     * @param o the object to compare with.
     * @return {@code true} if the specified object is equal to this {@code ServiceID}; {@code false} otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServiceID serviceID = (ServiceID) o;
        return Objects.equals(id, serviceID.id);
    }

    /**
     * Returns the hash code for this {@code ServiceID}.
     *
     * @return the hash code.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /**
     * Returns a string representation of this {@code ServiceID}.
     *
     * @return a string representation in the format "ServiceID{id='identifier'}".
     */
    @Override
    public String toString() {
        return "ServiceID{" + "id='" + id + '\'' + '}';
    }
}


