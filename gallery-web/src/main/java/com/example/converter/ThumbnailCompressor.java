package com.example.converter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.imgscalr.Scalr;

public class ThumbnailCompressor {
    public static byte[] compressImage(byte[] originalImageBytes, int targetSize) throws IOException {
        ByteArrayInputStream bis = new ByteArrayInputStream(originalImageBytes);
        BufferedImage bufferedImage = ImageIO.read(bis);
        BufferedImage resizedImage = Scalr.resize(bufferedImage, targetSize);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(resizedImage, "png", bos);
        return bos.toByteArray();
    }
}
