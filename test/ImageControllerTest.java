import static org.junit.Assert.assertEquals;

import controller.ImageController;
import model.Image;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for the {@link ImageController} class, specifically focused on verifying the behavior
 * of the controller when running scripts that perform various image operations. This test class
 * captures console output to validate that the expected messages are printed during the execution
 * of the script.
 *
 * <p>
 * The tests ensure that the controller correctly processes commands defined in a script file and
 * provides the appropriate feedback in the console output.
 * </p>
 *
 * @see ImageController
 * @see Image
 * @see ImageController
 */
public class ImageControllerTest {

  private ImageController controller;
  private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
  private final PrintStream originalOut = System.out;

  /**
   * Sets up the test environment by initializing the Image, ImageController, and ImageView
   * instances, and redirects standard output to capture console output for validation before each
   * test case is run.
   */
  @Before
  public void setUp() {
    System.setOut(new PrintStream(outputStreamCaptor));

    Image image = new Image();
    controller = new ImageController(image);
  }

  /**
   * Tests the execution of a script that performs various operations on the image. This test checks
   * if the output messages from the script execution match the expected output, validating the
   * correctness of the operations performed by the controller on the image.
   */
  @Test
  public void testRunScript() {
    String[] command = {"run-script", "Images/PNGScript.txt"};

    controller.getCommandMap().get(command[0]).accept(command);

    String expectedOutput = "Loading script from Images/PNGScript.txt\n"
        + "Loaded Image l1\n"
        + "Operation on l1\n"
        + "Saved Imagel1-red-component\n"
        + "Image saved as: res\\landscape-red-component.png\n"
        + "Operation on l1\n"
        + "Saved Imagel1-green-component\n"
        + "Image saved as: res\\landscape-green-component.png\n"
        + "Operation on l1\n"
        + "Saved Imagel1-blue-component\n"
        + "Image saved as: res\\landscape-blue-component.png\n"
        + "Operation on l1\n"
        + "Saved Imagel1-blur\n"
        + "Image saved as: res\\landscape-blur.png\n"
        + "Operation on l1\n"
        + "Saved Imagel1-sharper\n"
        + "Image saved as: res\\landscape-sharper.png\n"
        + "Brightened Image l1 by 20\n"
        + "Saved Imagel1-brighter\n"
        + "Image saved as: res\\landscape-brighter.png\n"
        + "Split Image l1into red , green and blue\n"
        + "Saved Imagel1-red-split\n"
        + "Image saved as: res\\landscape-red-split.png\n"
        + "Saved Imagel1-green-split\n"
        + "Image saved as: res\\landscape-green-split.png\n"
        + "Saved Imagel1-blue-split\n"
        + "Image saved as: res\\landscape-blue-split.png\n"
        + "Unknown command in Script: combine\n"
        + "Saved Imagel1-combine\n"
        + "Image has not been saved\n"
        + "Operation on l1\n"
        + "Saved Imagel1-vertical-flip\n"
        + "Image saved as: res\\landscape-vertical-flip.png\n"
        + "Operation on l1\n"
        + "Saved Imagel1-horizontal-flip\n"
        + "Image saved as: res\\landscape-horizontal-flip.png\n"
        + "Operation on l1\n"
        + "Saved Imagel1-greyscale\n"
        + "Image saved as: res\\landscape-greyscale.png\n"
        + "Operation on l1\n"
        + "Saved Imagel1-sepia\n"
        + "Image saved as: res\\landscape-sepia.png\n"
        + "Unknown command in Script: luma-component\n"
        + "Saved Imagel1-luma\n"
        + "Image has not been saved\n"
        + "Operation on l1\n"
        + "Saved Imagel1-value\n"
        + "Image saved as: res\\landscape-value.png\n"
        + "Operation on l1\n"
        + "Saved Imagel1-intensity\n"
        + "Image saved as: res\\landscape-intensity.png";

    String normalizedExpectedOutput = expectedOutput.replaceAll("\\r\\n?", "\n").trim();
    String normalizedCapturedOutput = outputStreamCaptor.toString().replaceAll("\\r\\n?", "\n")
        .trim();

    assertEquals(normalizedExpectedOutput, normalizedCapturedOutput);
  }

  /**
   * Restores the original standard output after each test case is run to prevent interference with
   * subsequent tests.
   */
  @After
  public void tearDown() {
    System.setOut(originalOut);
  }
}
