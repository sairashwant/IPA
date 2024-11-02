package model.imagetransformation.basicoperation;

import model.colorscheme.RGBPixel;
import model.imagetransformation.Transformation;
import java.util.HashMap;
import java.util.Arrays;

public class Compression implements Transformation {
  private final double compressionRatio;

  public Compression(double compressionRatio) {
    this.compressionRatio = compressionRatio;
  }

  @Override
  public RGBPixel[][] apply(String key, HashMap<String, RGBPixel[][]> h1) {
    // Add null check for input
    if (h1 == null || !h1.containsKey(key)) {
      throw new IllegalArgumentException("Invalid input: null or missing key");
    }

    RGBPixel[][] originalImage = h1.get(key);
    if (originalImage == null || originalImage.length == 0) {
      throw new IllegalArgumentException("Invalid image data");
    }

    int height = originalImage.length;
    int width = originalImage[0].length;

    // Separate channels
    int[][] redChannel = new int[height][width];
    int[][] greenChannel = new int[height][width];
    int[][] blueChannel = new int[height][width];

    // Extract color channels
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        RGBPixel pixel = originalImage[i][j];
        redChannel[i][j] = pixel.getRed();
        greenChannel[i][j] = pixel.getGreen();
        blueChannel[i][j] = pixel.getBlue();
      }
    }

    // Apply Haar transform and compression to each channel
    redChannel = compressChannel(redChannel);
    greenChannel = compressChannel(greenChannel);
    blueChannel = compressChannel(blueChannel);

    // Reconstruct the image
    RGBPixel[][] compressedImage = new RGBPixel[height][width];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        compressedImage[i][j] = new RGBPixel(
            Math.max(0, Math.min(255, redChannel[i][j])),
            Math.max(0, Math.min(255, greenChannel[i][j])),
            Math.max(0, Math.min(255, blueChannel[i][j]))
        );
      }
    }

    return compressedImage;
  }

  private int[][] compressChannel(int[][] channel) {
    int height = channel.length;
    int width = channel[0].length;

    // Apply Haar transform
    int[][] transformed = haarTransform(channel);

    // Apply compression
    applyCompression(transformed);

    // Inverse transform
    return inverseHaarTransform(transformed);
  }

  private int[][] haarTransform(int[][] data) {
    int[][] result = new int[data.length][data[0].length];
    for (int i = 0; i < data.length; i++) {
      System.arraycopy(data[i], 0, result[i], 0, data[i].length);
    }

    // Apply transform horizontally and vertically
    for (int i = 0; i < data.length; i++) {
      haarTransform1D(result[i]);
    }

    // Transform columns
    for (int j = 0; j < data[0].length; j++) {
      int[] column = new int[data.length];
      for (int i = 0; i < data.length; i++) {
        column[i] = result[i][j];
      }
      haarTransform1D(column);
      for (int i = 0; i < data.length; i++) {
        result[i][j] = column[i];
      }
    }

    return result;
  }

  private void haarTransform1D(int[] data) {
    if (data.length <= 1) return;

    int[] temp = new int[data.length];
    int h = data.length >> 1;

    for (int i = 0; i < h; i++) {
      int k = i << 1;
      temp[i] = (data[k] + data[k + 1]) >> 1;
      temp[i + h] = data[k] - data[k + 1];
    }

    System.arraycopy(temp, 0, data, 0, data.length);
  }

  private void applyCompression(int[][] data) {
    int height = data.length;
    int width = data[0].length;
    int totalElements = height * width;
    int elementsToZero = (int)(totalElements * compressionRatio / 100.0);

    // Collect all values
    int[] allValues = new int[totalElements];
    int index = 0;
    for (int[] row : data) {
      for (int value : row) {
        allValues[index++] = Math.abs(value);
      }
    }

    // Find threshold
    Arrays.sort(allValues);
    int threshold = allValues[elementsToZero];

    // Zero out small values
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        if (Math.abs(data[i][j]) < threshold) {
          data[i][j] = 0;
        }
      }
    }
  }

  private int[][] inverseHaarTransform(int[][] data) {
    int[][] result = new int[data.length][data[0].length];
    for (int i = 0; i < data.length; i++) {
      System.arraycopy(data[i], 0, result[i], 0, data[i].length);
    }

    // Inverse transform columns
    for (int j = 0; j < data[0].length; j++) {
      int[] column = new int[data.length];
      for (int i = 0; i < data.length; i++) {
        column[i] = result[i][j];
      }
      inverseHaarTransform1D(column);
      for (int i = 0; i < data.length; i++) {
        result[i][j] = column[i];
      }
    }

    // Inverse transform rows
    for (int i = 0; i < data.length; i++) {
      inverseHaarTransform1D(result[i]);
    }

    return result;
  }

  private void inverseHaarTransform1D(int[] data) {
    if (data.length <= 1) return;

    int[] temp = new int[data.length];
    int h = data.length >> 1;

    for (int i = 0; i < h; i++) {
      int k = i << 1;
      int a = data[i];
      int b = data[i + h];
      temp[k] = a + (b >> 1);
      temp[k + 1] = a - (b >> 1);
    }

    System.arraycopy(temp, 0, data, 0, data.length);
  }
}