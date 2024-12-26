package data.sensors;

// Test double for SensorBrake
public class MockSensorDataB implements SensorData {
    private float sensorB;

    public MockSensorDataB(float sensorB) {
        this.sensorB = sensorB;
    }

    @Override
    public String getSensorData() {
        return "Mock sensor B : " + sensorB;
    }

    @Override
    public String getSensorType() {
        return "Mock Sensor B";
    }

    public float getSensorB() {
        return sensorB;
    }

    public void setSensorB(float sensorB) {
        this.sensorB = sensorB;
    }
}
