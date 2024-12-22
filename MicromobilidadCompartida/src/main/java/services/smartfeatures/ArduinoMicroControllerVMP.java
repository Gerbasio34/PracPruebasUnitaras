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
    private boolean isInMovement = false;
    private long timeBraking = 0;

    private final float TIME_TO_STOP = 2; //seconds

    public ArduinoMicroControllerVMP(){}

    public void setIsVehicleBeingDriven(boolean isVehicleBeingDriven) throws ConnectException{
        if (!isBtConnected) {
            throw new ConnectException("Bluetooth connection is not established.");
        }
        this.isVehicleBeingDriven = isVehicleBeingDriven;
    }
    public void setIsTechnicalFailure(boolean isTechnicalFailure) {
        this.isTechnicalFailure = isTechnicalFailure;
    }
    public void setVehicleInUse(boolean isVehicleInUse){
        this.isVehicleInUse = isVehicleInUse;
    }
    private void checkInMovement(){
        if (timeBraking == 0) {
            isInMovement = true;
            return;
        }

        long endTimer = System.currentTimeMillis();
        float timePassed = endTimer - timeBraking;
        if ((timePassed / 1000.0) >= TIME_TO_STOP) { //to seconds
            isInMovement = false;
            timeBraking = 0;
        }
    }
    public void setIsBraking(boolean isBraking) throws ConnectException{
        if (!isBtConnected) {
            throw new ConnectException("Bluetooth connection is not established.");
        }
        if (isBraking) {
            timeBraking = System.currentTimeMillis();
        }

        checkInMovement();
        this.isBraking = isBraking;
    }

    public boolean getIsBtConnected() {
        return isBtConnected;
    }

    public boolean getIsVehicleInUse() {
        return isVehicleInUse;
    }

    public boolean getIsVehicleBeingDriven() {
        return isVehicleBeingDriven;
    }

    public boolean getIsTechnicalFailure() {
        return isTechnicalFailure;
    }

    public boolean getIsBraking() {
        return isBraking;
    }

    public boolean getIsInMovement() {
        return isInMovement;
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
            isInMovement = true;
        }
    }

    @Override
    public void stopDriving() throws PMVPhisicalException, ConnectException, ProceduralException {
        checkInMovement();

        // Validate if Bluetooth connection is established
        if (!isBtConnected) {
            throw new ConnectException("Bluetooth connection is not established.");
        }
        // Simulate a failure related to the brakes
        if (isTechnicalFailure) {
            throw new PMVPhisicalException("Technical issue with the brakes, cannot stop the vehicle.");
        }
        // Detects driver is braking until the vehicle comes to a stop.
        if (isInMovement) {
            throw new ProceduralException("The vehicle is moving, cannot stop right now.");
        }
        // Validate if the vehicle is being driven
        if (!isVehicleBeingDriven) {
            throw new ProceduralException("The vehicle is not being driven.");
        }

        // Stop the ride
        isVehicleInUse = false;
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
            isInMovement = false;
            timeBraking = 0;
        }
    }
}
