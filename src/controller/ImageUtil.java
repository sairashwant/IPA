package controller;

import controller.imageformat.JPGImage;
import controller.imageformat.PNGImage;
import controller.imageformat.PPMImage;
import model.colorscheme.Pixels;

/**
 * Utility class for loading and saving image files in various formats. The class supports PNG, JPG,
 * and PPM image formats and provides methods to load and save images as 2D arrays of Pixels.
 */
public class ImageUtil {

  /**
   * Loads an image from the specified file and converts it into a 2D array of Pixels. The method
   * detects the image format based on the file extension and delegates the loading process to the
   * corresponding image format handler.
   *
   * @param filename the path to the image file to be loaded.
   * @return a 2D array of Pixels representing the image.
   * @throws IllegalArgumentException if the image format is unsupported or if there is an error
   *                                  during loading.
   */
  public static Pixels[][] loadImage(String filename) {
    String fileExtension = filename.substring(filename.lastIndexOf("."));
    Pixels[][] pixels = null;

    try {
      switch (fileExtension.toLowerCase()) {
        case ".png":
          PNGImage pngFormat = new PNGImage();
          pixels = pngFormat.load(filename);
          break;
        case ".jpg":
          JPGImage jpgFormat = new JPGImage();
          pixels = jpgFormat.load(filename);
          break;
        case ".ppm":
          PPMImage ppmFormat = new PPMImage();
          pixels = ppmFormat.load(filename);
          break;
        default:
          throw new IllegalArgumentException("Unsupported image format");
      }
    } catch (Exception e) {
      throw new IllegalArgumentException("Error loading image: " + e.getMessage());
    }

    return pixels;
  }

  /**
   * Saves a 2D array of Pixels to an image file with the specified filename. The method detects the
   * image format based on the file extension and delegates the saving process to the corresponding
   * image format handler.
   *
   * @param filename the path where the image will be saved.
   * @param pixels   a 2D array of Pixels representing the image to be saved.
   * @throws IllegalArgumentException if the image format is unsupported or if there is an error
   *                                  during saving.
   */
  public static void saveImage(String filename, Pixels[][] pixels) {
    String fileExtension = filename.substring(filename.lastIndexOf("."));

    try {
      switch (fileExtension.toLowerCase()) {
        case ".png":
          PNGImage pngFormat = new PNGImage();
          pngFormat.save(filename, pixels);
          break;
        case ".jpg":
          JPGImage jpgFormat = new JPGImage();
          jpgFormat.save(filename, pixels);
          break;
        case ".ppm":
          PPMImage ppmFormat = new PPMImage();
          ppmFormat.save(filename, pixels);
          break;
        default:
          throw new IllegalArgumentException("Unsupported image format");
      }
    } catch (Exception e) {
      throw new IllegalArgumentException("Error saving image: " + e.getMessage());
    }
  }
}