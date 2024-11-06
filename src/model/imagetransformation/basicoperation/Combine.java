package model.imagetransformation.basicoperation;

import model.colorscheme.Pixels;
import model.colorscheme.RGBPixel;

/**
 * The {@code Combine} class extends {@code AbstractBasicOperation} to provide a specific image
 * transformation that combines three separate color channels (red, green, and blue) into a single
 * RGB image. This class assumes that the input images represent individual color components and
 * merges them to produce a full-color image.
 */
public class Combine extends AbstractBasicOperation {

  /**
   * Combines three separate color channel images (red, green, and blue) into a single RGB image.
   * Each input image represents a color component and must have the same dimensions.
   *
   * @param redPixels   a 2D array of {@code RGBPixel} representing the red channel of the image
   * @param greenPixels a 2D array of {@code RGBPixel} representing the green channel of the image
   * @param bluePixels  a 2D array of {@code RGBPixel} representing the blue channel of the image
   * @return a 2D array of {@code RGBPixel} representing the combined RGB image
   * @throws IllegalArgumentException if the input images do not have the same dimensions or if any
   *                                  of the pixels are not instances of {@code RGBPixel}
   */
  public Pixels[][] apply(RGBPixel[][] redPixels, RGBPixel[][] greenPixels,
      RGBPixel[][] bluePixels) {

    int height = redPixels.length;
    int width = redPixels[0].length;

    if (greenPixels.length != height || bluePixels.length != height ||
        greenPixels[0].length != width || bluePixels[0].length != width) {
      throw new IllegalArgumentException("All input images must have the same dimensions.");
    }

    RGBPixel[][] combinedImage = new RGBPixel[height][width];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        if (!(redPixels[i][j] instanceof RGBPixel) ||
            !(greenPixels[i][j] instanceof RGBPixel) ||
            !(bluePixels[i][j] instanceof RGBPixel)) {
          throw new IllegalArgumentException("Input images must contain RGBPixels.");
        }

        int red = (redPixels[i][j]).getRed();
        int green = (greenPixels[i][j]).getGreen();
        int blue = (bluePixels[i][j]).getBlue();

        combinedImage[i][j] = new RGBPixel(red, green, blue);
      }
    }

    return combinedImage;
  }
}