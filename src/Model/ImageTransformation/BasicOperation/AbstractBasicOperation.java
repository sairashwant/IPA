package Model.ImageTransformation.BasicOperation;

import Model.ImageTransformation.Transformation;
import Model.ColorScheme.RGBPixel;
import java.util.HashMap;

/**
 * The class provides a base implementation for basic image transformation operations.
 * It implements the Transformation interface and defines common logic for applying pixel-wise operations
 * on an image. The class is abstract and intended to be extended by specific transformation operations.
 */
public abstract class AbstractBasicOperation implements Transformation {

  /**
   * Applies a transformation to the specified image, identified by the given key, and returns the transformed image.
   * The transformation is performed on a pixel-by-pixel basis, producing a new image where each pixel's color is
   * computed based on the original pixel values.
   *
   * @param key the key used to identify the image in the {@code h1} HashMap
   * @param h1  a {@code HashMap} containing image data, where the key is a string identifier and the value is a
   *            2D array of {@code RGBPixel} objects representing the image's pixels
   * @return a 2D array of {@code RGBPixel} representing the transformed image
   * @throws IllegalArgumentException if a pixel is not an instance of {@code RGBPixel}
   */
  public RGBPixel[][] apply(String key, HashMap<String, RGBPixel[][]> h1){
    RGBPixel[][] pixels= h1.get(key);
    int height = pixels.length;
    int width = pixels[0].length;

    RGBPixel[][] abstractpixel = new RGBPixel[height][width];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        RGBPixel pixel = pixels[i][j];
        if (pixel instanceof RGBPixel) {
          RGBPixel rgbPixel = (RGBPixel) pixel;

          int red = rgbPixel.getRed();
          int green = rgbPixel.getGreen();
          int blue = rgbPixel.getBlue();

          int val = properties(red,green,blue);
          abstractpixel[i][j] = new RGBPixel(val, val, val);
        } else {
          throw new IllegalArgumentException("Expected an RGBPixel.");
        }
      }
    }

    return abstractpixel;
  }

  /**
   * Computes a property of the color based on the red, green, and blue values.
   * This method is intended to be overridden by subclasses to provide specific behavior for different operations.
   *
   * @param r the red component of the pixel's color
   * @param g the green component of the pixel's color
   * @param b the blue component of the pixel's color
   * @return an integer value representing the computed property for the given RGB values
   */
  public int properties(int r, int g, int b){

    return 0;
  }
}
