package Model.ImageTransformation.Filtering;

import Model.ImageTransformation.Transformation;
import Model.ColorScheme.RGBPixel;
import java.util.HashMap;

/**
 * AbstractFiltering is an abstract class that provides a framework for applying
 * a filtering operation to an image. The filtering operation involves using a filter
 * matrix (kernel) to compute new pixel values based on a pixel's neighborhood.
 * Subclasses must implement the `getFilter()` method to provide the specific filter matrix.
 */
public abstract class AbstractFiltering implements Transformation {

  /**
   * Applies a filtering operation to the input image using a filter matrix (kernel).
   * The filter is applied to each pixel by considering the neighboring pixels and
   * applying the filter to calculate the new color values.
   *
   * @param key the key to identify the input image in the HashMap
   * @param h1  a HashMap containing the input images, where the key refers to the image to filter
   * @return a 2D array of RGBPixel objects, representing the filtered image
   * @throws IllegalArgumentException if the input image is invalid or contains non-RGBPixel objects
   */
  @Override
  public RGBPixel[][] apply(String key, HashMap<String, RGBPixel[][]> h1) {

    RGBPixel[][] input = h1.get(key);
    int height = input.length;
    int width = input[0].length;

    if (height == 0 || width == 0) {
      System.out.println("Invalid pixel data.");
      return null;
    }

    RGBPixel[][] blurredPixels = new RGBPixel[height][width];

    double[][] filter = getFilter();

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        double redSum = 0, greenSum = 0, blueSum = 0;
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
   * Abstract method to get the filter matrix (kernel) for the filtering operation.
   * Subclasses must override this method to provide the specific filter matrix.
   *
   * @return a 3x3 filter matrix (kernel) used to apply the filtering operation
   */
  protected abstract double[][] getFilter();
}
