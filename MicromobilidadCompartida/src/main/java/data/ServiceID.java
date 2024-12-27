package data;

import java.util.Objects;

final public class ServiceID {
    private final String id;

    public ServiceID() {
        throw new IllegalArgumentException("ServiceID cannot be null");
    }

    public ServiceID(String id) {
        if (id == null) {
            throw new IllegalArgumentException("ServiceID cannot be null");
        }
        if (!id.matches("VH-\\d{6}-[a-zA-Z]{1,30}")) { // Ejemplo: formato "SV-123456"
            throw new IllegalArgumentException("Invalid ServiceID format. Expected 'SV-123456-name'");
        }
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServiceID vehicleID = (ServiceID) o;
        return Objects.equals(id, vehicleID.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "ServiceID{" + "id='" + id + '\'' + '}';
    }
}
