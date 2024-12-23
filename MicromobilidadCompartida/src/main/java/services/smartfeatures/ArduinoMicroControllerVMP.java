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
    private boolean inMovement = false;
    private long timeBraking = 0;
    private final float TIME_TO_STOP = 2; //seconds

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
    private void checkInMovement(){
        if (timeBraking == 0) {
            inMovement = true;
            return;
        }

        long endTimer = System.currentTimeMillis();
        float timePassed = endTimer - timeBraking;
        if ((timePassed / 1000.0) >= TIME_TO_STOP) { //to seconds
            inMovement = false;
            timeBraking = 0;
        }
    }
    public void setBraking(boolean isBraking) throws ConnectException{
        if (!btConnected) {
            throw new ConnectException("Bluetooth connection is not established.");
        }
        if (isBraking) {
            timeBraking = System.currentTimeMillis();
        }

        checkInMovement();
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

    public boolean getInMovement() {
        return inMovement;
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
            inMovement = true;
        }
    }

    @Override
    public void stopDriving() throws PMVPhisicalException, ConnectException, ProceduralException {
        checkInMovement();

        // Validate if Bluetooth connection is established
        if (!btConnected) {
            throw new ConnectException("Bluetooth connection is not established.");
        }
        // Simulate a failure related to the brakes
        if (technicalFailure) {
            throw new PMVPhisicalException("Technical issue with the brakes, cannot stop the vehicle.");
        }
        // Detects driver is braking until the vehicle comes to a stop.
        if (inMovement) {
            throw new ProceduralException("The vehicle is moving, cannot stop right now.");
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
            inMovement = false;
            timeBraking = 0;
        }
    }
}
