package controller;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import model.Image;
import model.colorscheme.Pixels;
import model.colorscheme.RGBPixel;
import view.ImageProcessorGUI;

public class ImageGUIController {
  private final ImageController imageController;
  Image i1;

  public ImageGUIController(ImageController imageController) {
    this.imageController = imageController;
    i1= new Image();
  }

  /**
   * Handles the loading of an image file when triggered by the GUI.
   * This method opens a file chooser dialog, retrieves the selected file,
   * and instructs the ImageController to load the image.
   */
  public void handleLoad(ImageProcessorGUI gui) {
    // Use JFileChooser to allow user to select an image file
    JFileChooser fileChooser = new JFileChooser();
    int returnValue = fileChooser.showOpenDialog(null);

    // Check if the user selected a file
    if (returnValue == JFileChooser.APPROVE_OPTION) {
      File selectedFile = fileChooser.getSelectedFile();
      String filename = selectedFile.getAbsolutePath(); // Get the full path of the selected file

      // Prompt user for a key to store the image
      String key = JOptionPane.showInputDialog("Enter the key to store the image:");

      // Validate the key input
      if (key != null && !key.trim().isEmpty()) {
        try {
          // Call the controller's handleLoad method with the appropriate arguments
          imageController.handleLoad(new String[]{"load", filename, key});

          // After loading, retrieve the stored pixels using the key directly from the model
          i1.storePixels(key,ImageUtil.loadImage(filename));
          Pixels[][] pixels = i1.getStoredPixels(key);

          // Convert to BufferedImage and display the loaded image in the GUI
          BufferedImage image = convertPixelsToBufferedImage(pixels);
          gui.displayImage(image);
        } catch (IllegalArgumentException ex) {
          showError("Error loading image: " + ex.getMessage());
        } catch (Exception ex) {
          showError("An unexpected error occurred: " + ex.getMessage());
        }
      } else {
        showError("Invalid key! Please enter a valid key to store the image.");
      }
    }
  }

  /**
   * Converts a 2D array of Pixels to a BufferedImage.
   *
   * @param pixels the 2D array of Pixels to convert
   * @return the resulting BufferedImage
   */
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
        } else {
          throw new IllegalArgumentException("Expected an RGBPixel.");
        }
      }
    }

    return image;
  }

  /**
   * Displays an error message dialog with the specified message.
   *
   * @param message The error message to display.
   */
  private void showError(String message) {
    JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
  }
}