package services.smartfeatures;


import exception.PMVPhisicalException;
import exception.ProceduralException;
import java.net.ConnectException;

public class ArduinoMicroControllerVMP implements ArduinoMicroController {

    // Simulate Bluetooth connection state and vehicle usage status
    private boolean btConnected = false;
    private boolean vehicleInUse = false;
    private boolean vehicleBeingDriven = false;
    private boolean technicalFailure = false;
    private boolean braking = false;
    public ArduinoMicroControllerVMP(){}

    public void setVehicleBeingDriven(boolean isVehicleBeingDriven) throws ConnectException{
        if (!btConnected) {
            throw new ConnectException("Bluetooth connection is not established.");
        }
        this.vehicleBeingDriven = isVehicleBeingDriven;
    }
    public void setTechnicalFailure(boolean isTechnicalFailure) {
        this.technicalFailure = isTechnicalFailure;
    }
    public void setVehicleInUse(boolean isVehicleInUse){
        this.vehicleInUse = isVehicleInUse;
    }
    public void setBraking(boolean isBraking) throws ConnectException{
        if (!btConnected) {
            throw new ConnectException("Bluetooth connection is not established.");
        }

        this.braking = isBraking;
    }

    public boolean getBtConnected() {
        return btConnected;
    }

    public boolean getVehicleInUse() {
        return vehicleInUse;
    }

    public boolean getVehicleBeingDriven() {
        return vehicleBeingDriven;
    }

    public boolean getTechnicalFailure() {
        return technicalFailure;
    }
    public boolean getBraking() {
        return braking;
    }

    @Override
    public void setBTconnection() throws ConnectException {
        // Simulate the process of establishing a Bluetooth connection
        if (btConnected) {
            throw new ConnectException("Bluetooth connection is already established.");
        }

        btConnected = true;
    }

    @Override
    public void startDriving() throws PMVPhisicalException, ConnectException, ProceduralException {
        // Detects driver has entered the vehicle and is handling it to start a journey.
        if (vehicleBeingDriven) {
            // Validate if Bluetooth connection is established
            if (!btConnected) {
                throw new ConnectException("Bluetooth connection is not established.");
            }

            // Simulate a technical failure in the vehicle (e.g., if the motor does not respond)
            if (technicalFailure) {
                throw new PMVPhisicalException("Technical issue with the vehicle, cannot start the ride.");
            }

            // Validate if the vehicle is already in use
            if (vehicleInUse) {
                throw new ProceduralException("The vehicle is already in use.");
            }

            // Start the ride
            vehicleInUse = true;
        }
    }

    @Override
    public void stopDriving() throws PMVPhisicalException, ConnectException, ProceduralException {

        // Validate if Bluetooth connection is established
        if (!btConnected) {
            throw new ConnectException("Bluetooth connection is not established.");
        }
        // Simulate a failure related to the brakes
        if (technicalFailure) {
            throw new PMVPhisicalException("Technical issue with the brakes, cannot stop the vehicle.");
        }
        // Validate if the vehicle is being driven
        if (!vehicleBeingDriven) {
            throw new ProceduralException("The vehicle is not being driven.");
        }

        // Stop the ride
        vehicleInUse = false;
    }

    @Override
    public void undoBTconnection() {
        // Undo the Bluetooth connection
        if (btConnected) {
            btConnected = false;
            vehicleInUse = false;
            vehicleBeingDriven = false;
            technicalFailure = false;
            braking = false;
        }
    }
}
