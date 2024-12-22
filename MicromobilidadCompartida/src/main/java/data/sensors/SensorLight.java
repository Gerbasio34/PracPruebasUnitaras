package data.sensors;

public class SensorLight implements SensorData {

    private boolean isLight;
    private final String sensorType = "Light Sensor";

    public SensorLight(boolean isLight) {
        this.isLight = isLight;
    }

    @Override
    public String getSensorData() {
        return "Current light is : " + (this.isLight ? "ON" : "OFF");
    }

    @Override
    public String getSensorType() {
        return "Sensor type: " + sensorType;
    }

    // Additional Methods
    public boolean getIsLight() {
        return isLight;
    }

    public void setIsLight(boolean isLight) {
        this.isLight = isLight;
    }
}