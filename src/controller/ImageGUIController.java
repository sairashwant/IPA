package controller;

import model.EnhancedImage;
import model.EnhancedImageModel;
import model.colorscheme.Pixels;
import model.colorscheme.RGBPixel;
import model.imagetransformation.basicoperation.Flip.Direction;
import view.ImageProcessorGUI;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Map;
import java.util.function.Consumer;

public class ImageGUIController extends ImageController implements ImageGUIControllerInterface {
  String latest;
  private final ImageController imageController;
  ImageProcessorGUI gui;
  EnhancedImageModel i1;
  Pixels[][] pixels;

  public ImageGUIController(EnhancedImageModel image, ImageController imageController) {
    super(image);
    this.imageController = imageController;
    i1 = new EnhancedImage();
  }


    @Override
    public void handleLoad(ImageProcessorGUI gui, String key) {
    this.gui = gui;

    JFileChooser fileChooser = new JFileChooser();
    int returnValue = fileChooser.showOpenDialog(null);

    if (returnValue == JFileChooser.APPROVE_OPTION) {
      File selectedFile = fileChooser.getSelectedFile();
      String filename = selectedFile.getAbsolutePath();

      int lastDotIndex = key.lastIndexOf('.');
      if (lastDotIndex > 0) {
        key = key.substring(0, lastDotIndex); // Remove the file extension
      }
      latest = key;

      try {
        imageController.handleLoad(new String[]{"load", filename, key});
        i1.storePixels(key, ImageUtil.loadImage(filename));
        Pixels[][] pixels = i1.getStoredPixels(key);
        BufferedImage image = convertPixelsToBufferedImage(pixels);
        gui.displayImage(image);
      } catch (IllegalArgumentException ex) {
        showError("Error loading image: " + ex.getMessage());
      } catch (Exception ex) {
        showError("An unexpected error occurred: " + ex.getMessage());
      }
    }
  }

