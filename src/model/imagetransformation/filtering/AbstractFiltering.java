package model.imagetransformation.filtering;

import model.colorscheme.Pixels;
import model.colorscheme.RGBPixel;
import model.imagetransformation.Transformation;

/**
 * The {@code AbstractFiltering} class provides an abstract framework for applying a filtering
 * operation to an image. The filter is applied to each pixel based on its neighboring pixels using
 * a filter matrix (kernel). The class defines the general structure for filtering, leaving the
 * specification of the filter matrix to be provided by subclasses.
 *
 * <p>This class applies a filter by considering the neighborhood of each pixel and calculating
 * new color values using the corresponding filter matrix. The filter matrix (kernel) is used to
 * calculate the new values for each pixel's red, green, and blue components.</p>
 *
 * <p>Subclasses are required to implement the {@code getFilter()} method to specify the actual
 * filter matrix used for the filtering operation.</p>
 *
 * <p>Typical use cases for this class involve blurring, sharpening, edge detection, and other
 * image filtering techniques.</p>
 */
public abstract class AbstractFiltering implements Transformation {

  /**
   * Applies a filtering operation to the input image using a filter matrix (kernel). The filter is
   * applied by considering each pixel's neighbors and calculating the new color values based on the
   * filter matrix.
   *
   * @param input a 2D array representing the input image, where each element is a {@code Pixels}
   *              object (typically {@code RGBPixel}).
   * @return a 2D array of {@code RGBPixel} objects, representing the filtered image
   * @throws IllegalArgumentException if the input image is invalid or contains non-{@code RGBPixel}
   *                                  objects
   */
  @Override
  public Pixels[][] apply(Pixels[][] input) {

    int height = input.length;
    int width = input[0].length;

    if (height == 0 || width == 0) {
      System.out.println("Invalid pixel data.");
      return null;
    }

    Pixels[][] blurredPixels = new RGBPixel[height][width];

    double[][] filter = getFilter();

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        double redSum = 0;
        double greenSum = 0;
        double blueSum = 0;
        for (int i = -1; i <= 1; i++) {
          for (int j = -1; j <= 1; j++) {
            int neighborY = y + i;
            int neighborX = x + j;

            if (neighborY >= 0 && neighborY < height && neighborX >= 0 && neighborX < width) {
              RGBPixel neighborPixel = (RGBPixel) input[neighborY][neighborX];

              double filterValue = filter[i + 1][j + 1];

              redSum += neighborPixel.getRed() * filterValue;
              greenSum += neighborPixel.getGreen() * filterValue;
              blueSum += neighborPixel.getBlue() * filterValue;
            }
          }
        }

        int red = (int) Math.round(redSum);
        int green = (int) Math.round(greenSum);
        int blue = (int) Math.round(blueSum);

        red = Math.max(0, Math.min(255, red));
        green = Math.max(0, Math.min(255, green));
        blue = Math.max(0, Math.min(255, blue));

        blurredPixels[y][x] = new RGBPixel(red, green, blue);
      }
    }

    return blurredPixels;
  }

  /**
   * Abstract method to obtain the filter matrix (kernel) for the filtering operation. Subclasses
   * must implement this method to define the specific filter matrix that will be applied to the
   * image.
   *
   * <p>The filter matrix is typically a 3x3 matrix that defines the weights of the neighboring
   * pixels in the filtering operation.</p>
   *
   * @return a 3x3 filter matrix (kernel) that will be applied to the image during the filtering
   * operation
   */
  protected abstract double[][] getFilter();
}
