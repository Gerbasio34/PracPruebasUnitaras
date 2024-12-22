package data.sensors;

// Test double for SensorLight
public class MockSensorLight implements SensorData {
    private boolean mockLight;

    public MockSensorLight(boolean mockLight) {
        this.mockLight = mockLight;
    }

    @Override
    public String getSensorData() {
        return "Mock Light is : " + (mockLight ? "ON" : "OFF");
    }

    @Override
    public String getSensorType() {
        return "Mock Light Sensor";
    }

    public boolean isMockLight() {
        return mockLight;
    }

    public void setMockLight(boolean mockLight) {
        this.mockLight = mockLight;
    }
}
