package model.imagetransformation;

import model.colorscheme.Pixels;
import model.colorscheme.RGBPixel;
import java.util.Arrays;

/**
 * {@code Compression} applies a compression transformation to an image by utilizing the Haar
 * transform and a specified compression ratio. This process reduces the image data size by zeroing
 * out small coefficients after applying the Haar transform to the color channels (red, green,
 * blue).
 *
 * <p>The class performs the following steps to achieve compression:</p>
 * <ul>
 *   <li>Pad the image dimensions to the nearest power of two.</li>
 *   <li>Separate the image into its red, green, and blue color channels.</li>
 *   <li>Apply the Haar transform to each channel.</li>
 *   <li>Apply compression by zeroing out the smallest coefficients based on
 *   the compression ratio.</li>
 *   <li>Reconstruct the compressed image from the modified channels.</li>
 *   <li>Unpad the image to restore it to its original dimensions.</li>
 * </ul>
 *
 * <p>The compression ratio determines the amount of data retained after
 * compression, with larger ratios retaining more data. A smaller ratio
 * results in higher compression and more data loss.</p>
 */
public class Compression implements Transformation {

  private final double compressionRatio;

  /**
   * Constructs a {@code Compression} object with the specified compression ratio.
   *
   * <p>The compression ratio determines the percentage of the smallest coefficients
   * to zero out in each channel, thereby controlling the degree of compression. A compression ratio
   * of 100 results in no compression, while a lower ratio increases the compression level.</p>
   *
   * @param compressionRatio The desired compression ratio (0-100).
   * @throws IllegalArgumentException if the compression ratio is outside the valid range (0-100).
   */
  public Compression(double compressionRatio) {
    this.compressionRatio = compressionRatio;
  }


  /**
   * Applies the compression transformation to the input image by compressing each color channel
   * using the Haar transform and the specified compression ratio.
   *
   * <p>This method processes the image through the following steps:</p>
   * <ul>
   *   <li>Padding the image to the nearest power of two.</li>
   *   <li>Compressing the red, green, and blue channels independently.</li>
   *   <li>Reconstructing the compressed image from the compressed channels.</li>
   *   <li>Unpadding the image back to its original dimensions.</li>
   * </ul>
   *
   * @param originalImage A 2D array of {@link Pixels} representing the original image.
   * @return A 2D array of compressed {@link Pixels} representing the transformed image.
   * @throws IllegalArgumentException if the input image is null or has invalid dimensions.
   */
  @Override
  public Pixels[][] apply(Pixels[][] originalImage) {
    // Add null check for input
    if (originalImage == null || originalImage.length == 0) {
      throw new IllegalArgumentException("Invalid image data");
    }

    int originalHeight = originalImage.length;
    int originalWidth = originalImage[0].length;

    int paddedHeight = nextPowerOfTwo(originalHeight);
    int paddedWidth = nextPowerOfTwo(originalWidth);
    Pixels[][] paddedImage = padImage(originalImage, paddedHeight, paddedWidth);

    int[][] redChannel = new int[paddedHeight][paddedWidth];
    int[][] greenChannel = new int[paddedHeight][paddedWidth];
    int[][] blueChannel = new int[paddedHeight][paddedWidth];

    for (int i = 0; i < paddedHeight; i++) {
      for (int j = 0; j < paddedWidth; j++) {
        RGBPixel pixel = (RGBPixel) paddedImage[i][j]; // Cast to RGBPixel
        redChannel[i][j] = pixel.getRed();
        greenChannel[i][j] = pixel.getGreen();
        blueChannel[i][j] = pixel.getBlue();
      }
    }

    redChannel = compressChannel(redChannel);
    greenChannel = compressChannel(greenChannel);
    blueChannel = compressChannel(blueChannel);

    RGBPixel[][] compressedImage = new RGBPixel[paddedHeight][paddedWidth];
    for (int i = 0; i < paddedHeight; i++) {
      for (int j = 0; j < paddedWidth; j++) {
        compressedImage[i][j] = new RGBPixel(
            Math.max(0, Math.min(255, redChannel[i][j])),
            Math.max(0, Math.min(255, greenChannel[i][j])),
            Math.max(0, Math.min(255, blueChannel[i][j]))
        );
      }
    }

    return unpadImage(compressedImage, originalHeight, originalWidth);
  }

  /**
   * Compresses a single color channel using the Haar transform and compression.
   *
   * <p>The method applies the Haar transform to the channel data, applies
   * compression by zeroing out small coefficients, and then performs an inverse Haar transform to
   * recover the data in compressed form.</p>
   *
   * @param channel The 2D array representing a single color channel (red, green, or blue).
   * @return The compressed color channel data.
   */
  private int[][] compressChannel(int[][] channel) {

    int[][] transformed = haarTransform(channel);

    applyCompression(transformed);

    return inverseHaarTransform(transformed);
  }

