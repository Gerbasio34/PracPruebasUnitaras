package data.sensors;

public class SensorLight implements SensorData {

    private boolean light;
    private final String sensorType = "Light Sensor";

    public SensorLight(boolean light) {
        this.light = light;
    }

    @Override
    public String getSensorData() {
        return "Current light is : " + (this.light ? "ON" : "OFF");
    }

    @Override
    public String getSensorType() {
        return "Sensor type: " + sensorType;
    }

    // Additional Methods
    public boolean getLight() {
        return light;
    }

    public void setLight(boolean light) {
        this.light = light;
    }
}