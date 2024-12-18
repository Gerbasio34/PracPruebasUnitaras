package services.smartfeatures;

import data.VehicleID;

import java.awt.image.BufferedImage;

public interface QRDecoder { // Decodes QR codes from an image
    VehicleID getVehicleID(BufferedImage QRImg) throws CorruptedImgException;
}