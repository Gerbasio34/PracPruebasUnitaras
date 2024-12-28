package data.sensors;

/**
 * Represents a temperature sensor that provides temperature data.
 * This class implements the SensorData interface and allows retrieving temperature information
 * in Celsius, as well as the sensor's type.
 */
public class SensorTemperature implements SensorData {

    private double temperature; // °C
    private final String sensorType = "Temperature Sensor";

    /**
     * Constructor to create a new Temperature Sensor with a specified temperature.
     *
     * @param temperature The temperature reading in Celsius (°C).
     */
    public SensorTemperature(double temperature) {
        this.temperature = temperature;
    }

    /**
     * Retrieves the sensor data, specifically the current temperature.
     *
     * @return A string representing the current temperature in Celsius.
     */
    @Override
    public String getSensorData() {
        return "Current temperature: " + temperature + "°C";
    }

    /**
     * Retrieves the type of the sensor.
     *
     * @return A string representing the sensor type, which is "Temperature Sensor".
     */
    @Override
    public String getSensorType() {
        return "Sensor type: " + sensorType;
    }

    // Additional Methods

    /**
     * Gets the current temperature reading.
     *
     * @return The temperature value in Celsius.
     */
    public double getTemperature() {
        return temperature;
    }

    /**
     * Sets a new temperature value for the sensor.
     *
     * @param temperature The new temperature value in Celsius.
     */
    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }
}
