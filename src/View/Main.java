package View;

import Controller.ImageController;
import Model.Image;

public class Main {
  public static void main(String[] args) {
    Image model = new Image();
    ImageController controller = new ImageController(model);
    ImageView view = new ImageView(controller);
    view.printMenu();
    view.run();
  }
}