package Model.ImageTransformation;

import Model.Image;
import Model.RGBPixel;

public class Intensity extends AbstractBasicOperation {

  @Override
  public int properties(int r, int g, int b) {

    int Value = (r + g + b) / 3;

    return Value;
  }

}
