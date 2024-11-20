package model;
import java.awt.image.BufferedImage;
import model.colorscheme.Pixels;
import model.colorscheme.RGBPixel;
import model.imagetransformation.advancedoperations.Downscale;
import model.imagetransformation.advancedoperations.MaskedOperation;

public class EnhancedImage extends Image implements EnhancedImageModel{

  @Override
  public void downscale(String key, int newwidth, int newht, String saveKey) {
    Downscale d1= new Downscale(newht,newwidth);
    updatedPixel=h1.get(key);
    updatedPixel=d1.apply(updatedPixel);
    h1.put(saveKey, updatedPixel);
  }

  @Override
  public void maskedOperation(String key, String operation, String maskKey, String saveKey) {
    Pixels[][] sourcePixels = h1.get(key);
    Pixels[][] maskPixels = h1.get(maskKey);

    if (sourcePixels == null || maskPixels == null) {
      throw new IllegalArgumentException("Source image or mask image not found.");
    }

    MaskedOperation maskedOp = new MaskedOperation(operation, maskPixels);
    Pixels[][] updatedPixels = maskedOp.apply(sourcePixels);
    h1.put(saveKey, updatedPixels);
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
