package View;

import Controller.ImageController;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Consumer;

/**
 * Represents the user interface for the image processing application,
 * handling user commands and interacting with the ImageController.
 */
public class ImageView {

  private ImageController controller;
  private Scanner scanner;
  private final Map<String, Consumer<String[]>> commandMap;

  /**
   * Initializes the ImageView with a given ImageController and sets up
   * the command mapping for user interactions.
   *
   * @param controller the ImageController to manage image operations
   */
  public ImageView(ImageController controller) {
    this.controller = controller;
    this.scanner = new Scanner(System.in);
    this.commandMap = new HashMap<>();

    commandMap.put("load", this::handleLoad);
    commandMap.put("save", this::handleSave);
    commandMap.put("brighten", this::handleBrighten);
    commandMap.put("rgb-combine", this::handleCombine);
    commandMap.put("rgb-split", this::handleSplit);
    commandMap.put("blur", this::handleOperation);
    commandMap.put("sharpen", this::handleOperation);
    commandMap.put("horizontal-flip", this::handleOperation);
    commandMap.put("vertical-flip", this::handleOperation);
    commandMap.put("greyscale", this::handleOperation);
    commandMap.put("value-component", this::handleOperation);
    commandMap.put("luma-component", this::handleOperation);
    commandMap.put("intensity-component", this::handleOperation);
    commandMap.put("sepia", this::handleOperation);
    commandMap.put("red-component", this::handleOperation);
    commandMap.put("green-component", this::handleOperation);
    commandMap.put("blue-component", this::handleOperation);
    commandMap.put("exit", args -> System.exit(0));
    commandMap.put("run-script", this::handleScript);
  }

  /**
   * Starts the command processing loop, prompting the user for input
   * and executing corresponding commands until the program is exited.
   */
  void run() {
    boolean running = true;
    while (running) {
      try {
        System.out.print("\nEnter command: ");
        String input = scanner.nextLine().trim();

        if (input.isEmpty()) {
          continue;
        }

        String[] parts = input.split("\\s+");
        String command = parts[0].toLowerCase();

        if (commandMap.containsKey(command)) {
          commandMap.get(command).accept(parts);
          if (command.equals("exit")) {
            running = false;
          }
        } else {
          System.out.println("Unknown command: " + command);
        }

      } catch (Exception e) {
        System.out.println("Error processing command: " + e.getMessage());
      }
    }
  }

  /**
   * Displays the available commands in the image processing menu.
   */
  void printMenu() {
    System.out.println("\n========== Image Processing Menu ==========");
    System.out.println("1. Load Image");
    System.out.println("2. Save Image");
    System.out.println("\n---- Image Transformations ----");
    System.out.println("3. Blur");
    System.out.println("4. Sharpen");
    System.out.println("5. Horizontal-Flip");
    System.out.println("6. Vertical-Flip");
    System.out.println("\n---- Color Adjustments ----");
    System.out.println("7. Greyscale");
    System.out.println("8. Sepia");
    System.out.println("9. Brighten");
    System.out.println("\n---- RGB Operations ----");
    System.out.println("10. RGB-Split");
    System.out.println("11. RGB-Combine");
    System.out.println("\n---- Component Extraction ----");
    System.out.println("12. Value-Component");
    System.out.println("13. Luma-Component");
    System.out.println("14. Intensity-Component");
    System.out.println("15. Red-Component");
    System.out.println("16. Green-Component");
    System.out.println("17. Blue-Component");
    System.out.println("\n---- Additional Operations ----");
    System.out.println("18. Run-Script");
    System.out.println("19. Exit Program");
    System.out.println("============================================");
  }

  /**
   * Handles the loading of an image using the provided filename and key.
   *
   * @param args an array containing the command arguments; expects
   *             <code>load <filename> <key></code>
   */
  private void handleLoad(String[] args) {
    if (args.length == 3) {
      System.out.println("Loaded Image " + args[2]);
      controller.loadIMage(args[2], args[1]);
    } else {
      System.out.println("Invalid load command. Usage: load <filename> <key>");
    }
  }

  /**
   * Handles the saving of an image using the provided filename and key.
   *
   * @param args an array containing the command arguments; expects
   *             <code>save <filename> <key></code>
   */
  private void handleSave(String[] args) {
    if (args.length == 3) {
      System.out.println("Saved Image " + args[2]);
      controller.saveImage(args[2], args[1]);
    } else {
      System.out.println("Invalid save command. Usage: save <filename> <key>");
    }
  }

  /**
   * Handles operations on images based on the provided command.
   *
   * @param args an array containing the command arguments; expects
   *             <code>operation <srcKey> <destKey></code>
   */
  private void handleOperation(String[] args) {
    if (args.length == 3) {
      System.out.println("Operation on " + args[1]);
      controller.applyOperations(args[0], args[1], args[2]);
    } else {
      System.out.println("Invalid command. Usage: " + args[0] + " <srcKey> <destKey>");
    }
  }

  /**
   * Handles the brighten operation by parsing the factor and invoking
   * the controller method.
   *
   * @param args an array containing the command arguments; expects
   *             <code>brighten <factor> <srcKey> <destKey></code>
   */
  private void handleBrighten(String[] args) {
    if (args.length == 4) {
      try {
        System.out.println("Brightened Image " + args[2] + " by " + args[1]);
        int factor = Integer.parseInt(args[1]);
        controller.brighten(factor, args[2], args[3]);
      } catch (NumberFormatException e) {
        System.out.println("Invalid brighten factor. Please enter an integer.");
      }
    } else {
      System.out.println("Invalid brighten command. Usage: brighten <factor> <srcKey> <destKey>");
    }
  }

  /**
   * Handles the RGB split operation, extracting the color components
   * from the source key and saving them under specified keys.
   *
   * @param args an array containing the command arguments; expects
   *             <code>split <srcKey> <redKey> <greenKey> <blueKey></code>
   */
  private void handleSplit(String[] args) {
    if (args.length == 5) {
      System.out.println("Split Image " + args[1] + " into red, green and blue");
      controller.split(args[1], args[2], args[3], args[4]);
    } else {
      System.out.println(
          "Invalid split command. Usage: split <srcKey> <redKey> <greenKey> <blueKey>");
    }
  }

  /**
   * Handles the RGB combine operation, merging the color components
   * from the specified keys into a destination key.
   *
   * @param args an array containing the command arguments; expects
   *             <code>combine <destKey> <redKey> <greenKey> <blueKey></code>
   */
  private void handleCombine(String[] args) {
    if (args.length == 5) {
      System.out.println("Combined Image " + args[2] + ", " + args[3] + " and " + args[4]);
      controller.combine(args[1], args[2], args[3], args[4]);
    } else {
      System.out.println(
          "Invalid combine command. Usage: combine <destKey> <redKey> <greenKey> <blueKey>");
    }
  }

  /**
   * Handles the script execution by reading commands from the specified file.
   *
   * @param args an array containing the command arguments; expects
   *             <code>run-script <scriptFilePath></code>
   */
  private void handleScript(String[] args) {
    if (args.length < 2) {
      System.out.println("Please provide a script file path");
      return;
    }
    System.out.println("Loading script from " + args[1]);
    ScriptReader scriptReader = new ScriptReader(this);
    scriptReader.readScript(args[1]);
  }
}
