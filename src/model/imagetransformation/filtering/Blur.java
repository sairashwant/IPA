package model.imagetransformation.filtering;

/**
 * The {@code Blur} class is a specific implementation of {@code AbstractFiltering} that applies a
 * simple blur effect to an image. The blur effect is achieved by using a 3x3 Gaussian blur filter
 * matrix, which smooths the image by averaging the values of each pixel and its surrounding
 * neighbors. The filter gives more importance to the center pixel and less to the surrounding
 * pixels.
 *
 * <p>The blur effect helps in reducing image noise, softening the image, and creating a smooth
 * transition between pixel regions. It is commonly used in image processing to reduce fine details
 * or to create artistic effects.</p>
 *
 * <p>The {@code Blur} class extends the {@code AbstractFiltering} class and overrides the
 * {@code getFilter()} method to provide the specific 3x3 Gaussian blur kernel used in the blurring
 * operation.</p>
 */
public class Blur extends AbstractFiltering {

  /**
   * Provides the 3x3 Gaussian blur filter matrix used for blurring the image. The matrix applies
   * weights to the center pixel and its neighboring pixels to compute the blurred value. The filter
   * matrix ensures that the center pixel has the highest weight, with the surrounding pixels
   * contributing less to the final blurred pixel value.
   *
   * <p>The filter weights used in this matrix are designed to create a Gaussian distribution,
   * where the center pixel has a greater influence on the result than its neighbors.</p>
   *
   * @return a 3x3 filter matrix representing the Gaussian blur kernel, which is used to calculate
   * the blurred values of the image
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

