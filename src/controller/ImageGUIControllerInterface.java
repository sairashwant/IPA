package controller;

import javax.swing.JFileChooser;
import view.ImageProcessorGUI;

public interface ImageGUIControllerInterface extends ImageControllerInterface{

  void handleShowOriginalImage();
  void handleUndo();
  void handleLoad(ImageProcessorGUI gui, String key, String filename);
  void handleSave(int userSelection, JFileChooser fileChooser,
      javax.swing.filechooser.FileFilter pngFilter, javax.swing.filechooser.FileFilter jpgFilter,
      javax.swing.filechooser.FileFilter ppmFilter);
  public String getLatestKey();
  public void applyHistogram(String[] args);
  public void displayImageByKey(ImageProcessorGUI gui, String key);
}
