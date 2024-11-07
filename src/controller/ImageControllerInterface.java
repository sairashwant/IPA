package controller;

import java.util.Map;
import java.util.function.Consumer;

/**
 * Interface for image processing controllers that handle user commands and manage image
 * transformations. It provides methods to load, save, transform, and manipulate images, as well as
 * to manage command execution from a user input loop.
 */
public interface ImageControllerInterface {

  /**
   * Retrieves the map of available image processing commands. Each command name is mapped to its
   * corresponding handler, which processes specific command-line arguments to execute
   * transformations or operations on images.
   *
   * @return a Map with command names as keys and command handlers as values.
   */
  Map<String, Consumer<String[]>> getCommandMap();

  /**
   * Starts the controller and runs the main command-processing loop. Continuously handles user
   * input, parses commands, and executes the corresponding image processing operations until an
   * exit command is received.
   */
  void run();

  /**
   * Loads an image from the specified file and stores it using the provided key.
   *
   * @param args an array containing the filename and storage key in the format: load <filename>
   *             <key>
   */
  void handleLoad(String[] args);

  /**
   * Saves an image to the specified file using the provided key to locate it in storage.
   *
   * @param args an array containing the filename and key in the format: save <filename> <key>
   */
  void handleSave(String[] args);

  /**
   * Adjusts the brightness of an image by a specified factor, storing the resulting image under a
   * new key.
   *
   * @param args an array containing the brightness factor, source key, and destination key in the
   *             format: brighten <factor> <srcKey> <destKey>
   */
  void handleBrighten(String[] args);

  /**
   * Splits an image into its red, green, and blue channels, storing each channel under a unique
   * key.
   *
   * @param args an array containing the source key and separate destination keys for red, green,
   *             and blue channels in the format: rgb-split <srcKey> <redKey> <greenKey> <blueKey>
   */
  void handleRGBSplit(String[] args);

  /**
   * Combines individual red, green, and blue channel images into a single image and stores it under
   * the specified destination key.
   *
   * @param args an array containing the destination key and source keys for red, green, and blue
   *             channels in the format: rgb-combine <destKey> <redKey> <greenKey> <blueKey>
   */
  void handleCombine(String[] args);


  /**
   * Compresses an image to the specified compression ratio, saving the result with a new key.
   *
   * @param args an array containing the compression ratio, source key, and destination key in the
   *             format: compress <ratio> <srcKey> <destKey>
   */
  void handleCompression(String[] args);

  /**
   * Adjusts the levels (black, mid, and white points) of an image to enhance contrast or
   * brightness, storing the adjusted image under a new key.
   *
   * @param args an array containing black, mid, and white points, source key, and destination key
   *             in the format: levels-adjust <black> <mid> <white> <srcKey> <destKey>
   */
  void handleLevelsAdjust(String[] args);


  /**
   * Splits an image based on a specified percentage and applies a transformation operation on each
   * part, saving the result under a specified destination key.
   *
   * @param args an array containing the operation, source key, destination key, and split
   *             percentage in the format:
   *             <operation> <srcKey> <destKey> split <splitPercentage>
   */
  void handleSplit(String[] args);

  /**
   * Executes a script file containing multiple image processing commands in sequence.
   *
   * @param args an array containing the script filename in the format: run-script <filename>
   */
  void handleScript(String[] args);

  /**
   * Applies a specified image transformation operation based on provided arguments. This is a
   * general-purpose method that can interpret various image processing commands and apply the
   * corresponding operation stored in the operations map.
   *
   * @param args an array of command arguments specifying the operation, source key, and destination
   *             key.
   */
  void applyOperation(String[] args);

  /**
   * Displays a menu of available commands for image processing, listing each command and its usage
   * format for easy reference by users.
   */
  void printMenu();
}