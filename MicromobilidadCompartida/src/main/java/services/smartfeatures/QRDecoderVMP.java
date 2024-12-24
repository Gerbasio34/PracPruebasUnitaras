package services.smartfeatures;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.qrcode.QRCodeReader;
import data.VehicleID;
import exception.CorruptedImgException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import java.awt.image.BufferedImage;

public class QRDecoderVMP implements QRDecoder {

    public VehicleID getVehicleID(BufferedImage QRImg) throws CorruptedImgException {
        try {
            // Create a QRCodeReader to decode the QR code
            QRCodeReader reader = new QRCodeReader();

            // Convert the BufferedImage to a BinaryBitmap object
            LuminanceSource source = new BufferedImageLuminanceSource(QRImg);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

            // Decode the QR code
            Result result = reader.decode(bitmap);

            // Extract the content of the QR (e.g., a vehicle ID)
            String vehicleIDStr = result.getText();
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