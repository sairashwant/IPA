package Model.ImageTransformation.ColorTransformation;

import Model.ImageTransformation.Transformation;
import Model.RGBPixel;
import java.util.HashMap;

/**
 *
 */
public abstract class AbstractColorTransformation implements Transformation {


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
    double[][] MATRIX = getMatrix();
    RGBPixel[][] transformedPixels = new RGBPixel[height][width];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        if (input[i][j] instanceof RGBPixel) {
          RGBPixel rgbPixel = (RGBPixel) input[i][j];
          int red = rgbPixel.getRed();
          int green = rgbPixel.getGreen();
          int blue = rgbPixel.getBlue();

          int newRed = (int) Math.min(255, Math.max(0,
              MATRIX[0][0] * red + MATRIX[0][1] * green + MATRIX[0][2] * blue));
          int newGreen = (int) Math.min(255, Math.max(0,
              MATRIX[1][0] * red + MATRIX[1][1] * green + MATRIX[1][2] * blue));
          int newBlue = (int) Math.min(255, Math.max(0,
              MATRIX[2][0] * red + MATRIX[2][1] * green + MATRIX[2][2] * blue));

          transformedPixels[i][j] = new RGBPixel(newRed, newGreen, newBlue);
        } else {
          throw new IllegalArgumentException("Expected an RGBPixel.");
        }
      }
    }

    return transformedPixels;
  }

  protected abstract double[][] getMatrix();
}
