package view;

import java.awt.image.BufferedImage;
import model.Image;

public interface ImageProcessorGUIInterface {

  void showError(String error);
  
  void displayImage(BufferedImage image);

  void displayHistogram(BufferedImage histogram);

  void addWindowListenerToGUI();

  void showPreview(BufferedImage image, String operation);
}
