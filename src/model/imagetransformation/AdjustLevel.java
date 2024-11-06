package model.imagetransformation;

import model.colorscheme.Pixels;
import model.colorscheme.RGBPixel;

/**
 * This class implements the levels adjustment transformation using quadratic curve fitting.
 * It allows adjustment of shadows, midtones, and highlights in an image.
 */
public class AdjustLevel implements Transformation{

  private final int blackPoint; // shadow point (black)
  private final int midPoint;   // midtone point
  private final int whitePoint; // highlight point
  private double a, b2, c;      // quadratic curve coefficients

  /**
   * Constructs an AdjustLevel object with specified shadow, midtone, and highlight points.
   *
   * @param black Shadow point (0-255)
   * @param mid Midtone point (0-255)
   * @param white Highlight point (0-255)
   * @throws IllegalArgumentException if values are outside the valid range or not in ascending order
   */
  public AdjustLevel(int black, int mid, int white) {
    if (black < 0 || black > 255 || mid < 0 || mid > 255 || white < 0 || white > 255) {
      throw new IllegalArgumentException("Values must be between 0 and 255.");
    }
    if (!(black < mid && mid < white)) {
      throw new IllegalArgumentException("Values must be in ascending order: black < mid < white.");
    }

    this.blackPoint = black;
    this.midPoint = mid;
    this.whitePoint = white;
    calculateQuadraticCoefficients();
  }

  /**
   * Calculates the coefficients for the quadratic curve fitting.
   */
  private void calculateQuadraticCoefficients() {
    // Solve the system of equations for quadratic curve fitting
    // f(x) = ax² + bx + c
    // Using three points: (blackPoint,0), (midPoint,128), (whitePoint,255)
    double[][] matrix = {
        {blackPoint * blackPoint, blackPoint, 1},
        {midPoint * midPoint, midPoint, 1},
        {whitePoint * whitePoint, whitePoint, 1}
    };
    double[] values = {0, 128, 255};

    // Calculate coefficients using Cramer's rule
    double det = matrix[0][0] * (matrix[1][1] * matrix[2][2] - matrix[1][2] * matrix[2][1])
        - matrix[0][1] * (matrix[1][0] * matrix[2][2] - matrix[1][2] * matrix[2][0])
        + matrix[0][2] * (matrix[1][0] * matrix[2][1] - matrix[1][1] * matrix[2][0]);

    a = (values[0] * (matrix[1][1] * matrix[2][2] - matrix[1][2] * matrix[2][1])
        - matrix[0][1] * (values[1] * matrix[2][2] - matrix[1][2] * values[2])
        + matrix[0][2] * (values[1] * matrix[2][1] - matrix[1][1] * values[2])) / det;

    b2 = (matrix[0][0] * (values[1] * matrix[2][2] - matrix[1][2] * values[2])
        - values[0] * (matrix[1][0] * matrix[2][2] - matrix[1][2] * matrix[2][0])
        + matrix[0][2] * (matrix[1][0] * values[2] - values[1] * matrix[2][0])) / det;

    c = (matrix[0][0] * (matrix[1][1] * values[2] - values[1] * matrix[2][1])
        - matrix[0][1] * (matrix[1][0] * values[2] - values[1] * matrix[2][0])
        + values[0] * (matrix[1][0] * matrix[2][1] - matrix[1][1] * matrix[2][0])) / det;
  }

  /**
   * Applies the levels adjustment transformation to the specified image pixels.
   *
   * @param pixels The 2D array of Pixels objects representing the image pixels.
   * @return A 2D array of RGBPixel objects representing the adjusted image pixels.
   * @throws IllegalArgumentException if the pixel data is null or if any pixel is not an RGBPixel.
   */
  public Pixels[][] apply(Pixels[][] pixels) {
    if (pixels == null) {
      throw new IllegalArgumentException("Input pixel array cannot be null.");
    }

    int height = pixels.length;
    int width = pixels[0].length;
    Pixels[][] adjustedPixels = new RGBPixel[height][width];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        if (!(pixels[i][j] instanceof RGBPixel)) {
          throw new IllegalArgumentException("Expected an RGBPixel.");
        }

        RGBPixel rgbPixel = (RGBPixel) pixels[i][j];

        // Apply quadratic transformation to each channel
        int newRed = adjustValue(rgbPixel.getRed());
        int newGreen = adjustValue(rgbPixel.getGreen());
        int newBlue = adjustValue(rgbPixel.getBlue());

        adjustedPixels[i][j] = new RGBPixel(newRed, newGreen, newBlue);
      }
    }

    return adjustedPixels;
  }

  /**
   * Adjusts a single color value using the quadratic curve.
   *
   * @param value the input color value
   * @return the adjusted color value, clamped to the range [0, 255]
   */
  private int adjustValue(int value) {
    // Apply quadratic transformation
    double result = a * value * value + b2 * value + c;

    // Clamp result to 0-255 range
    return Math.min(255, Math.max(0, (int) Math.round(result)));
  }
}
