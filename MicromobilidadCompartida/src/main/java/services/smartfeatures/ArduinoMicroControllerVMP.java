package services.smartfeatures;

import exception.PMVPhisicalException;
import exception.ProceduralException;
import java.net.ConnectException;

public class ArduinoMicroControllerVMP implements ArduinoMicroController {

    // Simulate Bluetooth connection state and vehicle usage status
    private boolean isBtConnected = false;
    private boolean isVehicleInUse = false;
    private boolean isVehicleBeingDriven = false;
    private boolean isTechnicalFailure = false;
    private boolean isBraking = false;

    public ArduinoMicroControllerVMP(){}

    public void setIsVehicleBeingDriven(boolean isVehicleBeingDriven) {
        this.isVehicleBeingDriven = isVehicleBeingDriven;
    }

    public void setIsTechnicalFailure(boolean isTechnicalFailure) {
        this.isTechnicalFailure = isTechnicalFailure;
    }

    public void setIsBraking(boolean isBraking) {
        this.isBraking = isBraking;
    }

    @Override
    public void setBTconnection() throws ConnectException {
        // Simulate the process of establishing a Bluetooth connection
        if (isBtConnected) {
            throw new ConnectException("Bluetooth connection is already established.");
        }
        isBtConnected = true;
    }

    @Override
    public void startDriving() throws PMVPhisicalException, ConnectException, ProceduralException {
        // Detects driver has entered the vehicle and is handling it to start a journey.
        if (isVehicleBeingDriven) {
            // Validate if Bluetooth connection is established
            if (!isBtConnected) {
                throw new ConnectException("Bluetooth connection is not established.");
            }

            // Simulate a technical failure in the vehicle (e.g., if the motor does not respond)
            if (isTechnicalFailure) {
                throw new PMVPhisicalException("Technical issue with the vehicle, cannot start the ride.");
            }

            // Validate if the vehicle is already in use
            if (isVehicleInUse) {
                throw new ProceduralException("The vehicle is already in use.");
            }

            // Start the ride
            isVehicleInUse = true;
        }
    }

    @Override
    public void stopDriving() throws PMVPhisicalException, ConnectException, ProceduralException {
        // Detects driver is braking until the vehicle comes to a stop.
        if (isBraking) {
            // Validate if Bluetooth connection is established
            if (!isBtConnected) {
                throw new ConnectException("Bluetooth connection is not established.");
            }

            // Simulate a failure related to the brakes
            if (isTechnicalFailure) {
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
        if (isBtConnected) {
            isBtConnected = false;
            isVehicleInUse = false;
            isVehicleBeingDriven = false;
            isTechnicalFailure = false;
            isBraking = false;
        }
    }
}
