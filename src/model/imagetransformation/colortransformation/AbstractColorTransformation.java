package model.imagetransformation.colortransformation;

import model.colorscheme.Pixels;
import model.imagetransformation.Transformation;
import model.colorscheme.RGBPixel;
import java.util.HashMap;

/**
 * AbstractColorTransformation is an abstract class that defines the structure for applying a color
 * transformation to an image. The transformation is carried out using a transformation matrix that
 * is applied to each pixel in the image. Subclasses must implement the `getMatrix()` method to
 * provide the specific transformation matrix.
 */
public abstract class AbstractColorTransformation implements Transformation {


  /**
   * Applies a color transformation to the input image using a predefined matrix. The transformation
   * is applied by multiplying each pixel's RGB values by the matrix.
   *
   * @param key the key to identify the input image in the HashMap
   * @param h1  a HashMap containing the input images, where the key refers to the image to
   *            transform
   * @return a 2D array of RGBPixel objects, representing the transformed image
   * @throws IllegalArgumentException if the image pixels are not instances of RGBPixel
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
   * Abstract method to get the transformation matrix. Subclasses must override this method to
   * provide the specific matrix for the color transformation.
   *
   * @return a 2D array representing the transformation matrix
   */
  protected abstract double[][] getMatrix();
}
