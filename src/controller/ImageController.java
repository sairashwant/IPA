package controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import model.ImageModel;
import model.colorscheme.Pixels;
import model.imagetransformation.basicoperation.Flip.Direction;

/**
 * The ImageController class handles user commands for image processing operations. It interacts
 * with an ImageModel to perform various image manipulations, including transformations, color
 * adjustments, and compression. The controller parses input commands, executes corresponding
 * actions, and maintains a menu of available operations.
 */
public class ImageController implements ImageControllerInterface {

  private final ImageModel imageModel;
  private final Scanner scanner;
  private final Map<String, Consumer<String[]>> commandMap;
  private final Map<String, BiConsumer<String, String>> operationsMap;
  private final Appendable out;
  boolean exitFlag;

  /**
   * Constructs an ImageController with the specified ImageModel, input reader, and output
   * appendable.
   *
   * @param image the ImageModel instance for performing image operations
   * @param in    the Readable input source for user commands
   * @param out   the Appendable output for displaying messages
   */
  public ImageController(ImageModel image, Readable in, Appendable out) {
    this.imageModel = image;
    this.scanner = new Scanner(in);
    this.out = out;
    this.commandMap = new HashMap<>();
    this.operationsMap = new HashMap<>();
    initializeOperationsMap();
    initializeCommandMap();
  }

  /**
   * Constructs an ImageController with the specified ImageModel and default input/output sources.
   * This constructor is intended for console-based input/output.
   *
   * @param image the ImageModel instance for performing image operations
   */
  public ImageController(ImageModel image) {
    this.imageModel = image;
    this.out = null;
    this.scanner = new Scanner(System.in);
    this.commandMap = new HashMap<>();
    this.operationsMap = new HashMap<>();
    boolean exitFlag = false;

    initializeOperationsMap();
    initializeCommandMap();
  }


  /**
   * Returns the command map containing available commands and their handlers.
   *
   * @return a map of command names to their corresponding handlers
   */
  public Map<String, Consumer<String[]>> getCommandMap() {
    return commandMap;
  }

  /**
   * Initializes the operations map with image processing operations.
   */
  private void initializeOperationsMap() {
    operationsMap.put("blur", imageModel::blur);
    operationsMap.put("sharpen", imageModel::sharpen);
    operationsMap.put("greyscale", imageModel::greyScale);
    operationsMap.put("sepia", imageModel::sepia);
    operationsMap.put("value-component", imageModel::value);
    operationsMap.put("luma-component", imageModel::luma);
    operationsMap.put("intensity-component", imageModel::intensity);
    operationsMap.put("red-component", imageModel::getRedChannel);
    operationsMap.put("green-component", imageModel::getGreenChannel);
    operationsMap.put("blue-component", imageModel::getBlueChannel);
    operationsMap.put("color-correction", imageModel::colorCorrection);
    operationsMap.put("histogram", imageModel::histogram);
  }

  /**
   * Initializes the command map with available commands and their associated methods.
   */
  private void initializeCommandMap() {
    commandMap.put("load", this::handleLoad);
    commandMap.put("save", this::handleSave);
    commandMap.put("brighten", this::handleBrighten);
    commandMap.put("rgb-combine", this::handleCombine);
    commandMap.put("rgb-split", this::handleRGBSplit);
    commandMap.put("blur", this::applyOperation);
    commandMap.put("sharpen", this::applyOperation);
    commandMap.put("greyscale", this::applyOperation);
    commandMap.put("sepia", this::applyOperation);
    commandMap.put("horizontal-flip", args -> handleFlip(args, Direction.HORIZONTAL));
    commandMap.put("vertical-flip", args -> handleFlip(args, Direction.VERTICAL));
    commandMap.put("value-component", this::applyOperation);
    commandMap.put("luma-component", this::applyOperation);
    commandMap.put("intensity-component", this::applyOperation);
    commandMap.put("red-component", this::applyOperation);
    commandMap.put("green-component", this::applyOperation);
    commandMap.put("blue-component", this::applyOperation);
    commandMap.put("compress", this::handleCompression);
    commandMap.put("histogram", this::applyOperation);
    commandMap.put("color-correction", this::applyOperation);
    commandMap.put("levels-adjust", this::handleLevelsAdjust);
    commandMap.put("split", this::handleSplit);
    commandMap.put("run-script", this::handleScript);
    commandMap.put("exit", args -> exitFlag = true);
  }

  /**
   * Applies an image operation based on the provided arguments.
   *
   * @param args the arguments specifying the operation, source key, and destination key
   */
  public void applyOperation(String[] args) {
    if (args.length > 3) {
      handleSplit(args); // Call handleSplit if arguments length is greater than 3
    } else {
      String operationName = args[0];
      String srcKey = args[1];
      String destKey = args[2];

      BiConsumer<String, String> operation = operationsMap.get(operationName);
      if (operation != null) {
        operation.accept(srcKey, destKey); // Apply the operation on the image
        System.out.println("Operation " + operationName + " on " + srcKey);
      } else {
        System.out.println("No such operation: " + operationName);
      }
    }
  }


