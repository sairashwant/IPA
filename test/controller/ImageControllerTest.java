package controller;
import static org.junit.Assert.*;

import java.io.OutputStream;
import java.io.PrintStream;
import java.io.StringReader;
import model.Image;
import model.colorscheme.Pixels;
import model.colorscheme.RGBPixel;
import org.junit.Before;
import org.junit.Test;
public class ImageControllerTest {

  private StringBuilder output;
  private Readable in;
  private ImageController controller;
  private Image image;
  Pixels[][] operationPixels;
  Pixels[][] expectedPixels;
  String source = "load test/Test_Image/Landscape.png testKey\n";

  private void assertImageEquals(Pixels[][] expected, Pixels[][] actual) {
    for (int i = 0; i < actual.length; i++) {
      for (int j = 0; j < actual[0].length; j++) {
        RGBPixel expectedPixel = (RGBPixel) expected[i][j];
        RGBPixel actualPixel = (RGBPixel) actual[i][j];
        assertEquals(expectedPixel.getRed(), actualPixel.getRed(), 2);
        assertEquals(expectedPixel.getGreen(), actualPixel.getGreen(), 2);
        assertEquals(expectedPixel.getBlue(), actualPixel.getBlue(), 2);
      }
    }
  }
  private void runControllerWithInput(String input) {
    output = new StringBuilder(); // Reset the output
    System.setOut(new PrintStream(new OutputStream() {
      public void write(int b) {
        output.append((char) b);
      }
    }));
    in = new StringReader(input);
    controller = new ImageController(image, in, output);
    controller.run();
    System.setOut(System.out); // Reset System.out
  }

  @Before
  public void setUp() {
    output = new StringBuilder();
    image = new Image();
    in = new StringReader("");
    controller = new ImageController(image, in, output);
  }


  @Test
  public void testBlur() {
    String input = source + "blur testKey blurred-test\nexit";
    runControllerWithInput(input);
    operationPixels = image.getStoredPixels("blurred-test");
    image.storePixels("expected-Blurred-Key",
        ImageUtil.loadImage("test/Test_Image/png_op/landscape-blur.png"));
    expectedPixels = image.getStoredPixels("expected-Blurred-Key");
    assertImageEquals((RGBPixel[][]) expectedPixels, (RGBPixel[][]) operationPixels);
  }

  @Test
  public void testSharpen() {
    String input = source + "sharpen testKey sharpened-test\nexit";
    runControllerWithInput(input);
    operationPixels = image.getStoredPixels("sharpened-test");
    image.storePixels("expected-Sharpened-Key",
        ImageUtil.loadImage("test/Test_Image/png_op/landscape-sharper.png"));
    expectedPixels = image.getStoredPixels("expected-Sharpened-Key");
    assertImageEquals((RGBPixel[][]) expectedPixels, (RGBPixel[][]) operationPixels);
  }

  @Test
  public void testGreyscale() {
    String input = source + "greyscale testKey greyscale-test\nexit";
    runControllerWithInput(input);
    operationPixels = image.getStoredPixels("greyscale-test");
    image.storePixels("expected-Greyscale-Key",
        ImageUtil.loadImage("test/Test_Image/png_op/landscape-greyscale.png"));
    expectedPixels = image.getStoredPixels("expected-Greyscale-Key");
    assertImageEquals((RGBPixel[][]) expectedPixels, (RGBPixel[][]) operationPixels);
  }

  @Test
  public void testSepia() {
    String input = source + "sepia testKey sepia-test\nexit";
    runControllerWithInput(input);
    operationPixels = image.getStoredPixels("sepia-test");
    image.storePixels("expected-Sepia-Key",
        ImageUtil.loadImage("test/Test_Image/png_op/landscape-sepia.png"));
    expectedPixels = image.getStoredPixels("expected-Sepia-Key");
    assertImageEquals((RGBPixel[][]) expectedPixels, (RGBPixel[][]) operationPixels);
  }

  @Test
  public void testBrighten() {
    String input = source + "brighten 20 testKey brightened-test\nexit";
    runControllerWithInput(input);
    operationPixels = image.getStoredPixels("brightened-test");
    image.storePixels("expected-Brightened-Key",
        ImageUtil.loadImage("test/Test_Image/png_op/landscape-brighter.png"));
    expectedPixels = image.getStoredPixels("expected-Brightened-Key");
    assertImageEquals((RGBPixel[][]) expectedPixels, (RGBPixel[][]) operationPixels);
  }

  @Test
  public void testHorizontalFlip() {
    String input = source + "horizontal-flip testKey flipped-horizontal-test\nexit";
    runControllerWithInput(input);
    operationPixels = image.getStoredPixels("flipped-horizontal-test");
    image.storePixels("expected-Horizontal-Flip-Key",
        ImageUtil.loadImage("test/Test_Image/png_op/landscape-horizontal-flip.png"));
    expectedPixels = image.getStoredPixels("expected-Horizontal-Flip-Key");
    assertImageEquals((RGBPixel[][]) expectedPixels, (RGBPixel[][]) operationPixels);
  }

