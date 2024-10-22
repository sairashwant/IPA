package Model.ImageTransformation;

import Model.RGBPixel;
import java.util.HashMap;

public interface Transformation {

  public RGBPixel[][] apply(String key, HashMap<String, RGBPixel[][]> h1);

}
