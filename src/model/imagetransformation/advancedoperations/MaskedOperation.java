package model.imagetransformation.advancedoperations;

import model.Image;
import model.colorscheme.Pixels;
import model.colorscheme.RGBPixel;
import model.imagetransformation.Transformation;
import model.imagetransformation.basicoperation.Split;

public class MaskedOperation implements Transformation {
  private Transformation operation;
  private Pixels[][] mask;
  private Split split;

  public MaskedOperation(Transformation operation, Pixels[][] mask) {
    this.operation = operation;
    this.mask = mask;
  }

  @Override
  public Pixels[][] apply(Pixels[][] sourcePixels) {
    int height = sourcePixels.length;
    int width = sourcePixels[0].length;
    Pixels[][] resultPixels = new RGBPixel[height][width];

    // Ensure the mask dimensions match the source pixels
    if (mask.length != height || mask[0].length != width) {
      throw new IllegalArgumentException("Mask dimensions must match source pixel dimensions.");
    }

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        // Check if the mask pixel is black (assuming RGBPixel and black is (0, 0, 0))
        if (((RGBPixel) mask[y][x]).getRed() == 0 && ((RGBPixel) mask[y][x]).getGreen() == 0 && ((RGBPixel) mask[y][x]).getBlue() == 0) {
          resultPixels[y][x] = operation.apply(new Pixels[][]{{sourcePixels[y][x]}})[0][0];
        } else {
          // Keep the original pixel color
          resultPixels[y][x] = sourcePixels[y][x];
        }
      }
    }
    return resultPixels;
  }
}
