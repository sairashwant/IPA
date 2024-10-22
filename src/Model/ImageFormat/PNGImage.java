package Model.ImageFormat;

import Model.RGBPixel;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class PNGImage extends AbstractCompressedImageFormat {

  @Override
  public void save(String filename, RGBPixel[][] pixels) {
    {
      int width = pixels[0].length;
      int height = pixels.length;

      BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
      for (int y = 0; y < height; y++) {
        for (int x = 0; x < width; x++) {
          RGBPixel pixel = (RGBPixel) pixels[y][x];
          int rgb = (pixel.getRed() << 16) | (pixel.getGreen() << 8) | pixel.getBlue();
          image.setRGB(x, y, rgb);
        }
      }
      try {
        File outputFile = new File(filename);
        ImageIO.write(image, "png", outputFile);
        System.out.println("Image saved as: " + outputFile.getAbsolutePath());
      } catch (IOException e) {
        System.out.println("Error saving the image: " + e.getMessage());
      }
    }
  }
}
