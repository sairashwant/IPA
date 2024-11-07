package model;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import model.colorscheme.Pixels;
import model.colorscheme.RGBPixel;
import model.imagetransformation.advancedoperations.AdjustLevel;
import model.imagetransformation.advancedoperations.ColorCorrection;
import model.imagetransformation.advancedoperations.Compression;
import model.imagetransformation.basicoperation.Luma;
import model.imagetransformation.basicoperation.Split;
import model.imagetransformation.filtering.Blur;
import model.imagetransformation.basicoperation.Brighten;
import model.imagetransformation.basicoperation.Combine;
import model.imagetransformation.basicoperation.Flip;
import model.imagetransformation.basicoperation.Flip.Direction;
import model.imagetransformation.colortransformation.GreyScale;
import model.imagetransformation.basicoperation.Intensity;
import model.imagetransformation.colortransformation.Sepia;
import model.imagetransformation.filtering.Sharpen;
import model.imagetransformation.basicoperation.Value;
import java.util.HashMap;

/**
 * The {@code Image} class implements the {@link ImageModel} interface and provides various image
 * transformation operations on pixel data. This class allows manipulation of RGB pixel data to
 * apply operations such as color adjustment, filtering, transformation, and combining different
 * image channels.
 *
 * <p>Operations include basic image manipulations (e.g., blur, sharpen, brighten), color channel
 * adjustments (e.g., sepia, grayscale, intensity), and more advanced effects (e.g., split &
 * transform, histogram generation, and compression). The transformed images can be stored and
 * accessed using unique keys, making it easy to apply a series of transformations in sequence.</p>
 */
public class Image implements ImageModel {

  private Pixels[][] updatedPixel;
  HashMap<String, Pixels[][]> h1 = new HashMap<>();

  /**
   * Stores the pixel data associated with a specified key.
   *
   * @param key    the key under which the pixel data will be stored
   * @param pixels the pixel data to be stored
   */
  public void storePixels(String key, Pixels[][] pixels) {
    this.updatedPixel = pixels;
    h1.put(key, pixels);
  }

  /**
   * Retrieves the pixel data stored under the specified key.
   *
   * @param key the key used to retrieve the pixel data
   * @return the pixel data associated with the key
   */
  public Pixels[][] getStoredPixels(String key) {
    return h1.get(key);
  }

  /**
   * Extracts the red color channel from the image and stores it under a specified key.
   *
   * @param key     the key used to retrieve the image
   * @param saveKey the key to store the red channel
   */
  public void getRedChannel(String key, String saveKey) {
    Split s1 = new Split();
    HashMap<String, Pixels[][]> temp = s1.apply(h1, h1.get(key), key, saveKey, "temp1", "temp2");
    Pixels[][] redChannel = temp.get(saveKey);
    h1.put(saveKey, redChannel);
  }

  /**
   * Extracts the green color channel from the image and stores it under a specified key.
   *
   * @param key     the key used to retrieve the image
   * @param saveKey the key to store the green channel
   */
  public void getGreenChannel(String key, String saveKey) {
    Split s1 = new Split();
    HashMap<String, Pixels[][]> temp = s1.apply(h1, h1.get(key), key, "temp1", saveKey, "temp2");
    Pixels[][] greenChannel = temp.get(saveKey);
    h1.put(saveKey, greenChannel);
  }

  /**
   * Extracts the blue color channel from the image and stores it under a specified key.
   *
   * @param key     the key used to retrieve the image
   * @param saveKey the key to store the blue channel
   */
  public void getBlueChannel(String key, String saveKey) {
    Split s1 = new Split();
    HashMap<String, Pixels[][]> temp = s1.apply(h1, h1.get(key), key, "temp1", "temp2", saveKey);
    Pixels[][] blueChannel = temp.get(saveKey);
    h1.put(saveKey, blueChannel);
  }


  /**
   * Applies a blur effect to the image and stores the result under a specified key.
   *
   * @param key     the key used to retrieve the image
   * @param savekey the key to store the blurred image
   */
  public void blur(String key, String savekey) {
    Blur b1 = new Blur();
    Pixels[][] temp1 = h1.get(key);
    updatedPixel = b1.apply(temp1);
    h1.put(savekey, updatedPixel);
  }

