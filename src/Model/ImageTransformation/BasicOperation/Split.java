package Model.ImageTransformation.BasicOperation;

import Model.RGBPixel;
import java.util.HashMap;

public class Split extends AbstractBasicOperation {


  public HashMap<String, RGBPixel[][]> apply(HashMap<String, RGBPixel[][]> h1,RGBPixel[][] originalPixels,String key, String saveKey1, String saveKey2, String saveKey3) {

    int height = originalPixels.length;
    int width = originalPixels[0].length;

    // Create three 2D arrays for the Red, Green, and Blue channels
    RGBPixel[][] redChannel = new RGBPixel[height][width];
    RGBPixel[][] greenChannel = new RGBPixel[height][width];
    RGBPixel[][] blueChannel = new RGBPixel[height][width];

    // HashMap to store the channels with "Red", "Green", "Blue" as keys
    HashMap<String, RGBPixel[][]> channelsMap = new HashMap<>();

    // Iterate over the original pixels to split them into separate color channels
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        if (!(originalPixels[i][j] instanceof RGBPixel)) {
          throw new IllegalArgumentException("Input image must contain RGBPixels");
        }

        RGBPixel currentPixel = originalPixels[i][j];

        // Set the individual channel values in each channel's array
        redChannel[i][j] = new RGBPixel(currentPixel.getRed(), currentPixel.getRed(), currentPixel.getRed());
        greenChannel[i][j] = new RGBPixel(currentPixel.getGreen(), currentPixel.getGreen(), currentPixel.getGreen());
        blueChannel[i][j] = new RGBPixel(currentPixel.getBlue(), currentPixel.getBlue(), currentPixel.getBlue());
      }
    }

    // Store the channel arrays in the HashMap
    channelsMap.put(saveKey1, redChannel);
    channelsMap.put(saveKey2, greenChannel);
    channelsMap.put(saveKey3, blueChannel);

    return channelsMap;
  }
}
