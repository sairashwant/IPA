package controller;

import model.Image;
import model.colorscheme.Pixels;
import model.colorscheme.RGBPixel;
import model.imagetransformation.basicoperation.Flip.Direction;
import view.ImageProcessorGUI;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Map;
import java.util.function.Consumer;

public class ImageGUIController implements ImageGUIControllerInterface {
  String latest;
  private final ImageController imageController;
  ImageProcessorGUI gui;
  Image i1;

  public ImageGUIController(ImageController imageController) {
    this.imageController = imageController;
    i1 = new Image();
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
    displayImageByKey(gui, key);
    latest = dest;
  }

  public void displayImageByKey(ImageProcessorGUI gui, String key) {
    try {
      Pixels[][] pixels = i1.getStoredPixels(key);
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
  public void handleLoad(String[] args) {

  }

  @Override
  public void handleSave(String[] args) {}

  @Override
  public void handleBrighten(String[] args) {}

  @Override
  public void handleRGBSplit(String[] args) {}

  @Override
  public void handleCombine(String[] args) {}

  @Override
  public void handleCompression(String[] args) {}

  @Override
  public void handleLevelsAdjust(String[] args) {}

  @Override
  public void handleSplit(String[] args) {}

  @Override
  public void handleScript(String[] args) {}

  @Override
  public void handleFlip(String[] args, Direction direction) {}

  @Override
  public void printMenu() {

  }
}
