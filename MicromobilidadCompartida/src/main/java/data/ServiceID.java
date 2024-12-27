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
        if (!id.matches("UA-[a-zA-Z]+-\\d{1,5}_VH-\\d{6}-[a-zA-Z]+_ST-\\d{5}-[a-zA-Z]+")) { // Ejemplo: formato "SV-123456"
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
        ServiceID serviceID = (ServiceID) o;
        return Objects.equals(id, serviceID.id);
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
