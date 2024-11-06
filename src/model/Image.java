package model;

import model.colorscheme.Pixels;
import model.colorscheme.RGBPixel;
import model.imagetransformation.*;
import model.imagetransformation.basicoperation.*;
import model.imagetransformation.filtering.*;
import model.imageformat.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

/**
 * Represents an image and provides methods for loading, saving, and manipulating pixel data using
 * various transformations.
 */
public class Image {  // Implementing the Pixels interface

  private Pixels[][] updatedPixel;
  private HashMap<String, Pixels[][]> h1 = new HashMap<>();

  // Constructor and other methods...

  /**
   * Loads pixel data from an image file and stores it in the specified key.
   *
   * @param key      the key under which the pixel data will be stored
   * @param filename the name of the image file to be loaded
   * @return a 2D array of RGBPixel representing the loaded image
   * @throws IllegalArgumentException if the image format is unsupported
   */
  public Pixels[][] getPixels(String key, String filename) {
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
    RGBPixel[][] tosavepixels = (RGBPixel[][]) h1.get(key);  // Casting to RGBPixel for save operation
    if (tosavepixels == null) {
      System.out.println("Image has not been saved");
      return;
    }
    if (fileExtension.equals(".png")) {
      PNGImage imageFormat = new PNGImage();
      imageFormat.save(outputFile, tosavepixels);
    } else if (fileExtension.equals(".jpg")) {
      JPGImage imageFormat = new JPGImage();
      imageFormat.save(outputFile, tosavepixels);
    } else if (fileExtension.equals(".ppm")) {
      PPMImage imageFormat = new PPMImage();
      imageFormat.save(outputFile, tosavepixels);
    } else {
      throw new IllegalArgumentException("Unsupported image format");
    }
  }

  /**
   * Returns the pixel data of the image.
   * This method provides access to the 2D array of pixels in the image.
   *
   * @return a 2D array of Pixels
   */
  public Pixels[][] getPixelData() {
    return updatedPixel;
  }

  // Methods for manipulating pixels...
  public void split(String key, String saveKey1, String saveKey2, String saveKey3) {
    Split s1 = new Split();
    HashMap<String, Pixels[][]> temp = s1.apply(updatedPixel, key, saveKey1, saveKey2, saveKey3);
    h1.putAll(temp);
  }

  // Add other transformations and operations like brighten, blur, combine, etc. similarly
  // Implement the specific methods for manipulating Pixels[][] data

  // Example: Apply a transformation and return the updated pixel data.
  public void brighten(int brightenFactor, String key, String savekey) {
    Brighten b1 = new Brighten(brightenFactor);
    RGBPixel[][] temp = (RGBPixel[][]) h1.get(key);
    updatedPixel = b1.apply(temp);
    h1.put(savekey, updatedPixel);
  }

  // Add other transformation methods similarly (e.g., blur, sharpen, sepia, etc.)
  // These methods will manipulate and save pixel data within h1 HashMap using the corresponding keys.

  /**
   * Applies a blur effect to the image associated with the specified key and saves the result under
   * the specified save key.
   *
   * @param key     the key used to retrieve the pixel data
   * @param savekey the key under which the blurred image will be stored
   */
  public void blur(String key, String savekey) {
    Blur b1 = new Blur();
    RGBPixel[][] temp1 = (RGBPixel[][]) h1.get(key);
    updatedPixel = b1.apply(temp1);
    h1.put(savekey, updatedPixel);
  }

  // More methods for various transformations like luma, sepia, intensity, adjust level, etc.

  // Example of histogram generation
  public void histogram(String key, String savekey) {
    RGBPixel[][] pixels = (RGBPixel[][]) h1.get(key);
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

    // Draw grid and histogram lines
    drawGrid(g2d);
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

  // Helper methods for drawing the grid and lines for the histogram (these are internal utility methods)
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

  private void drawHistogramLine(Graphics2D g2d, int[] freq, int maxFreq, Color color) {
    g2d.setColor(color);
    for (int x = 0; x < 256; x++) {
      int barHeight = (int) ((freq[x] / (double) maxFreq) * 256);
      g2d.drawLine(x, 255 - barHeight, x, 255);
    }
  }
}
