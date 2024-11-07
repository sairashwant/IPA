package model.imagetransformation.basicoperation;

import model.colorscheme.Pixels;
import model.colorscheme.RGBPixel;
import java.util.HashMap;


/**
 * This class represents a basic image transformation operation that brightens the pixels of an
 * image by a specified factor. It extends the {@code AbstractBasicOperation} class and overrides
 * the {@code apply} method to implement the specific logic for brightening an image.
 *
 * <p>The operation adds a specified value (brighten factor) to each pixel's color components (red,
 * green, and blue), within the range of 0 to 255. The resulting image has brighter or darker pixels
 * depending on the sign of the brightening factor.</p>
 */
public class Brighten extends AbstractBasicOperation {

  private final int brightenFactor;


  /**
   * Constructs a Brighten operation with the specified brightening factor.
   *
   * @param brightenFactor The amount by which to brighten each pixel's color values. This value can
   *                       be positive (to increase brightness) or negative (to decrease
   *                       brightness). The value is added to each color component of the pixel.
   */
  public Brighten(int brightenFactor) {
    this.brightenFactor = brightenFactor;
  }


  /**
   * Applies the brighten operation to the specified image pixels. This method iterates over each
   * pixel of the image, retrieves the RGB values, adds the brightening factor to each component
   * (clamping the values between 0 and 255), and returns the modified pixel array.
   *
   * <p>If the pixel data is not of type {@code RGBPixel}, an {@code IllegalArgumentException} is
   * thrown.</p>
   *
   * @param pixels A 2D array of {@code Pixels} representing the image to be brightened.
   * @return A 2D array of {@code RGBPixel} objects representing the brightened image pixels.
   * @throws IllegalArgumentException if a pixel is not an instance of {@code RGBPixel}.
   */
  @Override
  public Pixels[][] apply(Pixels[][] pixels) {

    int height = pixels.length;
    int width = pixels[0].length;

    Pixels[][] brightenedPixels = new RGBPixel[height][width];

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
