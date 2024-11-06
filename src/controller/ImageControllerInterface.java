package controller;

import java.util.Map;
import java.util.function.Consumer;

/**
 * Interface for image processing controllers that handle user commands and manage
 * image transformations.
 */
public interface ImageControllerInterface {

  /**
   * Gets the command map containing all available image processing commands.
   *
   * @return a Map with command names as keys and command handlers as values
   */
  Map<String, Consumer<String[]>> getCommandMap();

  /**
   * Starts the controller and runs the main processing loop.
   * Handles user input and executes corresponding commands.
   */
  void run();

  /**
   * Handles the loading of an image file.
   *
   * @param args array containing command arguments (filename and key)
   */
  void handleLoad(String[] args);

  /**
   * Handles the saving of an image file.
   *
   * @param args array containing command arguments (filename and key)
   */
  void handleSave(String[] args);

  /**
   * Handles the brightening of an image.
   *
   * @param args array containing command arguments (factor, source key, destination key)
   */
  void handleBrighten(String[] args);

  /**
   * Handles RGB splitting of an image into separate channels.
   *
   * @param args array containing command arguments (source key and destination keys for each channel)
   */
  void handleRGBSplit(String[] args);

  /**
   * Handles combining RGB channels into a single image.
   *
   * @param args array containing command arguments (destination key and source keys for each channel)
   */
  void handleCombine(String[] args);

  /**
   * Handles image compression.
   *
   * @param args array containing command arguments (compression ratio, source key, destination key)
   */
  void handleCompression(String[] args);

  /**
   * Handles level adjustment of an image.
   *
   * @param args array containing command arguments (black point, mid point, white point, source key, destination key)
   */
  void handleLevelsAdjust(String[] args);

  /**
   * Handles splitting and transforming parts of an image.
   *
   * @param args array containing command arguments (source key, destination key, operation, split percentage)
   */
  void handleSplit(String[] args);

  /**
   * Handles the execution of a script file containing multiple commands.
   *
   * @param args array containing command arguments (script filename)
   */
  void handleScript(String[] args);

  void applyOperation(String[] args);

  void printMenu();
}