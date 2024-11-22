package model.imagetransformation.basicoperation;

/**
 * The {@code Value} class is responsible for calculating the value component of a pixel in an
 * image. The value is defined as the maximum value among the red, green, and blue color components.
 * This operation is typically used in converting an RGB image to its value-based grayscale
 * equivalent.
 *
 * <p>The value component is a common approach in color image processing, especially when
 * transforming an image into a grayscale representation based on the intensity of the brightest
 * color channel in each pixel.</p>
 *
 * <p>This class extends {@code AbstractBasicOperation} and provides a transformation that
 * calculates the maximum of the RGB components of a pixel.</p>
 */
public class Value extends AbstractBasicOperation {

  /**
   * Computes the value component of a pixel by returning the maximum of its red, green, and blue
   * components.
   *
   * <p>The value is commonly used in operations like converting an image to grayscale, where
   * the pixel's brightness is based on the brightest of the three RGB channels.</p>
   *
   * @param r the red component of the pixel (0-255)
   * @param g the green component of the pixel (0-255)
   * @param b the blue component of the pixel (0-255)
   * @return the maximum value among the red, green, and blue components, representing the "value"
   *        or brightness of the pixel
   */

  @Override
  public int properties(int r, int g, int b) {
    int maxValue = Math.max(Math.max(r, g), b);
    return maxValue;
  }

}