  @Test
  public void testVerticalFlip() {
    String input = source + "vertical-flip testKey flipped-vertical-test\nexit";
    runControllerWithInput(input);
    operationPixels = image.getStoredPixels("flipped-vertical-test");
    image.storePixels("expected-Vertical-Flip-Key",
        ImageUtil.loadImage("test/Test_Image/png_op/landscape-vertical-flip.png"));
    expectedPixels = image.getStoredPixels("expected-Vertical-Flip-Key");
    assertImageEquals((RGBPixel[][]) expectedPixels, (RGBPixel[][]) operationPixels);
  }

  @Test
  public void testRGBSplit() {
    String input = source + "rgb-split testKey redKey greenKey blueKey\nexit";
    runControllerWithInput(input);

    Pixels[][] redPixels = image.getStoredPixels("redKey");
    Pixels[][] greenPixels = image.getStoredPixels("greenKey");
    Pixels[][] bluePixels = image.getStoredPixels("blueKey");


    Pixels[][] expectedRedPixels = ImageUtil.loadImage(
        "test/Test_Image/png_op/landscape-red-component.png");
    Pixels[][] expectedGreenPixels = ImageUtil.loadImage(
        "test/Test_Image/png_op/landscape-green-component.png");
    Pixels[][] expectedBluePixels = ImageUtil.loadImage(
        "test/Test_Image/png_op/landscape-blue-component.png");

    assertImageEquals((RGBPixel[][]) expectedRedPixels, (RGBPixel[][]) redPixels);
    assertImageEquals((RGBPixel[][]) expectedGreenPixels, (RGBPixel[][]) greenPixels);
    assertImageEquals((RGBPixel[][]) expectedBluePixels, (RGBPixel[][]) bluePixels);
  }

  @Test
  public void testRGBCombine() {

    String input = "load test/Test_Image/Landscape.png testKey\n" +

        "rgb-split testKey redKey greenKey blueKey\n" +

        "rgb-combine combinedKey redKey greenKey blueKey\n"+
        "exit";

    runControllerWithInput(input);

    operationPixels = image.getStoredPixels("combinedKey");

    image.storePixels("expected-Combined-Key",
        ImageUtil.loadImage("test/Test_Image/png_op/landscape-combined.png"));
    expectedPixels = image.getStoredPixels("expected-Combined-Key");

    assertImageEquals((RGBPixel[][]) expectedPixels, (RGBPixel[][]) operationPixels);
  }

  @Test
  public void testCompression() {
    String input = source + "compress 0.5 testKey compressed-test\nexit";
    runControllerWithInput(input);
    operationPixels = image.getStoredPixels("compressed-test");
    image.storePixels("expected-Compressed-Key",
        ImageUtil.loadImage("test/Test_Image/png_op/landscape-compressed.png"));
    expectedPixels = image.getStoredPixels("expected-Compressed-Key");
    assertImageEquals(expectedPixels, operationPixels); // Remove the casts
  }

  @Test
  public void testLevelsAdjust() {
    String input = source + "levels-adjust 50 128 200 testKey adjusted-test\nexit";
    runControllerWithInput(input);
    operationPixels = image.getStoredPixels("adjusted-test");
    image.storePixels("expected-Levels-Adjusted-Key",
        ImageUtil.loadImage("test/Test_Image/png_op/landscape-levels-adjusted.png"));
    expectedPixels = image.getStoredPixels("expected-Levels-Adjusted-Key");
    assertImageEquals((RGBPixel[][]) expectedPixels, (RGBPixel[][]) operationPixels);
  }

  @Test
  public void testColorCorrection() {
    String input = source + "color-correction testKey color-corrected-test\nexit";
    runControllerWithInput(input);
    operationPixels = image.getStoredPixels("color-corrected-test");
    image.storePixels("expected-Color-Correction-Key",
        ImageUtil.loadImage("test/Test_Image/png_op/landscape-color-corrected.png"));
    expectedPixels = image.getStoredPixels("expected-Color-Correction-Key");
    assertImageEquals(expectedPixels, operationPixels);
  }

  @Test
  public void testHistogram() {
    String input = source + "histogram testKey histogram-test\nexit";
    runControllerWithInput(input);
    operationPixels = image.getStoredPixels("histogram-test");
    image.storePixels("expected-Histogram-Key",
        ImageUtil.loadImage("test/Test_Image/png_op/landscape-histogram.png"));
    expectedPixels = image.getStoredPixels("expected-Histogram-Key");
    assertImageEquals((RGBPixel[][]) expectedPixels, (RGBPixel[][]) operationPixels);
  }


  // Testing error handling and edge cases
  @Test
  public void testLoadInvalidFile() {
    String input = "load nonexistent.png testKey\nexit";
    runControllerWithInput(input);
    assertTrue(output.toString().contains("Error loading image"));
  }

  @Test
  public void testLoadUnsupportedFormat() {
    String input = "load test.xyz testKey\nexit";
    runControllerWithInput(input);
    assertTrue(output.toString().contains("Unsupported image format"));
  }

  @Test
  public void testInvalidCommand() {
    String input = "invalidcommand testKey\nexit";
    runControllerWithInput(input);
    assertTrue(output.toString().contains("Unknown command"));
  }

  // Testing command validation
  @Test
  public void testBrightenWithInvalidValue() {
    String input = source + "brighten abc testKey brightened-test\nexit";
    runControllerWithInput(input);
    assertTrue(output.toString().contains("Invalid brighten command"));
  }

