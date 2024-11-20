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
import model.imagetransformation.basicoperation.Split;
import model.imagetransformation.basicoperation.Value;
import model.imagetransformation.colortransformation.GreyScale;
import model.imagetransformation.colortransformation.Sepia;
import model.imagetransformation.filtering.Blur;
import model.imagetransformation.filtering.Sharpen;

public class EnhancedImage extends Image implements EnhancedImageModel {

  @Override
  public void maskedOperation(String key, String operation, String maskKey, String saveKey) {
    Pixels[][] sourcePixels = h1.get(key);
    Pixels[][] maskPixels = h1.get(maskKey);

    if (sourcePixels == null || maskPixels == null) {
      throw new IllegalArgumentException("Source image or mask image not found.");
    }

    // Create the appropriate operation instance
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

    // Pass the operation instance and mask to MaskedOperation
    MaskedOperation maskedOp = new MaskedOperation(operationInstance, maskPixels);
    Pixels[][] updatedPixels = maskedOp.apply(sourcePixels);
    h1.put(saveKey, updatedPixels);
  }

  @Override
  public void downscale(String key, int newwidth, int newht, String saveKey) {
    Downscale d1 = new Downscale(newht, newwidth);
    Pixels[][] updatedPixel = h1.get(key);
    updatedPixel = d1.apply(updatedPixel);
    h1.put(saveKey, updatedPixel);
  }

  @Override
  public String getLatestKey() {
    if (h1.isEmpty()) {
      return null;
    }
    String latestKey = null;
    for (String key : h1.keySet()) {
      latestKey = key; // This will end up with the last key in the iteration
    }
    return latestKey;
  }

  private BufferedImage convertPixelsToBufferedImage(Pixels[][] pixels) {
    if (pixels == null || pixels.length == 0) {
      throw new IllegalArgumentException("No pixels to convert.");
    }

    int height = pixels.length;
    int width = pixels[0].length;
    BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        if (pixels[y][x] instanceof RGBPixel) {
          RGBPixel rgbPixel = (RGBPixel) pixels[y][x];
          int rgb = (rgbPixel.getRed() << 16) | (rgbPixel.getGreen() << 8) | rgbPixel.getBlue();
          image.setRGB(x, y, rgb);
        }
      }
    }

    return image;
  }
}
