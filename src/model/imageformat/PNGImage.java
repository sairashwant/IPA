package model.imageformat;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import model.colorscheme.RGBPixel;

/**
 * This class handles PNG image format.
 */
public class PNGImage extends AbstractCompressedImageFormat {

  /**
   * This method handles save for the JPG Image format. We take the red, green and blue values from
   * the pixels. We do bit-manipulation to get RGB values. We then store it using setRGB in buffered
   * image class. Then the image is written as a file. The image is saved in png format.
   *
   * @param filename Name of the file to save.
   * @param pixels   Pixels of the file to save.
   */
  @Override
  public void save(String filename, RGBPixel[][] pixels) {
    {
      int width = pixels[0].length;
      int height = pixels.length;

      BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
      for (int y = 0; y < height; y++) {
        for (int x = 0; x < width; x++) {
          RGBPixel pixel = pixels[y][x];
          int rgb = (pixel.getRed() << 16) | (pixel.getGreen() << 8) | pixel.getBlue();
          image.setRGB(x, y, rgb);
        }
      }
      try {
        File outputFile = new File(filename);
        ImageIO.write(image, "png", outputFile);
        System.out.println("Image saved as: " + outputFile.getPath());
      } catch (IOException e) {
        System.out.println("Error saving the image: " + e.getMessage());
      }
    }
  }
}
