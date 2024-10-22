package View;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.function.Consumer;

public class ScriptReader {

  private final ImageView imageView;
  private final Map<String, Consumer<String[]>> commandMap;

  public ScriptReader(ImageView imageView) {
    this.imageView = imageView;
    this.commandMap = imageView.getCommandMap();
  }

  public void readScript(String scriptPath){
    System.out.println("Before buffered Reader");
    try(BufferedReader reader = new BufferedReader(new FileReader(scriptPath))){
      System.out.println("After buffered Reader");
      String line = reader.readLine();
      System.out.println(line + "Before while");

      while ((line = reader.readLine()) != null) {
        line = line.trim();
        System.out.println(line + " After while");

        // Skip empty lines and comments
        if (line.isEmpty() || line.startsWith("#")) {
          continue;
        }

        String[] parts = line.split("\\s+"); // Fixed regex to split on whitespace
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
