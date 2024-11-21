package controller;

import java.util.Arrays;
import model.EnhancedImage;
import model.EnhancedImageModel;
import model.colorscheme.Pixels;
import model.imagetransformation.basicoperation.Flip.Direction;
import view.ImageProcessorGUI;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Controller for handling the GUI interactions of the image processor application. This class
 * manages the communication between the view (GUI) and the model, processes user inputs, and
 * executes image-related operations.
 */
public class ImageGUIController extends ImageController implements ImageGUIControllerInterface {

  String latest;
  private final ImageController imageController;
  ImageProcessorGUI gui;
  EnhancedImageModel i1;
  String original;

  /**
   * Constructs an ImageGUIController with the specified model and controller.
   *
   * @param image           The model for storing and manipulating images.
   * @param imageController The controller for handling core image processing operations.
   */
  public ImageGUIController(EnhancedImageModel image, ImageControllerInterface imageController) {
    super(image);
    this.imageController = (ImageController) imageController;
    i1 = new EnhancedImage();
  }

  /**
   * Handles the loading of an image into the application through the GUI.
   *
   * @param gui      The GUI instance for displaying the loaded image.
   * @param key      The key to associate with the loaded image.
   * @param filename The file path of the image to load.
   */
  @Override
  public void handleLoad(ImageProcessorGUI gui, String key, String filename) {
    this.gui = gui;
    gui.addWindowListenerToGUI();

    // Extract only the image name (without path and extension)
    File file = new File(filename);
    String fileNameWithExtension = file.getName(); // Get the name with extension
    int lastDotIndex = fileNameWithExtension.lastIndexOf('.'); // Find the last dot

    if (lastDotIndex > 0) {
      key = fileNameWithExtension.substring(0, lastDotIndex); // Remove the extension

      latest = key;  // Store the key
      original = key;

      try {
        // Pass the updated key to the controller and store pixels
        imageController.handleLoad(new String[]{"load", filename, key});
        i1.storePixels(key, ImageUtil.loadImage(filename));

        // Retrieve and display the stored image
        Pixels[][] pixels = i1.getStoredPixels(key);
        BufferedImage image = imageController.convertPixelsToBufferedImage(pixels);
        gui.displayImage(image);
      } catch (IllegalArgumentException ex) {
        gui.showError("Error loading image: " + ex.getMessage());
      } catch (Exception ex) {
        gui.showError("An unexpected error occurred: " + ex.getMessage());
      }
    } else {
      gui.showError("Invalid file selected: Missing extension.");
    }
  }


  /**
   * Retrieves the key for the most recently processed image.
   *
   * @return The key of the latest image.
   */
  @Override
  public String getLatestKey() {
    return imageController.getLatestKey();
  }

  /**
   * Applies an operation to the currently loaded image and updates the display.
   *
   * @param args The operation arguments, including the operation name.
   */
  @Override
  public void applyOperation(String[] args) {
    String operation = args[0];
    String key = latest;
    String dest = key + "_" + operation;
    String[] command = {operation, key, dest};
    imageController.applyOperation(command);
    latest = dest;
    displayImageByKey(gui, dest);
  }

  /**
   * Displays the histogram for the current image in the GUI.
   *
   * @param args The operation arguments for generating the histogram.
   */
  @Override
  public void applyHistogram(String[] args) {
    String key = latest;
    String dest = latest + "_histogram";
    String[] command = {"histogram", key, dest};
    imageController.applyOperation(command);
    Pixels[][] pixels = imageModel.getStoredPixels(dest);
    BufferedImage image = imageController.convertPixelsToBufferedImage(pixels);
    gui.displayHistogram(image);
  }


  /**
   * Displays an image in the GUI based on its key in the model.
   *
   * @param gui The GUI instance for displaying the image.
   * @param key The key associated with the image.
   */
  public void displayImageByKey(ImageProcessorGUI gui, String key) {
    try {
      Pixels[][] pixels = imageModel.getStoredPixels(key);
      if (pixels == null) {
        throw new IllegalArgumentException("No image found with key: " + key);
      }
      BufferedImage image = imageController.convertPixelsToBufferedImage(pixels);
      gui.displayImage(image);
    } catch (IllegalArgumentException ex) {
      gui.showError("Error displaying image: " + ex.getMessage());
    } catch (Exception ex) {
      gui.showError("An unexpected error occurred: " + ex.getMessage());
    }
  }


