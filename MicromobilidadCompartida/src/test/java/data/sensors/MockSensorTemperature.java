package data.sensors;

// Test double for SensorTemperature
public class MockSensorTemperature implements SensorData {
    private double mockTemperature;

    public MockSensorTemperature(double mockTemperature) {
        this.mockTemperature = mockTemperature;
    }

    @Override
    public String getSensorData() {
        return "Mock Temperature: " + mockTemperature + "°C";
    }

    @Override
    public String getSensorType() {
        return "Mock Temperature Sensor";
    }

    public double getMockTemperature() {
        return mockTemperature;
    }

    public void setMockTemperature(double mockTemperature) {
        this.mockTemperature = mockTemperature;
    }
}
