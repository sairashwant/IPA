package model.imagetransformation;

import model.colorscheme.RGBPixel;
import java.util.HashMap;

/**
 * This class implements the levels adjustment transformation using quadratic curve fitting.
 * It allows adjustment of shadows, midtones, and highlights in an image.
 */
public class AdjustLevel {
  private final int b; // shadow point (black)
  private final int m; // midtone point
  private final int w; // highlight point (white)
  private double a, b2, c; // quadratic curve coefficients

  /**
   * Constructs an AdjustLevel object with specified shadow, midtone, and highlight points.
   *
   * @param black shadow point (0-255)
   * @param mid midtone point (0-255)
   * @param white highlight point (0-255)
   * @throws IllegalArgumentException if values are outside valid range or not in ascending order
   */
  public AdjustLevel(int black, int mid, int white) {
    if (black < 0 || black > 255 || mid < 0 || mid > 255 || white < 0 || white > 255) {
      throw new IllegalArgumentException("Values must be between 0 and 255");
    }
    if (!(black < mid && mid < white)) {
      throw new IllegalArgumentException("Values must be in ascending order: black < mid < white");
    }

    this.b = black;
    this.m = mid;
    this.w = white;
    calculateQuadraticCoefficients();
  }

  /**
   * Calculates the coefficients for the quadratic curve fitting.
   */
  private void calculateQuadraticCoefficients() {
    // Solve the system of equations for quadratic curve fitting
    // f(x) = ax² + bx + c
    // Using three points: (b,0), (m,128), (w,255)
    double[][] matrix = {
        {b*b, b, 1},
        {m*m, m, 1},
        {w*w, w, 1}
    };
    double[] values = {0, 128, 255};

    // Calculate coefficients using Cramer's rule
    double det = matrix[0][0] * (matrix[1][1] * matrix[2][2] - matrix[1][2] * matrix[2][1])
        - matrix[0][1] * (matrix[1][0] * matrix[2][2] - matrix[1][2] * matrix[2][0])
        + matrix[0][2] * (matrix[1][0] * matrix[2][1] - matrix[1][1] * matrix[2][0]);

    a = ((values[0] * (matrix[1][1] * matrix[2][2] - matrix[1][2] * matrix[2][1]))
        - (matrix[0][1] * (values[1] * matrix[2][2] - matrix[1][2] * values[2]))
        + (matrix[0][2] * (values[1] * matrix[2][1] - matrix[1][1] * values[2]))) / det;

    b2 = ((matrix[0][0] * (values[1] * matrix[2][2] - matrix[1][2] * values[2]))
        - (values[0] * (matrix[1][0] * matrix[2][2] - matrix[1][2] * matrix[2][0]))
        + (matrix[0][2] * (matrix[1][0] * values[2] - values[1] * matrix[2][0]))) / det;

    c = ((matrix[0][0] * (matrix[1][1] * values[2] - values[1] * matrix[2][1]))
        - (matrix[0][1] * (matrix[1][0] * values[2] - values[1] * matrix[2][0]))
        + (values[0] * (matrix[1][0] * matrix[2][1] - matrix[1][1] * matrix[2][0]))) / det;
  }

  /**
   * Applies the levels adjustment transformation to an image.
   *
   * @param key the key of the image to transform
   * @param images the map containing all images
   * @return the transformed pixel array
   */
  public RGBPixel[][] apply(String key, HashMap<String, RGBPixel[][]> images) {
    RGBPixel[][] pixels = images.get(key);
    if (pixels == null) {
      throw new IllegalArgumentException("No image found for key: " + key);
    }

    int height = pixels.length;
    int width = pixels[0].length;
    RGBPixel[][] result = new RGBPixel[height][width];

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        RGBPixel pixel = pixels[y][x];

        // Apply quadratic transformation to each channel
        int newRed = adjustValue(pixel.getRed());
        int newGreen = adjustValue(pixel.getGreen());
        int newBlue = adjustValue(pixel.getBlue());

        result[y][x] = new RGBPixel(newRed, newGreen, newBlue);
      }
    }

    return result;
  }

  /**
   * Adjusts a single color value using the quadratic curve.
   *
   * @param value the input color value
   * @return the adjusted color value
   */
  private int adjustValue(int value) {
    // Apply quadratic transformation
    double result = a * value * value + b2 * value + c;

    // Clamp result to 0-255 range
    return Math.min(255, Math.max(0, (int) Math.round(result)));
  }
}