package data.sensors;

/**
 * Represents a brake sensor that provides information about whether the brake is currently engaged or not.
 * Implements the SensorData interface.
 */
public class SensorBrake implements SensorData {

    private boolean braking; // Whether the brake is engaged (ON or OFF)
    private final String sensorType = "Light Sensor"; // Type of the sensor

    /**
     * Constructs a SensorBrake object with the specified braking state.
     *
     * @param braking The initial braking state (true for ON, false for OFF).
     */
    public SensorBrake(boolean braking) {
        this.braking = braking;
    }

    /**
     * Retrieves the current state of the brake (ON or OFF).
     *
     * @return A string indicating whether the brake is ON or OFF.
     */
    @Override
    public String getSensorData() {
        return "Current brake is : " + (this.braking ? "ON" : "OFF");
    }

    /**
     * Retrieves the type of the sensor.
     *
     * @return The sensor type as a string.
     */
    @Override
    public String getSensorType() {
        return "Sensor type: " + sensorType;
    }

    // Additional Methods

    /**
     * Gets the current braking state.
     *
     * @return true if the brake is engaged, false otherwise.
     */
    public boolean getBraking() {
        return braking;
    }

    /**
     * Sets the braking state of the sensor.
     *
     * @param braking The braking state to set (true for ON, false for OFF).
     */
    public void setBraking(boolean braking) {
        this.braking = braking;
    }
}