  /**
   * Brightens the image by a specified factor and stores the result under a specified key.
   *
   * @param brightenFactor the factor to brighten the image
   * @param key            the key used to retrieve the image
   * @param savekey        the key to store the brightened image
   */
  public void brighten(int brightenFactor, String key, String savekey) {
    Brighten b1 = new Brighten(brightenFactor);
    Pixels[][] temp = h1.get(key);
    updatedPixel = b1.apply(temp);
    h1.put(savekey, updatedPixel);
  }


  /**
   * Splits the image into three color channels and stores each channel under specified keys.
   *
   * @param key      the key used to retrieve the image
   * @param saveKey1 the key to store the first color channel
   * @param saveKey2 the key to store the second color channel
   * @param saveKey3 the key to store the third color channel
   */
  public void split(String key, String saveKey1, String saveKey2, String saveKey3) {
    Split s1 = new Split();
    HashMap<String, Pixels[][]> temp = s1.apply(h1, updatedPixel, key, saveKey1, saveKey2,
        saveKey3);
    h1.putAll(temp);
  }

  /**
   * Combines three color channels into a single image and stores the result under a specified key.
   *
   * @param key  the key to store the combined image
   * @param key1 the key for the first color channel
   * @param key2 the key for the second color channel
   * @param key3 the key for the third color channel
   */
  public void combine(String key, String key1, String key2, String key3) {
    Combine c1 = new Combine();
    updatedPixel = c1.apply(h1.get(key1), h1.get(key2), h1.get(key3));
    h1.put(key, updatedPixel);
  }


  /**
   * Flips the image horizontally or vertically and stores the result under a specified key.
   *
   * @param key     the key used to retrieve the image
   * @param savekey the key to store the flipped image
   * @param d       the direction of the flip (horizontal or vertical)
   */
  public void flip(String key, String savekey, Direction d) {
    Flip f1 = new Flip();
    Pixels[][] temp = h1.get(key);
    updatedPixel = f1.apply(temp, d);
    h1.put(savekey, updatedPixel);
  }

  /**
   * Converts the image to grayscale and stores the result under a specified key.
   *
   * @param key     the key used to retrieve the image
   * @param savekey the key to store the grayscale image
   */
  public void greyScale(String key, String savekey) {
    GreyScale g1 = new GreyScale();
    Pixels[][] temp = h1.get(key);
    updatedPixel = g1.apply(temp);
    h1.put(savekey, updatedPixel);
  }

  /**
   * Applies a sepia tone effect to the image and stores the result under a specified key.
   *
   * @param key     the key used to retrieve the image
   * @param savekey the key to store the sepia-toned image
   */
  public void sepia(String key, String savekey) {
    Sepia sp1 = new Sepia();
    Pixels[][] temp = h1.get(key);
    updatedPixel = sp1.apply(temp);
    h1.put(savekey, updatedPixel);
  }

  /**
   * Applies a sharpening effect to the image and stores the result under a specified key.
   *
   * @param key     the key used to retrieve the image
   * @param savekey the key to store the sharpened image
   */
  public void sharpen(String key, String savekey) {
    Sharpen sp1 = new Sharpen();
    Pixels[][] temp = h1.get(key);
    updatedPixel = sp1.apply(temp);
    h1.put(savekey, updatedPixel);
  }

  /**
   * Applies a luma transformation to the image and stores the result under a specified key.
   *
   * @param key     the key used to retrieve the image
   * @param savekey the key to store the luma-transformed image
   */
  public void luma(String key, String savekey) {
    Luma l1 = new Luma();
    Pixels[][] temp = h1.get(key);
    updatedPixel = l1.apply(temp);
    h1.put(savekey, updatedPixel);
  }

  /**
   * Applies a value transformation to the image and stores the result under a specified key.
   *
   * @param key     the key used to retrieve the image
   * @param savekey the key to store the value-transformed image
   */
  public void value(String key, String savekey) {
    Value l1 = new Value();
    Pixels[][] temp = h1.get(key);
    updatedPixel = l1.apply(temp);
    h1.put(savekey, updatedPixel);
  }

  /**
   * Adjusts the image color levels and stores the result under a specified key.
   *
   * @param key     the key used to retrieve the image
   * @param savekey the key to store the color-adjusted image
   */
  public void intensity(String key, String savekey) {
    Intensity l1 = new Intensity();
    Pixels[][] temp = h1.get(key);
    updatedPixel = l1.apply(temp);
    h1.put(savekey, updatedPixel);
  }

