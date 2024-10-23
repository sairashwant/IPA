package Model.ImageTransformation.BasicOperation;

import Model.ColorScheme.RGBPixel;
import java.util.HashMap;

public class  Brighten extends AbstractBasicOperation {

  private final int brightenFactor;

  public Brighten(int brightenFactor) {
    this.brightenFactor = brightenFactor;
  }

  @Override
  public RGBPixel[][] apply(String key,HashMap<String, RGBPixel[][]> h1){

    RGBPixel[][] pixels = h1.get(key);
    int height = pixels.length;
    int width = pixels[0].length;

    RGBPixel[][] brightenedPixels = new RGBPixel[height][width];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        if (!(pixels[i][j] instanceof RGBPixel)) {
          throw new IllegalArgumentException("Expected an RGBPixel.");
        }

        RGBPixel rgbPixel = (RGBPixel) pixels[i][j];

        int newRed = Math.min(255, Math.max(0, rgbPixel.getRed() + brightenFactor));
        int newGreen = Math.min(255, Math.max(0, rgbPixel.getGreen() + brightenFactor));
        int newBlue = Math.min(255, Math.max(0, rgbPixel.getBlue() + brightenFactor));

        brightenedPixels[i][j] = new RGBPixel(newRed, newGreen, newBlue);
      }
    }

    return brightenedPixels;
  }

}
