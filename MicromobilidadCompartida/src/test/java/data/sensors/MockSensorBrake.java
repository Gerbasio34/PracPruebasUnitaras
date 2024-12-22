package data.sensors;

// Test double for SensorBrake
public class MockSensorBrake implements SensorData {
    private boolean mockBraking;

    public MockSensorBrake(boolean mockBraking) {
        this.mockBraking = mockBraking;
    }

    @Override
    public String getSensorData() {
        return "Mock Brake is : " + (mockBraking ? "ON" : "OFF");
    }

    @Override
    public String getSensorType() {
        return "Mock Brake Sensor";
    }

    public boolean isMockBraking() {
        return mockBraking;
    }

    public void setMockBraking(boolean mockBraking) {
        this.mockBraking = mockBraking;
    }
}
