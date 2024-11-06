package controller;

import model.colorscheme.Pixels;
import controller.imageformat.JPGImage;
import controller.imageformat.PNGImage;
import controller.imageformat.PPMImage;

public class ImageUtil {

  /**
   * Loads an image file and returns a 2D array of Pixels.
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
   * Saves a 2D array of Pixels to an image file.
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