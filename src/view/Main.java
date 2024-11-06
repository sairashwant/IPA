package view;

import controller.ImageController;
import controller.ImageControllerInterface;
import model.Image;
import model.ImageModel;
/**
 * The entry point of the image processing application. It initializes the model, controller, and
 * view, and starts the application by displaying the menu and processing user commands.
 */
public class Main {

  /**
   * The main method that serves as the starting point for the application. It creates the necessary
   * components including the image model, the image controller, and the view. It then
   * displays the menu and enters the command processing loop.
   *
   * @param args command-line arguments (not used in this application)
   */
  public static void main(String[] args) {
    ImageModel model =  new Image(); // Create an instance of the image model
    ImageControllerInterface controller = new ImageController(model); // Create the controller with model and view
    controller.run(); // Start the application
  }
}
