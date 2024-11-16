package model.imagetransformation.advancedoperations;

import model.colorscheme.Pixels;
import model.colorscheme.RGBPixel;
import model.imagetransformation.Transformation;
import model.imagetransformation.filtering.Blur; // Assuming you have a Blur class
import model.imagetransformation.filtering.Sharpen; // Assuming you have a Sharpen class
import model.imagetransformation.colortransformation.GreyScale; // Assuming you have a GreyScale class
import model.imagetransformation.colortransformation.Sepia; // Assuming you have a Sepia class
import model.imagetransformation.filtering.Sharpen;

public class MaskedOperation implements Transformation {
  private String operation;
  private Pixels[][] mask;

  public MaskedOperation(String operation, Pixels[][] mask) {
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

    // Create an instance of the operation class based on the operation name
    Transformation operationInstance = null;
    switch (operation.toLowerCase()) {
      case "blur":
        operationInstance = new Blur();
        break;
      case "sharpen":
        operationInstance = new Sharpen();
        break;
      case "greyscale":
        operationInstance = new GreyScale();
        break;
      case "sepia":
        operationInstance = new Sepia();
        break;
      default:
        throw new IllegalArgumentException("Unsupported operation: " + operation);
    }

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        // Check if the mask pixel is black (assuming RGBPixel and black is (0, 0, 0))
        if (mask[y][x] instanceof RGBPixel && ((RGBPixel) mask[y][x]).getRed() == 0) {
          // Apply the operation only if the mask pixel is black
          resultPixels[y][x] = operationInstance.apply(new Pixels[][]{ {sourcePixels[y][x]} })[0][0];
        } else {
          // Keep the original pixel color
          resultPixels[y][x] = sourcePixels[y][x];
        }
      }
    }
    return resultPixels;
  }
}