  /**
   * Continuously listens for and executes commands until the user exits.
   */
  public void run() {
    while (!exitFlag) {  // Use the exitFlag instead of System.exit
      System.out.print("\nEnter command: ");
      String input = scanner.nextLine().trim();
      if (input.isEmpty()) {
        continue;
      }

      String[] parts = input.split("\\s+");
      String command = parts[0].toLowerCase();

      if (commandMap.containsKey(command)) {
        try {
          commandMap.get(command).accept(parts);
        } catch (Exception e) {
          System.out.println("Error processing command: " + e.getMessage());
        }
      } else {
        System.out.println("Unknown command: " + command);
      }
    }
  }

  /**
   * Loads an image from the specified file and stores it in the model.
   *
   * @param args the command arguments for loading an image
   */
  public void handleLoad(String[] args) {
    if (args.length == 3) {
      try {
        Pixels[][] pixels = ImageUtil.loadImage(args[1]);
        imageModel.storePixels(args[2], pixels);
        System.out.println("Loaded Image " + args[2]);
      } catch (IllegalArgumentException e) {
        System.out.println(e.getMessage());
      }
    } else {
      System.out.println("Invalid load command. Usage: load <filename> <key>");
    }
  }

  /**
   * Saves an image from the model to the specified file.
   *
   * @param args the command arguments for saving an image
   */
  public void handleSave(String[] args) {
    if (args.length == 3) {
      try {
        Pixels[][] pixels = imageModel.getStoredPixels(args[2]);
        if (pixels != null) {
          ImageUtil.saveImage(args[1], pixels);
          System.out.println("Saved Image " + args[2]);
        } else {
          System.out.println("No imageModel found with key: " + args[2]);
        }
      } catch (IllegalArgumentException e) {
        System.out.println(e.getMessage());
      }
    } else {
      System.out.println("Invalid save command. Usage: save <filename> <key>");
    }
  }

  /**
   * Adjusts the brightness of an image.
   *
   * @param args the command arguments for brightening an image
   */
  public void handleBrighten(String[] args) {
    if (args.length == 4) {
      try {
        int factor = Integer.parseInt(args[1]);
        System.out.println("Brightened Image " + args[2] + " by " + factor);
        imageModel.brighten(factor, args[2], args[3]);
      } catch (NumberFormatException e) {
        System.out.println("Invalid brighten command. Usage: brighten <factor> <srcKey> <destKey>");
      }
    } else {
      System.out.println("Invalid brighten command. Usage: brighten <factor> <srcKey> <destKey>");
    }
  }

  /**
   * Flips an image either horizontally or vertically based on the specified direction and saves it
   * with a new key.
   *
   * @param args      the command-line arguments for flipping the image in the format:
   *                  <horizontal-flip|vertical-flip> <srcKey> <destKey>
   * @param direction the direction of the flip, either horizontal or vertical
   */
  public void handleFlip(String[] args, Direction direction) {
    if (args.length == 3) {
      try {
        System.out.println("Flipping image " + args[1] + " horizontally");
        imageModel.flip(args[1], args[2], direction);
      } catch (IllegalArgumentException e) {
        System.out.println("Error during flip operation: " + e.getMessage());
      }
    } else {
      System.out.println(
          "Invalid flip command. Usage: <horizontal-flip|vertical-flip> <srcKey> <destKey>");
    }
  }

  /**
   * Splits an image into red, green, and blue channels and stores each channel with a unique key.
   *
   * @param args the command-line arguments for splitting the image in the format: rgb-split
   *             <srcKey> <redKey> <greenKey> <blueKey>
   */
  public void handleRGBSplit(String[] args) {
    if (args.length != 5) {
      System.out.println(
          "Invalid rgb-split command. Usage: rgb-split <srcKey> <redKey> <greenKey> <blueKey>");
      return;
    }

    String srcKey = args[1];
    try {
      Pixels[][] pixels = imageModel.getStoredPixels(srcKey);
      if (pixels == null) {
        System.out.println("No image found with key: " + srcKey);
        return;
      }
      imageModel.split(srcKey, args[2], args[3], args[4]);
      System.out.println("Split Image " + srcKey + " into red, green and blue");
    } catch (Exception e) {
      System.out.println("Error processing command: " + e.getMessage());
    }

  }

  /**
   * Combines red, green, and blue channel images into one color image and saves it with a new key.
   *
   * @param args the command-line arguments for combining RGB channels in the format: rgb-combine
   *             <destKey> <redKey> <greenKey> <blueKey>
   */
  public void handleCombine(String[] args) {
    if (args.length == 5) {
      System.out.println("Combined Image " + args[2] + "," + args[3] + " and " + args[4]);
      imageModel.combine(args[1], args[2], args[3], args[4]);
    } else {
      System.out.println(
          "Invalid combine command. Usage: rgb-combine <destKey> <redKey> <greenKey> <blueKey>");
    }
  }

