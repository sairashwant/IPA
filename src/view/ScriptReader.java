package view;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.function.Consumer;

/**
 * The ScriptReader class is responsible for reading commands from a script file and executing them
 * using the associated ImageView instance.
 */
class ScriptReader {


  private final Map<String, Consumer<String[]>> commandMap;

  /**
   * Constructs a ScriptReader with the specified ImageView.
   *
   * @param imageView the ImageView instance used to execute commands
   */
  ScriptReader(ImageView imageView) {
    this.commandMap = imageView.getCommandMap();
  }


  /**
   * Reads and executes commands from the specified script file. Each line in the script is treated
   * as a separate command. Lines that are empty or start with '#' are ignored. If a command is
   * unrecognized, an error message is printed to the console.
   *
   * @param scriptPath the path to the script file to be read
   */
  void readScript(String scriptPath) {
    try (BufferedReader reader = new BufferedReader(new FileReader(scriptPath))) {
      String line;

      while ((line = reader.readLine()) != null) {
        line = line.trim();

        if (line.isEmpty() || line.startsWith("#")) {
          continue;
        }

        String[] parts = line.split("\\s+");
        String command = parts[0].toLowerCase();

        if (commandMap.containsKey(command)) {
          commandMap.get(command).accept(parts);
        } else {
          System.out.println("Unknown command in Script: " + command);
        }
      }
    } catch (IOException e) {
      System.out.println("Error reading script : " + e.getMessage());
    }
  }
}
