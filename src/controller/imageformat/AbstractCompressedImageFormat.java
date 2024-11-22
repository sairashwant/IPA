package controller.imageformat;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import model.colorscheme.Pixels;
import model.colorscheme.RGBPixel;

/**
 * AbstractCompressedImageFormat serves as an abstract class representing compressed image formats
 * such as JPG and PNG. It provides a common `load` method for loading image files into a 2D array
 * of RGB pixels. Specific image formats that extend this class need to implement the `save` method
 * to define how images are saved.
 */

public abstract class AbstractCompressedImageFormat implements ImageFormat {

  /**
   * Loads an image from the specified file and converts it into a 2D array of Pixels.
   *
   * @param filename the path to the image file to be loaded.
   * @return a 2D array of Pixels representing the image, or {@code null} if an error occurs.
   *         Each pixel is stored as an {@code RGBPixel} with red, green, and blue components.
   */

  public Pixels[][] load(String filename) {
    try {
      File file = new File(filename);
      BufferedImage bufferedImage = ImageIO.read(file);
      int width = bufferedImage.getWidth();
      int height = bufferedImage.getHeight();

      Pixels[][] pixels = new RGBPixel[height][width];
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
   * Saves a 2D array of RGB pixel data to an image file. This method must be implemented by any
   * subclass to specify the details of saving pixel data to a particular compressed image format.
   *
   * @param filename The name or path of the file to save the image.
   * @param pixels   A 2D array of {@link Pixels} objects containing RGB values of the image to be
   *                 saved.
   */
  public abstract void save(String filename, Pixels[][] pixels);
}
