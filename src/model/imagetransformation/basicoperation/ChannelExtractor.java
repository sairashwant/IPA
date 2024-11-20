package model.imagetransformation.basicoperation;

import model.colorscheme.Pixels;
import model.colorscheme.RGBPixel;
import model.imagetransformation.Transformation;

/**
 * The {@code ChannelExtractor} class represents an operation that extracts a specific color channel
 * from an image. It takes a channel input and returns the corresponding channel data.
 */
public class ChannelExtractor implements Transformation {

  private final int channel; // 1 for Red, 2 for Green, 3 for Blue

  /**
   * Constructs a ChannelExtractor operation with the specified channel.
   *
   * @param channel The channel to extract (1 for red, 2 for green, 3 for blue).
   */
  public ChannelExtractor(int channel) {
    this.channel = channel;
  }

  /**
   * Applies the channel extraction operation to the specified image pixels.
   *
   * @param pixels A 2D array of {@code Pixels} representing the image to extract the channel from.
   * @return A 2D array of {@code RGBPixel} representing the extracted channel of the image.
   * @throws IllegalArgumentException if any pixel is not an instance of {@code RGBPixel}.
   */
  @Override
  public Pixels[][] apply(Pixels[][] pixels) {
    if (pixels == null) {
      throw new IllegalArgumentException("Input pixel array cannot be null.");
    }

    int height = pixels.length;
    int width = pixels[0].length;
    Pixels[][] channelPixels = new RGBPixel[height][width];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        if (!(pixels[i][j] instanceof RGBPixel)) {
          throw new IllegalArgumentException("Expected an RGBPixel.");
        }

        RGBPixel rgbPixel = (RGBPixel) pixels[i][j];

        // Extract the specified channel
        int value = 0;
        switch (channel) {
          case 1: // Red channel
            value = rgbPixel.getRed();
            break;
          case 2: // Green channel
            value = rgbPixel.getGreen();
            break;
          case 3: // Blue channel
            value = rgbPixel.getBlue();
            break;
        }

        // Create a new RGBPixel with the extracted channel value and set others to the same value
        channelPixels[i][j] = new RGBPixel(value, value, value);
      }
    }

    return channelPixels;
  }
}