package controller;

import javax.swing.JFileChooser;
import view.ImageProcessorGUI;

/**
 * Interface that defines the methods for controlling the image processing GUI. This interface
 * extends {@link ImageControllerInterface} and adds GUI-specific methods for handling image
 * display, loading, saving, and applying image processing operations. It provides functionalities
 * for interacting with the image processor from the GUI perspective.
 */
public interface ImageGUIControllerInterface extends ImageControllerInterface {

  /**
   * Displays the original image in the GUI. This method is typically called when the user wants to
   * view the unmodified version of the image.
   */
  void handleShowOriginalImage();

  /**
   * Undoes the last image processing operation. This method is typically used to revert changes
   * made to the image.
   */
  void handleUndo();

  /**
   * Loads an image from a specified file and key into the GUI. The method interacts with the GUI to
   * display the loaded image.
   *
   * @param gui      the instance of {@link ImageProcessorGUI} where the image will be loaded.
   * @param key      the key used to identify the loaded image.
   * @param filename the path to the image file to be loaded.
   */
  void handleLoad(ImageProcessorGUI gui, String key, String filename);

  /**
   * Saves the current image to a file based on user selection. Allows saving to different file
   * formats such as PNG, JPG, or PPM.
   *
   * @param userSelection the file selection from the user.
   * @param fileChooser   the file chooser dialog used to select the save location.
   * @param pngFilter     the file filter for PNG format.
   * @param jpgFilter     the file filter for JPG format.
   * @param ppmFilter     the file filter for PPM format.
   */
  void handleSave(int userSelection, JFileChooser fileChooser,
      javax.swing.filechooser.FileFilter pngFilter,
      javax.swing.filechooser.FileFilter jpgFilter,
      javax.swing.filechooser.FileFilter ppmFilter);

  /**
   * Retrieves the key of the most recently loaded or modified image. This key is used to refer to
   * the current image in the processing operations.
   *
   * @return the key of the latest image.
   */
  String getLatestKey();

  /**
   * Applies a histogram operation to the current image. The histogram operation is applied based on
   * the provided arguments.
   *
   * @param args the arguments to configure the histogram operation.
   */
  void applyHistogram(String[] args);

  /**
   * Displays an image in the GUI using the specified image key.
   *
   * @param gui the instance of {@link ImageProcessorGUI} where the image will be displayed.
   * @param key the key that identifies the image to be displayed.
   */
  void displayImageByKey(ImageProcessorGUI gui, String key);
}
