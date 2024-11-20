package controller;

import view.ImageProcessorGUI;

public interface ImageGUIControllerInterface extends ImageControllerInterface{

  public void handleShowOriginalImage();
  public void handleUndo();
  public void handleLoad(ImageProcessorGUI gui, String key, String filename);

}
