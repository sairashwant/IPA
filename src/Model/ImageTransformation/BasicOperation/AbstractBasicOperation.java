package Model.ImageTransformation.BasicOperation;

import Model.ImageTransformation.Transformation;
import Model.RGBPixel;
import java.util.HashMap;

/**
 *
 */
public abstract class AbstractBasicOperation implements Transformation {

  /**
   *
   * @param key
   * @param h1
   * @return
   */
  public RGBPixel[][] apply(String key, HashMap<String, RGBPixel[][]> h1){
    RGBPixel[][] pixels= h1.get(key);
    int height = pixels.length;
    int width = pixels[0].length;

    RGBPixel[][] lumaImage = new RGBPixel[height][width];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        RGBPixel pixel = pixels[i][j];
        if (pixel instanceof RGBPixel) {
          RGBPixel rgbPixel = (RGBPixel) pixel;

          int red = rgbPixel.getRed();
          int green = rgbPixel.getGreen();
          int blue = rgbPixel.getBlue();

          int val = properties(red,green,blue);
          lumaImage[i][j] = new RGBPixel(val, val, val);
        } else {
          throw new IllegalArgumentException("Expected an RGBPixel.");
        }
      }
    }

    return lumaImage;
  }


  public int properties(int r, int g, int b){

    return 0;
  }
}
