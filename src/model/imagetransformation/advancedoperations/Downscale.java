package model.imagetransformation.advancedoperations;

import model.colorscheme.Pixels;
import model.colorscheme.RGBPixel;
import model.imagetransformation.Transformation;

public class Downscale implements Transformation {

  int newHeight;
  int newWidth;

  public Downscale(int newWidth, int newHeight) {
    this.newHeight = newHeight;
    this.newWidth = newWidth;
  }
  @Override
  public Pixels[][] apply(Pixels[][] originalPixels) {
    int originalHeight = originalPixels.length;
    int originalWidth = originalPixels[0].length;
    Pixels[][] downsizedPixels = new RGBPixel[newHeight][newWidth];
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
