package Model.ImageTransformation.BasicOperation;

import Model.ColorScheme.RGBPixel;
import java.util.HashMap;


/**
 * This class represents a basic image transformation operation that brightens the pixels of an
 * image by a specified factor. It extends the AbstractBasicOperation class.
 */
public class Brighten extends AbstractBasicOperation {

  private final int brightenFactor;


  /**
   * Constructs a Brighten operation with the specified brighten factor.
   *
   * @param brightenFactor The amount by which to brighten each pixel's color values. This value can
   *                       be positive (to increase brightness) or negative (to decrease
   *                       brightness).
   */
  public Brighten(int brightenFactor) {
    this.brightenFactor = brightenFactor;
  }

  /**
   * Applies the brighten operation to the specified image pixels. It retrieves the pixel data using
   * the provided key from the HashMap, brightens each pixel by the brighten factor, and returns the
   * modified pixel array.
   *
   * @param key The key used to retrieve the image pixel data from the HashMap.
   * @param h1  A HashMap containing image pixel data, where the key is a String and the value is a
   *            2D array of RGBPixel objects.
   * @return A 2D array of RGBPixel objects representing the brightened image pixels.
   * @throws IllegalArgumentException if the pixel data retrieved is not of type RGBPixel.
   */
  @Override
  public RGBPixel[][] apply(String key, HashMap<String, RGBPixel[][]> h1) {

    RGBPixel[][] pixels = h1.get(key);
    int height = pixels.length;
    int width = pixels[0].length;

    RGBPixel[][] brightenedPixels = new RGBPixel[height][width];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        if (!(pixels[i][j] instanceof RGBPixel)) {
          throw new IllegalArgumentException("Expected an RGBPixel.");
        }

        RGBPixel rgbPixel = (RGBPixel) pixels[i][j];

        int newRed = Math.min(255, Math.max(0, rgbPixel.getRed() + brightenFactor));
        int newGreen = Math.min(255, Math.max(0, rgbPixel.getGreen() + brightenFactor));
        int newBlue = Math.min(255, Math.max(0, rgbPixel.getBlue() + brightenFactor));

        brightenedPixels[i][j] = new RGBPixel(newRed, newGreen, newBlue);
      }
    }

    return brightenedPixels;
  }

}