  /**
   * Compresses the image and stores the result under a specified key.
   *
   * @param key     the key used to retrieve the image
   * @param savekey the key to store the compressed image
   */
  public void compress(String key, String savekey, double compressionratio) {
    if (compressionratio < 0 || compressionratio > 100) {
      throw new IllegalArgumentException("Compression level must be between 0 and 100");
    }
    Compression l1 = new Compression(compressionratio);
    Pixels[][] temp = h1.get(key);
    updatedPixel = l1.apply(temp);
    h1.put(savekey, updatedPixel);
  }

  /**
   * Applies color correction to the image and stores the result under a specified key. The color
   * correction adjusts the image based on pre-defined algorithms to enhance colors.
   *
   * @param key     the key used to retrieve the image
   * @param savekey the key to store the color-corrected image
   */
  public void colorCorrection(String key, String savekey) {
    ColorCorrection c1 = new ColorCorrection();
    Pixels[][] temp = h1.get(key);
    updatedPixel = c1.apply(temp);
    h1.put(savekey, updatedPixel);
  }

  /**
   * Adjusts the color levels of the image (black, mid, white) and stores the result under a
   * specified key. This method allows fine-tuning of image brightness and contrast by adjusting the
   * levels of black, mid, and white points.
   *
   * @param black   the black point to adjust the image contrast
   * @param mid     the mid point (gamma) adjustment for brightness
   * @param white   the white point to control highlights in the image
   * @param key     the key used to retrieve the image
   * @param savekey the key to store the adjusted image
   */
  public void adjustLevel(int black, int mid, int white, String key, String savekey) {
    AdjustLevel a1 = new AdjustLevel(black, mid, white);
    Pixels[][] temp = h1.get(key);
    updatedPixel = a1.apply(temp);
    h1.put(savekey, updatedPixel);
  }


  /**
   * Splits the image vertically into two parts based on the given split value, applies a specified
   * transformation on each part, and then combines them back into a single image. The
   * transformation operation can be blur, sharpen, sepia, greyscale, color correction, or levels
   * adjustment. After the transformations, the two parts are rejoined to form the final image.
   *
   * @param key        the key used to retrieve the original image
   * @param saveKey    the key to store the final combined image after transformation
   * @param splitValue the percentage value (0-100) to determine the split location of the image
   * @param operation  the transformation to apply to the split parts (e.g., "blur", "sharpen")
   * @param params     additional parameters required for certain operations (e.g., black, mid,
   *                   white for levels adjustment)
   * @throws IllegalArgumentException if the split value is not between 0 and 100, or if levels
   *                                  adjustment parameters are invalid
   */
  public void splitAndTransform(String key, String saveKey, int splitValue, String operation,
      int... params) {
    if (splitValue < 0 || splitValue > 100) {
      throw new IllegalArgumentException("Invalid split value. It must be between 0 and 100.");
    }

    String part1Key = "splitPart1";
    String part2Key = "splitPart2";

    Pixels[][] originalPixels = h1.get(key);
    int height = originalPixels.length;
    int width = originalPixels[0].length;
    int splitIndex = (int) (width * (splitValue / 100.0));

    RGBPixel[][] part1Pixels = new RGBPixel[height][splitIndex];
    RGBPixel[][] part2Pixels = new RGBPixel[height][width - splitIndex];

    for (int i = 0; i < height; i++) {
      System.arraycopy(originalPixels[i], 0, part1Pixels[i], 0, splitIndex);
      System.arraycopy(originalPixels[i], splitIndex, part2Pixels[i], 0, width - splitIndex);
    }

    h1.put(part1Key, part1Pixels);
    h1.put(part2Key, part2Pixels);

    switch (operation) {
      case "blur":
        blur(part1Key, part1Key);
        break;
      case "sharpen":
        sharpen(part2Key, part2Key);
        break;
      case "sepia":
        sepia(part1Key, part1Key);
        break;
      case "greyscale":
        greyScale(part2Key, part2Key);
        break;
      case "color-correction":
        colorCorrection(part2Key, part2Key);
        break;
      case "levels-adjust":
        if (params.length != 3) {
          throw new IllegalArgumentException(
              "Levels-adjust requires 3 parameters: black, mid, and white points");
        }
        adjustLevel(params[0], params[1], params[2], part2Key, part2Key);
        break;
      default:
        System.out.println("Invalid operation");
    }

    RGBPixel[][] combinedPixels = new RGBPixel[height][width];

    for (int i = 0; i < height; i++) {
      System.arraycopy(h1.get(part1Key)[i], 0, combinedPixels[i], 0, splitIndex);
      System.arraycopy(h1.get(part2Key)[i], 0, combinedPixels[i], splitIndex, width - splitIndex);
    }

    h1.put(saveKey, combinedPixels);
  }

