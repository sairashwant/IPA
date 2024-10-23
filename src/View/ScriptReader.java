package View;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.function.Consumer;

/**
 *
 */
 class ScriptReader {

  private final ImageView imageView;
  private final Map<String, Consumer<String[]>> commandMap;

  /**
   *
   * @param imageView
   */
   ScriptReader(ImageView imageView) {
    this.imageView = imageView;
    this.commandMap = imageView.getCommandMap();
  }

  /**
   *
   * @param scriptPath
   */
   void readScript(String scriptPath){
    try(BufferedReader reader = new BufferedReader(new FileReader(scriptPath))){
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
    }catch (IOException e){
      System.out.println("Error reading script : " + e.getMessage());
    }
  }
}
