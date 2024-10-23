package Model.ImageTransformation.BasicOperation;

import Model.ColorScheme.RGBPixel;
import java.util.HashMap;
/**
 * The {@code Flip} class extends {@code AbstractBasicOperation} to provide an image transformation
 * that flips an image either horizontally or vertically based on the specified direction.
 * It takes an image from a given key in a HashMap and returns the flipped version.
 */
public class Flip extends AbstractBasicOperation{

  /**
   * The {@code Direction} enum represents the two possible directions for flipping an image:
   * horizontally or vertically.
   */

  public enum Direction {
    HORIZONTAL,
    VERTICAL
  }

  /**
   * Flips the image stored in the given key either horizontally or vertically depending on the specified direction.
   *
   * @param key       the key associated with the image in the HashMap
   * @param h1        a HashMap containing the images with their associated keys
   * @param direction the direction to flip the image, either {@code HORIZONTAL} or {@code VERTICAL}
   * @return a 2D array of {@code RGBPixel} representing the flipped image
   * @throws IllegalArgumentException if the specified key is not found in the HashMap
   */
  public RGBPixel[][] apply(String key, HashMap<String, RGBPixel[][]> h1,Direction direction){
    RGBPixel[][] input= h1.get(key);
    int height = input.length;
    int width = input[0].length;

    RGBPixel[][] flippedImage = new RGBPixel[height][width];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        if (direction == Direction.HORIZONTAL) {
          flippedImage[i][width - 1 - j] = input[i][j];
        } else if (direction == Direction.VERTICAL) {
          flippedImage[height - 1 - i][j] = input[i][j];
        }
      }
    }

    return flippedImage;
  }
}
