package controller.imageformat;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import model.colorscheme.Pixels;
import model.colorscheme.RGBPixel;

/**
 * The JPGImage class provides functionality to save images in JPG format. It extends
 * {@link AbstractCompressedImageFormat} and implements the `save` method to write RGB pixel data
 * into a JPG file.
 */
public class JPGImage extends AbstractCompressedImageFormat {

  /**
   * Saves the given RGB pixel data to a JPG image file. The method converts each pixel's red,
   * green, and blue values into an RGB integer, which is then set on a BufferedImage. The image is
   * then written to a file in JPG format.
   *
   * @param filename The name or path of the file where the image will be saved.
   * @param pixels   A 2D array of {@link Pixels} representing the RGB values of each pixel to be
   *                 saved in the image file.
   */
  @Override
  public void save(String filename, Pixels[][] pixels) {
    {
      int width = pixels[0].length;
      int height = pixels.length;

      BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
      for (int y = 0; y < height; y++) {
        for (int x = 0; x < width; x++) {
          RGBPixel pixel = (RGBPixel) pixels[y][x];  // Cast to RGBPixel
          int rgb = (pixel.getRed() << 16) | (pixel.getGreen() << 8) | pixel.getBlue();
          image.setRGB(x, y, rgb);
        }
      }
      try {
        File outputFile = new File(filename);
        ImageIO.write(image, "jpg", outputFile);
        System.out.println("Image saved as: " + outputFile.getPath());
      } catch (IOException e) {
        System.out.println("Error saving the image: " + e.getMessage());
      }
    }
  }
}
