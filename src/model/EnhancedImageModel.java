package model;

/**
 * The {@code EnhancedImageModel} interface extends the {@link ImageModel} interface and provides
 * additional methods for advanced image processing. It includes operations for downscaling images,
 * applying transformations with a mask, and retrieving the most recent image key. Implementations
 * of this interface are expected to perform these operations on images stored in a map or similar
 * structure.
 */
public interface EnhancedImageModel extends ImageModel {

  /**
   * Downscales an image to a specified width and height and stores the result under a new key.
   *
   * @param key      the key of the image to downscale in the image map
   * @param newwidth the new width for the downscaled image
   * @param newht    the new height for the downscaled image
   * @param savekey  the key under which to save the downscaled image
   */
  void downscale(String key, int newwidth, int newht, String savekey);

  /**
   * Applies a masked operation on an image, where the operation is applied to the source image only
   * at positions specified by a mask. The mask determines which pixels in the source image are
   * modified by the transformation.
   *
   * @param key       the key of the source image in the image map
   * @param operation the name of the transformation operation to apply (e.g., "blur", "sharpen",
   *                  "greyscale")
   * @param maskKey   the key of the mask image in the image map
   * @param saveKey   the key under which to save the result of the masked operation
   */
  void maskedOperation(String key, String operation, String maskKey, String saveKey);

  /**
   * Retrieves the key of the most recently added image in the image map.
   *
   * @return the key of the latest image, or {@code null} if no images are present
   */
  String getLatestKey();
}
