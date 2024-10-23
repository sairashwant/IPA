package Model.ImageTransformation;

import Model.ColorScheme.RGBPixel;
import java.util.HashMap;

public interface Transformation {

  public RGBPixel[][] apply(String key, HashMap<String, RGBPixel[][]> h1);

}
