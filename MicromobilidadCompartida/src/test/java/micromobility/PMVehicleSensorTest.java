package micromobility;
import data.GeographicPoint;
import data.sensors.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import java.util.ArrayList;

// NO USAR Mockito TENEMOS QUE CREARLO NOSOTROS

class PMVehicleSensorTest {

    @Test
    void testGetSensorsData_WithMocks() {
        // Crear mocks de SensorData
        SensorData mockSensor1 = mock(SensorData.class);
        SensorData mockSensor2 = mock(SensorData.class);

        // Configurar comportamiento de los mocks
        when(mockSensor1.getSensorType()).thenReturn("Mock Sensor Type 1");
        when(mockSensor1.getSensorData()).thenReturn("Mock Sensor Data 1");

        when(mockSensor2.getSensorType()).thenReturn("Mock Sensor Type 2");
        when(mockSensor2.getSensorData()).thenReturn("Mock Sensor Data 2");

        // Crear lista de sensores simulados
        ArrayList<SensorData> mockSensors = new ArrayList<>();
        mockSensors.add(mockSensor1);
        mockSensors.add(mockSensor2);

        // Crear un PMVehicle con los mocks
        PMVehicle vehicle = new PMVehicle("VH-123456-Auto", PMVState.AVAILABLE, new GeographicPoint(0, 0), 50.0, null, mockSensors);

        // Probar el método getSensorsData
        String result = vehicle.getSensorsData();

        // Validar resultados
        assertTrue(result.contains("Mock Sensor Type 1: Mock Sensor Data 1"));
        assertTrue(result.contains("Mock Sensor Type 2: Mock Sensor Data 2"));

        // Verificar interacciones con los mocks
        verify(mockSensor1).getSensorType();
        verify(mockSensor1).getSensorData();
        verify(mockSensor2).getSensorType();
        verify(mockSensor2).getSensorData();
    }
}
