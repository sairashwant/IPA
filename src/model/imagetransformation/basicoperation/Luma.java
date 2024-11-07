package model.imagetransformation.basicoperation;

/**
 * This class represents an operation that calculates the Luma (brightness) of a pixel based on its
 * RGB color values. It extends the {@code AbstractBasicOperation} class.
 *
 * <p>Luma is a weighted calculation of the red, green, and blue components of a pixel. It is used to
 * determine the perceived brightness of the pixel, with different weights for each color component.
 * This transformation is typically used for image processing tasks that require brightness adjustments
 * or grayscale conversions.</p>
 */
public class Luma extends AbstractBasicOperation {

  /**
   * Calculates the Luma of a pixel given its red, green, and blue color values. The Luma is
   * computed using the following formula:
   * <pre>
   * Luma = 0.2126 * R + 0.7152 * G + 0.0722 * B
   * </pre>
   *
   * <p>The formula applies different weights to each color component, with green contributing the most
   * to the brightness, followed by red and blue. This calculation reflects the human eye's sensitivity
   * to these colors, making green the most important component for perceived brightness.</p>
   *
   * @param r The red color value of the pixel (0-255).
   * @param g The green color value of the pixel (0-255).
   * @param b The blue color value of the pixel (0-255).
   * @return The calculated Luma value, which is the brightness of the pixel. The value is rounded to
   *         the nearest integer, resulting in an integer between 0 and 255.
   */
  @Override
  public int properties(int r, int g, int b) {
    int luma = (int) Math.round(0.2126 * r + 0.7152 * g + 0.0722 * b);
    return luma;
  }

}
