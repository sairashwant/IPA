package Model.ImageFormat;

import Model.ColorScheme.RGBPixel;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * This class represents compressed image formats such as jpg,png. The load function is common for
 * both jpg and png so it's written in this class.
 */

public abstract class AbstractCompressedImageFormat implements ImageFormat {

  /**
   * Loads a file as RGB pixels. We use bufferedImage to load rgb values from the file. Then we use
   * bit-manipulation to split red,green and blue values.
   *
   * @param filename Image file name.
   * @returns RGBpixel type 2D Array.
   */
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

  /**
   * This function saves a given 2d Array of RGB values.
   *
   * @param filename Name of the file to save.
   * @param pixels   Pixels of the file to save.
   */
  public void save(String filename, RGBPixel[][] pixels) {

  }
}
