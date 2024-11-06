package model.imagetransformation.basicoperation;

import model.colorscheme.Pixels;
import model.colorscheme.RGBPixel;
import java.util.HashMap;

/**
 * The Split class is responsible for splitting an image's RGB channels into three separate
 * channels: red, green, and blue. Each channel will be represented as an image where only the
 * corresponding color information is retained (the other two channels are set to the same value as
 * the target channel).
 */
public class Split extends AbstractBasicOperation {

  /**
   * Splits the given image into its red, green, and blue channels. Each resulting channel will
   * contain the respective color value, with the other two channels being the same as the target
   * channel value.
   *
   * @param h1
   * @param originalPixels the original image pixels to split, expected to be in RGB format
   * @param saveKey1       the key under which to save the red channel in the output HashMap
   * @param saveKey2       the key under which to save the green channel in the output HashMap
   * @param saveKey3       the key under which to save the blue channel in the output HashMap
   * @param key3
   * @return a HashMap containing the red, green, and blue channel images, stored under saveKey1,
   * saveKey2, and saveKey3 respectively
   * @throws IllegalArgumentException if the input image does not contain RGBPixel objects
   */
  public HashMap<String, Pixels[][]> apply(HashMap<String, Pixels[][]> h1, Pixels[][] originalPixels, String key3, String saveKey1, String saveKey2, String saveKey3) {

    int height = originalPixels.length;
    int width = originalPixels[0].length;

    // Prepare separate channels for red, green, and blue
    Pixels[][] redChannel = new RGBPixel[height][width];
    Pixels[][] greenChannel = new RGBPixel[height][width];
    Pixels[][] blueChannel = new RGBPixel[height][width];

    HashMap<String, Pixels[][]> channelsMap = new HashMap<>();

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        // Ensure that the pixels are instances of RGBPixel
        if (!(originalPixels[i][j] instanceof RGBPixel)) {
          throw new IllegalArgumentException("Input image must contain RGBPixels");
        }

        // Cast the current pixel to RGBPixel
        RGBPixel currentPixel = (RGBPixel) originalPixels[i][j];

        // Split the pixel into its RGB channels
        redChannel[i][j] = new RGBPixel(currentPixel.getRed(), currentPixel.getRed(), currentPixel.getRed());
        greenChannel[i][j] = new RGBPixel(currentPixel.getGreen(), currentPixel.getGreen(), currentPixel.getGreen());
        blueChannel[i][j] = new RGBPixel(currentPixel.getBlue(), currentPixel.getBlue(), currentPixel.getBlue());
      }
    }

    // Save the resulting channels in the HashMap with the provided keys
    channelsMap.put(saveKey1, redChannel);
    channelsMap.put(saveKey2, greenChannel);
    channelsMap.put(saveKey3, blueChannel);

    return channelsMap;
  }
}
