package micromobility;

import data.GeographicPoint;
import data.VehicleID;
import data.sensors.*;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class PMVehicle {
    //class Members
    private final VehicleID id;
    private PMVState state;
    private GeographicPoint location;
    final ArrayList<SensorData> sensorsData;
    private double chargeLevel; // Battery charge level as a percentage (0.0 to 100.0)
    private BufferedImage QRCode; // QR code image for the vehicle

    public PMVehicle() {
        throw new IllegalArgumentException("ID cannot be null");
    }
    // Constructor
    public PMVehicle(String id, PMVState initialState, GeographicPoint initialLocation, double chargeLevel, BufferedImage QRCode) {
        this.id = new VehicleID(id);
        this.state = initialState;
        this.location = initialLocation;
        this.chargeLevel = chargeLevel;
        this.QRCode = QRCode;

        this.sensorsData = new ArrayList<SensorData>();
        sensorsData.add(new SensorLight(false));
        sensorsData.add(new SensorTemperature(20));
        sensorsData.add(new SensorBreak(true));
        sensorsData.add(new SensorSpeed(0));
    }

    public PMVehicle(String id, PMVState initialState, GeographicPoint initialLocation, double chargeLevel, BufferedImage QRCode, ArrayList<SensorData> sensorsData) {
        this.id = new VehicleID(id);
        this.state = initialState;
        this.location = initialLocation;
        this.sensorsData = sensorsData;
        this.chargeLevel = chargeLevel;
        this.QRCode = QRCode;
    }

    // Getter methods
    public String getId() {
        return id.toString();
    }

    public PMVState getState() {
        return state;
    }

    public GeographicPoint getLocation() {
        return location;
    }

    public BufferedImage getQRCode() {
        return QRCode;
    }

    public double getChargeLevel() {
        return chargeLevel;
    }

    public String getSensorsData() {
        String results = "";
        for (SensorData sensorData : this.sensorsData) {
            results += String.format("%s: %s\n", sensorData.getSensorType(), sensorData.getSensorData());
        }
        return results;
    }

    // Setter methods for state transitions
    public void setNotAvailb() {
        this.state = PMVState.NOT_AVAILABLE;
    }

    public void setUnderWay() {
        this.state = PMVState.UNDER_WAY;
    }

    public void setAvailb() {
        this.state = PMVState.AVAILABLE;
    }

    public void setTemporaryParking() {
        this.state = PMVState.TEMPORARY_PARKING;
    }

    // Setter method for location
    public void setLocation(GeographicPoint gP) {
        if (gP == null) new NullPointerException("Location must be defined cannot be null");
        this.location = gP;
    }

    public void setChargeLevel(double chargeLevel) {
        if (chargeLevel < 0.0 || chargeLevel > 100.0) {
            throw new IllegalArgumentException("Charge level must be between 0.0 and 100.0");
        }
        this.chargeLevel = chargeLevel;
    }

    public void setQRCode(BufferedImage QRCode) {
        this.QRCode = QRCode;
    }


}
