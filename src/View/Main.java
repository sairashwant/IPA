package View;

import Controller.ImageController;
import Model.Image;

/**
 * The entry point of the image processing application.
 * It initializes the model, controller, and view,
 * and starts the application by displaying the menu and
 * processing user commands.
 */
public class Main {

 /**
  * The main method that serves as the starting point for the application.
  * It creates the necessary components including the image model,
  * the image controller, and the image view. It then displays
  * the menu and enters the command processing loop.
  *
  * @param args command-line arguments (not used in this application)
  */
 public static void main(String[] args) {
  Image model = new Image(); // Create an instance of the image model
  ImageController controller = new ImageController(model); // Create the controller with the model
  ImageView view = new ImageView(controller); // Create the view with the controller
  view.printMenu(); // Display the menu to the user
  view.run(); // Start processing user commands
 }
}
