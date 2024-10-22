package Model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public abstract class AbstractCompressedImageFormat implements ImageFormat {

  public RGBPixel[][] load(String filename) {
    try {
      File file = new File(filename);
      BufferedImage bufferedImage = ImageIO.read(file);
      int width = bufferedImage.getWidth();
      int height = bufferedImage.getHeight();

      RGBPixel[][] pixels = new RGBPixel[height][width];
      for (int y = 0; y < height; y++) {
        for (int x = 0; x < width; x++) {
          int rgb = bufferedImage.getRGB(x, y);
          int red = (rgb >> 16) & 0xFF;
          int green = (rgb >> 8) & 0xFF;
          int blue = rgb & 0xFF;
          pixels[y][x] = new RGBPixel(red, green, blue);
        }
      }

      return pixels;

    } catch (IOException e) {
      System.out.println("Error loading image: " + e.getMessage());
    }
    return null;
  }
  public void save(String filename, RGBPixel[][] pixels) {

  }
}
