package controller;

import java.io.FileFilter;
import javax.swing.JFileChooser;
import view.ImageProcessorGUI;

public interface ImageGUIControllerInterface extends ImageControllerInterface{

  public void handleShowOriginalImage();
  public void handleUndo();
  public void handleLoad(ImageProcessorGUI gui, String key, String filename);
  public void handleSave(int userSelection, JFileChooser fileChooser, javax.swing.filechooser.FileFilter pngFilter,javax.swing.filechooser.FileFilter jpgFilter,javax.swing.filechooser.FileFilter ppmFilter);

}
