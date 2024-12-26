package data.sensors;

// Test double for SensorBrake
public class MockSensorDataA implements SensorData {
    private boolean sensorA;

    public MockSensorDataA(boolean sensorA) {
        this.sensorA = sensorA;
    }

    @Override
    public String getSensorData() {
        return "Mock sensor A is : " + (sensorA ? "ON" : "OFF");
    }

    @Override
    public String getSensorType() {
        return "Mock Sensor A";
    }

    public boolean isSensorA() {
        return sensorA;
    }

    public void setSensorA(boolean sensorA) {
        this.sensorA = sensorA;
    }
}
