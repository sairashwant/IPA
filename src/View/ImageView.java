package View;

import Controller.ImageController;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Consumer;

public class ImageView {

  private ImageController controller;
  private Scanner scanner;
  private final Map<String, Consumer<String[]>> commandMap;

  public Map<String, Consumer<String[]>> getCommandMap() {
    return commandMap;
  }

  public ImageView(ImageController controller) {
    this.controller = controller;
    this.scanner = new Scanner(System.in);
    this.commandMap = new HashMap<>();

    commandMap.put("load",this::handleLoad);
    commandMap.put("save",this::handleSave);
    commandMap.put("brighten",this::handleBrighten);
    commandMap.put("combine",this::handleCombine);
    commandMap.put("split",this::handleSplit);
    commandMap.put("blur",this::handleOperation);
    commandMap.put("sharpen",this::handleOperation);
    commandMap.put("horizontal-flip",this::handleOperation);
    commandMap.put("vertical-flip",this::handleOperation);
    commandMap.put("greyscale",this::handleOperation);
    commandMap.put("sepia",this::handleOperation);
    commandMap.put("exit", args -> System.exit(0));
    commandMap.put("run-script", this::handleScript);
  }


  public void run() {
    boolean running = true;
    while (running) {
      try {
        System.out.print("\nEnter command: ");
        String input = scanner.nextLine().trim();

        if (input.isEmpty()) {
          continue;
        }

        String[] parts = input.split("\\s+");
        String command = parts[0].toLowerCase();

        if (commandMap.containsKey(command)) {
          commandMap.get(command).accept(parts);
          if (command.equals("exit")) {
            running = false;
          }
        } else {
          System.out.println("Unknown command: " + command);
        }

      } catch (Exception e) {
        System.out.println("Error processing command: " + e.getMessage());
      }
    }
  }

  public void printMenu() {
    System.out.println("\nMenu");
    System.out.println("\tLoad Image");
    System.out.println("\tSave Image");
    System.out.println("\tblur");
    System.out.println("\thorizontal-flip");
    System.out.println("\tvertical-flip");
    System.out.println("\tgreyscale");
    System.out.println("\tsepia");
    System.out.println("\tsharpen");
    System.out.println("\tsplit");
    System.out.println("\tcombine");
    System.out.println("\tbrighten");
    System.out.println("\trun-script");
    System.out.println("\tExit Program");
  }

  private void handleLoad(String[] args) {
    if (args.length == 3) {
      controller.loadIMage(args[1], args[2]);
    } else {
      System.out.println("Invalid load command. Usage: load <key> <filename>");
    }
  }

  private void handleSave(String[] args) {
    if (args.length == 3) {
      controller.saveImage(args[1], args[2]);
    } else {
      System.out.println("Invalid save command. Usage: save <key> <filename>");
    }
  }

  private void handleOperation(String[] args) {
    if (args.length == 3) {
      controller.applyOperations(args[0], args[1], args[2]);
    } else {
      System.out.println("Invalid command. Usage: " + args[0] + " <srcKey> <destKey>");
    }
  }

  private void handleBrighten(String[] args) {
    if (args.length == 4) {
      try {
        int factor = Integer.parseInt(args[1]);
        controller.brighten(factor,args[2], args[3] );
      } catch (NumberFormatException e) {
        System.out.println("Invalid brighten factor. Please enter an integer.");
      }
    } else {
      System.out.println("Invalid brighten command. Usage: brighten <factor> <srcKey> <destKey> ");
    }
  }

  private void handleSplit(String[] args) {
    if (args.length == 5) {
      controller.split(args[1], args[2], args[3], args[4]);
    } else {
      System.out.println("Invalid split command. Usage: split <srcKey> <redKey> <greenKey> <blueKey>");
    }
  }


  private void handleCombine(String[] args) {
    if (args.length == 5) {
      controller.combine(args[1], args[2], args[3], args[4]);
    } else {
      System.out.println("Invalid combine command. Usage: combine <destKey> <redKey> <greenKey> <blueKey>");
    }
  }

  private void handleScript(String[] args) {
    if (args.length < 2) {
      System.out.println("Please provide a script file path");
      return;
    }
    System.out.println("Loading script from " + args[1]);
    ScriptReader scriptReader = new ScriptReader(this);
    scriptReader.readScript(args[1]);
  }

}
