package model.imagetransformation.basicoperation;

import model.colorscheme.Pixels;
import model.imagetransformation.Transformation;
import model.colorscheme.RGBPixel;

/**
 * The {@code AbstractBasicOperation} class provides a base implementation for basic image
 * transformation operations. It implements the {@code Transformation} interface and defines
 * common logic for applying pixel-wise operations on an image. This class is abstract and
 * intended to be extended by specific transformation operations that modify pixel values.
 *
 * <p>The {@code apply} method processes each pixel of the image based on its RGB components,
 * transforming them according to the logic defined in subclasses.</p>
 */
public abstract class AbstractBasicOperation implements Transformation {

  /**
   * Applies a transformation to the specified image, identified by the given key, and returns
   * the transformed image. The transformation is applied to each pixel, and the resulting pixel's
   * color is calculated based on the original pixel values. The transformed image is returned
   * as a new 2D array of {@code RGBPixel} objects.
   *
   * <p>This method processes the image pixel-by-pixel and applies the transformation logic
   * defined in the {@code properties} method. Subclasses should override the {@code properties}
   * method to define the specific behavior of the transformation.</p>
   *
   * @param pixels a 2D array of {@code Pixels} representing the image to be transformed
   * @return a 2D array of {@code RGBPixel} objects representing the transformed image
   * @throws IllegalArgumentException if a pixel is not an instance of {@code RGBPixel}
   */
  @Override
  public Pixels[][] apply(Pixels[][] pixels) {
    int height = pixels.length;
    int width = pixels[0].length;

    Pixels[][] abstractpixel = new RGBPixel[height][width];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        Pixels pixel = pixels[i][j];
        if (pixel instanceof RGBPixel) {
          RGBPixel rgbPixel = (RGBPixel) pixel;

          int red = rgbPixel.getRed();
          int green = rgbPixel.getGreen();
          int blue = rgbPixel.getBlue();

          int val = properties(red, green, blue);
          abstractpixel[i][j] = new RGBPixel(val, val, val);
        } else {
          throw new IllegalArgumentException("Expected an RGBPixel.");
        }
      }
    }

    return abstractpixel;
  }

  /**
   * Computes a property of the color based on the red, green, and blue values. This method is
   * intended to be overridden by subclasses to define specific transformations, such as grayscale
   * conversion, brightness adjustment, etc.
   *
   * <p>The base implementation of this method returns 0, which means it doesn't perform any actual
   * transformation. Subclasses should override this method to implement the desired transformation.</p>
   *
   * @param r the red component of the pixel's color (0-255)
   * @param g the green component of the pixel's color (0-255)
   * @param b the blue component of the pixel's color (0-255)
   * @return an integer value representing the computed property for the given RGB values
   */
  public int properties(int r, int g, int b) {

    return 0;
  }

}
