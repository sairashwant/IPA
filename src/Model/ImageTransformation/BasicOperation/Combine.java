package Model.ImageTransformation.BasicOperation;

import Model.RGBPixel;

public class Combine extends AbstractBasicOperation {

  public RGBPixel[][] apply(RGBPixel[][] redPixels, RGBPixel[][] greenPixels, RGBPixel[][] bluePixels) {

    int height = redPixels.length;
    int width = redPixels[0].length;

    if (greenPixels.length != height || bluePixels.length != height ||
        greenPixels[0].length != width || bluePixels[0].length != width) {
      throw new IllegalArgumentException("All input images must have the same dimensions.");
    }

    RGBPixel[][] combinedImage = new RGBPixel[height][width];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        // Ensure all pixels are RGBPixel instances
        if (!(redPixels[i][j] instanceof RGBPixel) ||
            !(greenPixels[i][j] instanceof RGBPixel) ||
            !(bluePixels[i][j] instanceof RGBPixel)) {
          throw new IllegalArgumentException("Input images must contain RGBPixels.");
        }

        // Correctly access the pixel values
        int red = ((RGBPixel) redPixels[i][j]).getRed();
        int green = ((RGBPixel) greenPixels[i][j]).getGreen();
        int blue = ((RGBPixel) bluePixels[i][j]).getBlue();

        // Create a new RGBPixel with the combined values
        combinedImage[i][j] = new RGBPixel(red, green, blue);
      }
    }

    return combinedImage;
  }
}