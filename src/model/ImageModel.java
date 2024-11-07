package model;

import model.colorscheme.Pixels;
import model.imagetransformation.basicoperation.Flip.Direction;

/**
 * Interface representing operations that can be performed on an image. This includes various
 * transformations such as blurring, sharpening, color corrections, extracting channels, and more.
 * The interface defines the contract for image manipulation and storage of pixel data for later
 * processing or visualization.
 */
public interface ImageModel {

  /**
   * Stores the pixel data under the specified key.
   *
   * @param key    the key under which to store the pixel data
   * @param pixels the pixel data to be stored
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
   * Extracts the red channel from the image and stores it under the specified key.
   *
   * @param key     the source image key
   * @param saveKey the key under which to store the red channel data
   */
  void getRedChannel(String key, String saveKey);

  /**
   * Extracts the green channel from the image and stores it under the specified key.
   *
   * @param key     the source image key
   * @param saveKey the key under which to store the green channel data
   */
  void getGreenChannel(String key, String saveKey);

  /**
   * Extracts the blue channel from the image and stores it under the specified key.
   *
   * @param key     the source image key
   * @param saveKey the key under which to store the blue channel data
   */
  void getBlueChannel(String key, String saveKey);

  /**
   * Applies a blur effect to the image and stores the result under the specified key.
   *
   * @param key     the source image key
   * @param saveKey the key under which to store the blurred image
   */
  void blur(String key, String saveKey);

  /**
   * Brightens or darkens the image based on the specified factor.
   *
   * @param brightenFactor the amount to brighten (positive) or darken (negative)
   * @param key            the source image key
   * @param saveKey        the key under which to store the transformed image
   */
  void brighten(int brightenFactor, String key, String saveKey);

  /**
   * Splits the image into its individual RGB components (red, green, blue).
   *
   * @param key      the source image key
   * @param saveKey1 key for storing the red channel
   * @param saveKey2 key for storing the green channel
   * @param saveKey3 key for storing the blue channel
   */
  void split(String key, String saveKey1, String saveKey2, String saveKey3);

  /**
   * Combines separate RGB channels back into a single image.
   *
   * @param key  the key under which to store the combined image
   * @param key1 the key for the red channel
   * @param key2 the key for the green channel
   * @param key3 the key for the blue channel
   */
  void combine(String key, String key1, String key2, String key3);

  /**
   * Flips the image in the specified direction (horizontal or vertical).
   *
   * @param key     the source image key
   * @param saveKey the key under which to store the flipped image
   * @param d       the direction to flip (horizontal or vertical)
   */
  void flip(String key, String saveKey, Direction d);

  /**
   * Converts the image to greyscale and stores the result under the specified key.
   *
   * @param key     the source image key
   * @param saveKey the key under which to store the greyscale image
   */
  void greyScale(String key, String saveKey);

  /**
   * Applies a sepia tone effect to the image and stores the result under the specified key.
   *
   * @param key     the source image key
   * @param saveKey the key under which to store the sepia-toned image
   */
  void sepia(String key, String saveKey);

  /**
   * Sharpens the image and stores the result under the specified key.
   *
   * @param key     the source image key
   * @param saveKey the key under which to store the sharpened image
   */
  void sharpen(String key, String saveKey);

  /**
   * Applies a luma transformation to the image and stores the result under the specified key.
   *
   * @param key     the source image key
   * @param saveKey the key under which to store the luma-transformed image
   */
  void luma(String key, String saveKey);

  /**
   * Extracts the value component of the image and stores it under the specified key.
   *
   * @param key     the source image key
   * @param saveKey the key under which to store the value component of the image
   */
  void value(String key, String saveKey);

  /**
   * Calculates the intensity of the image and stores the result under the specified key.
   *
   * @param key     the source image key
   * @param saveKey the key under which to store the intensity data
   */
  void intensity(String key, String saveKey);

  /**
   * Compresses the image based on the specified compression ratio.
   *
   * @param key              the source image key
   * @param saveKey          the key under which to store the compressed image
   * @param compressionRatio the compression ratio (0 to 100)
   */
  void compress(String key, String saveKey, double compressionRatio);

  /**
   * Applies color correction to the image and stores the result under the specified key.
   *
   * @param key     the source image key
   * @param saveKey the key under which to store the color-corrected image
   */
  void colorCorrection(String key, String saveKey);

  /**
   * Adjusts the image levels by adjusting the black, mid, and white point values.
   *
   * @param black   the black point value
   * @param mid     the midtone point value
   * @param white   the white point value
   * @param key     the source image key
   * @param saveKey the key under which to store the adjusted image
   */
  void adjustLevel(int black, int mid, int white, String key, String saveKey);

  /**
   * Creates a histogram visualization of the image and stores the result under the specified key.
   *
   * @param key     the source image key
   * @param saveKey the key under which to store the histogram image
   */
  void histogram(String key, String saveKey);

  /**
   * Splits the image into two parts based on a vertical split value and applies the specified
   * transformation. After transformation, the two parts are recombined into a single image.
   *
   * @param key        the source image key
   * @param saveKey    the key under which to store the result
   * @param splitValue the percentage at which to split the image
   * @param operation  the operation to apply to the split portion ("blur", "sharpen", etc.)
   * @param params     optional parameters for the operation
   */
  void splitAndTransform(String key, String saveKey, int splitValue, String operation,
      int... params);
}