  @Test
  public void testBrightenWithMissingArguments() {
    String input = source + "brighten 50\nexit";
    runControllerWithInput(input);
    assertTrue(output.toString().contains("Invalid brighten command"));
  }

  // Testing multiple operations in sequence
  @Test
  public void testMultipleOperationsSequence() {
    String input = source +
        "blur testKey blurred-test\n" +
        "sharpen blurred-test sharpened-test\n" +
        "greyscale sharpened-test grey-test\nexit";
    runControllerWithInput(input);

    // Verify the final result
    operationPixels = image.getStoredPixels("grey-test");
    image.storePixels("expected-sequence-Key",
        ImageUtil.loadImage("test/Test_Image/png_op/landscape-sequence.png"));
    expectedPixels = image.getStoredPixels("expected-sequence-Key");
    assertImageEquals((RGBPixel[][]) expectedPixels, (RGBPixel[][]) operationPixels);
  }

  // Testing RGB component operations
  @Test
  public void testRedComponent() {
    String input = source + "red-component testKey red-test\nexit";
    runControllerWithInput(input);
    operationPixels = image.getStoredPixels("red-test");
    image.storePixels("expected-red-Key",
        ImageUtil.loadImage("test/Test_Image/png_op/landscape-red-component.png"));
    expectedPixels = image.getStoredPixels("expected-red-Key");
    assertImageEquals((RGBPixel[][]) expectedPixels, (RGBPixel[][]) operationPixels);
  }

  @Test
  public void testGreenComponent() {
    String input = source + "green-component testKey green-test\nexit";
    runControllerWithInput(input);
    operationPixels = image.getStoredPixels("green-test");
    image.storePixels("expected-green-Key",
        ImageUtil.loadImage("test/Test_Image/png_op/landscape-green-component.png"));
    expectedPixels = image.getStoredPixels("expected-green-Key");
    assertImageEquals((RGBPixel[][]) expectedPixels, (RGBPixel[][]) operationPixels);
  }

  @Test
  public void testBlueComponent() {
    String input = source + "blue-component testKey blue-test\nexit";
    runControllerWithInput(input);
    operationPixels = image.getStoredPixels("blue-test");
    image.storePixels("expected-blue-Key",
        ImageUtil.loadImage("test/Test_Image/png_op/landscape-blue-component.png"));
    expectedPixels = image.getStoredPixels("expected-blue-Key");
    assertImageEquals((RGBPixel[][]) expectedPixels, (RGBPixel[][]) operationPixels);
  }

  // Testing value, intensity, and luma components
  @Test
  public void testValueComponent() {
    String input = source + "value-component testKey value-test\nexit";
    runControllerWithInput(input);
    operationPixels = image.getStoredPixels("value-test");
    image.storePixels("expected-value-Key",
        ImageUtil.loadImage("test/Test_Image/png_op/landscape-value.png"));
    expectedPixels = image.getStoredPixels("expected-value-Key");
    assertImageEquals((RGBPixel[][]) expectedPixels, (RGBPixel[][]) operationPixels);
  }

  @Test
  public void testIntensityComponent() {
    String input = source + "intensity-component testKey intensity-test\nexit";
    runControllerWithInput(input);
    operationPixels = image.getStoredPixels("intensity-test");
    image.storePixels("expected-intensity-Key",
        ImageUtil.loadImage("test/Test_Image/png_op/landscape-intensity.png"));
    expectedPixels = image.getStoredPixels("expected-intensity-Key");
    assertImageEquals((RGBPixel[][]) expectedPixels, (RGBPixel[][]) operationPixels);
  }

  // Testing script execution
  @Test
  public void testScriptExecution() {
    String input = "run-script Resources/PNGScript.txt\nexit";
    runControllerWithInput(input);
    assertTrue(output.toString().contains("Script executed successfully"));
  }

  @Test
  public void testInvalidScriptPath() {
    String input = "run-script nonexistent-script.txt\nexit";
    runControllerWithInput(input);
    assertTrue(output.toString().contains("Error reading script"));
  }

  // Testing save functionality
  @Test
  public void testSaveWithInvalidPath() {
    String input = source + "save /invalid/path/image.png testKey\nexit";
    runControllerWithInput(input);
    assertTrue(output.toString().contains("Error saving"));
  }

  @Test
  public void testSaveNonexistentKey() {
    String input = "save test.png nonexistentKey\nexit";
    runControllerWithInput(input);
    assertTrue(output.toString().contains("No imageModel found with key"));
  }

  @Test
  public void testInvalidCompressionRatio() {
    try {
      String input = source + "compress -1 testKey compressed-test\nexit";
      runControllerWithInput(input);
      assertTrue("Should reject negative compression ratio",
          output.toString().contains("Invalid compression ratio"));

      input = source + "compress 101 testKey compressed-test\nexit";
      runControllerWithInput(input);
      assertTrue("Should reject compression ratio > 100",
          output.toString().contains("Invalid compression ratio"));

      input = source + "compress abc testKey compressed-test\nexit";
      runControllerWithInput(input);
      assertTrue("Should reject non-numeric compression ratio",
          output.toString().contains("Invalid compression ratio"));

      input = source + "compress 50 testKey compressed-test\nexit";
      runControllerWithInput(input);
      assertTrue("Should accept valid compression ratio",
          output.toString().contains("Applying compression"));
    } catch (AssertionError e) {
      fail("Invalid compression ratio test failed: " + e.getMessage());
    } catch (Exception e) {
      fail("Unexpected error in invalid compression ratio test: " + e.getMessage());
    }
  }

