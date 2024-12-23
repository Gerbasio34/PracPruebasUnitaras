package data.sensors;

public class SensorBrake implements SensorData {

    private boolean braking;
    private final String sensorType = "Light Sensor";

    public SensorBrake(boolean braking) {
        this.braking = braking;
    }

    @Override
    public String getSensorData() {
        return "Current brake is : " + (this.braking ? "ON" : "OFF");
    }

    @Override
    public String getSensorType() {
       return "Sensor type: " + sensorType;
    }

    // Additional Methods
    public boolean getBraking() {
        return braking;
    }

    public void setBraking(boolean braking) {
        this.braking = braking;
    }
}