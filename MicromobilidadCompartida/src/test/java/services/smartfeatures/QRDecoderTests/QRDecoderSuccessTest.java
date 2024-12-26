package services.smartfeatures.QRDecoderTests;

import static org.junit.jupiter.api.Assertions.*;
import data.VehicleID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import exception.CorruptedImgException;
import services.smartfeatures.QRDecoderVMP;

public class QRDecoderSuccessTest {

    private QRDecoderVMP qrDecoder;
    private BufferedImage qrImage;

    @BeforeEach
    public void setUp() {
        // Initialize the QRDecoderVMP instance before each test
        qrDecoder = new QRDecoderVMP();
        // Load the QR code image
        InputStream imageInputStream = QRDecoderSuccessTest.class.getClassLoader().getResourceAsStream("qrcode-dummy.png");
        try {
            // Load the QR image from the resources folder
            qrImage = ImageIO.read(imageInputStream);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load the QR code image", e);
        }
    }

    @Test
    @DisplayName("Test1: Should decode a valid QR code and return the correct VehicleID")
    public void testGetVehicleIDValidQRCode() throws CorruptedImgException {
        VehicleID vehicleID = qrDecoder.getVehicleID(qrImage);
        assertNotNull(vehicleID);
        assertEquals("VH-123456-TestVehicle", vehicleID.getId());
    }

}
