package Model.ImageTransformation.BasicOperation;

import Model.ColorScheme.RGBPixel;
import java.util.HashMap;


public class Split extends AbstractBasicOperation {


  public HashMap<String, RGBPixel[][]> apply(HashMap<String, RGBPixel[][]> h1,RGBPixel[][] originalPixels,String key, String saveKey1, String saveKey2, String saveKey3) {

    int height = originalPixels.length;
    int width = originalPixels[0].length;


    RGBPixel[][] redChannel = new RGBPixel[height][width];
    RGBPixel[][] greenChannel = new RGBPixel[height][width];
    RGBPixel[][] blueChannel = new RGBPixel[height][width];


    HashMap<String, RGBPixel[][]> channelsMap = new HashMap<>();


    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        if (!(originalPixels[i][j] instanceof RGBPixel)) {
          throw new IllegalArgumentException("Input image must contain RGBPixels");
        }

        RGBPixel currentPixel = originalPixels[i][j];


        redChannel[i][j] = new RGBPixel(currentPixel.getRed(), currentPixel.getRed(), currentPixel.getRed());
        greenChannel[i][j] = new RGBPixel(currentPixel.getGreen(), currentPixel.getGreen(), currentPixel.getGreen());
        blueChannel[i][j] = new RGBPixel(currentPixel.getBlue(), currentPixel.getBlue(), currentPixel.getBlue());
      }
    }


    channelsMap.put(saveKey1, redChannel);
    channelsMap.put(saveKey2, greenChannel);
    channelsMap.put(saveKey3, blueChannel);

    return channelsMap;
  }
}
