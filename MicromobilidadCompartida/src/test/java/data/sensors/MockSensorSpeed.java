package data.sensors;

// Test double for SensorSpeed
public class MockSensorSpeed implements SensorData {
    private double mockSpeed;

    public MockSensorSpeed(double mockSpeed) {
        this.mockSpeed = mockSpeed;
    }

    @Override
    public String getSensorData() {
        return "Mock Speed: " + mockSpeed + "km/h";
    }

    @Override
    public String getSensorType() {
        return "Mock Speed Sensor";
    }

    public double getMockSpeed() {
        return mockSpeed;
    }

    public void setMockSpeed(double mockSpeed) {
        this.mockSpeed = mockSpeed;
    }
}
