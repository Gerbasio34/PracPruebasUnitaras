package services.smartfeatures.QRDecoderTests;

import exception.CorruptedImgException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import services.smartfeatures.QRDecoderVMP;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class QRDecoderFailTest {
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
    @DisplayName("Test1: Should throw CorruptedImgException when QR code is corrupted")
    public void testGetVehicleIDCorruptedQRCode() {
        BufferedImage corruptedImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB); // Simulating a corrupted QR image
        assertThrows(CorruptedImgException.class, () -> qrDecoder.getVehicleID(corruptedImage));
    }

    @Test
    @DisplayName("Test3: Should throw CorruptedImgException when no QR code is found")
    public void testGetVehicleIDNoQRCode() {
        BufferedImage noQRCodeImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB); // No QR code in this image
        assertThrows(CorruptedImgException.class, () -> qrDecoder.getVehicleID(noQRCodeImage));
    }

}
