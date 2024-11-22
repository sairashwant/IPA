package model.imagetransformation.basicoperation;

import java.util.HashMap;
import java.util.Map;
import model.colorscheme.Pixels;
import model.colorscheme.RGBPixel;


/**
 * The {@code Split} class is responsible for splitting an image's RGB channels into three separate
 * channels: red, green, and blue. Each channel will be represented as an image where only the
 * corresponding color information is retained, while the other two channels are set to the same
 * value as the target channel.
 *
 * <p>This class extends {@code AbstractBasicOperation} and is used in image processing to isolate
 * individual color channels (red, green, or blue) from a full RGB image. The resulting channels can
 * be processed separately or used for further transformations like color manipulation or
 * analysis.</p>
 */
public class Split extends AbstractBasicOperation {


  /**
   * Splits the given image into its red, green, and blue channels. Each resulting channel will
   * contain only the respective color value, with the other two channels being set to the same
   * value as the target channel.
   *
   * <p>This method processes the input image, which should be in RGB format, and creates three new
   * images: one for the red channel, one for the green channel, and one for the blue channel. These
   * channels are stored in a {@code HashMap} using the provided keys.</p>
   *
   * @param h1             a {@code HashMap} containing image data, where the key is a string
   *                       identifier and the value is a 2D array of {@code RGBPixel} objects
   *                       representing the image's pixels
   * @param originalPixels a 2D array of {@code Pixels} representing the original image in RGB
   *                       format to be split
   * @param key3           a string key used for internal processing, could be used in future
   *                       extensions
   * @param saveKey1       the key under which to save the red channel in the output
   *                       {@code HashMap}
   * @param saveKey2       the key under which to save the green channel in the output
   *                       {@code HashMap}
   * @param saveKey3       the key under which to save the blue channel in the output
   *                       {@code HashMap}
   * @return a {@code HashMap<String, Pixels[][]>} containing the red, green, and blue channel
   * images, stored under {@code saveKey1}, {@code saveKey2}, and {@code saveKey3} respectively
   * @throws IllegalArgumentException if the input image does not contain {@code RGBPixel} objects
   */

  public HashMap<String, Pixels[][]> apply(Map<String, Pixels[][]> h1,
      Pixels[][] originalPixels, String key3, String saveKey1, String saveKey2, String saveKey3) {

    int height = originalPixels.length;
    int width = originalPixels[0].length;

    Pixels[][] redChannel = new RGBPixel[height][width];
    Pixels[][] greenChannel = new RGBPixel[height][width];
    Pixels[][] blueChannel = new RGBPixel[height][width];

    HashMap<String, Pixels[][]> channelsMap = new HashMap<>();

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {

        if (!(originalPixels[i][j] instanceof RGBPixel)) {
          throw new IllegalArgumentException("Input image must contain RGBPixels");
        }

        RGBPixel currentPixel = (RGBPixel) originalPixels[i][j];

        redChannel[i][j] = new RGBPixel(currentPixel.getRed(), currentPixel.getRed(),
            currentPixel.getRed());
        greenChannel[i][j] = new RGBPixel(currentPixel.getGreen(), currentPixel.getGreen(),
            currentPixel.getGreen());
        blueChannel[i][j] = new RGBPixel(currentPixel.getBlue(), currentPixel.getBlue(),
            currentPixel.getBlue());
      }
    }

    channelsMap.put(saveKey1, redChannel);
    channelsMap.put(saveKey2, greenChannel);
    channelsMap.put(saveKey3, blueChannel);

    return channelsMap;
  }
}
