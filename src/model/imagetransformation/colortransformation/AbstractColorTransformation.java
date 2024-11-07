package model.imagetransformation.colortransformation;

import model.colorscheme.Pixels;
import model.colorscheme.RGBPixel;
import model.imagetransformation.Transformation;

/**
 * The {@code AbstractColorTransformation} class is an abstract class that provides a structure for
 * applying a color transformation to an image using a transformation matrix. The matrix is applied
 * to each pixel in the image, modifying its color values according to the specific transformation.
 * Subclasses of this class must implement the {@code getMatrix()} method to provide the appropriate
 * transformation matrix for the operation.
 *
 * <p>This class implements the {@link Transformation} interface, making it suitable for use in
 * image processing pipelines where color transformations need to be applied to images.</p>
 */
public abstract class AbstractColorTransformation implements Transformation {


  /**
   * Applies the color transformation to the input image using a transformation matrix. The matrix
   * is applied to each pixel's RGB values by performing matrix multiplication on the individual
   * color components. The resulting transformed pixels are clamped between 0 and 255 to ensure
   * valid RGB values.
   *
   * <p>This method iterates over each pixel of the input image, applies the transformation matrix
   * to the pixel's RGB values, and stores the resulting transformed pixel in a new 2D array.</p>
   *
   * @param input a 2D array of {@code Pixels}, representing the input image to be transformed
   * @return a 2D array of {@code RGBPixel} objects representing the transformed image with the
   *          applied color transformation
   * @throws IllegalArgumentException if any of the pixels in the input array are not instances of
   *                                  {@code RGBPixel}
   */

  @Override
  public Pixels[][] apply(Pixels[][] input) {
    int height = input.length;
    int width = input[0].length;
    double[][] matrix = getMatrix();
    Pixels[][] transformedPixels = new RGBPixel[height][width];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        if (input[i][j] instanceof RGBPixel) {
          RGBPixel rgbPixel = (RGBPixel) input[i][j];
          int red = rgbPixel.getRed();
          int green = rgbPixel.getGreen();
          int blue = rgbPixel.getBlue();

          int newRed = (int) Math.min(255, Math.max(0,
              matrix[0][0] * red + matrix[0][1] * green + matrix[0][2] * blue));
          int newGreen = (int) Math.min(255, Math.max(0,
              matrix[1][0] * red + matrix[1][1] * green + matrix[1][2] * blue));
          int newBlue = (int) Math.min(255, Math.max(0,
              matrix[2][0] * red + matrix[2][1] * green + matrix[2][2] * blue));

          transformedPixels[i][j] = new RGBPixel(newRed, newGreen, newBlue);
        } else {
          throw new IllegalArgumentException("Expected an RGBPixel.");
        }
      }
    }

    return transformedPixels;
  }

  /**
   * Abstract method to retrieve the transformation matrix used for the color transformation.
   * Subclasses must override this method to provide the specific transformation matrix.
   *
   * <p>The matrix is typically a 3x3 matrix, where each entry defines how the red, green, and blue
   * components of a pixel should be weighted during the transformation.</p>
   *
   * @return a 2D array representing the transformation matrix for the color operation
   */
  protected abstract double[][] getMatrix();
}
