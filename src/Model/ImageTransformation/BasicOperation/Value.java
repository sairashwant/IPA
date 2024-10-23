package Model.ImageTransformation.BasicOperation;

/**
 * The Value class is responsible for calculating the value component of a pixel in an image. The
 * value is defined as the maximum value among the red, green, and blue color components. This
 * operation is typically used in converting an RGB image to its value-based grayscale equivalent.
 */
public class Value extends AbstractBasicOperation {

  /**
   * Computes the value component of a pixel by returning the maximum of its red, green, and blue
   * components.
   *
   * @param r the red component of the pixel (0-255)
   * @param g the green component of the pixel (0-255)
   * @param b the blue component of the pixel (0-255)
   * @return the maximum value among the red, green, and blue components
   */
  @Override
  public int properties(int r, int g, int b) {
    int maxValue = Math.max(Math.max(r, g), b);
    return maxValue;
  }

}
