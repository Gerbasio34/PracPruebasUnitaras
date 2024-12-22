package data.sensors;

public class SensorBreak implements SensorData {

    private boolean isBreaking;
    private final String sensorType = "Light Sensor";

    public SensorBreak(boolean isBreaking) {
        this.isBreaking = isBreaking;
    }

    @Override
    public String getSensorData() {
        return "Current break is : " + (this.isBreaking ? "ON" : "OFF");
    }

    @Override
    public String getSensorType() {
       return "Sensor type: " + sensorType;
    }

    // Additional Methods
    public boolean getIsBreaking() {
        return isBreaking;
    }

    public void setIsBreaking(boolean isBreaking) {
        this.isBreaking = isBreaking;
    }
}