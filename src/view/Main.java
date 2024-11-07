package view;

import controller.ImageController;
import controller.ImageControllerInterface;
import model.Image;
import model.ImageModel;

/**
 * The entry point of the image processing application. It initializes the model, controller, and
 * view, and starts the application by displaying the menu and processing user commands. This class
 * is responsible for setting up the necessary components and starting the main application loop.
 */
public class Main {

  /**
   * The main method that serves as the starting point for the application. It creates the necessary
   * components including the image model, the image controller, and the view. It then displays the
   * menu and enters the command processing loop.
   * <p>
   * This method initializes the ImageModel (the model representing the image and its operations),
   * the ImageController (which handles user commands and interacts with the model), and starts the
   * interaction by calling the controller to display the menu and begin processing user inputs.
   *
   * @param args command-line arguments (not used in this application)
   */
  public static void main(String[] args) {

    ImageModel model = new Image();
    ImageControllerInterface controller = new ImageController(model);
    controller.printMenu();
    controller.run();
  }
}
