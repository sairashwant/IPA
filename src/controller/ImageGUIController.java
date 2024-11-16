package controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.function.Consumer;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import model.colorscheme.Pixels;
import model.imagetransformation.basicoperation.Flip.Direction;

public class ImageGUIController implements ImageGUIControllerInterface {
  private JLabel imageLabel;
  private BufferedImage currentImage;

  private final ImageController imageController;// Reference to the ImageController

  public ImageGUIController(ImageController imageController) {
    this.imageController = imageController;
  }


  @Override
  public Map<String, Consumer<String[]>> getCommandMap() {
    return imageController.getCommandMap(); // Delegating to the ImageController's command map
  }

  @Override
  public void run() {

  }

  @Override
  public void handleLoad(String[] args) {
    imageLabel = new JLabel();
    JFileChooser fileChooser = new JFileChooser();
    if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
      File selectedFile = fileChooser.getSelectedFile();
      if (selectedFile == null || !selectedFile.exists()) {
        showError("Error: File does not exist.");
        return;
      }

      try {
        currentImage = ImageIO.read(selectedFile); // Load the image
        if (currentImage != null) {
          imageLabel.setIcon(new ImageIcon(currentImage)); // Display the image
          imageLabel.revalidate();
          imageLabel.repaint();
        } else {
          showError("Error: Unsupported image format.");
        }
      } catch (IOException e) {
        showError("Error loading image: " + e.getMessage());
      }
    }
  }



  @Override
  public void handleSave(String[] args) {
    JFileChooser fileChooser = new JFileChooser(); // Create a file chooser for saving images
    int returnValue = fileChooser.showSaveDialog(null); // Show the dialog to choose a file
    if (returnValue == JFileChooser.APPROVE_OPTION) { // If a file is selected
      File selectedFile = fileChooser.getSelectedFile(); // Get the selected file
      String filename = selectedFile.getAbsolutePath(); // Get the absolute path of the file
      String key = JOptionPane.showInputDialog("Enter the key of the image to save:"); // Prompt for a key
      if (key != null && !key.trim().isEmpty()) { // Ensure the key is not empty
        imageController.handleSave(new String[]{"save", filename, key}); // Call handleSave in ImageController
      } else {
        showError("Invalid key! "); // Show error if the key is invalid
      }
    }
  }

  @Override
  public void handleBrighten(String[] args) {
    // Implementation for brightening images
  }

  @Override
  public void handleRGBSplit(String[] args) {
    // Implementation for RGB splitting
  }

  @Override
  public void handleCombine(String[] args) {
    // Implementation for combining images
  }

  @Override
  public void handleCompression(String[] args) {
    // Implementation for compressing images
  }

  @Override
  public void handleLevelsAdjust(String[] args) {
    // Implementation for adjusting levels
  }

  @Override
  public void handleSplit(String[] args) {
    // Implementation for splitting images
  }

  @Override
  public void handleScript(String[] args) {
    // Implementation for running scripts
  }

  @Override
  public void applyOperation(String[] args) {
    String operation = args[0];
    String srcKey = args[1];
    String destKey = args[2];

    if (operation != null && srcKey != null && destKey != null) { // Ensure all inputs are provided
      // Construct the args array for the operation
      String[] operationArgs = new String[]{operation, srcKey, destKey};
      imageController.applyOperation(operationArgs); // Call applyOperation in ImageController
    } else {
      showError("Invalid input! "); // Show error if any input is invalid
    }
  }

  @Override
  public void handleFlip(String[] args, Direction direction) {
    // Implementation for flipping images
  }

  @Override
  public void printMenu() {
    // Implementation for displaying the menu
  }

  private void showError(String message) {
    JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE); // Show error message
  }

  // Additional methods to handle other functionalities can be added here
}