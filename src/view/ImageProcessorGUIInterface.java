package view;

import java.awt.image.BufferedImage;

/**
 * Interface representing the GUI functionality for an image processor application. This interface
 * defines methods for displaying images, showing errors, and interacting with the application's
 * main window.
 */
public interface ImageProcessorGUIInterface {

  /**
   * Displays an error message to the user.
   *
   * @param error The error message to be displayed.
   */
  void showError(String error);

  /**
   * Displays an image in the GUI.
   *
   * @param image The {@link BufferedImage} to be displayed.
   */
  void displayImage(BufferedImage image);

  /**
   * Displays a histogram of the image in the GUI.
   *
   * @param histogram The {@link BufferedImage} representing the histogram to be displayed.
   */
  void displayHistogram(BufferedImage histogram);

  /**
   * Adds a window listener to the GUI to handle window events such as closing.
   */
  void addWindowListenerToGUI();

  /**
   * Displays a preview of the image in the GUI for a specific operation.
   *
   * @param image     The {@link BufferedImage} representing the image preview.
   * @param operation The name of the operation associated with the preview (e.g., "blur",
   *                  "sharpen").
   */
  void showPreview(BufferedImage image, String operation);
}
