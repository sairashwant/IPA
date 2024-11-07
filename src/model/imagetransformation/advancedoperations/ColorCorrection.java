package model.imagetransformation.advancedoperations;

import model.colorscheme.Pixels;
import model.colorscheme.RGBPixel;
import model.imagetransformation.Transformation;

/**
 * {@code ColorCorrection} implements a color correction transformation on an image. It adjusts the
 * color balance of the image by analyzing the histograms of the red, green, and blue channels,
 * finding the peak values for each channel, and adjusting the color values to match a target peak.
 * This process helps to balance the image's colors based on the distribution of pixel values.
 *
 * <p>The correction is achieved by shifting the peaks of each channel towards
 * a target peak, which is the average of the peak values from the red, green, and blue channels.
 * The resulting image has adjusted color balance based on the histogram data.</p>
 */
public class ColorCorrection implements Transformation {

  private static final int MIN_MEANINGFUL_VALUE = 10;
  private static final int MAX_MEANINGFUL_VALUE = 245;


  /**
   * Finds the peak value in a histogram array within a meaningful range.
   *
   * <p>This method identifies the most frequent color intensity (peak) in the histogram
   * by considering values in the range [10, 245] to avoid extreme outliers. The peak represents the
   * most common color value for a given channel.</p>
   *
   * @param histogram Frequency array for a color channel (0-255).
   * @return The value (0-255) where the peak occurs in the histogram.
   */
  private int findPeak(int[] histogram) {
    int maxFreq = 0;
    int peakValue = 0;

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
   * <p>This method generates a histogram representing the frequency of pixel values (0-255)
   * for a specified color channel (red, green, or blue) within the image's pixel data.</p>
   *
   * @param pixels  The 2D array of {@link Pixels} representing the image.
   * @param channel The color channel to analyze (0 for red, 1 for green, 2 for blue).
   * @return Histogram array of size 256 representing the frequency of pixel values for the
   * specified channel.
   * @throws IllegalArgumentException if the pixel data is null or if any pixel is not an instance
   *                                  of {@link RGBPixel}.
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


  /**
   * Applies the color correction transformation to the given image pixels.
   *
   * <p>The method calculates the peak value for each color channel (red, green, blue),
   * computes a target peak as the average of these peaks, and adjusts the pixel values accordingly.
   * Each channel is adjusted by the difference between the target peak and the peak for that
   * channel. The corrected pixel values are then clamped to the valid range [0, 255] to ensure
   * valid RGB values.</p>
   *
   * @param pixels The 2D array of {@link Pixels} representing the image to be corrected.
   * @return A 2D array of corrected {@link RGBPixel} objects representing the adjusted image
   * pixels.
   * @throws IllegalArgumentException if the pixel data is null or if any pixel is not an instance
   *                                  of {@link RGBPixel}.
   */
  @Override
  public Pixels[][] apply(Pixels[][] pixels) {
    if (pixels == null) {
      throw new IllegalArgumentException("Input pixel array cannot be null.");
    }

    int[] redHistogram = getChannelHistogram(pixels, 0);
    int[] greenHistogram = getChannelHistogram(pixels, 1);
    int[] blueHistogram = getChannelHistogram(pixels, 2);

    int redPeak = findPeak(redHistogram);
    int greenPeak = findPeak(greenHistogram);
    int bluePeak = findPeak(blueHistogram);

    int targetPeak = (redPeak + greenPeak + bluePeak) / 3;

    int redOffset = targetPeak - redPeak;
    int greenOffset = targetPeak - greenPeak;
    int blueOffset = targetPeak - bluePeak;

    int height = pixels.length;
    int width = pixels[0].length;
    Pixels[][] correctedPixels = new Pixels[height][width];

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        Pixels original = pixels[y][x];

        if (!(original instanceof RGBPixel)) {
          throw new IllegalArgumentException("Expected an instance of RGBPixel.");
        }

        RGBPixel rgbPixel = (RGBPixel) original;

        int newRed = Math.min(255, Math.max(0, rgbPixel.getRed() + redOffset));
        int newGreen = Math.min(255, Math.max(0, rgbPixel.getGreen() + greenOffset));
        int newBlue = Math.min(255, Math.max(0, rgbPixel.getBlue() + blueOffset));

        correctedPixels[y][x] = new RGBPixel(newRed, newGreen, newBlue);
      }
    }

    return correctedPixels;
  }
}
