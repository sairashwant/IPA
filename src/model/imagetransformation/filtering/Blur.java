package model.imagetransformation.filtering;

/**
 * The Blur class is a specific implementation of AbstractFiltering that applies a simple blur
 * effect to an image. The blur effect is achieved using a 3x3 Gaussian blur filter, which smooths
 * out the image by averaging the neighboring pixel values.
 */
public class Blur extends AbstractFiltering {

  /**
   * Provides the Gaussian blur filter matrix used for blurring the image. The matrix is a 3x3
   * kernel that applies weights to a pixel and its neighboring pixels to compute the blurred value.
   * The filter weights are designed to give more importance to the center pixel while averaging the
   * surrounding pixels.
   *
   * @return a 3x3 Gaussian blur filter matrix
   */
  protected double[][] getFilter() {
    return new double[][]{
        {1.0 / 16, 1.0 / 8, 1.0 / 16},
        {1.0 / 8, 1.0 / 4, 1.0 / 8},
        {1.0 / 16, 1.0 / 8, 1.0 / 16}
    };
  }

  ;
}