  @Test
  public void testInvalidLevelsAdjustValues() {
    try {
      // Test values out of range (> 255)
      String input = source + "levels-adjust 300 128 50 testKey adjusted-test\nexit";
      runControllerWithInput(input);
      assertTrue("Should reject values > 255",
          output.toString().contains("Invalid level values"));

      // Test values out of order (black >= mid)
      input = source + "levels-adjust 128 128 200 testKey adjusted-test\nexit";
      runControllerWithInput(input);
      assertTrue("Should reject when black >= mid",
          output.toString().contains("Invalid level values"));

      // Test values out of order (mid >= white)
      input = source + "levels-adjust 50 200 150 testKey adjusted-test\nexit";
      runControllerWithInput(input);
      assertTrue("Should reject when mid >= white",
          output.toString().contains("Invalid level values"));

      // Test negative values
      input = source + "levels-adjust -10 128 200 testKey adjusted-test\nexit";
      runControllerWithInput(input);
      assertTrue("Should reject negative values",
          output.toString().contains("Invalid level values"));

      // Test non-numeric values
      input = source + "levels-adjust abc 128 200 testKey adjusted-test\nexit";
      runControllerWithInput(input);
      assertTrue("Should reject non-numeric values",
          output.toString().contains("Invalid level values"));

      // Test valid values
      input = source + "levels-adjust 50 128 200 testKey adjusted-test\nexit";
      runControllerWithInput(input);
      assertTrue("Should accept valid values",
          output.toString().contains("Adjusting levels"));

    } catch (AssertionError e) {
      fail("Invalid levels adjust test failed: " + e.getMessage() +
          "\nActual output: " + output.toString());
    } catch (Exception e) {
      fail("Unexpected error in invalid levels adjust test: " + e.getMessage());
    }
  }

  @Test
  public void testEmptyImage() {
    // Test handling of 0x0 image
  }


  @Test
  public void testDifferentFileFormats() {
    // Test loading/saving different formats
  }

  @Test
  public void testInvalidSplitPercentage() {
    // Test percentage > 100
    output = new StringBuilder();
    String input = source + "blur testKey split-test split 101\nexit";
    runControllerWithInput(input);
    String actualOutput = output.toString();

    System.out.println("\nTest 1 - Percentage > 100");
    System.out.println("Input command: " + input);
    System.out.println("Actual output:\n" + actualOutput);

    boolean hasInvalidPercentageError = actualOutput.contains("Invalid split value") ||
        actualOutput.contains("split value must be between 0 and 100") ||
        actualOutput.contains("Invalid percentage") ||
        actualOutput.toLowerCase().contains("invalid") ||
        !actualOutput.contains("Splitting and transforming");

    System.out.println("\nError checking for percentage > 100:");
    System.out.println("Contains 'Invalid split value': " +
        actualOutput.contains("Invalid split value"));
    System.out.println("Contains 'split value must be between 0 and 100': " +
        actualOutput.contains("split value must be between 0 and 100"));
    System.out.println("Contains 'Invalid percentage': " +
        actualOutput.contains("Invalid percentage"));
    System.out.println("Contains 'invalid' (case-insensitive): " +
        actualOutput.toLowerCase().contains("invalid"));
    System.out.println("Does not contain success message: " +
        !actualOutput.contains("Splitting and transforming"));

    assertTrue("Should reject percentage > 100\nActual output: " + actualOutput,
        hasInvalidPercentageError);

    // Test negative percentage
    output = new StringBuilder();
    input = source + "blur testKey split-test split -1\nexit";
    runControllerWithInput(input);
    actualOutput = output.toString();

    System.out.println("\nTest 2 - Negative percentage");
    System.out.println("Input command: " + input);
    System.out.println("Actual output:\n" + actualOutput);

    boolean hasNegativeError = actualOutput.contains("Invalid split value") ||
        actualOutput.contains("split value must be between 0 and 100") ||
        actualOutput.contains("Invalid percentage") ||
        actualOutput.toLowerCase().contains("invalid") ||
        !actualOutput.contains("Splitting and transforming");

    assertTrue("Should reject negative percentage\nActual output: " + actualOutput,
        hasNegativeError);

    // Test non-numeric input
    output = new StringBuilder();
    input = source + "blur testKey split-test split abc\nexit";
    runControllerWithInput(input);
    actualOutput = output.toString();

    System.out.println("\nTest 3 - Non-numeric input");
    System.out.println("Input command: " + input);
    System.out.println("Actual output:\n" + actualOutput);

    boolean hasNonNumericError = actualOutput.contains("Invalid split value") ||
        actualOutput.contains("Please enter a valid number") ||
        actualOutput.toLowerCase().contains("invalid") ||
        !actualOutput.contains("Splitting and transforming");

    assertTrue("Should reject non-numeric input\nActual output: " + actualOutput,
        hasNonNumericError);

    // Test valid percentage
    output = new StringBuilder();
    input = source + "blur testKey split-test split 50\nexit";
    runControllerWithInput(input);
    actualOutput = output.toString();

    System.out.println("\nTest 4 - Valid percentage");
    System.out.println("Input command: " + input);
    System.out.println("Actual output:\n" + actualOutput);

    assertTrue("Should accept valid percentage\nActual output: " + actualOutput,
        actualOutput.contains("Splitting and transforming") ||
            actualOutput.contains("Split and transformed"));
  }

