package controller.imageformat;

import model.colorscheme.Pixels;

/**
 * The ImageFormat interface defines the structure for processing different image formats within the
 * application. Implementing classes should provide methods to load an image as RGB pixel data and
 * to save RGB pixel data back to an image file.
 */
public interface ImageFormat {

  /**
   * Loads an image file and converts it into a 2D array of RGB pixel values.
   *
   * @param filename The name or path of the image file to load.
   * @return A 2D array of {@link Pixels} representing the RGB values of each pixel in the image.
   */
  Pixels[][] load(String filename);

  /**
   * Saves a 2D array of RGB pixel data to an image file.
   *
   * @param filename The name or path of the file where the image will be saved.
   * @param pixels   A 2D array of {@link Pixels} representing the RGB values of each pixel to be
   *                 saved in the image file.
   */
  void save(String filename, Pixels[][] pixels);

}
