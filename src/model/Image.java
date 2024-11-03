package model;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.List;
import model.colorscheme.RGBPixel;
import model.imagetransformation.AdjustLevel;
import model.imagetransformation.ColorCorrection;
import model.imagetransformation.Compression;
import model.imagetransformation.basicoperation.Luma;
import model.imagetransformation.basicoperation.Split;
import model.imagetransformation.ColorCorrection;
import model.imagetransformation.filtering.Blur;
import model.imagetransformation.basicoperation.Brighten;
import model.imagetransformation.basicoperation.Combine;
import model.imagetransformation.basicoperation.Flip;
import model.imagetransformation.basicoperation.Flip.Direction;
import model.imageformat.JPGImage;
import model.imageformat.PNGImage;
import model.imageformat.PPMImage;
import model.imagetransformation.colortransformation.GreyScale;
import model.imagetransformation.basicoperation.Intensity;
import model.imagetransformation.colortransformation.Sepia;
import model.imagetransformation.filtering.Sharpen;
import model.imagetransformation.basicoperation.Value;
import java.util.HashMap;

/**
 * Represents an image and provides methods for loading, saving, and manipulating pixel data using
 * various transformations.
 */
public class Image {

  RGBPixel[][] updatedPixel;
  public HashMap<String, RGBPixel[][]> h1 = new HashMap<String, RGBPixel[][]>();

  /**
   * Loads pixel data from an image file and stores it in the specified key.
   *
   * @param key      the key under which the pixel data will be stored
   * @param filename the name of the image file to be loaded
   * @return a 2D array of RGBPixel representing the loaded image
   * @throws IllegalArgumentException if the image format is unsupported
   */
  public RGBPixel[][] getPixels(String key, String filename) {
    String fileExtension = filename.substring(filename.lastIndexOf("."));
    if (fileExtension.equals(".png")) {
      PNGImage imageFormat = new PNGImage();
      updatedPixel = imageFormat.load(filename);
    } else if (fileExtension.equals(".jpg")) {
      JPGImage imageFormat = new JPGImage();
      updatedPixel = imageFormat.load(filename);
    } else if (fileExtension.equals(".ppm")) {
      PPMImage imageFormat = new PPMImage();
      updatedPixel = imageFormat.load(filename);
    } else {
      throw new IllegalArgumentException("Unsupported image format");
    }
    h1.put(key, updatedPixel);
    return updatedPixel;
  }

  /**
   * Saves pixel data to an image file based on the specified key.
   *
   * @param key      the key used to retrieve the pixel data to save
   * @param filename the name of the output image file
   * @throws IllegalArgumentException if the image format is unsupported
   */
  public void savePixels(String key, String filename) {
    String outputFile = filename;
    String fileExtension = filename.substring(filename.lastIndexOf("."));
    RGBPixel[][] tosavepixels = h1.get(key);
    if (fileExtension.equals(".png")) {
      PNGImage imageFormat = new PNGImage();
      if (tosavepixels == null) {
        System.out.println("Image has not been saved");
        return;
      }
      imageFormat.save(outputFile, tosavepixels);
    } else if (fileExtension.equals(".jpg")) {
      JPGImage imageFormat = new JPGImage();
      if (tosavepixels == null) {
        System.out.println("Image has not been saved");
        return;
      }
      imageFormat.save(outputFile, tosavepixels);
    } else if (fileExtension.equals(".ppm")) {
      PPMImage imageFormat = new PPMImage();
      if (tosavepixels == null) {
        System.out.println("Image has not been saved");
        return;
      }
      imageFormat.save(outputFile, updatedPixel);
    } else {
      throw new IllegalArgumentException("Unsupported image format");
    }
  }

  /**
   * Extracts the red channel from the image associated with the specified key and saves it under
   * the specified save key.
   *
   * @param key     the key used to retrieve the pixel data
   * @param saveKey the key under which the red channel will be stored
   */
  public void getRedChannel(String key, String saveKey) {
    Split s1 = new Split();
    HashMap<String, RGBPixel[][]> temp = s1.apply(h1, h1.get(key), key, saveKey, "temp1", "temp2");
    RGBPixel[][] redChannel = temp.get(saveKey);
    h1.put(saveKey, redChannel);
  }

  /**
   * Extracts the green channel from the image associated with the specified key and saves it under
   * the specified save key.
   *
   * @param key     the key used to retrieve the pixel data
   * @param saveKey the key under which the green channel will be stored
   */
  public void getGreenChannel(String key, String saveKey) {
    Split s1 = new Split();
    HashMap<String, RGBPixel[][]> temp = s1.apply(h1, h1.get(key), key, "temp1", saveKey, "temp2");
    RGBPixel[][] greenChannel = temp.get(saveKey);
    h1.put(saveKey, greenChannel);
  }

