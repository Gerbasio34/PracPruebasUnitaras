package data.sensors;

public class SensorTemperature implements SensorData {

    private double temperature; // °C
    private final String sensorType = "Temperature Sensor";

    public SensorTemperature(double temperature) {
        this.temperature = temperature;
    }

    @Override
    public String getSensorData() {
        return "Current temperature: " + temperature + "°C";
    }

    @Override
    public String getSensorType() {
         return "Sensor type: " + sensorType;
    }

    // Additional Methods
    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }
}
