package controller;

import static org.junit.Assert.assertEquals;

import java.io.StringReader;
import java.util.Map;
import java.util.function.Consumer;

import model.EnhancedImage;
import model.imagetransformation.basicoperation.Flip.Direction;

import org.junit.Before;
import org.junit.Test;

public class ImageGUIControllerTest {

  private StringBuilder output;
  private ImageControllerMock mockImageController;

  @Before
  public void setUp() {
    output = new StringBuilder();

    // Initialize the mock controller
    mockImageController = new ImageControllerMock();
  }


  private class ImageControllerMock implements ImageControllerInterface {

    @Override
    public Map<String, Consumer<String[]>> getCommandMap() {
      output.append("Map has been called\n");
      return Map.of();
    }

    @Override
    public void run() {
      output.append("Running run\n");
    }

    @Override
    public void handleLoad(String[] args) {
      output.append("Load has been called.").append(args[2]).append("\n");
    }

    @Override
    public void handleSave(String[] args) {
      output.append("Save has been called.").append(args[2]).append("\n");
    }

    @Override
    public void handleBrighten(String[] args) {
      output.append("Brighten has been called.").append(args[1]).append("\n");
    }

    @Override
    public void handleRGBSplit(String[] args) {
      output.append("RGB split has been called.").append(args[1]).append("\n");
    }

    @Override
    public void handleCombine(String[] args) {
      output.append("Combine has been called.").append(args[1]).append("\n");
    }

    @Override
    public void handleCompression(String[] args) {
      output.append("Compression has been called.").append(args[1]).append("\n");
    }

    @Override
    public void handleLevelsAdjust(String[] args) {
      output.append("Levels adjust has been called.").append(args[1]).append("\n");
    }

    @Override
    public void handleSplit(String[] args) {
      output.append("Split has been called.").append(args[1]).append("\n");
    }

    @Override
    public void handleScript(String[] args) {
      output.append("Script has been called.").append(args[1]).append("\n");
    }

    @Override
    public void applyOperation(String[] args) {
      output.append("Operation has been called.").append(args[1]).append("\n");
    }

    @Override
    public void handleFlip(String[] args, Direction direction) {
      output.append("Flip has been called.").append(args[1]).append("\n");
    }

    @Override
    public void printMenu() {
      output.append("Menu has been called\n");
    }
  }

  @Test
  public void testHandleLoad() {
    String[] args = {"load", "imagePath", "testImage"};
    mockImageController.handleLoad(args);

    assertEquals("Load has been called.testImage\n", output.toString());
  }

  @Test
  public void testHandleSave() {
    String[] args = {"save", "imagePath", "testImage"};
    mockImageController.handleSave(args);

    assertEquals("Save has been called.testImage\n", output.toString());
  }

  @Test
  public void testHandleBrighten() {
    String[] args = {"brighten", "1.5", "testImage"};
    mockImageController.handleBrighten(args);

    assertEquals("Brighten has been called.1.5\n", output.toString());
  }

  @Test
  public void testHandleFlip() {
    String[] args = {"flip", "testImage", "horizontal"};
    Direction direction = Direction.HORIZONTAL;
    mockImageController.handleFlip(args, direction);

    assertEquals("Flip has been called.testImage\n", output.toString());
  }

  @Test
  public void testGetCommandMap() {
    mockImageController.getCommandMap();

    assertEquals("Map has been called\n", output.toString());
  }

  @Test
  public void testPrintMenu() {
    mockImageController.printMenu();

    assertEquals("Menu has been called\n", output.toString());
  }

  @Test
  public void testHandleRGBSplit() {
    String[] args = {"rgbsplit", "testImage"};
    mockImageController.handleRGBSplit(args);

    assertEquals("RGB split has been called.testImage\n", output.toString());
  }

  @Test
  public void testHandleCombine() {
    String[] args = {"combine", "testImage"};
    mockImageController.handleCombine(args);

    assertEquals("Combine has been called.testImage\n", output.toString());
  }

  @Test
  public void testHandleCompression() {
    String[] args = {"compress", "testImage"};
    mockImageController.handleCompression(args);

    assertEquals("Compression has been called.testImage\n", output.toString());
  }

  @Test
  public void testHandleLevelsAdjust() {
    String[] args = {"levels", "testImage"};
    mockImageController.handleLevelsAdjust(args);

    assertEquals("Levels adjust has been called.testImage\n", output.toString());
  }

  @Test
  public void testHandleSplit() {
    String[] args = {"split", "testImage"};
    mockImageController.handleSplit(args);

    assertEquals("Split has been called.testImage\n", output.toString());
  }

  @Test
  public void testHandleScript() {
    String[] args = {"script", "testScript"};
    mockImageController.handleScript(args);

    assertEquals("Script has been called.testScript\n", output.toString());
  }

  @Test
  public void testApplyOperation() {
    String[] args = {"operation", "testImage"};
    mockImageController.applyOperation(args);

    assertEquals("Operation has been called.testImage\n", output.toString());
  }

  @Test
  public void testRun() {
    mockImageController.run();

    assertEquals("Running run\n", output.toString());
  }

}
