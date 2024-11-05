package model.imagetransformation;

import model.colorscheme.RGBPixel;
import java.util.HashMap;

/**
 * Performs color correction by aligning histogram peaks of RGB channels.
 * Ignores peaks near the extremes (values < 10 or > 245) to avoid being misled
 * by dark or blown-out regions.
 */
public class ColorCorrection implements Transformation {
  private static final int MIN_MEANINGFUL_VALUE = 10;
  private static final int MAX_MEANINGFUL_VALUE = 245;

  /**
   * Finds the peak value in a histogram array within meaningful range.
   * @param histogram frequency array for a color channel
   * @return the value (0-255) where the peak occurs
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
   * Extracts histogram data for a single channel from pixel array.
   * @param pixels the image pixels
   * @param channel 0 for red, 1 for green, 2 for blue
   * @return histogram array for the specified channel
   */
  private int[] getChannelHistogram(RGBPixel[][] pixels, int channel) {
    int[] histogram = new int[256];
    for (RGBPixel[] row : pixels) {
      for (RGBPixel pixel : row) {
        int value;
        switch (channel) {
          case 0:
            value = pixel.getRed();
            break;
          case 1:
            value = pixel.getGreen();
            break;
          case 2:
            value = pixel.getBlue();
            break;
          default:
            throw new IllegalArgumentException("Invalid channel");
        }
        histogram[value]++;
      }
    }
    return histogram;
  }

  @Override
  public RGBPixel[][] apply(RGBPixel[][] pixels) {
    if (pixels == null) {
      throw new IllegalArgumentException("No image found");
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

    // Apply corrections to create new image
    int height = pixels.length;
    int width = pixels[0].length;
    RGBPixel[][] correctedPixels = new RGBPixel[height][width];

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        RGBPixel original = pixels[y][x];

        // Apply offsets and clamp values between 0 and 255
        int newRed = Math.min(255, Math.max(0, original.getRed() + redOffset));
        int newGreen = Math.min(255, Math.max(0, original.getGreen() + greenOffset));
        int newBlue = Math.min(255, Math.max(0, original.getBlue() + blueOffset));

        correctedPixels[y][x] = new RGBPixel(newRed, newGreen, newBlue);
      }
    }

    return correctedPixels;
  }
}