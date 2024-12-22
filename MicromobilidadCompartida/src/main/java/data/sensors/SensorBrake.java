package data.sensors;

public class SensorBrake implements SensorData {

    private boolean isBraking;
    private final String sensorType = "Light Sensor";

    public SensorBrake(boolean isBraking) {
        this.isBraking = isBraking;
    }

    @Override
    public String getSensorData() {
        return "Current break is : " + (this.isBraking ? "ON" : "OFF");
    }

    @Override
    public String getSensorType() {
       return "Sensor type: " + sensorType;
    }

    // Additional Methods
    public boolean getIsBraking() {
        return isBraking;
    }

    public void setIsBraking(boolean isBraking) {
        this.isBraking = isBraking;
    }
}