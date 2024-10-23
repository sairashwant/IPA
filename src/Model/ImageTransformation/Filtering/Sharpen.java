package Model.ImageTransformation.Filtering;
/**
 * The Sharpen class is a specific implementation of AbstractFiltering that
 * applies a sharpening filter to an image. The sharpening filter enhances
 * the edges in the image, making the image appear clearer and more defined.
 * It is achieved by applying a high-pass filter, which emphasizes pixel intensity
 * differences between a pixel and its neighbors.
 */
public class Sharpen extends AbstractFiltering {

  /**
   * Provides the filter matrix used for sharpening the image. This matrix
   * enhances edges by applying a high-pass filter, where the center pixel
   * is given a higher weight, and surrounding pixels are subtracted to increase contrast.
   *
   * @return a 5x5 sharpening filter matrix
   */
  @Override
  protected double[][] getFilter() {
    return new double[][]{
        {-1.0/8, -1.0/8, -1.0/8 , -1.0/8 , -1.0/8},
        {-1.0/8, 1.0/4, 1.0/4 , 1.0/4 , -1.0/8},
        {-1.0/8, 1.0/4, 1.0 , 1.0/4 , -1.0/8},
        {-1.0/8, 1.0/4, 1.0/4 , 1.0/4 , -1.0/8},
        {-1.0/8, -1.0/8, -1.0/8 , -1.0/8 , -1.0/8}
    };
  }


}
