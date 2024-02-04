package ar.edu.itba.paw.webapp.miscellaneous;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public enum ImagesSizes {
    CUADRADA(500, 500),
    FULL(200, 200),
    PORTADA(820, 312);

    private final int width;
    private final int height;

    ImagesSizes(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public byte[] resizeImage(byte[] image)  {
        if (image == null || this.name().equals("FULL")) {
            return image;
        }
        try{
        InputStream inputStream = new ByteArrayInputStream(image);
        BufferedImage originalBufferedImage = ImageIO.read(inputStream);

        int originalWidth = originalBufferedImage.getWidth();
        int originalHeight = originalBufferedImage.getHeight();

        int newWidth, newHeight;
        double aspectRatio = (double) originalWidth / originalHeight;

        if (aspectRatio > 1) {
            newWidth = this.width;
            newHeight = (int) (this.width / aspectRatio);
        } else {
            newWidth = (int) (this.height * aspectRatio);
            newHeight = this.height;
        }

        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
        resizedImage.createGraphics().drawImage(originalBufferedImage, 0, 0, newWidth, newHeight, null);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(resizedImage, "jpg", outputStream);
        return outputStream.toByteArray();
        }catch (IOException e){
            return image;
        }
    }
}
