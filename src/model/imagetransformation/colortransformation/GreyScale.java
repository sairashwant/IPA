package model.imagetransformation.colortransformation;

/**
 * The {@code GreyScale} class is a specific implementation of the
 * {@code AbstractColorTransformation} class that applies a grayscale transformation to an image.
 * This transformation uses the luminance formula, which calculates the grayscale intensity of a
 * pixel based on a weighted sum of its red, green, and blue components.
 *
 * <p>The transformation is carried out by applying a 3x3 matrix to each pixel in the image. This
 * matrix uses the standard coefficients for luminance calculation: 0.2126 for red, 0.7152 for
 * green, and 0.0722 for blue. These values reflect the perceived brightness of each color component
 * to the human eye.</p>
 *
 * <p>After the transformation, each pixel in the image will have the same value for the red,
 * green, and blue components, producing a grayscale image.</p>
 */

public class GreyScale extends AbstractColorTransformation {

  /**
   * Provides the transformation matrix used to convert an image to grayscale. The matrix is based
   * on the luminance formula, where the red, green, and blue components contribute differently to
   * the intensity. The standard coefficients used for the grayscale conversion are 0.2126 for red,
   * 0.7152 for green, and 0.0722 for blue.
   *
   * <p>The resulting 3x3 matrix ensures that the weighted sum of the RGB components is applied to
   * each pixel, resulting in grayscale values.</p>
   *
   * @return a 3x3 transformation matrix that is used to calculate the grayscale values of the
   *        image. Each row in the matrix is identical and represents the luminance coefficients for RGB to
   *        grayscale conversion.
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





