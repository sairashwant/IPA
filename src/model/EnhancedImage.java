package model;

import java.util.HashMap;
import model.colorscheme.Pixels;
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

}