  /**
   * Extracts the blue channel from the image associated with the specified key and saves it under
   * the specified save key.
   *
   * @param key     the key used to retrieve the pixel data
   * @param saveKey the key under which the blue channel will be stored
   */
  public void getBlueChannel(String key, String saveKey) {
    Split s1 = new Split();
    HashMap<String, RGBPixel[][]> temp = s1.apply(h1, h1.get(key), key, "temp1", "temp2", saveKey);
    RGBPixel[][] blueChannel = temp.get(saveKey);
    h1.put(saveKey, blueChannel);
  }

  /**
   * Applies a blur effect to the image associated with the specified key and saves the result under
   * the specified save key.
   *
   * @param key     the key used to retrieve the pixel data
   * @param savekey the key under which the blurred image will be stored
   */
  public void blur(String key, String savekey) {
    Blur b1 = new Blur();
    updatedPixel = b1.apply(key, h1);
    h1.put(savekey, updatedPixel);
  }

  /**
   * Brightens the image associated with the specified key by a given factor and saves the result
   * under the specified save key.
   *
   * @param brightenFactor the amount to brighten the image
   * @param key            the key used to retrieve the pixel data
   * @param savekey        the key under which the brightened image will be stored
   */
  public void brighten(int brightenFactor, String key, String savekey) {
    Brighten b1 = new Brighten(brightenFactor);
    updatedPixel = b1.apply(key, h1);
    h1.put(savekey, updatedPixel);
  }

  /**
   * Splits the image associated with the specified key into three separate channels and stores them
   * under the specified save keys.
   *
   * @param key      the key used to retrieve the pixel data
   * @param saveKey1 the key under which the first split channel will be stored
   * @param saveKey2 the key under which the second split channel will be stored
   * @param saveKey3 the key under which the third split channel will be stored
   */
  public void split(String key, String saveKey1, String saveKey2, String saveKey3) {
    Split s1 = new Split();
    HashMap<String, RGBPixel[][]> temp = s1.apply(h1, updatedPixel, key, saveKey1, saveKey2,
        saveKey3);
    h1.putAll(temp);
  }

  /**
   * Combines three color channels into a single image based on the specified keys.
   *
   * @param key  the key under which the combined image will be stored
   * @param key1 the key for the first channel
   * @param key2 the key for the second channel
   * @param key3 the key for the third channel
   */
  public void combine(String key, String key1, String key2, String key3) {
    Combine c1 = new Combine();
    updatedPixel = c1.apply(h1.get(key1), h1.get(key2), h1.get(key3));
    h1.put(key, updatedPixel);
  }

  /**
   * Flips the image associated with the specified key in the given direction and saves the result
   * under the specified save key.
   *
   * @param key     the key used to retrieve the pixel data
   * @param savekey the key under which the flipped image will be stored
   * @param d       the direction in which to flip the image (horizontal or vertical)
   */
  public void flip(String key, String savekey, Direction d) {
    Flip f1 = new Flip();
    updatedPixel = f1.apply(key, h1, d);
    h1.put(savekey, updatedPixel);
  }

  /**
   * Converts the image associated with the specified key to grayscale and saves the result under
   * the specified save key.
   *
   * @param key     the key used to retrieve the pixel data
   * @param savekey the key under which the grayscale image will be stored
   */
  public void greyScale(String key, String savekey) {
    GreyScale g1 = new GreyScale();
    updatedPixel = g1.apply(key, h1);
    h1.put(savekey, updatedPixel);
  }

  /**
   * Applies a sepia effect to the image associated with the specified key and saves the result
   * under the specified save key.
   *
   * @param key     the key used to retrieve the pixel data
   * @param savekey the key under which the sepia image will be stored
   */
  public void sepia(String key, String savekey) {
    Sepia sp1 = new Sepia();
    updatedPixel = sp1.apply(key, h1);
    h1.put(savekey, updatedPixel);
  }

  /**
   * Applies a sharpening effect to the image associated with the specified key and saves the result
   * under the specified save key.
   *
   * @param key     the key used to retrieve the pixel data
   * @param savekey the key under which the sharpened image will be stored
   */
  public void sharpen(String key, String savekey) {
    Sharpen sp1 = new Sharpen();
    updatedPixel = sp1.apply(key, h1);
    h1.put(savekey, updatedPixel);
  }

  /**
   * Applies a Luma transformation to the image associated with the specified key and saves the
   * result under the specified save key.
   *
   * @param key     the key used to retrieve the pixel data
   * @param savekey the key under which the Luma image will be stored
   */
  public void luma(String key, String savekey) {
    Luma l1 = new Luma();
    updatedPixel = l1.apply(key, h1);
    h1.put(savekey, updatedPixel);
  }

  /**
   * Applies a value transformation to the image associated with the specified key and saves the
   * result under the specified save key.
   *
   * @param key     the key used to retrieve the pixel data
   * @param savekey the key under which the value image will be stored
   */
  public void value(String key, String savekey) {
    Value l1 = new Value();
    updatedPixel = l1.apply(key, h1);
    h1.put(savekey, updatedPixel);
  }