  private BufferedImage convertPixelsToBufferedImage(Pixels[][] pixels) {
    if (pixels == null || pixels.length == 0) {
      throw new IllegalArgumentException("No pixels to convert.");
    }

    int height = pixels.length;
    int width = pixels[0].length;
    BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        if (pixels[y][x] instanceof RGBPixel) {
          RGBPixel rgbPixel = (RGBPixel) pixels[y][x];
          int rgb = (rgbPixel.getRed() << 16) | (rgbPixel.getGreen() << 8) | rgbPixel.getBlue();
          image.setRGB(x, y, rgb);
        }
      }
    }

    return image;
  }

  private void showError(String message) {
    JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
  }

  public String getLatestKey() {
    return imageController.getLatestKey();
  }

  @Override
  public void applyOperation(String[] args) {
    String operation = args[0];
    String key = latest;
    String dest = key + "-" + operation;
    latest = dest;
    String[] command = {operation, key, dest};
    imageController.applyOperation(command);
    displayImageByKey(gui, latest);
  }

  public void displayImageByKey(ImageProcessorGUI gui, String key) {
    try {
      Pixels[][] pixels = imageModel.getStoredPixels(key);
      if (pixels == null) {
        throw new IllegalArgumentException("No image found with key: " + key);
      }
      BufferedImage image = convertPixelsToBufferedImage(pixels);
      gui.displayImage(image);
    } catch (IllegalArgumentException ex) {
      showError("Error displaying image: " + ex.getMessage());
    } catch (Exception ex) {
      showError("An unexpected error occurred: " + ex.getMessage());
    }
  }

  @Override
  public Map<String, Consumer<String[]>> getCommandMap() {
    return Map.of();
  }

  @Override
  public void run() {}

  @Override
  public void handleLoad(String[] args) { }

  @Override
  public void handleSave(String[] args) {
    // Check if the latest key is not null or empty
    if (latest == null || latest.isEmpty()) {
      showError("No image loaded to save. Please load an image first.");
      return;
    }

    // Prompt the user for a filename to save the image
    String filename = JOptionPane.showInputDialog("Enter filename to save the image (with extension):");

    if (filename != null && !filename.trim().isEmpty()) {
      // Create the command array to call the controller's handleSave method
      String[] command = {"save", filename, latest};
      try {
        // Call the controller's handleSave method
        imageController.handleSave(command);
        // Display the saved image
        displayImageByKey(gui, latest);
      } catch (Exception e) {
        showError("An error occurred while saving the image: " + e.getMessage());
      }
    } else {
      showError("Invalid filename. Please provide a valid filename to save the image.");
    }
  }

  @Override
  public void handleBrighten(String[] args) {
    if (args.length == 4) {
      try {
        int factor = Integer.parseInt(args[1]);
        String key = latest; // Use the latest key
        latest = key + "-brightened";
        String[] command = {"brighten", String.valueOf(factor), key, latest};
        imageController.handleBrighten(command);
        displayImageByKey(gui, latest);
      } catch (NumberFormatException e) {
        showError("Invalid brighten command. Please enter a valid integer for the factor.");
      } catch (Exception e) {
        showError("An unexpected error occurred: " + e.getMessage());
      }
    } else {
      showError("Invalid brighten command. Usage: brighten <factor> <srcKey> <destKey>");
    }
  }

  @Override
  public void handleRGBSplit(String[] args) {}

  @Override
  public void handleCombine(String[] args) {}

  @Override
  public void handleCompression(String[] args) {
    if (args.length == 4) {
      try {
        double compressionRatio = Double.parseDouble(args[1]);
        String key = latest; // Use the latest key
        String dest = key + "-compressed";
        latest = dest; // Update the latest key
        String[] command = {"compress", String.valueOf(compressionRatio), key, dest};
        imageController.handleCompression(command);
        displayImageByKey(gui, latest);
      } catch (NumberFormatException e) {
        showError("Invalid compression ratio. Please enter a valid number.");
      } catch (Exception e) {
        showError("An unexpected error occurred: " + e.getMessage());
      }
    } else {
      showError("Invalid compression command. Usage: compress <ratio> <srcKey> <destKey>");
    }
  }

  @Override
  public void handleLevelsAdjust(String[] args) {
    if (args.length == 5) {
      try {
        int black = Integer.parseInt(args[1]);
        int mid = Integer.parseInt(args[2]);
        int white = Integer.parseInt(args[3]);
        String key = latest;
        String dest = key + "-levels-adjusted";
        latest = dest; // Update the latest key
        String[] command = {"levels-adjust", String.valueOf(black), String.valueOf(mid), String.valueOf(white), key, dest};
        imageController.handleLevelsAdjust(command);
        displayImageByKey(gui, latest);
      } catch (NumberFormatException e) {
        showError("Invalid level values. Please enter integers for black, mid, and white points.");
      } catch (Exception e) {
        showError("An unexpected error occurred: " + e.getMessage());
      }
    } else {
      showError("Invalid levels-adjust command. Usage: levels-adjust <black> <mid> <white> <srcKey> <destKey>");
    }
  }

  @Override
  public void handleSplit(String[] args) {
    if (args.length >= 5) {
      String operation = args[0];
      String srcKey = args[1];
      String destKey = args[2];
      String splitPercentage = args[4];
      try {
        String[] command = {operation, srcKey, destKey, "split", splitPercentage};
        imageController.handleSplit(command);
        displayImageByKey(gui, destKey);
      } catch (Exception e) {
        showError("Error processing split command: " + e.getMessage());
      }
    } else {
      showError("Invalid split command. Usage: <operation> <srcKey> <destKey> split <splitPercentage>");
    }
  }

  @Override
  public void handleScript(String[] args) {}

  @Override
  public void handleFlip(String[] args, Direction direction) {
    if (latest == null || latest.isEmpty()) {
      showError("No image loaded to flip. Please load an image first.");
      return;
    }

    if (args.length == 3) {
      // Use the latest key as the source key
      String srcKey = latest;
      String destKey = srcKey + "-flipped"; // Generate the destination key
      latest = destKey; // Update the latest key to the destination key
      String[] command = {"flip", srcKey, destKey};

      try {
        imageController.handleFlip(command, direction); // Perform the flip operation
        displayImageByKey(gui, latest); // Display the flipped image
      } catch (Exception e) {
        showError("Error processing flip command: " + e.getMessage());
      }
    } else {
      showError("Invalid flip command. Usage: <horizontal-flip|vertical-flip>");
    }
  }


  @Override
  public void printMenu() {
  }

}
