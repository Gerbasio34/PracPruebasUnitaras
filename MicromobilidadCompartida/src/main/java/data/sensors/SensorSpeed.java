package data.sensors;

public class SensorSpeed implements SensorData {

    private double speed; // km/h
    private final String sensorType = "Speed Sensor";

    public SensorSpeed(double speed) {
        this.speed = speed;
    }

    @Override
    public String getSensorData() {
        return "Current speed: " + speed + "km/h";
    }

    @Override
    public String getSensorType() {
        return "Sensor type: " + sensorType;
    }

    // Additional Methods
    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }
}