  /**
   * Applies an intensity transformation to the image associated with the specified key and saves
   * the result under the specified save key.
   *
   * @param key     the key used to retrieve the pixel data
   * @param savekey the key under which the intensity image will be stored
   */
  public void intensity(String key, String savekey) {
    Intensity l1 = new Intensity();
    updatedPixel = l1.apply(key, h1);
    h1.put(savekey, updatedPixel);
  }

  public void compress(String key, String savekey, double compressionration) {
    if(compressionration<0 || compressionration>100)
    {
      throw new IllegalArgumentException("Compression level must be between 0 and 100");
    }
    Compression l1 = new Compression(compressionration);
    updatedPixel = l1.apply(key, h1);
    h1.put(savekey, updatedPixel);
  }

  public void colorCorrection(String key, String savekey) {
    ColorCorrection c1 = new ColorCorrection();
    updatedPixel = c1.apply(key, h1);
    h1.put(savekey, updatedPixel);
  }

  public void adjustLevel(int black, int mid, int white ,String key, String savekey ) {
    AdjustLevel a1 = new AdjustLevel(black, mid, white);
    updatedPixel = a1.apply(key, h1);
    h1.put(savekey, updatedPixel);
  }


  /**
   * Splits the image vertically into two parts based on the splitValue,
   * applies transformations on each part, and combines them into a single image.
   *
   * @param key           the key to retrieve the original image pixel data
   * @param saveKey       the key to store the combined image after transformations
   * @param splitValue    the vertical split percentage for the image
   * @param operation     apply transformation on the first part if true
   */
  public void splitAndTransform(String key, String saveKey, int splitValue, String operation, int... params) {
    String part1Key = "splitPart1";
    String part2Key = "splitPart2";

    RGBPixel[][] originalPixels = h1.get(key);
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
          throw new IllegalArgumentException("Levels-adjust requires 3 parameters: black, mid, and white points");
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

  public void histogram(String key, String savekey) {
    RGBPixel[][] pixels = h1.get(key);
    if (pixels == null) {
      throw new IllegalArgumentException("No image found for key: " + key);
    }

    // Initialize frequency arrays for each channel
    int[] redFreq = new int[256];
    int[] greenFreq = new int[256];
    int[] blueFreq = new int[256];

    // Count frequency of each intensity value
    for (RGBPixel[] row : pixels) {
      for (RGBPixel pixel : row) {
        redFreq[pixel.getRed()]++;
        greenFreq[pixel.getGreen()]++;
        blueFreq[pixel.getBlue()]++;
      }
    }

    // Find the maximum frequency for scaling
    int maxFreq = 0;
    for (int i = 0; i < 256; i++) {
      maxFreq = Math.max(maxFreq, redFreq[i]);
      maxFreq = Math.max(maxFreq, greenFreq[i]);
      maxFreq = Math.max(maxFreq, blueFreq[i]);
    }

    // Create a new BufferedImage for the histogram
    BufferedImage histogramImage = new BufferedImage(256, 256, BufferedImage.TYPE_INT_RGB);
    Graphics2D g2d = histogramImage.createGraphics();

    // Fill background with white
    g2d.setColor(Color.WHITE);
    g2d.fillRect(0, 0, 256, 256);

    // Draw grid
    drawGrid(g2d);

    // Draw histogram lines
    // Using thinner lines and full opacity for better visibility
    drawHistogramLine(g2d, redFreq, maxFreq, Color.RED);
    drawHistogramLine(g2d, greenFreq, maxFreq, Color.GREEN);
    drawHistogramLine(g2d, blueFreq, maxFreq, Color.BLUE);

    g2d.dispose();

    // Convert BufferedImage to RGBPixel array
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
    // Draw light grey grid lines
    g2d.setColor(new Color(220, 220, 220));
    g2d.setStroke(new BasicStroke(1.0f));

    // Draw vertical grid lines with smaller increments (e.g., every 16 pixels)
    for (int x = 0; x < 256; x += 16) { // Change 32 to 16 for smaller boxes
      g2d.drawLine(x, 0, x, 255);
    }

    // Draw horizontal grid lines with smaller increments (e.g., every 16 pixels)
    for (int y = 0; y < 256; y += 16) { // Change 32 to 16 for smaller boxes
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
    g2d.setStroke(new BasicStroke(1.0f)); // Thinner line for better visibility

    int[] xPoints = new int[256];
    int[] yPoints = new int[256];

    // Prepare points for smoother line
    for (int i = 0; i < 256; i++) {
      xPoints[i] = i;
      // Invert Y coordinates since (0,0) is at top-left
      yPoints[i] = 255 - (int)((freq[i] * 255.0) / maxFreq);
    }

    // Draw the line connecting all points
    for (int i = 1; i < 256; i++) {
      g2d.drawLine(xPoints[i-1], yPoints[i-1], xPoints[i], yPoints[i]);
    }
  }

}


