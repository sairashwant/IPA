package controller;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import model.Image;
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
public class ImageScriptTest {

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
    String[] command = {"run-script", "Resources/PNGScript.txt"};

    controller.getCommandMap().get(command[0]).accept(command);

    String expectedOutput = "Loaded Image l1\n"
        + "Operation red-component on l1\n"
        + "Image saved as: res\\landscape-red-component.png\n"
        + "Saved Image l1-red-component\n"
        + "Operation green-component on l1\n"
        + "Image saved as: res\\landscape-green-component.png\n"
        + "Saved Image l1-green-component\n"
        + "Operation blue-component on l1\n"
        + "Image saved as: res\\landscape-blue-component.png\n"
        + "Saved Image l1-blue-component\n"
        + "Operation blur on l1\n"
        + "Image saved as: res\\landscape-blur.png\n"
        + "Saved Image l1-blur\n"
        + "Operation sharpen on l1\n"
        + "Image saved as: res\\landscape-sharper.png\n"
        + "Saved Image l1-sharper\n"
        + "Brightened Image l1 by 20\n"
        + "Image saved as: res\\landscape-brighter.png\n"
        + "Saved Image l1-brighter\n"
        + "Split Image l1 into red, green and blue\n"
        + "Image saved as: res\\landscape-red-split.png\n"
        + "Saved Image l1-red-split\n"
        + "Image saved as: res\\landscape-green-split.png\n"
        + "Saved Image l1-green-split\n"
        + "Image saved as: res\\landscape-blue-split.png\n"
        + "Saved Image l1-blue-split\n"
        + "Combined Image l1-red-split,l1-green-split and l1-blue-split\n"
        + "Image saved as: res\\landscape-combine.png\n"
        + "Saved Image l1-combine\n"
        + "Flipping image l1 horizontally\n"
        + "Image saved as: res\\landscape-vertical-flip.png\n"
        + "Saved Image l1-vertical-flip\n"
        + "Flipping image l1 horizontally\n"
        + "Image saved as: res\\landscape-horizontal-flip.png\n"
        + "Saved Image l1-horizontal-flip\n"
        + "Operation greyscale on l1\n"
        + "Image saved as: res\\landscape-greyscale.png\n"
        + "Saved Image l1-greyscale\n"
        + "Operation sepia on l1\n"
        + "Image saved as: res\\landscape-sepia.png\n"
        + "Saved Image l1-sepia\n"
        + "Operation luma-component on l1\n"
        + "Image saved as: res\\landscape-luma.png\n"
        + "Saved Image l1-luma\n"
        + "Operation value-component on l1\n"
        + "Image saved as: res\\landscape-value.png\n"
        + "Saved Image l1-value\n"
        + "Operation intensity-component on l1\n"
        + "Image saved as: res\\landscape-intensity.png\n"
        + "Saved Image l1-intensity\n"
        + "Script executed successfully: Resources/PNGScript.txt";

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
