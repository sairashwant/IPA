package model;

import model.colorscheme.Pixels;
import model.imagetransformation.basicoperation.Flip.Direction;

/**
 * Interface representing the operations that can be performed on an image.
 * Defines the contract for image manipulation operations including loading, saving,
 * and various transformations like blur, sharpen, color adjustments, etc.
 */
public interface ImageModel {

  /**
   * Stores the pixel data under the specified key.
   *
   * @param key    the key under which to store the pixels
   * @param pixels the pixel data to store
   */
  void storePixels(String key, Pixels[][] pixels);

  /**
   * Retrieves the pixel data stored under the specified key.
   *
   * @param key the key associated with the desired pixel data
   * @return the pixel data associated with the key
   */
  Pixels[][] getStoredPixels(String key);

  /**
   * Extracts the red channel from the image.
   *
   * @param key     the source image key
   * @param saveKey the key under which to store the result
   */
  void getRedChannel(String key, String saveKey);

  /**
   * Extracts the green channel from the image.
   *
   * @param key     the source image key
   * @param saveKey the key under which to store the result
   */
  void getGreenChannel(String key, String saveKey);

  /**
   * Extracts the blue channel from the image.
   *
   * @param key     the source image key
   * @param saveKey the key under which to store the result
   */
  void getBlueChannel(String key, String saveKey);

  /**
   * Applies a blur effect to the image.
   *
   * @param key     the source image key
   * @param saveKey the key under which to store the result
   */
  void blur(String key, String saveKey);

  /**
   * Adjusts the brightness of the image.
   *
   * @param brightenFactor the amount to brighten (positive) or darken (negative)
   * @param key           the source image key
   * @param saveKey       the key under which to store the result
   */
  void brighten(int brightenFactor, String key, String saveKey);

  /**
   * Splits the image into its RGB components.
   *
   * @param key      the source image key
   * @param saveKey1 key for red component
   * @param saveKey2 key for green component
   * @param saveKey3 key for blue component
   */
  void split(String key, String saveKey1, String saveKey2, String saveKey3);

  /**
   * Combines separate color channels into a single image.
   *
   * @param key  the key under which to store the combined image
   * @param key1 red channel key
   * @param key2 green channel key
   * @param key3 blue channel key
   */
  void combine(String key, String key1, String key2, String key3);

  /**
   * Flips the image in the specified direction.
   *
   * @param key     the source image key
   * @param saveKey the key under which to store the result
   * @param d       the direction to flip (horizontal or vertical)
   */
  void flip(String key, String saveKey, Direction d);

  /**
   * Converts the image to greyscale.
   *
   * @param key     the source image key
   * @param saveKey the key under which to store the result
   */
  void greyScale(String key, String saveKey);

  /**
   * Applies a sepia tone effect to the image.
   *
   * @param key     the source image key
   * @param saveKey the key under which to store the result
   */
  void sepia(String key, String saveKey);

  /**
   * Sharpens the image.
   *
   * @param key     the source image key
   * @param saveKey the key under which to store the result
   */
  void sharpen(String key, String saveKey);

  /**
   * Applies a luma transformation to the image.
   *
   * @param key     the source image key
   * @param saveKey the key under which to store the result
   */
  void luma(String key, String saveKey);

  /**
   * Extracts the value component of the image.
   *
   * @param key     the source image key
   * @param saveKey the key under which to store the result
   */
  void value(String key, String saveKey);

  /**
   * Calculates the intensity of the image.
   *
   * @param key     the source image key
   * @param saveKey the key under which to store the result
   */
  void intensity(String key, String saveKey);

  /**
   * Compresses the image by the specified ratio.
   *
   * @param key              the source image key
   * @param saveKey          the key under which to store the result
   * @param compressionRatio the compression ratio (0-100)
   */
  void compress(String key, String saveKey, double compressionRatio);

  /**
   * Applies color correction to the image.
   *
   * @param key     the source image key
   * @param saveKey the key under which to store the result
   */
  void colorCorrection(String key, String saveKey);

  /**
   * Adjusts the levels of the image.
   *
   * @param black   the black point value
   * @param mid     the midtone point value
   * @param white   the white point value
   * @param key     the source image key
   * @param saveKey the key under which to store the result
   */
  void adjustLevel(int black, int mid, int white, String key, String saveKey);

  /**
   * Creates a histogram visualization of the image.
   *
   * @param key     the source image key
   * @param saveKey the key under which to store the result
   */
  void histogram(String key, String saveKey);

  /**
   * Splits and transforms part of the image.
   *
   * @param key        the source image key
   * @param saveKey    the key under which to store the result
   * @param splitValue the percentage at which to split the image
   * @param operation  the operation to apply to the split portion
   * @param params    optional parameters for the operation
   */
  void splitAndTransform(String key, String saveKey, int splitValue, String operation, int... params);
}