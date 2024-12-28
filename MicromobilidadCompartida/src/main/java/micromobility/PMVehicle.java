package micromobility;

import data.GeographicPoint;
import data.VehicleID;
import data.sensors.*;
import exception.CorruptedImgException;
import services.smartfeatures.QRDecoderVMP;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Represents a Personal Mobility Vehicle (PMVehicle) with various attributes
 * such as state, location, charge level, and sensors data.
 */
public class PMVehicle {
    // Class Members
    private VehicleID id;
    private PMVState state;
    private GeographicPoint location;
    final ArrayList<SensorData> sensorsData;
    private double chargeLevel; // Battery charge level as a percentage (0.0 to 100.0)
    private BufferedImage QRCode; // QR code image for the vehicle
    private String QRPathFile;

    /**
     * Default constructor. Throws an exception because an ID must be provided.
     *
     * @throws IllegalArgumentException if ID is null.
     */
    public PMVehicle() {
        throw new IllegalArgumentException("ID cannot be null");
    }

    /**
     * Constructor to initialize a PMVehicle with a state, location, and charge level.
     *
     * @param initialState the initial state of the vehicle.
     * @param initialLocation the initial geographic location of the vehicle.
     * @param chargeLevel the initial battery charge level (0.0 to 100.0).
     */
    public PMVehicle(PMVState initialState, GeographicPoint initialLocation, double chargeLevel) {
        this.state = initialState;
        this.location = initialLocation;
        this.chargeLevel = chargeLevel;

        this.sensorsData = new ArrayList<SensorData>();
        sensorsData.add(new SensorLight(false));
        sensorsData.add(new SensorTemperature(20));
        sensorsData.add(new SensorBrake(false));
        sensorsData.add(new SensorSpeed(0));
    }

    /**
     * Constructor to initialize a PMVehicle with additional sensors data.
     *
     * @param initialState the initial state of the vehicle.
     * @param initialLocation the initial geographic location of the vehicle.
     * @param chargeLevel the initial battery charge level (0.0 to 100.0).
     * @param sensorsData a list of sensors data associated with the vehicle.
     */
    public PMVehicle(PMVState initialState, GeographicPoint initialLocation, double chargeLevel, ArrayList<SensorData> sensorsData) {
        this.state = initialState;
        this.location = initialLocation;
        this.sensorsData = sensorsData;
        this.chargeLevel = chargeLevel;
    }

    /**
     * Gets the unique ID of the vehicle.
     *
     * @return the vehicle ID.
     */
    public VehicleID getId() {
        return id;
    }

    /**
     * Gets the current state of the vehicle.
     *
     * @return the state of the vehicle.
     */
    public PMVState getState() {
        return state;
    }

    /**
     * Gets the current geographic location of the vehicle.
     *
     * @return the location of the vehicle.
     */
    public GeographicPoint getLocation() {
        return location;
    }

    /**
     * Gets the QR code image of the vehicle.
     *
     * @return the QR code image.
     */
    public BufferedImage getQRCode() {
        return QRCode;
    }

    /**
     * Gets the current battery charge level of the vehicle.
     *
     * @return the charge level as a percentage (0.0 to 100.0).
     */
    public double getChargeLevel() {
        return chargeLevel;
    }

    /**
     * Retrieves sensor data as a formatted string.
     *
     * @return a string containing all sensor data.
     */
    public String getSensorsData() {
        String results = "";
        for (SensorData sensorData : this.sensorsData) {
            results += String.format("%s: %s\n", sensorData.getSensorType(), sensorData.getSensorData());
        }
        return results;
    }

    /**
     * Sets the unique ID of the vehicle.
     *
     * @param id the unique ID to assign.
     */
    public void setId(VehicleID id) {
        this.id = id;
    }

    /**
     * Sets the QR code image from a file path.
     *
     * @param QRPathFile the path to the QR code image file.
     */
    public void setQRCode(String QRPathFile) {
        InputStream imageInputStream = PMVehicle.class.getClassLoader().getResourceAsStream(QRPathFile);
        if (imageInputStream != null) {
            try {
                this.QRCode = ImageIO.read(imageInputStream);
            } catch (IOException e) {
                QRCode = null;
            }
        }
        if (this.QRCode == null) {
            this.QRCode = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB); // Dummy QR code
        }
    }

    /**
     * Sets the vehicle state to "NOT_AVAILABLE".
     */
    public void setNotAvailb() {
        this.state = PMVState.NOT_AVAILABLE;
    }

    /**
     * Sets the vehicle state to "UNDER_WAY".
     */
    public void setUnderWay() {
        this.state = PMVState.UNDER_WAY;
    }

    /**
     * Sets the vehicle state to "AVAILABLE".
     */
    public void setAvailb() {
        this.state = PMVState.AVAILABLE;
    }

    /**
     * Sets the vehicle state to "TEMPORARY_PARKING".
     */
    public void setTemporaryParking() {
        this.state = PMVState.TEMPORARY_PARKING;
    }

    /**
     * Updates the geographic location of the vehicle.
     *
     * @param gP the new geographic location.
     * @throws NullPointerException if the location is null.
     */
    public void setLocation(GeographicPoint gP) throws NullPointerException {
        if (gP == null) throw new NullPointerException("Location must be defined cannot be null");
        this.location = gP;
    }

    /**
     * Sets the battery charge level of the vehicle.
     *
     * @param chargeLevel the new charge level (0.0 to 100.0).
     * @throws IllegalArgumentException if the charge level is out of range.
     */
    public void setChargeLevel(double chargeLevel) {
        if (chargeLevel < 0.0 || chargeLevel > 100.0) {
            throw new IllegalArgumentException("Charge level must be between 0.0 and 100.0");
        }
        this.chargeLevel = chargeLevel;
    }

    /**
     * Sets the QR code image directly.
     *
     * @param QRCode the new QR code image.
     */
    public void setQRCode(BufferedImage QRCode) {
        this.QRCode = QRCode;
    }
}
