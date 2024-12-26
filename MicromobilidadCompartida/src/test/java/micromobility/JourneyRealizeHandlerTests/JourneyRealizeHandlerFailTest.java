package micromobility.JourneyRealizeHandlerTests;

import data.GeographicPoint;
import data.StationID;
import data.UserAccount;
import micromobility.JourneyRealizeHandler;
import micromobility.PMVState;
import micromobility.PMVehicle;
import org.junit.jupiter.api.BeforeEach;
import services.Server;
import services.smartfeatures.ArduinoMicroController;

import java.time.LocalDateTime;

public class JourneyRealizeHandlerFailTest {
        private JourneyRealizeHandler handler;
        private ArduinoMicroController arduinoMock;
        private Server serverMock;
        private PMVehicle vehicle;
        private UserAccount user;
        private GeographicPoint gp;
        private StationID stID;

        @BeforeEach
        void setUp() {
            arduinoMock = new MockArduinoMicroController();
            serverMock = new MockServer();
            gp = new GeographicPoint(40.4168f, -3.7038f); // Madrid coordinates
            vehicle = new PMVehicle(PMVState.AVAILABLE, gp, 80.0);

            user = new UserAccount("UA-johnsmith-12345");
            stID = new StationID("ST-12345-Madrid");

            handler = new JourneyRealizeHandler(user, gp, vehicle);
            handler.setArduino(arduinoMock);
            handler.setServer(serverMock);
            handler.setStID(stID);
            handler.setDate(LocalDateTime.now());
        }
}
