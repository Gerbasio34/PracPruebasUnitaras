package services.smartfeatures;

import static org.junit.jupiter.api.Assertions.*;
import data.VehicleID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import exception.CorruptedImgException;

public class QRDecoderVMPTest {

    private QRDecoderVMP qrDecoder;
    private BufferedImage qrImage;

    @BeforeEach
    public void setUp() {
        // Initialize the QRDecoderVMP instance before each test
        qrDecoder = new QRDecoderVMP();

        // Load the QR code image
        InputStream imageInputStream = QRDecoderVMP.class.getClassLoader().getResourceAsStream("qrcode-dummy.png");
        try {
            // Load the QR image from the resources folder
            qrImage = ImageIO.read(imageInputStream);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load the QR code image", e);
        }
    }

    @Test
    public void testGetVehicleIDValidQRCode() throws Exception {
        // Test1: Should decode a valid QR code and return the correct VehicleID
        VehicleID vehicleID = qrDecoder.getVehicleID(qrImage);
        assertNotNull(vehicleID);
        assertEquals("12345", vehicleID.getId());
    }

    @Test
    public void testGetVehicleIDCorruptedQRCode() {
        // Test2: Should throw CorruptedImgException when QR code is corrupted
        BufferedImage corruptedImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB); // Simulating a corrupted QR image
        assertThrows(CorruptedImgException.class, () -> qrDecoder.getVehicleID(corruptedImage));
    }

    @Test
    public void testGetVehicleIDNoQRCode() {
        // Test3: Should throw CorruptedImgException when no QR code is found
        BufferedImage noQRCodeImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB); // No QR code in this image
        assertThrows(CorruptedImgException.class, () -> qrDecoder.getVehicleID(noQRCodeImage));
    }
}
