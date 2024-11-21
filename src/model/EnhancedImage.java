package model;

import java.awt.image.BufferedImage;
import model.colorscheme.Pixels;
import model.colorscheme.RGBPixel;
import model.imagetransformation.Transformation;
import model.imagetransformation.advancedoperations.Downscale;
import model.imagetransformation.advancedoperations.MaskedOperation;
import model.imagetransformation.basicoperation.ChannelExtractor;
import model.imagetransformation.basicoperation.Intensity;
import model.imagetransformation.basicoperation.Luma;
import model.imagetransformation.basicoperation.Value;
import model.imagetransformation.colortransformation.GreyScale;
import model.imagetransformation.colortransformation.Sepia;
import model.imagetransformation.filtering.Blur;
import model.imagetransformation.filtering.Sharpen;

/**
 * The {@code EnhancedImage} class extends the {@link Image} class and implements the
 * {@link EnhancedImageModel} interface. It provides additional image transformation operations such
 * as applying masked operations, downscaling images, and retrieving the latest image key. These
 * operations allow manipulation of pixel data using various transformation techniques. This class
 * interacts with a hash map (`h1`) to store and retrieve pixel data associated with unique image
 * keys.
 */
public class EnhancedImage extends Image implements EnhancedImageModel {

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
   * @throws IllegalArgumentException if the source or mask image cannot be found, or if an
   *                                  unsupported operation is provided
   */
  @Override
  public void maskedOperation(String key, String operation, String maskKey, String saveKey) {
    Pixels[][] sourcePixels = h1.get(key);
    Pixels[][] maskPixels = h1.get(maskKey);

    if (sourcePixels == null || maskPixels == null) {
      throw new IllegalArgumentException("Source image or mask image not found.");
    }

    Transformation operationInstance;
    switch (operation.toLowerCase()) {
      case "blur":
        operationInstance = new Blur();
        break;
      case "sharpen":
        operationInstance = new Sharpen();
        break;
      case "greyscale":
        operationInstance = new GreyScale();
        break;
      case "sepia":
        operationInstance = new Sepia();
        break;
      case "luma-component":
        operationInstance = new Luma();
        break;
      case "value-component":
        operationInstance = new Value();
        break;
      case "intensity-component":
        operationInstance = new Intensity();
        break;
      case "red-component":
        operationInstance = new ChannelExtractor(1);
        break;
      case "green-component":
        operationInstance = new ChannelExtractor(2);
        break;
      case "blue-component":
        operationInstance = new ChannelExtractor(3);
        break;
      default:
        throw new IllegalArgumentException("Unsupported operation: " + operation);
    }

    MaskedOperation maskedOp = new MaskedOperation(operationInstance, maskPixels);
    Pixels[][] updatedPixels = maskedOp.apply(sourcePixels);
    h1.put(saveKey, updatedPixels);
  }


  /**
   * Downscales an image by the specified width and height, storing the resulting downscaled image
   * under a new key.
   *
   * @param key      the key of the image to downscale in the image map
   * @param newwidth the new width for the downscaled image
   * @param newht    the new height for the downscaled image
   * @param saveKey  the key under which to save the downscaled image
   */
  @Override
  public void downscale(String key, int newwidth, int newht, String saveKey) {
    Downscale d1 = new Downscale(newht, newwidth);
    Pixels[][] updatedPixel = h1.get(key);
    updatedPixel = d1.apply(updatedPixel);
    h1.put(saveKey, updatedPixel);
  }

  /**
   * Retrieves the key of the most recently added image in the image map.
   *
   * @return the key of the latest image, or {@code null} if no images are present
   */
  @Override
  public String getLatestKey() {
    if (h1.isEmpty()) {
      return null;
    }
    String latestKey = null;
    for (String key : h1.keySet()) {
      latestKey = key;
    }
    return latestKey;
  }
}
