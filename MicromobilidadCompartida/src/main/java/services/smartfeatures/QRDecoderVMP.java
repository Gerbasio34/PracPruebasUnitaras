package services.smartfeatures;

import data.VehicleID;
import exception.CorruptedImgException;

import java.awt.image.BufferedImage;

public class QRDecoderVMP implements QRDecoder {
    @Override
    public VehicleID getVehicleID(BufferedImage QRImg) throws CorruptedImgException {
        return null;
    }
}
