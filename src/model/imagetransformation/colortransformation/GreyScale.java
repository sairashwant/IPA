package model.imagetransformation.colortransformation;

/**
 * The GreyScale class is a specific implementation of AbstractColorTransformation that applies a
 * grayscale transformation to an image. This transformation uses the luminance formula, which is a
 * weighted sum of the red, green, and blue components of a pixel to calculate the grayscale
 * intensity.
 */

public class GreyScale extends AbstractColorTransformation {

  /**
   * Provides the transformation matrix used to convert an image to grayscale. The matrix is based
   * on the luminance formula, where the red, green, and blue components contribute differently to
   * the intensity, using the standard coefficients 0.2126, 0.7152, and 0.0722.
   *
   * @return a 3x3 transformation matrix used to calculate the grayscale values of the image
   */
  @Override
  protected double[][] getMatrix() {
    return new double[][]{
        {0.2126, 0.7152, 0.0722},
        {0.2126, 0.7152, 0.0722},
        {0.2126, 0.7152, 0.0722}
    };
  }

  ;
}