  /**
   * Generates a histogram for the image and stores the result under a specified key.
   *
   * @param key     the key used to retrieve the image
   * @param savekey the key to store the histogram
   */
  public void histogram(String key, String savekey) {
    Pixels[][] pixels = h1.get(key);
    if (pixels == null) {
      throw new IllegalArgumentException("No image found for key: " + key);
    }

    int[] redFreq = new int[256];
    int[] greenFreq = new int[256];
    int[] blueFreq = new int[256];

    if (pixels instanceof RGBPixel[][]) {
      for (Pixels[] row : pixels) {
        for (Pixels pixel : row) {
          redFreq[((RGBPixel) pixel).getRed()]++;
          greenFreq[((RGBPixel) pixel).getGreen()]++;
          blueFreq[((RGBPixel) pixel).getBlue()]++;
        }
      }
    }

    int maxFreq = 0;
    for (int i = 0; i < 256; i++) {
      maxFreq = Math.max(maxFreq, redFreq[i]);
      maxFreq = Math.max(maxFreq, greenFreq[i]);
      maxFreq = Math.max(maxFreq, blueFreq[i]);
    }

    BufferedImage histogramImage = new BufferedImage(256, 256, BufferedImage.TYPE_INT_RGB);
    Graphics2D g2d = histogramImage.createGraphics();

    g2d.setColor(Color.WHITE);
    g2d.fillRect(0, 0, 256, 256);

    drawGrid(g2d);

    drawHistogramLine(g2d, redFreq, maxFreq, Color.RED);
    drawHistogramLine(g2d, greenFreq, maxFreq, Color.GREEN);
    drawHistogramLine(g2d, blueFreq, maxFreq, Color.BLUE);

    g2d.dispose();

    RGBPixel[][] histogramPixels = new RGBPixel[256][256];
    for (int y = 0; y < 256; y++) {
      for (int x = 0; x < 256; x++) {
        int rgb = histogramImage.getRGB(x, y);
        Color color = new Color(rgb);
        histogramPixels[y][x] = new RGBPixel(color.getRed(), color.getGreen(), color.getBlue());
      }
    }

    h1.put(savekey, histogramPixels);
  }

  /**
   * Draws the grid pattern for the histogram background.
   *
   * @param g2d Graphics2D object to draw on
   */
  private void drawGrid(Graphics2D g2d) {

    g2d.setColor(new Color(220, 220, 220));
    g2d.setStroke(new BasicStroke(1.0f));

    for (int x = 0; x < 256; x += 16) {
      g2d.drawLine(x, 0, x, 255);
    }

    for (int y = 0; y < 256; y += 16) {
      g2d.drawLine(0, y, 255, y);
    }
  }

  /**
   * Draws a single histogram line for a color channel.
   *
   * @param g2d     Graphics2D object to draw on
   * @param freq    frequency array for the channel
   * @param maxFreq maximum frequency value for scaling
   * @param color   color to draw the line with
   */
  private void drawHistogramLine(Graphics2D g2d, int[] freq, int maxFreq, Color color) {
    g2d.setColor(color);
    g2d.setStroke(new BasicStroke(1.0f));

    int[] xPoints = new int[256];
    int[] yPoints = new int[256];

    for (int i = 0; i < 256; i++) {
      xPoints[i] = i;

      yPoints[i] = 255 - (int) ((freq[i] * 255.0) / maxFreq);
    }

    for (int i = 1; i < 256; i++) {
      g2d.drawLine(xPoints[i - 1], yPoints[i - 1], xPoints[i], yPoints[i]);
    }
  }

}