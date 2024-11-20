import controller.ImageController;
import controller.ImageGUIController;
import model.EnhancedImage;
import view.ImageProcessorGUI;

import javax.swing.SwingUtilities;

public class Main {
  public static void main(String[] args) {
    if (args.length == 0) {
      loadGui();
    } else if (args.length == 2 && args[0].equals("-file")) {
      runScript(args[1]);
    } else if (args.length == 1 && args[0].equals("-text")) {
      launchInteractiveMode();
    } else {
      System.out.println("Invalid arguments. Use -file <file path> to run a script or -text to run the command line interface.");
    }
  }

  private static void loadGui(){
    EnhancedImage model = new EnhancedImage();
    ImageController imageController = new ImageController(model);
    ImageGUIController guiController = new ImageGUIController(model, imageController);
    SwingUtilities.invokeLater(() -> new ImageProcessorGUI(guiController));
  }

  private static void runScript(String scriptPath){
    EnhancedImage model = new EnhancedImage();
    ImageController imageController = new ImageController(model);
    imageController.handleScript(new String[]{"run-script", scriptPath});
  }

  private static void launchInteractiveMode() {
    EnhancedImage model = new EnhancedImage();
    ImageController imageController = new ImageController(model);
    imageController.printMenu();
    imageController.run();
  }
}