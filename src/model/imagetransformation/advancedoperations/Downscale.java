package model.imagetransformation.advancedoperations;

import model.colorscheme.Pixels;
import model.colorscheme.RGBPixel;
import model.imagetransformation.Transformation;

/**
 * The {@code Downscale} class implements the {@link Transformation} interface and performs the
 * downscaling operation on an image. It resizes the given image (represented as a 2D array of
 * pixels) to a new width and height using bilinear interpolation. The class performs interpolation
 * based on the four nearest pixels to maintain smooth color transitions when downscaling.
 */
public class Downscale implements Transformation {

  private int newHeight;
  private int newWidth;

  /**
   * Constructor to initialize the downscale operation with the desired new width and height.
   *
   * @param newWidth  the desired width of the downscaled image
   * @param newHeight the desired height of the downscaled image
   * @throws NegativeArraySizeException if newWidth or newHeight is negative
   */
  public Downscale(int newWidth, int newHeight) {
    if (newWidth < 0 || newHeight < 0) {
      throw new NegativeArraySizeException("Width and height cannot be negative.");
    }
    if (newWidth == 0 || newHeight == 0) {
      throw new IllegalArgumentException("Width and height must be greater than zero.");
    }
    this.newHeight = newHeight;
    this.newWidth = newWidth;
  }

  /**
   * Applies the downscale transformation to the given image pixels. The method resizes the original
   * image to the specified width and height using bilinear interpolation.
   * <p>
   * Bilinear interpolation computes the pixel values by considering the four closest pixels in the
   * original image and weighting them based on their relative distance to the target pixel in the
   * downscaled image.
   *
   * @param originalPixels a 2D array representing the pixels of the original image
   * @return a 2D array of pixels representing the downscaled image
   * @throws IllegalArgumentException if the input image is empty (i.e., no pixels)
   */
  @Override
  public Pixels[][] apply(Pixels[][] originalPixels) {
    if (originalPixels == null || originalPixels.length == 0 || originalPixels[0].length == 0) {
      throw new IllegalArgumentException("Input image cannot be empty.");
    }

    int originalHeight = originalPixels.length;
    int originalWidth = originalPixels[0].length;

    Pixels[][] downsizedPixels = new RGBPixel[newHeight][newWidth];

    // Iterate over each pixel in the downscaled image
    for (int y = 0; y < newHeight; y++) {
      for (int x = 0; x < newWidth; x++) {
        // Map the pixel location from downsized image to original image
        float originalX = (float) x * originalWidth / newWidth;
        float originalY = (float) y * originalHeight / newHeight;

        // Get the integer pixel locations around the floating-point location
        int x1 = (int) Math.floor(originalX);
        int y1 = (int) Math.floor(originalY);
        int x2 = (int) Math.ceil(originalX);
        int y2 = (int) Math.ceil(originalY);

        // Ensure the coordinates are within bounds
        x1 = Math.min(x1, originalWidth - 1);
        x2 = Math.min(x2, originalWidth - 1);
        y1 = Math.min(y1, originalHeight - 1);
        y2 = Math.min(y2, originalHeight - 1);

        // Get the colors at the four corners
        RGBPixel p1 = (RGBPixel) originalPixels[y1][x1];
        RGBPixel p2 = (RGBPixel) originalPixels[y1][x2];
        RGBPixel p3 = (RGBPixel) originalPixels[y2][x1];
        RGBPixel p4 = (RGBPixel) originalPixels[y2][x2];

        // Interpolate colors based on the distances
        float xWeight = originalX - x1;
        float yWeight = originalY - y1;

        int red = (int) Math.round(
            (1 - xWeight) * (1 - yWeight) * p1.getRed() +
                xWeight * (1 - yWeight) * p2.getRed() +
                (1 - xWeight) * yWeight * p3.getRed() +
                xWeight * yWeight * p4.getRed()
        );

        int green = (int) Math.round(
            (1 - xWeight) * (1 - yWeight) * p1.getGreen() +
                xWeight * (1 - yWeight) * p2.getGreen() +
                (1 - xWeight) * yWeight * p3.getGreen() +
                xWeight * yWeight * p4.getGreen()
        );

        int blue = (int) Math.round(
            (1 - xWeight) * (1 - yWeight) * p1.getBlue() +
                xWeight * (1 - yWeight) * p2.getBlue() +
                (1 - xWeight) * yWeight * p3.getBlue() +
                xWeight * yWeight * p4.getBlue()
        );

        downsizedPixels[y][x] = new RGBPixel(red, green, blue);
      }
    }

    return downsizedPixels;
  }
}
