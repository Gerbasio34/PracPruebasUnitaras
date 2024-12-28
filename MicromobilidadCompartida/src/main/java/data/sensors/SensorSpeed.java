package data.sensors;

/**
 * Represents a speed sensor that provides speed-related data.
 * Implements the {@link SensorData} interface to provide sensor-specific methods.
 */
public class SensorSpeed implements SensorData {

    private double speed; // km/h
    private final String sensorType = "Speed Sensor";

    /**
     * Constructs a SensorSpeed object with the specified speed.
     *
     * @param speed The speed value (in km/h) to set for the sensor.
     */
    public SensorSpeed(double speed) {
        this.speed = speed;
    }

    /**
     * Retrieves the sensor data, specifically the current speed.
     *
     * @return A string describing the current speed of the vehicle in km/h.
     */
    @Override
    public String getSensorData() {
        return "Current speed: " + speed + " km/h";
    }

    /**
     * Retrieves the type of sensor.
     *
     * @return A string indicating the sensor type ("Speed Sensor").
     */
    @Override
    public String getSensorType() {
        return "Sensor type: " + sensorType;
    }

    /**
     * Gets the current speed value.
     *
     * @return The current speed in km/h.
     */
    public double getSpeed() {
        return speed;
    }

    /**
     * Sets the speed value for the sensor.
     *
     * @param speed The new speed value to set (in km/h).
     */
    public void setSpeed(double speed) {
        this.speed = speed;
    }
}
