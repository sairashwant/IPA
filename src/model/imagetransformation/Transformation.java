package model.imagetransformation;

import model.colorscheme.Pixels;
import model.colorscheme.RGBPixel;
import java.util.HashMap;

/**
 * An interface that represents a transformation operation to be applied to an image.
 * Implementations of this interface should define specific transformations that can be applied to
 * RGB pixel data.
 */
public interface Transformation {

  /**
   * Applies the transformation to the image data associated with the specified key.
   * @param pixels  a map containing the RGB pixel data associated with various keys
   * @return a 2D array of RGBPixel representing the transformed image
   */
  Pixels[][] apply(RGBPixel[][] pixels);
}
