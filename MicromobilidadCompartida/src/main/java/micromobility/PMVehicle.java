package micromobility;

import data.GeographicPoint;
import data.VehicleID;
import java.awt.image.BufferedImage;

public class PMVehicle {
    //class Members
    private String id;
    private PMVState state;
    private GeographicPoint location;
    private String sensorsData; // Sensor data as a string
    private double chargeLevel; // Battery charge level as a percentage (0.0 to 100.0)
    private BufferedImage QRCode; // QR code image for the vehicle

    // Constructor
    public PMVehicle(String id, PMVState initialState, GeographicPoint initialLocation, String sensorsData, double chargeLevel, BufferedImage QRCode) {
        this.id = id;
        this.state = initialState;
        this.location = initialLocation;
        this.sensorsData = sensorsData;
        this.chargeLevel = chargeLevel;
        this.QRCode = QRCode;
    }
    // Getter methods
    public String getId() {
        return id;
    }

    public PMVState getState() {
        return state;
    }

    public GeographicPoint getLocation() {
        return location;
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

    // Setter method for location
    public void setLocation(GeographicPoint gP) {
        if (gP != null) {
            this.location = gP;
        }
    }
    public void setSensorsData(String sensorsData) {
        this.sensorsData = sensorsData;
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
