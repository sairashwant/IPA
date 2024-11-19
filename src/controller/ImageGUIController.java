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
  String latesthistogram;
  String original;

  public ImageGUIController(EnhancedImageModel image, ImageController imageController) {
    super(image);
    this.imageController = imageController;
    i1 = new EnhancedImage();
  }


    @Override
    public void handleLoad(ImageProcessorGUI gui, String key) {
    this.gui = gui;
    original = key;
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
    String dest = key + "_" + operation;
    String[] command = {operation, key, dest};
    imageController.applyOperation(command);
//    if(operation.contentEquals("histogram"))
//    {
//      Pixels[][] pixels = imageModel.getStoredPixels(dest);
//      BufferedImage image = convertPixelsToBufferedImage(pixels);
//      gui.displayHistogram(image);
//    }
    latest = dest;
    displayImageByKey(gui, dest);
  }

  public void applyHistogram(String[] args) {
    String key = latest;
    String dest = latest + "_histogram";
    String[] command = {"histogram", key, dest};
    imageController.applyOperation(command);
    Pixels[][] pixels = imageModel.getStoredPixels(dest);
    BufferedImage image = convertPixelsToBufferedImage(pixels);
    gui.displayHistogram(image);
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

    // Create a JFileChooser to let the user choose a directory and file name
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setDialogTitle("Save Image");

    // Add file filters for different image formats
    javax.swing.filechooser.FileFilter pngFilter = new javax.swing.filechooser.FileNameExtensionFilter("PNG Images", "png");
    javax.swing.filechooser.FileFilter jpgFilter = new javax.swing.filechooser.FileNameExtensionFilter("JPG Images", "jpg");
    javax.swing.filechooser.FileFilter ppmFilter = new javax.swing.filechooser.FileNameExtensionFilter("PPM Images", "ppm");

    // Add the file filters to the file chooser
    fileChooser.addChoosableFileFilter(pngFilter);
    fileChooser.addChoosableFileFilter(jpgFilter);
    fileChooser.addChoosableFileFilter(ppmFilter);

    // Set the default filter (optional, you can choose one to start with)
    fileChooser.setFileFilter(pngFilter); // Default filter, for example, PNG

    // Open the file chooser dialog to select the file to save
    int userSelection = fileChooser.showSaveDialog(null);

    if (userSelection == JFileChooser.APPROVE_OPTION) {
      // User selected a file
      File fileToSave = fileChooser.getSelectedFile();

      // Get the selected file filter
      javax.swing.filechooser.FileFilter selectedFilter = fileChooser.getFileFilter();

      // Determine the file extension based on the selected filter
      String extension = "";
      if (selectedFilter == pngFilter) {
        extension = "png";
      } else if (selectedFilter == jpgFilter) {
        extension = "jpg";
      } else if (selectedFilter == ppmFilter) {
        extension = "ppm";
      }

      // If the file does not already have the correct extension, append it
      String fileName = fileToSave.getName();
      if (!fileName.toLowerCase().endsWith("." + extension)) {
        fileToSave = new File(fileToSave.getAbsolutePath() + "." + extension);
      }

      // Create the command array to call the controller's handleSave method
      String[] command = {"save", fileToSave.getAbsolutePath(), latest};

      try {
        // Call the controller's handleSave method to save the image
        imageController.handleSave(command);
        // Optionally, display the saved image (if needed)
        displayImageByKey(gui, latest);

        // Show a success message
        JOptionPane.showMessageDialog(null, "Image saved successfully to " + fileToSave.getAbsolutePath(),
            "Save Success", JOptionPane.INFORMATION_MESSAGE);
      } catch (Exception e) {
        showError("An error occurred while saving the image: " + e.getMessage());
      }
    } else {
      // The user canceled the file save operation
      showError("Save operation was canceled.");
    }
  }






  @Override
  public void handleBrighten(String[] args) {
    if (args.length == 4) {
      try {
        int factor = Integer.parseInt(args[1]);
        String key = latest; // Use the latest key
        String dest = key + "_brightened";
        String[] command = {"brighten", String.valueOf(factor), key, dest};
        imageController.handleBrighten(command);
        latest = dest;
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
        String dest = key + "_compressed";
        String[] command = {"compress", String.valueOf(compressionRatio), key, dest};
        imageController.handleCompression(command);
        latest = dest;
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
    if (args.length == 3) {
      try {
        int black = Integer.parseInt(args[0]);
        int mid = Integer.parseInt(args[1]);
        int white = Integer.parseInt(args[2]);
        String key = latest;
        String dest = key + "_levels-adjusted";
        String[] command = {"levels-adjust", String.valueOf(black), String.valueOf(mid), String.valueOf(white), key, dest};
        imageController.handleLevelsAdjust(command);
        latest = dest;
        displayImageByKey(gui, latest);
      } catch (NumberFormatException e) {
        showError("Invalid level values. Please enter integers for black, mid, and white points.");
      } catch (Exception e) {
        showError("An unexpected error occurred: " + e.getMessage());
      }
    } else if (args.length == 4) {
      try {
        int black = Integer.parseInt(args[0]);
        int mid = Integer.parseInt(args[1]);
        int white = Integer.parseInt(args[2]);
        int percentage = Integer.parseInt(args[3]);
        String key = latest;
        String dest = key + "_split-"+"levels-adjusted";
        String[] command = {"levels-adjust", String.valueOf(black), String.valueOf(mid), String.valueOf(white), key, dest, "split",String.valueOf(percentage)};
        imageController.handleLevelsAdjust(command);
        latest = dest;
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
    if (args.length >= 2) {
      String operation = args[0];
      String key = latest;
      String dest = key + "_split-" + operation ;
      String splitPercentage = args[1];
      try {
        String[] command = {operation, key, dest, "split", splitPercentage};
        imageController.handleSplit(command);
        latest = dest;
        displayImageByKey(gui, dest);
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
      String dest = srcKey + "_flipped-"+ direction; // Generate the destination key
      String[] command = {"flip", srcKey, dest};

      try {
        imageController.handleFlip(command, direction); // Perform the flip operation
        latest = dest;
        displayImageByKey(gui, dest);// Display the flipped image
      } catch (Exception e) {
        showError("Error processing flip command: " + e.getMessage());
      }
    } else {
      showError("Invalid flip command. Usage: <horizontal-flip|vertical-flip>");
    }
  }

  public void handleUndo(){

  }


  @Override
  public void printMenu() {
  }

  @Override
  public void handleShowOriginalImage() {
    String key = latest;
    String dest = key + "_original";
    i1.storePixels(dest,i1.getStoredPixels(key));
    BufferedImage originalImage = convertPixelsToBufferedImage(i1.getStoredPixels(dest)); // Fetch the original image from the controller
    if (originalImage != null) {
      gui.displayImage(originalImage); // Display the original image
      latest = dest;
    } else {
      showError("No original image available.");
    }
  }


}
