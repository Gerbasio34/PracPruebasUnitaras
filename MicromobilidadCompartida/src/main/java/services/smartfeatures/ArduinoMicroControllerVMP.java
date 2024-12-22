package services.smartfeatures;

import exception.PMVPhisicalException;
import exception.ProceduralException;
import java.net.ConnectException;

public class ArduinoMicroControllerVMP implements ArduinoMicroController {

    // Simulate Bluetooth connection state and vehicle usage status
    private boolean btConnected = false;
    private boolean isVehicleInUse = false;
    private boolean driverIntoVehicle = false;
    private boolean vehicleHandled = false;
    private boolean technicalFailure = false;
    private boolean isBraking = false;

    public void setDriverIntoVehicle(boolean driverIntoVehicle) {
        this.driverIntoVehicle = driverIntoVehicle;
    }

    public void setVehicleHandled(boolean vehicleHandled) {
        this.vehicleHandled = vehicleHandled;
    }

    public void setTechnicalFailure(boolean technicalFailure) {
        this.technicalFailure = technicalFailure;
    }

    public void setBraking(boolean isBraking) {
        this.isBraking = isBraking;
    }

    @Override
    public void setBTconnection() throws ConnectException {
        // Simulate the process of establishing a Bluetooth connection
        if (btConnected) {
            throw new ConnectException("Bluetooth connection is already established.");
        }
        System.out.println("Establishing Bluetooth connection with the vehicle...");
        btConnected = true;
        System.out.println("Bluetooth connection established successfully.");
    }

    @Override
    public void startDriving() throws PMVPhisicalException, ConnectException, ProceduralException {
        // Detects driver has entered the vehicle and is handling it to start a journey.
        if (driverIntoVehicle && vehicleHandled) {
            // Validate if Bluetooth connection is established
            if (!btConnected) {
                throw new ConnectException("Bluetooth connection is not established.");
            }

            // Simulate a technical failure in the vehicle (e.g., if the motor does not respond)
            if (technicalFailure) {
                throw new PMVPhisicalException("Technical issue with the vehicle, cannot start the ride.");
            }

            // Validate if the vehicle is already in use
            if (isVehicleInUse) {
                throw new ProceduralException("The vehicle is already in use.");
            }

            // Start the ride
            isVehicleInUse = true;
            System.out.println("Ride started, the vehicle is in motion.");
        }
    }

    @Override
    public void stopDriving() throws PMVPhisicalException, ConnectException, ProceduralException {
        // Detects driver is braking until the vehicle comes to a stop.
        if (isBraking) {
            // Validate if Bluetooth connection is established
            if (!btConnected) {
                throw new ConnectException("Bluetooth connection is not established.");
            }

            // Simulate a failure related to the brakes
            if (technicalFailure) {
                throw new PMVPhisicalException("Technical issue with the brakes, cannot stop the vehicle.");
            }

            // Validate if the vehicle is in use
            if (!isVehicleInUse) {
                throw new ProceduralException("The vehicle is not in use.");
            }

            // Stop the ride
            isVehicleInUse = false;
            System.out.println("Ride stopped, the vehicle has come to a halt.");
        }
    }

    @Override
    public void undoBTconnection() {
        // Undo the Bluetooth connection
        if (!btConnected) {
            System.out.println("No active Bluetooth connection.");
        } else {
            System.out.println("Disconnecting Bluetooth...");
            btConnected = false;
            System.out.println("Bluetooth connection disconnected successfully.");
        }
    }
}
