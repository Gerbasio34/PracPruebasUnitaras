package services.smartfeatures;

import exception.PMVPhisicalException;
import exception.ProceduralException;

import java.net.ConnectException;

public class ArduinoMicroControllerVMP implements ArduinoMicroController {
    @Override
    public void setBTconnection() throws ConnectException {

    }

    @Override
    public void startDriving() throws PMVPhisicalException, ConnectException, ProceduralException {

    }

    @Override
    public void stopDriving() throws PMVPhisicalException, ConnectException, ProceduralException {

    }

    @Override
    public void undoBTconnection() {

    }
}