  /**
   * Compresses an image by a specified compression ratio and saves it with a new key.
   *
   * @param args the command-line arguments for compressing the image in the format: compress
   *             <ratio> <srcKey> <destKey>
   */
  public void handleCompression(String[] args) {
    if (args.length == 4) {
      try {
        double compressionRatio = Double.parseDouble(args[1]);
        if (compressionRatio < 0 || compressionRatio > 100) {
          System.out.println("Invalid compression ratio. Must be between 0 and 100.");
          return;
        }
        System.out.println(
            "Applying compression to " + args[2] + " with ratio " + compressionRatio);
        imageModel.compress(args[2], args[3], compressionRatio);
      } catch (NumberFormatException e) {
        System.out.println("Invalid compression ratio. Please enter a number.");
      }
    } else {
      System.out.println("Invalid compression command. Usage: compress <ratio> <srcKey> <destKey>");
    }
  }


  /**
   * Adjusts the levels of black, mid, and white points of an image and saves the adjusted image
   * with a new key.
   *
   * @param args the command-line arguments for levels adjustment in the format: levels-adjust
   *             <black> <mid> <white> <srcKey> <destKey>
   */
  public void handleLevelsAdjust(String[] args) {
    if (args.length > 6) {
      int split = Integer.parseInt(args[7]);
      int black = Integer.parseInt(args[1]);
      int mid = Integer.parseInt(args[2]);
      int white = Integer.parseInt(args[3]);
      imageModel.splitAndTransform(args[4], args[5], split, "levels-adjust", black, mid, white);
      System.out.println("Adjusting levels for " + args[4]);
    } else if (args.length == 6) {
      try {
        int black = Integer.parseInt(args[1]);
        int mid = Integer.parseInt(args[2]);
        int white = Integer.parseInt(args[3]);

        if (black < 0 || black > 255 ||
            mid < 0 || mid > 255 ||
            white < 0 || white > 255 ||
            black >= mid || mid >= white) {
          System.out.println(
              "Invalid level values. Values must be between 0 and 255, and black < mid < white");
          return;
        }

        System.out.println("Adjusting levels for " + args[4]);
        imageModel.adjustLevel(black, mid, white, args[4], args[5]);
      } catch (NumberFormatException e) {
        System.out.println(
            "Invalid level values. Please enter integers for black, mid, and white points.");
      }
    } else {
      System.out.println(
          "Invalid levels-adjust command. Usage: levels-adjust <black> <mid> <white> <srcKey> <destKey>");
    }
  }

  /**
   * Splits an image based on a percentage, applies a transformation, and saves it with a new key.
   *
   * @param args the command-line arguments for splitting and transforming the image in the format:
   *             <operation> <srcKey> <destKey> split <splitPercentage>
   */
  public void handleSplit(String[] args) {
    if (args.length < 5) {
      System.out.println(
          "Invalid split command. Usage: <operation> <srcKey> <destKey> split <splitPercentage>");
      return;
    }

    if (!args[3].equalsIgnoreCase("split")) {
      System.out.println("Invalid split command. Expected 'split' keyword.");
      return;
    }

    try {
      String srcKey = args[1];
      Pixels[][] pixels = imageModel.getStoredPixels(srcKey);
      if (pixels == null) {
        System.out.println("No image found with key: " + srcKey);
        return;
      }

      int splitValue = Integer.parseInt(args[4]);
      if (splitValue < 0 || splitValue > 100) {
        System.out.println("Invalid split value. Must be between 0 and 100.");
        return;
      }

      String operation = args[0];
      imageModel.splitAndTransform(srcKey, args[2], splitValue, operation);
      System.out.println("Split and transformed image " + srcKey + " with operation " + operation);
    } catch (NumberFormatException e) {
      System.out.println("Invalid split value. Please enter a valid number.");
    } catch (Exception e) {
      System.out.println("Error processing command: " + e.getMessage());
    }
  }

  /**
   * Executes a script containing multiple commands.
   *
   * @param args the command-line arguments for running the script in the format: run-script
   *             <filename>
   */
  public void handleScript(String[] args) {
    if (args.length != 2) {
      System.out.println("Invalid script command. Usage: script <filename>");
      return;
    }

    String scriptPath = args[1];
    ScriptReader scriptReader = new ScriptReader(this);

    try {
      scriptReader.readScript(scriptPath);
      System.out.println("Script executed successfully: " + scriptPath);
    } catch (IOException e) {
      System.out.println("Error reading script: " + e.getMessage());
    }
  }

  /**
   * Displays the available commands and options in the image processing menu.
   */
  public void printMenu() {
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
    System.out.println("\n---- New Operations ----");
    System.out.println("18. Compression");
    System.out.println("19. Histogram");
    System.out.println("20. Color-Correction");
    System.out.println("21. Levels-Adjust");
    System.out.println("22. Split-And-Transform");
    System.out.println("\n---- Additional Operations ----");
    System.out.println("23. run-script");
    System.out.println("24. Exit Program");
    System.out.println("============================================");
  }
}