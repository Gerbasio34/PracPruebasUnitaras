
package services.smartfeatures;

import exception.PMVPhisicalException;
import exception.ProceduralException;

import java.net.ConnectException;

/**
 * External services involved in the functioning of some features
 */
public interface ArduinoMicroController {
    // Software para microcontroladores

    public void setBTconnection() throws ConnectException;

    public void startDriving() throws PMVPhisicalException,
            ConnectException,
            ProceduralException;

    public void stopDriving() throws PMVPhisicalException,
            ConnectException,
            ProceduralException;

    public void undoBTconnection();
}