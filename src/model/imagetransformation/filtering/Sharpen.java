package model.imagetransformation.filtering;

/**
 * The {@code Sharpen} class is a specific implementation of {@code AbstractFiltering} that applies
 * a sharpening filter to an image. The sharpening filter enhances the edges and fine details in the
 * image, making it appear clearer and more defined. It is achieved by applying a high-pass filter,
 * which emphasizes the differences in pixel intensities between a pixel and its neighboring
 * pixels.
 *
 * <p>Sharpening is commonly used to improve the clarity of an image, especially for making details
 * more prominent or for enhancing the outlines of objects within the image. The filter matrix
 * increases the contrast between the center pixel and its neighbors, enhancing the edges.</p>
 *
 * <p>The {@code Sharpen} class extends {@code AbstractFiltering} and overrides the
 * {@code getFilter()} method to provide the specific 5x5 filter matrix used for the sharpening
 * operation.</p>
 */
public class Sharpen extends AbstractFiltering {

  /**
   * Provides the 5x5 filter matrix used for sharpening the image. The matrix emphasizes edges by
   * applying a high-pass filter. The center pixel is given a higher weight, and surrounding pixels
   * are subtracted to increase contrast, which sharpens the image.
   *
   * <p>The filter works by enhancing the intensity difference between the pixel and its neighbors.
   * This emphasizes the edges and fine details, making the image appear crisper and more
   * defined.</p>
   *
   * @return a 5x5 filter matrix representing the sharpening filter, which is used to calculate the
   * sharpened values of the image
   */
  @Override
  protected double[][] getFilter() {
    return new double[][]{
        {-1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8},
        {-1.0 / 8, 1.0 / 4, 1.0 / 4, 1.0 / 4, -1.0 / 8},
        {-1.0 / 8, 1.0 / 4, 1.0, 1.0 / 4, -1.0 / 8},
        {-1.0 / 8, 1.0 / 4, 1.0 / 4, 1.0 / 4, -1.0 / 8},
        {-1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8}
    };
  }


}
