package controller;

import java.io.IOException;
import model.Image;
import model.imagetransformation.basicoperation.Flip.Direction;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.function.BiConsumer;

public class ImageController {
  private final Image image;
  private final Scanner scanner;
  private final Map<String, Consumer<String[]>> commandMap;
  private final Map<String, BiConsumer<String, String>> operationsMap;

  public ImageController(Image image) {
    this.image = image;
    this.scanner = new Scanner(System.in);
    this.commandMap = new HashMap<>();
    this.operationsMap = new HashMap<>();

    initializeOperationsMap();
    initializeCommandMap();
  }

  public Map<String, Consumer<String[]>> getCommandMap() {
    return commandMap;
  }

  private void initializeOperationsMap() {
    operationsMap.put("blur", image::blur);
    operationsMap.put("sharpen", image::sharpen);
    operationsMap.put("greyscale", image::greyScale);
    operationsMap.put("sepia", image::sepia);
    operationsMap.put("value-component", image::value);
    operationsMap.put("luma-component", image::luma);
    operationsMap.put("intensity-component", image::intensity);
    operationsMap.put("red-component", image::getRedChannel);
    operationsMap.put("green-component", image::getGreenChannel);
    operationsMap.put("blue-component", image::getBlueChannel);
    operationsMap.put("color-correction", image::colorCorrection);
    operationsMap.put("histogram", image::histogram);
  }

  private void initializeCommandMap() {
    commandMap.put("load", this::handleLoad);
    commandMap.put("save", this::handleSave);
    commandMap.put("brighten", this::handleBrighten);
    commandMap.put("rgb-combine", this::handleCombine);
    commandMap.put("rgb-split", this::handleRGBSplit);
    commandMap.put("blur", args -> applyOperation("blur", args[1], args[2]));
    commandMap.put("sharpen", args -> applyOperation("sharpen", args[1], args[2]));
    commandMap.put("greyscale", args -> applyOperation("greyscale", args[1], args[2]));
    commandMap.put("sepia", args -> applyOperation("sepia", args[1], args[2]));
    commandMap.put("horizontal-flip", args -> image.flip(args[1], args[2], Direction.HORIZONTAL));
    commandMap.put("vertical-flip", args -> image.flip(args[1], args[2], Direction.VERTICAL));
    commandMap.put("value-component", args -> applyOperation("value-component", args[1], args[2]));
    commandMap.put("luma-component", args -> applyOperation("luma-component", args[1], args[2]));
    commandMap.put("intensity-component", args -> applyOperation("intensity-component", args[1], args[2]));
    commandMap.put("red-component", args -> applyOperation("red-component", args[1], args[2]));
    commandMap.put("green-component", args -> applyOperation("green-component", args[1], args[2]));
    commandMap.put("blue-component", args -> applyOperation("blue-component", args[1], args[2]));
    commandMap.put("compress", this::handleCompression);
    commandMap.put("histogram", args -> applyOperation("histogram", args[1], args[2]));
    commandMap.put("color-correction", args -> applyOperation("color-correction", args[1], args[2]));
    commandMap.put("levels-adjust", this::handleLevelsAdjust);
    commandMap.put("split", this::handleSplit);
    commandMap.put("run-script", this::handleScript);
    commandMap.put("exit", args -> System.exit(0));
  }

  private void applyOperation(String operationName, String srcKey, String destKey) {
    BiConsumer<String, String> operation = operationsMap.get(operationName);
    if (operation != null) {
      operation.accept(srcKey, destKey);
    } else {
      System.out.println("No such operation: " + operationName);
    }
  }

  // ... rest of the methods remain the same ...

  public void run() {
    printMenu();
    while (true) {
      System.out.print("\nEnter command: ");
      String input = scanner.nextLine().trim();
      if (input.isEmpty()) continue;

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

  private void handleLoad(String[] args) {
    if (args.length == 3) {
      System.out.println("Loaded Image " + args[2]);
      image.getPixels(args[2], args[1]);
    } else {
      System.out.println("Invalid load command. Usage: load <filename> <key>");
    }
  }

  private void handleSave(String[] args) {
    if (args.length == 3) {
      System.out.println("Saved Image " + args[2]);
      image.savePixels(args[2], args[1]);
    } else {
      System.out.println("Invalid save command. Usage: save <filename> <key>");
    }
  }

  private void handleBrighten(String[] args) {
    if (args.length == 4) {
      try {
        int factor = Integer.parseInt(args[1]);
        System.out.println("Brightened Image " + args[2] + " by " + factor);
        image.brighten(factor, args[2], args[3]);
      } catch (NumberFormatException e) {
        System.out.println("Invalid brighten factor. Please enter an integer.");
      }
    } else {
      System.out.println("Invalid brighten command. Usage: brighten <factor> <srcKey> <destKey>");
    }
  }

  private void handleRGBSplit(String[] args) {
    if (args.length == 5) {
      System.out.println("Split Image " + args[1] + " into red, green and blue");
      image.split(args[1], args[2], args[3], args[4]);
    } else {
      System.out.println("Invalid split command. Usage: rgb-split <srcKey> <redKey> <greenKey> <blueKey>");
    }
  }

  private void handleCombine(String[] args) {
    if (args.length == 5) {
      System.out.println("Combined Image " + args[2] + "," + args[3] + " and " + args[4]);
      image.combine(args[1], args[2], args[3], args[4]);
    } else {
      System.out.println("Invalid combine command. Usage: rgb-combine <destKey> <redKey> <greenKey> <blueKey>");
    }
  }

  private void handleCompression(String[] args) {
    if (args.length == 4) {
      try {
        double compressionRatio = Double.parseDouble(args[1]);
        System.out.println("Applying compression to " + args[2] + " with ratio " + compressionRatio);
        image.compress(args[2], args[3], compressionRatio);
      } catch (NumberFormatException e) {
        System.out.println("Invalid compression ratio. Please enter a number.");
      }
    } else {
      System.out.println(" Invalid compression command. Usage: compress <ratio> <srcKey> <destKey>");
    }
  }

  private void handleLevelsAdjust(String[] args) {
    if (args.length == 6) {
      try {
        int black = Integer.parseInt(args[1]);
        int mid = Integer.parseInt(args[2]);
        int white = Integer.parseInt(args[3]);
        System.out.println("Adjusting levels for " + args[4]);
        image.adjustLevel(black, mid, white, args[4], args[5]);
      } catch (NumberFormatException e) {
        System.out.println("Invalid level values. Please enter integers for black, mid, and white points.");
      }
    } else {
      System.out.println("Invalid levels-adjust command. Usage: levels-adjust <black> <mid> <white> <srcKey> <destKey>");
    }
  }

  private void handleSplit(String[] args) {
    if (args.length == 5) {
      try {
        int splitValue = Integer.parseInt(args[4]);
        String operation = args[3];
        System.out.println("Splitting and transforming " + args[1]);
        image.splitAndTransform(args[1], args[2], splitValue, operation);
      } catch (NumberFormatException e) {
        System.out.println("Invalid split value. Please enter an integer for the split percentage.");
      }
    } else {
      System.out.println("Invalid split command. Usage: split <srcKey> <destKey> <operation> <splitPercentage>");
    }
  }

  private void handleScript(String[] args) {
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

  private void printMenu() {
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