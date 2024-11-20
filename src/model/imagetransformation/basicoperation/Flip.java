package model.imagetransformation.basicoperation;

import model.colorscheme.Pixels;
import model.colorscheme.RGBPixel;

/**
 * The {@code Flip} class extends {@code AbstractBasicOperation} to provide an image transformation
 * that flips an image either horizontally or vertically based on the specified direction. The
 * transformation is applied to the image represented by the input 2D array of pixels.
 *
 * <p>This operation inverts the image's pixels either by flipping it along the horizontal axis
 * (left-right) or the vertical axis (top-bottom), depending on the provided {@code Direction}.</p>
 */
public class Flip extends AbstractBasicOperation {

  /**
   * The {@code Direction} enum represents the two possible directions for flipping an image:
   * horizontally or vertically.
   */

  public enum Direction {
    HORIZONTAL,
    VERTICAL
  }

  /**
   * Flips the image stored in the given 2D pixel array either horizontally or vertically, depending
   * on the specified direction.
   *
   * <p>This method processes the image represented by the input pixel array and produces a new
   * image where the pixel positions are flipped according to the specified direction. For
   * horizontal flipping, the leftmost pixels are swapped with the rightmost, and for vertical
   * flipping, the topmost pixels are swapped with the bottommost.</p>
   *
   * @param input     the 2D array of {@code Pixels} representing the image to be flipped
   * @param direction the direction to flip the image, either {@code HORIZONTAL} or
   *                  {@code VERTICAL}
   * @return a 2D array of {@code RGBPixel} representing the flipped image
   * @throws IllegalArgumentException if the input array is null or empty
   */
  public Pixels[][] apply(Pixels[][] input, Direction direction) {
    int height = input.length;
    int width = input[0].length;

    Pixels[][] flippedImage = new RGBPixel[height][width];

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