  /**
   * Applies the Haar transform to the 2D data (image channel).
   *
   * <p>This method performs a 2D Haar wavelet transform, first applying it to
   * the rows and then to the columns of the data.</p>
   *
   * @param data The 2D array of data to transform (e.g., an image channel).
   * @return The transformed 2D array of data.
   */
  private int[][] haarTransform(int[][] data) {
    int[][] result = new int[data.length][data[0].length];
    for (int i = 0; i < data.length; i++) {
      System.arraycopy(data[i], 0, result[i], 0, data[i].length);
    }

    for (int i = 0; i < data.length; i++) {
      haarTransform1D(result[i]);
    }

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

  /**
   * Applies a 1D Haar transform to an array of data (row or column).
   *
   * @param data The array of data to transform.
   */
  private void haarTransform1D(int[] data) {
    if (data.length <= 1) {
      return;
    }

    int[] temp = new int[data.length];
    int h = data.length >> 1;

    for (int i = 0; i < h; i++) {
      int k = i << 1;
      temp[i] = (data[k] + data[k + 1]) >> 1;
      temp[i + h] = data[k] - data[k + 1];
    }

    System.arraycopy(temp, 0, data, 0, data.length);
  }

  /**
   * Applies compression by zeroing out small coefficients based on the compression ratio.
   *
   * @param data The 2D array of transformed data (e.g., Haar-transformed channel).
   */
  private void applyCompression(int[][] data) {
    int height = data.length;
    int width = data[0].length;
    int totalElements = height * width;
    int elementsToZero = (int) (totalElements * (compressionRatio / 100.0));

    if (elementsToZero == totalElements) {
      for (int[] datum : data) {
        Arrays.fill(datum, 0);
      }
      return;
    }

    int[] allValues = new int[totalElements];
    int index = 0;
    for (int[] row : data) {
      for (int value : row) {
        allValues[index++] = Math.abs(value);
      }
    }

    Arrays.sort(allValues);
    int threshold = allValues[elementsToZero];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        if (Math.abs(data[i][j]) < threshold) {
          data[i][j] = 0;
        }
      }
    }
  }

  /**
   * Applies the inverse Haar transform to the 2D data (image channel).
   *
   * @param data The 2D array of transformed data to invert (e.g., compressed channel).
   * @return The inversely transformed 2D array of data.
   */
  private int[][] inverseHaarTransform(int[][] data) {
    int[][] result = new int[data.length][data[0].length];
    for (int i = 0; i < data.length; i++) {
      System.arraycopy(data[i], 0, result[i], 0, data[i].length);
    }

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

    for (int i = 0; i < data.length; i++) {
      inverseHaarTransform1D(result[i]);
    }

    return result;
  }

  /**
   * Applies a 1D inverse Haar transform to an array of data (row or column).
   *
   * @param data The array of data to invert.
   */
  private void inverseHaarTransform1D(int[] data) {
    if (data.length <= 1) {
      return;
    }

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

  /**
   * Returns the next power of two greater than or equal to the specified number.
   *
   * @param n The number to round up.
   * @return The smallest power of two greater than or equal to {@code n}.
   */
  private int nextPowerOfTwo(int n) {
    int power = 1;
    while (power < n) {
      power <<= 1;
    }
    return power;
  }

  /**
   * Pads the image to the specified dimensions, filling any extra space with black pixels.
   *
   * @param image     The original image.
   * @param newHeight The desired height of the padded image.
   * @param newWidth  The desired width of the padded image.
   * @return The padded image.
   * @throws IllegalArgumentException if the original image contains non-RGB pixels.
   */
  private Pixels[][] padImage(Pixels[][] image, int newHeight, int newWidth) {
    int originalHeight = image.length;
    int originalWidth = image[0].length;
    Pixels[][] paddedImage = new Pixels[newHeight][newWidth];

    for (int i = 0; i < newHeight; i++) {
      for (int j = 0; j < newWidth; j++) {
        if (i < originalHeight && j < originalWidth) {

          if (!(image[i][j] instanceof RGBPixel)) {
            throw new IllegalArgumentException("Expected an instance of RGBPixel.");
          }
          paddedImage[i][j] = image[i][j];
        } else {

          paddedImage[i][j] = new RGBPixel(0, 0, 0);
        }
      }
    }

    return paddedImage;
  }

  /**
   * Unpads the image to its original dimensions.
   *
   * @param image          The padded image.
   * @param originalHeight The original height of the image before padding.
   * @param originalWidth  The original width of the image before padding.
   * @return The unpadded image.
   */
  private Pixels[][] unpadImage(Pixels[][] image, int originalHeight, int originalWidth) {
    Pixels[][] unpaddedImage = new Pixels[originalHeight][originalWidth];
    for (int i = 0; i < originalHeight; i++) {
      System.arraycopy(image[i], 0, unpaddedImage[i], 0, originalWidth);
    }
    return unpaddedImage;
  }
}