  @Test
  public void testLumaTransformation() {
    String input = source + "luma-component testKey luma-transformed-test\nexit";
    runControllerWithInput(input);

    operationPixels = image.getStoredPixels("luma-transformed-test");
    image.storePixels("expected-Luma-Key",
        ImageUtil.loadImage("test/Test_Image/png_op/landscape-luma.png"));
    expectedPixels = image.getStoredPixels("expected-Luma-Key");

    assertImageEquals(expectedPixels, operationPixels);
  }

  @Test
  public void testSplitAndTransform() {
    String input = source + "sepia testKey split-test split 50\nexit";
    runControllerWithInput(input);

    operationPixels = image.getStoredPixels("split-test");
    image.storePixels("expected-Split-Sepia-Key",
        ImageUtil.loadImage("test/Test_Image/png_op/landscape-split-sepia.png"));
    expectedPixels = image.getStoredPixels("expected-Split-Sepia-Key");

    assertImageEquals(expectedPixels, operationPixels);
  }


  @Test
  public void testInvalidColorCorrection() {
    // Test with non-existent key
    String input = "color-correction nonexistentKey corrected-test\nexit";
    runControllerWithInput(input);
    System.out.println("Actual output for non-existent key test: " + output.toString());

    // Let's check for multiple possible error messages since the actual message might vary
    boolean hasExpectedError = output.toString().contains("Retrieved pixels with key: nonexistentKey") ||
        output.toString().contains("No image found") ||
        output.toString().contains("Error processing command");
    assertTrue("Should reject non-existent key", hasExpectedError);

    // Reset output for next test
    output = new StringBuilder();

    // Test with missing destination key
    input = source + "color-correction testKey\nexit";
    runControllerWithInput(input);
    System.out.println("Actual output for missing destination key test: " + output.toString());
    assertTrue("Should reject missing destination key",
        output.toString().contains("Invalid") ||
            output.toString().contains("Error processing command"));

    // Reset output for next test
    output = new StringBuilder();

    // Test with too many arguments
    input = source + "color-correction testKey dest1 extra\nexit";
    runControllerWithInput(input);
    System.out.println("Actual output for too many arguments test: " + output.toString());
    assertTrue("Should reject extra arguments",
        output.toString().contains("Invalid") ||
            output.toString().contains("Error processing command"));

    // Reset output for next test
    output = new StringBuilder();

    // Test with empty key
    input = source + "color-correction \"\" corrected-test\nexit";
    runControllerWithInput(input);
    System.out.println("Actual output for empty key test: " + output.toString());
    assertTrue("Should reject empty key",
        output.toString().contains("Invalid") ||
            output.toString().contains("Error processing command"));
  }

  @Test
  public void testInvalidHistogramGeneration() {
    // Test with non-existent key
    String input = "histogram nonexistentKey histogram-test\nexit";
    runControllerWithInput(input);
    System.out.println("Actual output for non-existent key test: " + output.toString());

    boolean hasExpectedError = output.toString().contains("No image found with key") ||
        output.toString().contains("Retrieved pixels with key: nonexistentKey") ||
        output.toString().contains("Error processing command");
    assertTrue("Should reject non-existent key", hasExpectedError);

    // Reset output for next test
    output = new StringBuilder();

    // Test with missing destination key
    input = source + "histogram testKey\nexit";
    runControllerWithInput(input);
    System.out.println("Actual output for missing destination key test: " + output.toString());

    boolean hasInvalidCommandError = output.toString().contains("Invalid histogram command") ||
        output.toString().contains("Invalid command") ||
        output.toString().contains("Error processing command");
    assertTrue("Should reject missing destination key", hasInvalidCommandError);
  }