  @Override
  public Map<String, Consumer<String[]>> getCommandMap() {
    return Map.of();
  }

  @Override
  public void run() {
  }

  @Override
  public void handleLoad(String[] args) {
  }


  /**
   * Handles saving the currently loaded image to a file selected by the user.
   *
   * @param userSelection The user's file selection action (e.g., approval or cancellation).
   * @param fileChooser   The file chooser dialog.
   * @param pngFilter     The PNG file filter.
   * @param jpgFilter     The JPG file filter.
   * @param ppmFilter     The PPM file filter.
   */
  @Override
  public void handleSave(int userSelection, JFileChooser fileChooser,
      javax.swing.filechooser.FileFilter pngFilter, javax.swing.filechooser.FileFilter jpgFilter,
      javax.swing.filechooser.FileFilter ppmFilter) {
    // Check if the latest key is not null or empty

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
        JOptionPane.showMessageDialog(null,
            "Image saved successfully to " + fileToSave.getAbsolutePath(),
            "Save Success", JOptionPane.INFORMATION_MESSAGE);
      } catch (Exception e) {
        gui.showError("An error occurred while saving the image: " + e.getMessage());
      }
    } else {
      // The user canceled the file save operation
      gui.showError("Save operation was canceled.");
    }
  }

  /**
   * Handles the brightening operation for the currently loaded image. The method increases the
   * brightness of the image by a specified factor and updates the GUI with the result.
   *
   * @param args the command arguments:
   *                         <ul>
   *                           <li><code>args[0]</code>: the operation name ("brighten").</li>
   *                           <li><code>args[1]</code>: the factor by which to brighten the image (an integer).</li>
   *                           <li><code>args[2]</code>: the source key (latest image key is used).</li>
   *                           <li><code>args[3]</code>: the destination key for the brightened image.</li>
   *                         </ul>
   *             <p>
   *             The method validates the brightness factor and executes the brightening operation.
   *             It updates the <code>latest</code> image key and displays the brightened image in the GUI.
   * @throws NumberFormatException if the brightness factor is not a valid integer.
   * @throws Exception             if an unexpected error occurs during the brightening process.
   * @throws Exception             if an unexpected error occurs during the brightening process.
   */
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
        gui.showError("Invalid brighten command. Please enter a valid integer for the factor.");
      } catch (Exception e) {
        gui.showError("An unexpected error occurred: " + e.getMessage());
      }
    } else {
      gui.showError("Invalid brighten command. Usage: brighten <factor> <srcKey> <destKey>");
    }
  }


  /**
   * Handles undoing the most recent image operation.
   */
  @Override
  public void handleUndo() {
    if (latest == null || latest.isEmpty()) {
      gui.showError("No operation to undo. Please perform an operation first.");
      return;
    }
    // Find the last occurrence of the underscore in the latest key
    int lastUnderscoreIndex = latest.lastIndexOf('_');

    if (lastUnderscoreIndex != -1) {
      // Remove the part after the last underscore
      latest = latest.substring(0, lastUnderscoreIndex);

      try {
        // Display the updated image
        displayImageByKey(gui, latest);
      } catch (Exception e) {
        gui.showError("Error displaying the previous version of the image: " + e.getMessage());
      }
    } else {
      gui.showError("No previous version available to undo.");
    }
  }

  /**
   * Handles the compression operation for the currently loaded image. The method applies a
   * specified compression ratio to the image and updates the GUI with the result.
   *
   * @param args the command arguments:
   *                         <ul>
   *                           <li><code>args[0]</code>: the operation name ("compress").</li>
   *                           <li><code>args[1]</code>: the compression ratio as a <code>double</code>.</li>
   *                           <li><code>args[2]</code>: the source key (latest image key is used).</li>
   *                           <li><code>args[3]</code>: the destination key for the compressed image.</li>
   *                         </ul>
   *             <p>
   *             The method validates the compression ratio and executes the compression operation.
   *             It updates the <code>latest</code> image key and displays the compressed image in the GUI.
   * @throws NumberFormatException if the compression ratio is not a valid <code>double</code>.
   * @throws Exception             if an unexpected error occurs during the compression process.
   */
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
        gui.showError("Invalid compression ratio. Please enter a valid number.");
      } catch (Exception e) {
        gui.showError("An unexpected error occurred: " + e.getMessage());
      }
    } else {
      gui.showError("Invalid compression command. Usage: compress <ratio> <srcKey> <destKey>");
    }
  }

  /**
   * Handles the levels adjustment operation on the currently loaded image. The method allows
   * adjusting the black, mid, and white levels of the image with an optional split percentage for
   * previewing the adjustment.
   *
   * @param args the command arguments:
   *                         <ul>
   *                           <li>If <code>args.length == 3</code>:
   *                               <ul>
   *                                 <li><code>args[0]</code>: black level as an integer.</li>
   *                                 <li><code>args[1]</code>: mid-level as an integer.</li>
   *                                 <li><code>args[2]</code>: white level as an integer.</li>
   *                               </ul>
   *                           </li>
   *                           <li>If <code>args.length == 4</code>:
   *                               <ul>
   *                                 <li><code>args[0]</code>: black level as an integer.</li>
   *                                 <li><code>args[1]</code>: mid-level as an integer.</li>
   *                                 <li><code>args[2]</code>: white level as an integer.</li>
   *                                 <li><code>args[3]</code>: split percentage (integer between 0 and 100).</li>
   *                               </ul>
   *                           </li>
   *                         </ul>
   *             <p>
   *             The method applies the levels adjustment and either displays the result or shows a preview.
   * @throws NumberFormatException    if the input values are not valid integers.
   * @throws IllegalArgumentException if the split percentage is outside the range [0, 100] or if
   *                                  the preview cannot be generated.
   * @throws Exception                if an unexpected error occurs during the adjustment process.
   */
  @Override
  public void handleLevelsAdjust(String[] args) {
    if (args.length == 3) {
      // Handle normal levels adjustment without preview
      try {
        int black = Integer.parseInt(args[0]);
        int mid = Integer.parseInt(args[1]);
        int white = Integer.parseInt(args[2]);

        String key = latest;
        String dest = key + "_levels-adjusted";

        String[] command = {
            "levels-adjust",
            String.valueOf(black),
            String.valueOf(mid),
            String.valueOf(white),
            key,
            dest
        };

        imageController.handleLevelsAdjust(command);
        latest = dest;
        displayImageByKey(gui, latest);

      } catch (NumberFormatException e) {
        gui.showError(
            "Invalid level values. Please enter integers for black, mid, and white points.");
      } catch (Exception e) {
        gui.showError("An unexpected error occurred: " + e.getMessage());
      }
    } else if (args.length == 4) {
      // Handle levels adjustment with split and preview
      try {
        int black = Integer.parseInt(args[0]);
        int mid = Integer.parseInt(args[1]);
        int white = Integer.parseInt(args[2]);
        int percentage = Integer.parseInt(args[3]);

        if (percentage < 0 || percentage > 100) {
          throw new IllegalArgumentException("Split percentage must be between 0 and 100.");
        }

        String key = latest;
        String dest = key + "_split-levels-adjusted";

        String[] command = {
            "levels-adjust",
            String.valueOf(black),
            String.valueOf(mid),
            String.valueOf(white),
            key,
            dest,
            "split",
            String.valueOf(percentage)
        };

        // Execute the levels adjustment command
        System.out.println("ImageGUIController " + Arrays.toString(command));
        imageController.handleLevelsAdjust(command);

        // Retrieve the preview image
        Pixels[][] previewPixels = imageModel.getStoredPixels(dest);
        if (previewPixels == null) {
          throw new IllegalArgumentException(
              "Failed to generate preview for split levels adjustment.");
        }

        BufferedImage previewImage = imageController.convertPixelsToBufferedImage(previewPixels);

        // Show preview in the GUI
        gui.showPreviewLevelAdj(previewImage, args);

      } catch (NumberFormatException e) {
        gui.showError("Invalid level values or split percentage. Please enter integers.");
      } catch (IllegalArgumentException e) {
        gui.showError(e.getMessage());
      } catch (Exception e) {
        gui.showError("An unexpected error occurred: " + e.getMessage());
      }
    } else {
      // Invalid input format
      gui.showError(
          "Invalid levels-adjust command. Usage: levels-adjust <black> <mid> <white> [<splitPercentage>]");
    }
  }


  /**
   * Handles the split operation on the currently loaded image. This method splits the image based
   * on the specified operation and split percentage. The result is displayed as a preview in the
   * GUI.
   *
   * @param args the command arguments. The array must contain at least two elements:
   *             <ul>
   *               <li><code>args[0]</code>: the operation to be performed (e.g., "split").</li>
   *               <li><code>args[1]</code>: the split percentage as a string.</li>
   *             </ul>
   * @throws IllegalArgumentException if the arguments are invalid or missing required values.
   * @throws Exception                if an unexpected error occurs during the split operation.
   */
  @Override
  public void handleSplit(String[] args) {
    if (args.length >= 2) {
      String operation = args[0];
      String key = latest;
      String dest = key + "_split-" + operation;
      String splitPercentage = args[1];
      try {
        String[] command = {operation, key, dest, "split", splitPercentage};
        imageController.handleSplit(command);
        BufferedImage preview = convertPixelsToBufferedImage(imageModel.getStoredPixels(dest));

        gui.showPreview(preview, operation);
      } catch (Exception e) {
        gui.showError("Error processing split command: " + e.getMessage());
      }
    } else {
      gui.showError(
          "Invalid split command. Usage: <operation> <srcKey> <destKey> split <splitPercentage>");
    }
  }


  @Override
  public void handleScript(String[] args) {
  }

  /**
   * Handles the flip operation on the currently loaded image. The method validates the input and
   * performs the specified flip operation (horizontal or vertical) using the provided direction. If
   * successful, the flipped image is displayed in the GUI.
   *
   * @param args      the command arguments. Must contain at least three elements: the operation
   *                  name, source key, and destination key.
   * @param direction the direction of the flip operation (horizontal or vertical).
   * @throws IllegalArgumentException if no image is currently loaded or the arguments are invalid.
   * @throws Exception                if an unexpected error occurs during the flip operation.
   */
  @Override
  public void handleFlip(String[] args, Direction direction) {
    if (latest == null || latest.isEmpty()) {
      gui.showError("No image loaded to flip. Please load an image first.");
      return;
    }

    if (args.length == 3) {
      // Use the latest key as the source key
      String srcKey = latest;
      String dest = srcKey + "_flipped-" + direction; // Generate the destination key
      String[] command = {"flip", srcKey, dest};

      try {
        imageController.handleFlip(command, direction); // Perform the flip operation
        latest = dest;
        displayImageByKey(gui, dest);// Display the flipped image
      } catch (Exception e) {
        gui.showError("Error processing flip command: " + e.getMessage());
      }
    } else {
      gui.showError("Invalid flip command. Usage: <horizontal-flip|vertical-flip>");
    }
  }

  /**
   * Handles the downscaling of the currently loaded image to the specified width and height. This
   * method validates the provided dimensions, constructs a downscale command, and performs the
   * operation using the underlying controller. If successful, the downscaled image is displayed in
   * the GUI.
   *
   * @param widthInput  the new width of the image as a string input; must be a valid integer.
   * @param heightInput the new height of the image as a string input; must be a valid integer.
   * @throws NumberFormatException if the width or height inputs are not valid integers.
   * @throws Exception             if an unexpected error occurs during the downscale operation.
   */
  public void handleDownscale(String widthInput, String heightInput) {
    if (latest == null || latest.isEmpty()) {
      gui.showError("No image loaded to downscale. Please load an image first.");
      return;
    }
    try {
      int newWidth = Integer.parseInt(widthInput);
      int newHeight = Integer.parseInt(heightInput);

      String dest = latest + "_downscaled";

      String[] command = {"downscale", latest,  String.valueOf(newWidth),
          String.valueOf(newHeight),dest};

      imageController.handleDownscale(command);

      latest = dest;

      displayImageByKey(gui, dest);

    } catch (NumberFormatException e) {
      gui.showError("Invalid dimensions. Please enter valid integers for width and height.");
    } catch (Exception e) {
      gui.showError("An unexpected error occurred: " + e.getMessage());
    }
  }


  @Override
  public void printMenu() {
  }

  /**
   * Handles displaying the original loaded image in the GUI.
   */
  public void handleShowOriginalImage() {
    String dest = original;
    latest = dest;
    i1.storePixels(dest, i1.getStoredPixels(original));
    BufferedImage originalImage = convertPixelsToBufferedImage(
        i1.getStoredPixels(dest)); // Fetch the original image from the controller
    if (originalImage != null) {
      latest = dest;
      gui.displayImage(originalImage); // Display the original image
    } else {
      gui.showError("No original image available.");
    }
  }

}
