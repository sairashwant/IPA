package Model.ImageTransformation.Filtering;

import Model.ImageTransformation.Transformation;
import Model.ColorScheme.RGBPixel;
import java.util.HashMap;

/**
 *
 */
public abstract class AbstractFiltering implements Transformation {

  /**
   * @param key
   * @param h1
   * @return
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
   * @return
   */
  protected abstract double[][] getFilter();
}
