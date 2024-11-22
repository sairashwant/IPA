package model.imagetransformation;

import model.colorscheme.Pixels;
import model.colorscheme.RGBPixel;

/**
 * An interface that represents a transformation operation to be applied to an image.
 * <p>Implementations of this interface should define specific transformations that can be applied
 * to RGB pixel data. These transformations may include operations such as compression, color
 * adjustment, filtering, or other image processing techniques that modify the pixel data of an
 * image.</p>
 *
 * <p>The transformations are expected to operate on a 2D array of {@link Pixels} and return a new
 * 2D array of transformed {@link RGBPixel} objects representing the modified image data.</p>
 */
public interface Transformation {

  /**
   * Applies the transformation to the image data represented by a 2D array of {@link Pixels}.
   *
   * <p>The method takes in a 2D array of pixels, processes the pixel data based on the specific
   * transformation, and returns a new 2D array with the transformed image.</p>
   *
   * @param pixels a 2D array of {@link Pixels} representing the image data to be transformed. Each
   *               {@link Pixels} object represents a pixel, and the array represents the entire
   *               image.
   * @return a 2D array of {@link RGBPixel} representing the transformed image. This array holds the
   * resulting RGB pixel data after the transformation has been applied.
   * @throws IllegalArgumentException if the input pixel data is invalid or cannot be processed.
   */

  Pixels[][] apply(Pixels[][] pixels);
}
