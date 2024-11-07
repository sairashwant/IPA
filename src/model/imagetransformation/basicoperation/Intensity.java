package model.imagetransformation.basicoperation;

/**
 * The {@code Intensity} class extends {@code AbstractBasicOperation} to calculate the intensity
 * component of an image. The intensity is computed as the average of the red, green, and blue color
 * values of each pixel, resulting in a grayscale representation of the image.
 *
 * <p>This transformation applies a grayscale effect to the image by converting each pixel into a
 * single intensity value. The resulting grayscale value represents the average brightness of the
 * pixel, where a higher intensity corresponds to a brighter pixel.</p>
 */
public class Intensity extends AbstractBasicOperation {

  /**
   * Calculates the intensity value of a pixel based on its red, green, and blue color components.
   * The intensity is computed as the average of the red, green, and blue values.
   *
   * <p>This method transforms each pixel from its original color representation to a grayscale
   * intensity value. The intensity value is calculated by averaging the red, green, and blue
   * components of the pixel.</p>
   *
   * @param r the red component of the pixel (0-255)
   * @param g the green component of the pixel (0-255)
   * @param b the blue component of the pixel (0-255)
   * @return the calculated intensity value, which is the average of the red, green, and blue
   *         components, resulting in a single grayscale value (0-255)
   */

  @Override
  public int properties(int r, int g, int b) {

    int value = (r + g + b) / 3;

    return value;
  }

}
