package model.imagetransformation;

import model.colorscheme.Pixels;
import model.colorscheme.RGBPixel;

public class ColorCorrection implements Transformation {
  private static final int MIN_MEANINGFUL_VALUE = 10;
  private static final int MAX_MEANINGFUL_VALUE = 245;

  /**
   * Finds the peak value in a histogram array within a meaningful range.
   *
   * @param histogram Frequency array for a color channel
   * @return The value (0-255) where the peak occurs
   */
  private int findPeak(int[] histogram) {
    int maxFreq = 0;
    int peakValue = 0;

    // Only consider values between MIN_MEANINGFUL_VALUE and MAX_MEANINGFUL_VALUE
    for (int i = MIN_MEANINGFUL_VALUE; i <= MAX_MEANINGFUL_VALUE; i++) {
      if (histogram[i] > maxFreq) {
        maxFreq = histogram[i];
        peakValue = i;
      }
    }
    return peakValue;
  }

  /**
   * Extracts histogram data for a single channel from the pixel array.
   *
   * @param pixels  The image pixels
   * @param channel 0 for red, 1 for green, 2 for blue
   * @return Histogram array for the specified channel
   */
  private int[] getChannelHistogram(Pixels[][] pixels, int channel) {
    int[] histogram = new int[256];
    for (Pixels[] row : pixels) {
      for (Pixels pixel : row) {
        if (!(pixel instanceof RGBPixel)) {
          throw new IllegalArgumentException("Expected an instance of RGBPixel.");
        }
        RGBPixel rgbPixel = (RGBPixel) pixel;
        int value;
        switch (channel) {
          case 0:
            value = rgbPixel.getRed();
            break;
          case 1:
            value = rgbPixel.getGreen();
            break;
          case 2:
            value = rgbPixel.getBlue();
            break;
          default:
            throw new IllegalArgumentException("Invalid channel: must be 0, 1, or 2.");
        }
        histogram[value]++;
      }
    }
    return histogram;
  }

  @Override
  public Pixels[][] apply(Pixels[][] pixels) {
    if (pixels == null) {
      throw new IllegalArgumentException("Input pixel array cannot be null.");
    }

    // Get histograms for each channel
    int[] redHistogram = getChannelHistogram(pixels, 0);
    int[] greenHistogram = getChannelHistogram(pixels, 1);
    int[] blueHistogram = getChannelHistogram(pixels, 2);

    // Find peaks for each channel
    int redPeak = findPeak(redHistogram);
    int greenPeak = findPeak(greenHistogram);
    int bluePeak = findPeak(blueHistogram);

    // Calculate target peak position (average of all peaks)
    int targetPeak = (redPeak + greenPeak + bluePeak) / 3;

    // Calculate offsets for each channel
    int redOffset = targetPeak - redPeak;
    int greenOffset = targetPeak - greenPeak;
    int blueOffset = targetPeak - bluePeak;

    // Apply corrections to create a new image
    int height = pixels.length;
    int width = pixels[0].length;
    Pixels[][] correctedPixels = new Pixels[height][width];

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        Pixels original = pixels[y][x];

        // Ensure original is an RGBPixel
        if (!(original instanceof RGBPixel)) {
          throw new IllegalArgumentException("Expected an instance of RGBPixel.");
        }

        RGBPixel rgbPixel = (RGBPixel) original;

        // Apply offsets and clamp values between 0 and 255
        int newRed = Math.min(255, Math.max(0, rgbPixel.getRed() + redOffset));
        int newGreen = Math.min(255, Math.max(0, rgbPixel.getGreen() + greenOffset));
        int newBlue = Math.min(255, Math.max(0, rgbPixel.getBlue() + blueOffset));

        // Create a new RGBPixel with corrected values
        correctedPixels[y][x] = new RGBPixel(newRed, newGreen, newBlue);
      }
    }

    return correctedPixels;
  }
}
