package controller.imageformat;

import model.colorscheme.RGBPixel;

/**
 * This interface represents all Image formats that will be processed by this app.
 */
public interface ImageFormat {

  /**
   * Loads an image and returns RGB pixel values.
   *
   * @param filename Name of file to load.
   * @return RGB Pixel values.
   */
  RGBPixel[][] load(String filename);

  /**
   * This file saves the rgb pixel as an image in the given filename.
   *
   * @param filename Name of the file to save.
   * @param pixels   Pixel values of the file to save.
   */
  void save(String filename, RGBPixel[][] pixels);

}
