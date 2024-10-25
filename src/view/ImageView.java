package view;

import controller.ImageController;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Consumer;

/**
 * A class that represents the view component of an image processing application. It handles user
 * commands, interacts with the controller, and provides a menu-driven interface.
 */
public class ImageView {

  private ImageController controller;
  private Scanner scanner;
  private final Map<String, Consumer<String[]>> commandMap;

  /**
   * Gets the command map which links user commands to their respective handlers.
   *
   * @return a map of command strings to handler functions.
   */
  public Map<String, Consumer<String[]>> getCommandMap() {
    return commandMap;
  }

  /**
   * Constructs an ImageView with the given controller. Initializes the scanner and command map with
   * command handlers.
   *
   * @param controller the ImageController to interact with for processing images.
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
    commandMap.put("Luma-component", this::handleOperation);
    commandMap.put("intensity-component", this::handleOperation);
    commandMap.put("sepia", this::handleOperation);
    commandMap.put("red-component", this::handleOperation);
    commandMap.put("green-component", this::handleOperation);
    commandMap.put("blue-component", this::handleOperation);
    commandMap.put("exit", args -> System.exit(0));
    commandMap.put("run-script", this::handleScript);
  }

  /**
   * Runs the main loop for handling user input commands. Processes commands entered by the user and
   * executes appropriate actions.
   */
  public void run() {
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
   * Prints the menu options available to the user. Lists image operations and transformations.
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
   * Handles the 'load' command to load an image into the application.
   *
   * @param args the command arguments: load.
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
   * Handles the 'save' command to save an image from the application.
   *
   * @param args the command arguments: save.
   */
  private void handleSave(String[] args) {
    if (args.length == 3) {
      System.out.println("Saved Image" + args[2]);
      controller.saveImage(args[2], args[1]);
    } else {
      System.out.println("Invalid save command. Usage: save <filename> <key> ");
    }
  }

  /**
   * Handles generic image operations like blur, sharpen, etc.
   *
   * @param args the command arguments.
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
   * Handles the 'brighten' command to adjust the brightness of an image.
   *
   * @param args the command arguments: brighten.
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
      System.out.println("Invalid brighten command. Usage: brighten <factor> <srcKey> <destKey> ");
    }
  }

  /**
   * Handles the 'rgb-split' command to split an image into RGB components.
   *
   * @param args the command arguments: split.
   */
  private void handleSplit(String[] args) {
    if (args.length == 5) {
      System.out.println("Split Image " + args[1] + "into red , green and blue");
      controller.split(args[1], args[2], args[3], args[4]);
    } else {
      System.out.println(
          "Invalid split command. Usage: split <srcKey> <redKey> <greenKey> <blueKey>");
    }
  }

  /**
   * Handles the 'rgb-combine' command to combine RGB components into an image.
   *
   * @param args the command arguments: combine.
   */
  private void handleCombine(String[] args) {
    if (args.length == 5) {
      System.out.println("Combined Image " + args[2] + "," + args[3] + " and" + args[4]);
      controller.combine(args[1], args[2], args[3], args[4]);
    } else {
      System.out.println(
          "Invalid combine command. Usage: combine <destKey> <redKey> <greenKey> <blueKey>");
    }
  }

  /**
   * Handles the 'run-script' command to execute a series of commands from a script file.
   *
   * @param args the command arguments: run-script.
   */
  public void handleScript(String[] args) {
    if (args.length < 2) {
      System.out.println("Please provide a script file path");
      return;
    }
    System.out.println("Loading script from " + args[1]);
    ScriptReader scriptReader = new ScriptReader(this);
    scriptReader.readScript(args[1]);
  }

}
