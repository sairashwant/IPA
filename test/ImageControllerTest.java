import static org.junit.Assert.assertEquals;

import Controller.ImageController;
import Model.Image;
import View.ImageView;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ImageControllerTest {
  Image image;
  ImageController controller;
  ImageView view;
  private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
  private final PrintStream originalOut = System.out;

  @Before
  public void setUp() {
    System.setOut(new PrintStream(outputStreamCaptor));

    image = new Image();
    controller = new ImageController(image);
    view = new ImageView(controller);
  }

  @Test
  public void testRunScript() {
    String[] command = {"run-script", "Images/PNGScript.txt"};

    view.getCommandMap().get(command[0]).accept(command);

    String expectedOutput = "Loading script from Images/PNGScript.txt\n"
        + "Loaded Image l1\n"
        + "Operation on l1\n"
        + "Saved Imagel1-red-component\n"
        + "Image saved as: C:\\Development\\PDP\\Assignment_4_v2\\res\\landscape-red-component.png\n"
        + "Operation on l1\n"
        + "Saved Imagel1-green-component\n"
        + "Image saved as: C:\\Development\\PDP\\Assignment_4_v2\\res\\landscape-green-component.png\n"
        + "Operation on l1\n"
        + "Saved Imagel1-blue-component\n"
        + "Image saved as: C:\\Development\\PDP\\Assignment_4_v2\\res\\landscape-blue-component.png\n"
        + "Operation on l1\n"
        + "Saved Imagel1-blur\n"
        + "Image saved as: C:\\Development\\PDP\\Assignment_4_v2\\res\\landscape-blur.png\n"
        + "Operation on l1\n"
        + "Saved Imagel1-sharper\n"
        + "Image saved as: C:\\Development\\PDP\\Assignment_4_v2\\res\\landscape-sharper.png\n"
        + "Brightened Image l1 by 20\n"
        + "Saved Imagel1-brighter\n"
        + "Image saved as: C:\\Development\\PDP\\Assignment_4_v2\\res\\landscape-brighter.png\n"
        + "Split Image l1into red , green and blue\n"
        + "Saved Imagel1-red-split\n"
        + "Image saved as: C:\\Development\\PDP\\Assignment_4_v2\\res\\landscape-red-split.png\n"
        + "Saved Imagel1-green-split\n"
        + "Image saved as: C:\\Development\\PDP\\Assignment_4_v2\\res\\landscape-green-split.png\n"
        + "Saved Imagel1-blue-split\n"
        + "Image saved as: C:\\Development\\PDP\\Assignment_4_v2\\res\\landscape-blue-split.png\n"
        + "Unknown command in Script: combine\n"
        + "Saved Imagel1-combine\n"
        + "Image has not been saved\n"
        + "Operation on l1\n"
        + "Saved Imagel1-vertical-flip\n"
        + "Image saved as: C:\\Development\\PDP\\Assignment_4_v2\\res\\landscape-vertical-flip.png\n"
        + "Operation on l1\n"
        + "Saved Imagel1-horizontal-flip\n"
        + "Image saved as: C:\\Development\\PDP\\Assignment_4_v2\\res\\landscape-horizontal-flip.png\n"
        + "Operation on l1\n"
        + "Saved Imagel1-greyscale\n"
        + "Image saved as: C:\\Development\\PDP\\Assignment_4_v2\\res\\landscape-greyscale.png\n"
        + "Operation on l1\n"
        + "Saved Imagel1-sepia\n"
        + "Image saved as: C:\\Development\\PDP\\Assignment_4_v2\\res\\landscape-sepia.png\n"
        + "Operation on l1\n"
        + "Saved Imagel1-luma\n"
        + "Image saved as: C:\\Development\\PDP\\Assignment_4_v2\\res\\landscape-luma.png\n"
        + "Operation on l1\n"
        + "Saved Imagel1-value\n"
        + "Image saved as: C:\\Development\\PDP\\Assignment_4_v2\\res\\landscape-value.png\n"
        + "Operation on l1\n"
        + "Saved Imagel1-intensity\n"
        + "Image saved as: C:\\Development\\PDP\\Assignment_4_v2\\res\\landscape-intensity.png";

    String normalizedExpectedOutput = expectedOutput.replaceAll("\\r\\n?", "\n").trim();
    String normalizedCapturedOutput = outputStreamCaptor.toString().replaceAll("\\r\\n?", "\n").trim();

    assertEquals(normalizedExpectedOutput, normalizedCapturedOutput);
  }

  @After
  public void tearDown() {
    System.setOut(originalOut);
  }
}
