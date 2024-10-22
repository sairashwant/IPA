package Model.ImageTransformation;

import Model.Image;
import Model.RGBPixel;

public class luma extends AbstractBasicOperation{

  @Override
  public int properties (int r,int g,int b){
    int luma = (int) Math.round(0.2126 * r + 0.7152 * g + 0.0722 * b);
    return luma;
}

}
