package services.smartfeatures;

import com.google.zxing.qrcode.QRCodeReader;
import data.VehicleID;
import exception.CorruptedImgException;
import com.google.zxing.*;
import com.google.zxing.common.HybridBinarizer;

import java.awt.image.BufferedImage;

public class QRDecoder {

    public VehicleID getVehicleID(BufferedImage QRImg) throws CorruptedImgException {
        try {
            // Create a QRCodeReader to decode the QR code
            QRCodeReader reader = new QRCodeReader();

            // Convert the BufferedImage to a format that ZXing can read
            LuminanceSource source = new BufferedImageLuminanceSource(QRImg);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

            // Decode the QR code
            Result result = reader.decode(bitmap);

            // Extract the content of the QR (e.g., a vehicle ID)
            String vehicleIDStr = result.getText();

            // Convert the QR content to a VehicleID (this will depend on how the ID is structured)
            // Here we assume that vehicleIDStr is a valid vehicle ID
            return new VehicleID(vehicleIDStr);

        } catch (NotFoundException e) {
            // If no QR code is found in the image
            throw new CorruptedImgException("No QR code found in the image.", e);
        } catch (Exception e) {
            // General exception handling
            throw new CorruptedImgException("Error decoding QR code.", e);
        }
    }
}