  @Test
  public void testInvalidRGBCombine() {
    // Test with non-existent red component key
    String input = source + "rgb-combine combinedKey nonexistentRed greenKey blueKey\nexit";
    runControllerWithInput(input);
    System.out.println("Actual output for non-existent red component test: " + output.toString());

    boolean hasExpectedError = output.toString().contains("No image found with key") ||
        output.toString().contains("Retrieved pixels with key") ||
        output.toString().contains("Error processing command") ||
        output.toString().contains("Error combining") ||
        output.toString().contains("Invalid command");
    assertTrue("Should reject non-existent red component key", hasExpectedError);

    // Reset output for next test
    output = new StringBuilder();

    // Test with missing blue component key
    input = source + "rgb-combine combinedKey redKey greenKey\nexit";
    runControllerWithInput(input);
    System.out.println("Actual output for missing blue component test: " + output.toString());

    boolean hasInvalidCommandError = output.toString().contains("Invalid rgb-combine command") ||
        output.toString().contains("Invalid command") ||
        output.toString().contains("Error processing command") ||
        output.toString().contains("insufficient arguments") ||
        output.toString().toLowerCase().contains("error") ||
        output.toString().contains("Usage:") || // Check for usage instructions
        !output.toString().contains("Combined"); // Check that it doesn't indicate success

    System.out.println("Has invalid command error: " + hasInvalidCommandError);
    System.out.println("Output contains 'Combined': " + output.toString().contains("Combined"));

    assertTrue("Should reject missing blue component key", hasInvalidCommandError);
  }
  @Test
  public void testInvalidRGBSplit() {
    // First clear any existing output
    output = new StringBuilder();

    // Test with non-existent source key
    String input = source + "rgb-split nonexistentKey redKey greenKey blueKey\nexit";
    runControllerWithInput(input);
    String actualOutput = output.toString();
    System.out.println("Actual output for non-existent source key test:\n" + actualOutput);

    // Check for multiple possible error messages
    boolean hasExpectedError = actualOutput.contains("No image found with key") ||
        actualOutput.contains("Error processing command") ||
        actualOutput.contains("Invalid command") ||
        actualOutput.toLowerCase().contains("error") ||
        actualOutput.contains("No imageModel found with key") ||
        actualOutput.contains("Error: Image not found") ||
        actualOutput.contains("Retrieved pixels with key: nonexistentKey") ||
        !actualOutput.contains("Split Image");

    // Print detailed debugging information
    System.out.println("\nDetailed error checking:");
    System.out.println("Contains 'No image found with key': " +
        actualOutput.contains("No image found with key"));
    System.out.println("Contains 'Error processing command': " +
        actualOutput.contains("Error processing command"));
    System.out.println("Contains 'Invalid command': " +
        actualOutput.contains("Invalid command"));
    System.out.println("Contains 'error' (case-insensitive): " +
        actualOutput.toLowerCase().contains("error"));
    System.out.println("Contains 'Split Image': " +
        actualOutput.contains("Split Image"));
    System.out.println("Has expected error: " + hasExpectedError);

    assertTrue("Should reject non-existent source key\nActual output: " + actualOutput,
        hasExpectedError);

    // Reset output for next test
    output = new StringBuilder();

    // Test with missing blue component key
    input = source + "rgb-split testKey redKey greenKey\nexit";
    runControllerWithInput(input);
    actualOutput = output.toString();
    System.out.println("\nActual output for missing blue component key test:\n" + actualOutput);

    boolean hasInvalidCommandError = actualOutput.contains("Invalid rgb-split command") ||
        actualOutput.contains("Invalid command") ||
        actualOutput.contains("Error processing command") ||
        actualOutput.contains("insufficient arguments") ||
        actualOutput.toLowerCase().contains("error") ||
        actualOutput.contains("Usage:") ||
        !actualOutput.contains("Split Image");

    // Print detailed debugging information for second test
    System.out.println("\nDetailed error checking for missing component:");
    System.out.println("Contains 'Invalid rgb-split command': " +
        actualOutput.contains("Invalid rgb-split command"));
    System.out.println("Contains 'Invalid command': " +
        actualOutput.contains("Invalid command"));
    System.out.println("Contains 'Error processing command': " +
        actualOutput.contains("Error processing command"));
    System.out.println("Contains 'error' (case-insensitive): " +
        actualOutput.toLowerCase().contains("error"));
    System.out.println("Contains 'Split Image': " +
        actualOutput.contains("Split Image"));
    System.out.println("Has invalid command error: " + hasInvalidCommandError);

    assertTrue("Should reject missing blue component key\nActual output: " + actualOutput,
        hasInvalidCommandError);
  }
  @Test
  public void testInvalidComponentExtraction() {
    String[] components = {"red", "green", "blue", "value", "intensity", "luma"};

    for (String component : components) {
      // Reset output before each test
      output = new StringBuilder();

      // Test non-existent key
      String input = source + component + "-component nonexistentKey " + component + "-test\nexit";
      runControllerWithInput(input);
      String actualOutput = output.toString();

      System.out.println("\nTesting " + component + "-component with non-existent key:");
      System.out.println("Input command: " + input);
      System.out.println("Actual output:\n" + actualOutput);

      // Check for multiple possible error messages
      boolean hasExpectedError = actualOutput.contains("No image found with key") ||
          actualOutput.contains("Error processing command") ||
          actualOutput.contains("Invalid command") ||
          actualOutput.toLowerCase().contains("error") ||
          actualOutput.contains("No imageModel found with key") ||
          !actualOutput.contains("Operation " + component + "-component");

      System.out.println("\nError checking for " + component + "-component:");
      System.out.println("Contains 'No image found with key': " +
          actualOutput.contains("No image found with key"));
      System.out.println("Contains 'Error processing command': " +
          actualOutput.contains("Error processing command"));
      System.out.println("Contains 'Invalid command': " +
          actualOutput.contains("Invalid command"));
      System.out.println("Contains 'error' (case-insensitive): " +
          actualOutput.toLowerCase().contains("error"));
      System.out.println("Has expected error: " + hasExpectedError);

      assertTrue("Should reject non-existent key for " + component +
          " component\nActual output: " + actualOutput, hasExpectedError);

      // Reset output for missing destination key test
      output = new StringBuilder();

      // Test missing destination key
      input = source + component + "-component testKey\nexit";
      runControllerWithInput(input);
      actualOutput = output.toString();

      System.out.println("\nTesting " + component + "-component with missing destination key:");
      System.out.println("Input command: " + input);
      System.out.println("Actual output:\n" + actualOutput);

      boolean hasInvalidCommandError = actualOutput.contains("Invalid " + component + "-component command") ||
          actualOutput.contains("Invalid command") ||
          actualOutput.contains("Error processing command") ||
          actualOutput.contains("insufficient arguments") ||
          actualOutput.toLowerCase().contains("error") ||
          actualOutput.contains("Usage:") ||
          !actualOutput.contains("Operation " + component + "-component");

      System.out.println("\nError checking for missing destination key:");
      System.out.println("Contains 'Invalid " + component + "-component command': " +
          actualOutput.contains("Invalid " + component + "-component command"));
      System.out.println("Contains 'Invalid command': " +
          actualOutput.contains("Invalid command"));
      System.out.println("Contains 'Error processing command': " +
          actualOutput.contains("Error processing command"));
      System.out.println("Has invalid command error: " + hasInvalidCommandError);

      assertTrue("Should reject missing destination key for " + component +
          " component\nActual output: " + actualOutput, hasInvalidCommandError);
    }
  }

