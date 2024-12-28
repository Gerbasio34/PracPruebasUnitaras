package services.smartfeatures;

import exception.PMVPhisicalException;
import exception.ProceduralException;
import java.net.ConnectException;

/**
 * Simulates the behavior of an Arduino microcontroller for managing vehicle operations.
 * This includes handling Bluetooth connectivity, vehicle state, and technical failures.
 */
public class ArduinoMicroControllerVMP implements ArduinoMicroController {

    private boolean btConnected = false; // Indicates if Bluetooth is connected
    private boolean vehicleInUse = false; // Indicates if the vehicle is currently in use
    private boolean vehicleBeingDriven = false; // Indicates if the vehicle is being driven
    private boolean technicalFailure = false; // Indicates if there's a technical failure
    private boolean braking = false; // Indicates if the vehicle is braking

    /**
     * Default constructor.
     */
    public ArduinoMicroControllerVMP() {}

    /**
     * Sets whether the vehicle is being driven.
     *
     * @param isVehicleBeingDriven true if the vehicle is being driven, false otherwise.
     * @throws ConnectException if the Bluetooth connection is not established.
     */
    public void setVehicleBeingDriven(boolean isVehicleBeingDriven) throws ConnectException {
        if (!btConnected) {
            throw new ConnectException("Bluetooth connection is not established.");
        }
        this.vehicleBeingDriven = isVehicleBeingDriven;
    }

    /**
     * Sets whether there's a technical failure.
     *
     * @param isTechnicalFailure true if there's a technical failure, false otherwise.
     */
    public void setTechnicalFailure(boolean isTechnicalFailure) {
        this.technicalFailure = isTechnicalFailure;
    }

    /**
     * Sets whether the vehicle is in use.
     *
     * @param isVehicleInUse true if the vehicle is in use, false otherwise.
     */
    public void setVehicleInUse(boolean isVehicleInUse) {
        this.vehicleInUse = isVehicleInUse;
    }

    /**
     * Sets whether the vehicle is braking.
     *
     * @param isBraking true if the vehicle is braking, false otherwise.
     * @throws ConnectException if the Bluetooth connection is not established.
     */
    public void setBraking(boolean isBraking) throws ConnectException {
        if (!btConnected) {
            throw new ConnectException("Bluetooth connection is not established.");
        }
        this.braking = isBraking;
    }

    /**
     * Gets the Bluetooth connection status.
     *
     * @return true if Bluetooth is connected, false otherwise.
     */
    public boolean getBtConnected() {
        return btConnected;
    }

    /**
     * Gets whether the vehicle is in use.
     *
     * @return true if the vehicle is in use, false otherwise.
     */
    public boolean getVehicleInUse() {
        return vehicleInUse;
    }

    /**
     * Gets whether the vehicle is being driven.
     *
     * @return true if the vehicle is being driven, false otherwise.
     */
    public boolean getVehicleBeingDriven() {
        return vehicleBeingDriven;
    }

    /**
     * Gets whether there's a technical failure.
     *
     * @return true if there's a technical failure, false otherwise.
     */
    public boolean getTechnicalFailure() {
        return technicalFailure;
    }

    /**
     * Gets whether the vehicle is braking.
     *
     * @return true if the vehicle is braking, false otherwise.
     */
    public boolean getBraking() {
        return braking;
    }

    /**
     * Establishes a Bluetooth connection.
     *
     * @throws ConnectException if Bluetooth is already connected.
     */
    @Override
    public void setBTconnection() throws ConnectException {
        if (btConnected) {
            throw new ConnectException("Bluetooth connection is already established.");
        }
        btConnected = true;
    }

    /**
     * Starts driving the vehicle.
     *
     * @throws PMVPhisicalException if there's a technical issue with the vehicle.
     * @throws ConnectException if the Bluetooth connection is not established.
     * @throws ProceduralException if the vehicle is already in use.
     */
    @Override
    public void startDriving() throws PMVPhisicalException, ConnectException, ProceduralException {
        if (vehicleBeingDriven) {
            if (!btConnected) {
                throw new ConnectException("Bluetooth connection is not established.");
            }
            if (technicalFailure) {
                throw new PMVPhisicalException("Technical issue with the vehicle, cannot start the ride.");
            }
            if (vehicleInUse) {
                throw new ProceduralException("The vehicle is already in use.");
            }
            vehicleInUse = true;
        }
    }

    /**
     * Stops driving the vehicle.
     *
     * @throws PMVPhisicalException if there's a technical issue with the brakes.
     * @throws ConnectException if the Bluetooth connection is not established.
     * @throws ProceduralException if the vehicle is not being driven.
     */
    @Override
    public void stopDriving() throws PMVPhisicalException, ConnectException, ProceduralException {
        if (!btConnected) {
            throw new ConnectException("Bluetooth connection is not established.");
        }
        if (technicalFailure) {
            throw new PMVPhisicalException("Technical issue with the brakes, cannot stop the vehicle.");
        }
        if (!vehicleBeingDriven) {
            throw new ProceduralException("The vehicle is not being driven.");
        }
        vehicleInUse = false;
    }

    /**
     * Disconnects the Bluetooth connection and resets the vehicle state.
     */
    @Override
    public void undoBTconnection() {
        if (btConnected) {
            btConnected = false;
            vehicleInUse = false;
            vehicleBeingDriven = false;
            technicalFailure = false;
            braking = false;
        }
    }
}
