package data.sensors;

/**
 * Represents a light sensor that provides information about its current state (ON or OFF).
 */
public class SensorLight implements SensorData {

    private boolean light;
    private final String sensorType = "Light Sensor";

    /**
     * Constructs a SensorLight object with the specified initial state.
     *
     * @param light the initial state of the light sensor (true for ON, false for OFF).
     */
    public SensorLight(boolean light) {
        this.light = light;
    }

    /**
     * Gets the current state of the light sensor.
     *
     * @return a string describing whether the light is ON or OFF.
     */
    @Override
    public String getSensorData() {
        return "Current light is : " + (this.light ? "ON" : "OFF");
    }

    /**
     * Gets the type of the sensor.
     *
     * @return a string describing the sensor type.
     */
    @Override
    public String getSensorType() {
        return "Sensor type: " + sensorType;
    }

    /**
     * Retrieves the current state of the light.
     *
     * @return true if the light is ON, false otherwise.
     */
    public boolean getLight() {
        return light;
    }

    /**
     * Updates the state of the light.
     *
     * @param light the new state of the light (true for ON, false for OFF).
     */
    public void setLight(boolean light) {
        this.light = light;
    }
}