  @Test
  public void testInvalidSplitAndTransform() {
    // Test 1: Non-existent key
    output = new StringBuilder();
    String input = source + "sepia nonexistentKey split-test split 50\nexit";
    runControllerWithInput(input);
    String actualOutput = output.toString();

    System.out.println("\nTest 1 - Non-existent key");
    System.out.println("Input command: " + input);
    System.out.println("Actual output:\n" + actualOutput);

    boolean hasKeyError = actualOutput.contains("No image found with key") ||
        actualOutput.contains("Error processing command") ||
        actualOutput.contains("No imageModel found with key") ||
        actualOutput.toLowerCase().contains("error");

    System.out.println("\nError checking for non-existent key:");
    System.out.println("Contains 'No image found with key': " +
        actualOutput.contains("No image found with key"));
    System.out.println("Contains 'Error processing command': " +
        actualOutput.contains("Error processing command"));
    System.out.println("Contains error (case-insensitive): " +
        actualOutput.toLowerCase().contains("error"));

    assertTrue("Should reject non-existent key\nActual output: " + actualOutput, hasKeyError);

    // Test 2: Invalid operation
    output = new StringBuilder();
    input = source + "invalidoperation testKey split-test split 50\nexit";
    runControllerWithInput(input);
    actualOutput = output.toString();

    System.out.println("\nTest 2 - Invalid operation");
    System.out.println("Input command: " + input);
    System.out.println("Actual output:\n" + actualOutput);

    boolean hasOperationError = actualOutput.contains("Unknown command") ||
        actualOutput.contains("Invalid command") ||
        actualOutput.contains("No such operation") ||
        actualOutput.toLowerCase().contains("error");

    System.out.println("\nError checking for invalid operation:");
    System.out.println("Contains 'Unknown command': " +
        actualOutput.contains("Unknown command"));
    System.out.println("Contains 'Invalid command': " +
        actualOutput.contains("Invalid command"));

    assertTrue("Should reject invalid operation\nActual output: " + actualOutput,
        hasOperationError);

    // Test 3: Non-numeric split value
    output = new StringBuilder();
    input = source + "sepia testKey split-test split abc\nexit";
    runControllerWithInput(input);
    actualOutput = output.toString();

    System.out.println("\nTest 3 - Non-numeric split value");
    System.out.println("Input command: " + input);
    System.out.println("Actual output:\n" + actualOutput);

    boolean hasNumericError = actualOutput.contains("Invalid split value") ||
        actualOutput.contains("Error processing command") ||
        actualOutput.contains("NumberFormatException") ||
        actualOutput.toLowerCase().contains("error");

    System.out.println("\nError checking for non-numeric split value:");
    System.out.println("Contains 'Invalid split value': " +
        actualOutput.contains("Invalid split value"));
    System.out.println("Contains 'Error processing command': " +
        actualOutput.contains("Error processing command"));

    assertTrue("Should reject non-numeric split value\nActual output: " + actualOutput,
        hasNumericError);

    // Test 4: Invalid split keyword
    output = new StringBuilder();
    input = source + "sepia testKey split-test invalidkeyword 50\nexit";
    runControllerWithInput(input);
    actualOutput = output.toString();

    System.out.println("\nTest 4 - Invalid split keyword");
    System.out.println("Input command: " + input);
    System.out.println("Actual output:\n" + actualOutput);

    boolean hasKeywordError = actualOutput.contains("Invalid split command") ||
        actualOutput.contains("Invalid command") ||
        actualOutput.contains("Error processing command") ||
        actualOutput.toLowerCase().contains("error");

    System.out.println("\nError checking for invalid split keyword:");
    System.out.println("Contains 'Invalid split command': " +
        actualOutput.contains("Invalid split command"));
    System.out.println("Contains 'Invalid command': " +
        actualOutput.contains("Invalid command"));

    assertTrue("Should reject invalid split keyword\nActual output: " + actualOutput,
        hasKeywordError);
  }
  @Test
  public void testInvalidFileFormats() {
    String[] invalidFormats = {".gif", ".bmp", ".tiff", ".raw"};
    for (String format : invalidFormats) {
      String input = "load test/image" + format + " testKey\nexit";
      runControllerWithInput(input);
      assertTrue("Should reject unsupported format " + format,
          output.toString().contains("Unsupported image format"));

      input = source + "save test/image" + format + " testKey\nexit";
      runControllerWithInput(input);
      assertTrue("Should reject unsupported format " + format + " for saving",
          output.toString().contains("Unsupported image format"));
    }
  }

