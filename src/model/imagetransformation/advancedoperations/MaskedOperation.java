package model.imagetransformation.advancedoperations;

import model.colorscheme.Pixels;
import model.colorscheme.RGBPixel;
import model.imagetransformation.Transformation;

/**
 * The {@code MaskedOperation} class implements the {@link Transformation} interface and applies a
 * specified image transformation to a source image based on a mask. The mask determines which
 * pixels in the source image should be modified by the transformation operation. If the
 * corresponding mask pixel is black (0, 0, 0), the operation is applied; otherwise, the original
 * pixel value is retained.
 */
public class MaskedOperation implements Transformation {

  private Transformation operation;
  private Pixels[][] mask;

  /**
   * Constructs a new {@code MaskedOperation} with the specified transformation operation and mask.
   *
   * @param operation the {@link Transformation} to apply to the source image pixels
   * @param mask      a 2D array of {@link Pixels} representing the mask used to determine which
   *                  pixels to transform
   */
  public MaskedOperation(Transformation operation, Pixels[][] mask) {
    this.operation = operation;
    this.mask = mask;
  }

  /**
   * Applies the masked transformation operation to the provided source image pixels. The operation
   * is applied only to the pixels in the source image where the corresponding mask pixel is black
   * (0, 0, 0). Other pixels in the source image remain unchanged.
   *
   * @param sourcePixels a 2D array representing the source image pixels
   * @return a 2D array of pixels representing the resulting image after the masked operation is
   *        applied
   * @throws IllegalArgumentException if the dimensions of the mask do not match the dimensions of
   *                                  the source pixels
   */
  @Override
  public Pixels[][] apply(Pixels[][] sourcePixels) {
    int height = sourcePixels.length;
    int width = sourcePixels[0].length;
    Pixels[][] resultPixels = new RGBPixel[height][width];

    // Ensure the mask dimensions match the source pixels
    if (mask.length != height || mask[0].length != width) {
      throw new IllegalArgumentException("Mask dimensions must match source pixel dimensions.");
    }

    // Apply the transformation based on the mask
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        // Check if the mask pixel is black (assuming RGBPixel and black is (0, 0, 0))
        if (((RGBPixel) mask[y][x]).getRed() == 0 && ((RGBPixel) mask[y][x]).getGreen() == 0
            && ((RGBPixel) mask[y][x]).getBlue() == 0) {
          // Apply the transformation to this pixel
          resultPixels[y][x] = operation.apply(new Pixels[][]{{sourcePixels[y][x]}})[0][0];
        } else {
          // Keep the original pixel color if the mask is not black
          resultPixels[y][x] = sourcePixels[y][x];
        }
      }
    }
    return resultPixels;
  }
}
