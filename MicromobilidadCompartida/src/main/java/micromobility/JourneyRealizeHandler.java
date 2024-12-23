package micromobility;

import data.GeographicPoint;
import data.StationID;
import data.UserAccount;
import data.VehicleID;
import exception.*;
import services.Server;
import services.ServerMC;
import services.smartfeatures.ArduinoMicroControllerVMP;
import services.smartfeatures.QRDecoderVMP;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.time.LocalDateTime;

// Class that handles events and operations related to journey realization.
public class JourneyRealizeHandler {

    // Class members
    private StationID stID;
    private QRDecoderVMP qrDecoder;
    private BufferedImage qrImage;
    private  ServerMC server;
    private ArduinoMicroControllerVMP arduino;
    private UserAccount user;
    private GeographicPoint gp;
    private VehicleID vehID;
    private LocalDateTime date;

    // Constructors
    public JourneyRealizeHandler(UserAccount user) {
        // Object initialization
        server = new ServerMC();
        arduino = new ArduinoMicroControllerVMP();
        user = user;
    }

    //GETTERS

    public StationID getStID() {
        return stID;
    }



    // User interface input events
    public void scanQR() throws ConnectException, InvalidPairingArgsException, CorruptedImgException, PMVNotAvailException,
            ProceduralException {
        // Implementation
        // Initialize the QRDecoderVMP instance before each test
        qrDecoder = new QRDecoderVMP();
        // Load the QR code image
        InputStream imageInputStream = QRDecoderVMP.class.getClassLoader().getResourceAsStream("qrcode-dummy.png");
        try {
            if (imageInputStream == null) {
                throw new CorruptedImgException("QR Code image is missing or corrupted.");
            }
            // Load the QR image from the resources folder
            qrImage = ImageIO.read(imageInputStream);
            if (qrImage == null) {
                throw new CorruptedImgException("Failed to read the QR Code image.");
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load the QR code image", e);
        }
        //decode the QR to obtain the VehicleID
        VehicleID vehicleID = qrDecoder.getVehicleID(qrImage);

        //check if the vehicle is available
        server.checkPMVAvail(vehicleID);

        //conect BT connection
        arduino.setBTconnection();

        //try to register the pairing of the user with the vehicle
        server.registerPairing(user,vehID, stID, gp, date);

    }

    public void unPairVehicle()
            throws ConnectException, InvalidPairingArgsException,
            PairingNotFoundException, ProceduralException {
        // Implementation
    }

    // Input events from the unbonded Bluetooth channel
    public void broadcastStationID(StationID stID) throws ConnectException {
        if (stID == null) {
            throw new ConnectException("Null Station ID received.");
        }
        this.stID = stID;
    }

    // Input events from the Arduino microcontroller channel
    public void startDriving()
            throws ConnectException, ProceduralException {
        // Implementation
    }

    public void stopDriving()
            throws ConnectException, ProceduralException {
        // Implementation
    }

    // Internal operations
    private void calculateValues(GeographicPoint gP, LocalDateTime date) {
        // Implementation
    }

    private void calculateImport(float dis, int dur, float avSp, LocalDateTime date) {
        // Implementation
    }

    // Setter methods for injecting dependences

}