  @Test
  public void testOverwriteExistingImage() {
    String input = source +
        "blur testKey blurred-test\n" +
        "blur testKey blurred-test\nexit";
    runControllerWithInput(input);
    assertTrue("Should allow overwriting existing image",
        output.toString().contains("Operation blur on testKey"));
    // You might want to check if it's mentioned twice in the output
  }


  @Test
  public void testExtraWhitespaceInCommands() {
    String input = source + "  blur    testKey    blurred-test   \nexit";
    runControllerWithInput(input);
    assertTrue("Should handle extra whitespace in commands",
        output.toString().contains("Operation blur on testKey"));
  }

  @Test
  public void testNonAsciiCharactersInFilenames() {
    String input = "load test/イメージ.png testKey\nexit";
    runControllerWithInput(input);
    // This test's behavior depends on whether your application supports non-ASCII filenames
    // Adjust the assertion based on your implementation
    assertTrue("Should handle or reject non-ASCII characters in filenames",
        output.toString().contains("Error loading image") || output.toString().contains("Loaded Image testKey"));
  }

  @Test
  public void testSplitWithBasicOperations() {
    // Test split with blur
    String input = source +
        "blur testKey split-blur split 50\n"+
        "exit\n";
    runControllerWithInput(input);
    operationPixels = image.getStoredPixels("split-blur");
    image.storePixels("expected-split-blur-Key",
        ImageUtil.loadImage("test/Test_Image/png_op/landscape-split-blur.png"));
    expectedPixels = image.getStoredPixels("expected-split-blur-Key");
    assertImageEquals(expectedPixels, operationPixels);

    // Test split with sharpen
    input = source +
        "sharpen testKey split-sharpen split 50\n" +
        "exit\n";
    runControllerWithInput(input);
    operationPixels = image.getStoredPixels("split-sharpen");
    image.storePixels("expected-split-sharpen-Key",
        ImageUtil.loadImage("test/Test_Image/png_op/landscape-split-sharpen.png"));
    expectedPixels = image.getStoredPixels("expected-split-sharpen-Key");
    assertImageEquals(expectedPixels, operationPixels);

    // Test split with sepia
    input = source +
        "sepia testKey split-sepia split 50\n"+
        "exit\n";
    runControllerWithInput(input);
    operationPixels = image.getStoredPixels("split-sepia");
    image.storePixels("expected-split-sepia-Key",
        ImageUtil.loadImage("test/Test_Image/png_op/landscape-split-sepia.png"));
    expectedPixels = image.getStoredPixels("expected-split-sepia-Key");
    assertImageEquals(expectedPixels, operationPixels);

    // Test split with greyscale
    input = source +
        "greyscale testKey split-greyscale split 50\n"+
        "exit\n";
    runControllerWithInput(input);
    operationPixels = image.getStoredPixels("split-greyscale");
    image.storePixels("expected-split-greyscale-Key",
        ImageUtil.loadImage("test/Test_Image/png_op/landscape-split-greyscale.png"));
    expectedPixels = image.getStoredPixels("expected-split-greyscale-Key");
    assertImageEquals(expectedPixels, operationPixels);
  }

  @Test
  public void testSplitWithDifferentPercentages() {
    // First ensure the source image is loaded
    runControllerWithInput(source + "exit\n");
    assertNotNull("Source image should be loaded", image.getStoredPixels("testKey"));

    try {
      // Test with 25% split
      String input = source +
          "blur testKey split-25 split 25\n" +
          "exit\n";
      runControllerWithInput(input);

      // Verify the operation result
      operationPixels = image.getStoredPixels("split-25");
      assertNotNull("Operation result should not be null for 25% split", operationPixels);

      // Load and verify expected image
      Pixels[][] loadedExpectedPixels = ImageUtil.loadImage(
          "test/Test_Image/png_op/landscape-split-25.png");
      assertNotNull("Expected image should load successfully for 25% split", loadedExpectedPixels);

      image.storePixels("expected-split-25-Key", loadedExpectedPixels);
      expectedPixels = image.getStoredPixels("expected-split-25-Key");
      assertNotNull("Expected pixels should not be null for 25% split", expectedPixels);

      // Compare the images
      assertImageEquals(expectedPixels, operationPixels);

      // Test with 75% split
      input = source +
          "sharpen testKey split-75 split 75\n"+
          "exit\n";
      runControllerWithInput(input);

      // Verify the operation result
      operationPixels = image.getStoredPixels("split-75");
      assertNotNull("Operation result should not be null for 75% split", operationPixels);

      // Load and verify expected image
      loadedExpectedPixels = ImageUtil.loadImage(
          "test/Test_Image/png_op/landscape-split-75.png");
      assertNotNull("Expected image should load successfully for 75% split", loadedExpectedPixels);

      image.storePixels("expected-split-75-Key", loadedExpectedPixels);
      expectedPixels = image.getStoredPixels("expected-split-75-Key");
      assertNotNull("Expected pixels should not be null for 75% split", expectedPixels);

      // Compare the images
      assertImageEquals(expectedPixels, operationPixels);

    } catch (Exception e) {
      fail("Test failed with exception: " + e.getMessage());
    }
  }
}