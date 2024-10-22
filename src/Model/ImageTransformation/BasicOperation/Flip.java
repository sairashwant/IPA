package Model.ImageTransformation.BasicOperation;

import Model.RGBPixel;
import java.util.HashMap;

public class Flip extends AbstractBasicOperation{

  public enum Direction {
    HORIZONTAL,
    VERTICAL
  }


  public RGBPixel[][] apply(String key, HashMap<String, RGBPixel[][]> h1,Direction direction){
    RGBPixel[][] input= h1.get(key);
    int height = input.length;
    int width = input[0].length;

    RGBPixel[][] flippedImage = new RGBPixel[height][width];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        if (direction == Direction.HORIZONTAL) {
          flippedImage[i][width - 1 - j] = input[i][j];
        } else if (direction == Direction.VERTICAL) {
          flippedImage[height - 1 - i][j] = input[i][j];
        }
      }
    }

    return flippedImage;
  }
}
