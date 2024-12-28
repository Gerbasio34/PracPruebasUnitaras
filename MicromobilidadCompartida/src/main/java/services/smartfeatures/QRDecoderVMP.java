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

/**
 * Class responsible for decoding QR codes to extract vehicle IDs.
 * Implements the QRDecoder interface.
 */
public class QRDecoderVMP implements QRDecoder {

    /**
     * Decodes a given QR code image to extract the vehicle ID.
     *
     * @param QRImg The QR code image in the form of a BufferedImage.
     * @return The extracted VehicleID from the QR code.
     * @throws CorruptedImgException If the QR code is corrupted or cannot be decoded.
     */
    public VehicleID getVehicleID(BufferedImage QRImg) throws CorruptedImgException {
        try {
            // Create a QRCodeReader to decode the QR code
            QRCodeReader reader = new QRCodeReader();

            // Convert the BufferedImage to a BinaryBitmap object for processing
            LuminanceSource source = new BufferedImageLuminanceSource(QRImg);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

            // Decode the QR code to extract the result
            Result result = reader.decode(bitmap);

            // Extract the content of the QR (e.g., vehicle ID)
            String vehicleIDStr = result.getText();

            // Return the VehicleID extracted from the QR code
            return new VehicleID(vehicleIDStr);

        } catch (NotFoundException e) {
            // If no QR code is found in the image
            throw new CorruptedImgException("No QR code found in the image.", e);
        } catch (Exception e) {
            // General exception handling for other errors
            throw new CorruptedImgException("Error decoding QR code.", e);
        }
    }
}
