import java.util.HashMap;
import model.Image;
import controller.ImageController;
import model.colorscheme.RGBPixel;
import model.imagetransformation.basicoperation.Brighten;
import model.imagetransformation.basicoperation.Combine;
import model.imagetransformation.basicoperation.Flip;
import model.imagetransformation.basicoperation.Flip.Direction;
import model.imagetransformation.basicoperation.Luma;
import model.imagetransformation.basicoperation.Split;
import model.imagetransformation.colortransformation.GreyScale;
import model.imagetransformation.colortransformation.Sepia;
import model.imagetransformation.filtering.Blur;
import model.imagetransformation.filtering.Sharpen;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Unit tests for the {@link Image} model and the {@link ImageController} operations specifically
 * focused on handling PNG images. This class includes tests for various image operations such as
 * loading an image, applying filters (e.g., blur, greyscale, sepia), flipping, brightness
 * adjustment, and channel extraction (red, green, blue components).
 */
public class ImagePNGModelTest {

  private Image image;
  RGBPixel[][] operationPixels;
  RGBPixel[][] expectedPixels;
  HashMap<String, RGBPixel[][]> t1 = new HashMap<>();

  String load = "test/Test_Image/Landscape.png";

  /**
   * Asserts that two images are equal by comparing their pixel values.
   *
   * @param expected The expected pixel values of the image.
   * @param actual   The actual pixel values of the image after an operation.
   */
  private void assertImageEquals(RGBPixel[][] expected, RGBPixel[][] actual) {
    for (int i = 0; i < expected.length; i++) {
      for (int j = 0; j < expected[0].length; j++) {
        assertEquals(expected[i][j].getRed(), actual[i][j].getRed(), 2);
        assertEquals(expected[i][j].getGreen(), actual[i][j].getGreen(), 2);
        assertEquals(expected[i][j].getBlue(), actual[i][j].getBlue(), 2);
      }
    }
  }

  /**
   * Sets up the test environment by initializing the image and controller, and loading a test image
   * before each test case is run.
   */
  @Before
  public void setUp() {
    image = new Image();

    // Assuming you have a method to load the image into the controller.
    image.getPixels("testKey", load);
  }

  /**
   * Tests the blur operation on the image.
   */
  @Test
  public void testBlur() {
    Blur blur = new Blur();
    operationPixels = blur.apply(image.getPixels("testKey", load)); // Apply the blur
    expectedPixels = image.getPixels("expected-Blurred-Key", "test/Test_Image/png_op/landscape-blur.png"); // Expected image after blur

    // Compare the actual pixels with the expected pixels
    assertImageEquals(expectedPixels, operationPixels);
  }

  /**
   * Tests the horizontal flip operation on the image.
   */
  @Test
  public void testHorizontalFlip() {
    // Apply horizontal flip directly using a controller operation or manual pixel manipulation.
    Flip flip = new Flip();
    operationPixels = flip.apply(image.getPixels("testKey", load), Direction.HORIZONTAL);
    expectedPixels = image.getPixels("expected-horizontal-flip-Key", "test/Test_Image/png_op/landscape-horizontal-flip.png");

    // Compare the actual pixels with the expected pixels
    assertImageEquals(expectedPixels, operationPixels);
  }

  /**
   * Tests the vertical flip operation on the image.
   */
  @Test
  public void testVerticalFlip() {
    // Apply vertical flip directly using a controller operation or manual pixel manipulation.
    Flip flip = new Flip();
    operationPixels = flip.apply(image.getPixels("testKey", load), Direction.VERTICAL);
    expectedPixels = image.getPixels("expected-vertical-flip-Key", "test/Test_Image/png_op/landscape-vertical-flip.png");

    // Compare the actual pixels with the expected pixels
    assertImageEquals(expectedPixels, operationPixels);
  }

  /**
   * Tests the brighten operation on the image.
   */
  @Test
  public void testBrighten() {
    Brighten brighten = new Brighten(20); // Assuming Brighten class takes an intensity parameter
    operationPixels = brighten.apply(image.getPixels("testKey", load));
    expectedPixels = image.getPixels("expected-BrightenedKey", "test/Test_Image/png_op/landscape-brighter.png");

    // Compare the actual pixels with the expected pixels
    assertImageEquals(expectedPixels, operationPixels);
  }

  /**
   * Tests the darken operation on the image by applying a negative brightness adjustment.
   */
  @Test
  public void testNegativeBrighten() {
    Brighten brighten = new Brighten(-20); // Negative brightness to darken the image
    operationPixels = brighten.apply(image.getPixels("testKey", load));
    expectedPixels = image.getPixels("expected-darkened-Key", "test/Test_Image/png_op/landscape-darker.png");

    // Compare the actual pixels with the expected pixels
    assertImageEquals(expectedPixels, operationPixels);
  }

  /**
   * Tests the greyscale operation on the image.
   */
  @Test
  public void testGreyscale() {
    GreyScale greyscale = new GreyScale();
    operationPixels = greyscale.apply(image.getPixels("testKey", load));
    expectedPixels = image.getPixels("expected-greyscale-Key", "test/Test_Image/png_op/landscape-greyscale.png");

    // Compare the actual pixels with the expected pixels
    assertImageEquals(expectedPixels, operationPixels);
  }

  @Test
  public void testCombine() {
    // First split the image to get individual color components
    Split split = new Split();

    t1.put("testKey", image.getPixels("testKey", load));

    // Apply the split operation to get individual channels
    HashMap<String, RGBPixel[][]> splitResult = split.apply(t1, t1.get("testKey"),
        "testKey", "red-Key", "green-Key", "blue-Key");

    // Store the split components in the image's HashMap
    t1.putAll(splitResult);

    // Perform the combine operation
    Combine combine = new Combine();
    operationPixels = combine.apply(t1.get("red-Key"), t1.get("green-Key"), t1.get("blue-Key"));

    // Get the expected result - should be the original image
    expectedPixels = image.getPixels("expected-combine-Key", "test/Test_Image/Landscape.png");

    // Compare the actual pixels with the expected pixels
    assertImageEquals(expectedPixels, operationPixels);
  }

  @Test
  public void testSplit() {
    Split split = new Split();

    // Create a local HashMap t1 and populate it with pixel data
    t1.put("testKey", image.getPixels("testKey", load)); // Assuming load is defined somewhere

    // Apply the split using the local HashMap t1
    HashMap<String, RGBPixel[][]> splitResult = split.apply(t1, t1.get("testKey"), "testKey", "red-Key", "green-Key", "blue-Key");

    // Test red component
    RGBPixel[][] operationPixels = splitResult.get("red-Key");
    RGBPixel[][] expectedPixels = image.getPixels("expected-red-split-Key", "test/Test_Image/png_op/landscape-red-component.png");
    assertImageEquals(expectedPixels, operationPixels);

    // Test green component
    operationPixels = splitResult.get("green-Key");
    expectedPixels = image.getPixels("expected-green-split-Key", "test/Test_Image/png_op/landscape-green-component.png");
    assertImageEquals(expectedPixels, operationPixels);

    // Test blue component
    operationPixels = splitResult.get("blue-Key");
    expectedPixels = image.getPixels("expected-blue-split-Key", "test/Test_Image/png_op/landscape-blue-component.png");
    assertImageEquals(expectedPixels, operationPixels);
  }

  /**
   * Tests extracting the Luma component from the image.
   */
  @Test
  public void testLuma() {
    Luma luma = new Luma();
    operationPixels = luma.apply(image.getPixels("testKey", load));
    expectedPixels = image.getPixels("expected-Luma-component-Key", "test/Test_Image/png_op/landscape-Luma.png");

    // Compare the actual pixels with the expected pixels
    assertImageEquals(expectedPixels, operationPixels);
  }

  /**
   * Tests applying the sepia effect to the image.
   */
  @Test
  public void testSepia() {
    Sepia sepia = new Sepia();
    operationPixels = sepia.apply(image.getPixels("testKey", load));
    expectedPixels = image.getPixels("expected-sepia-key", "test/Test_Image/png_op/landscape-sepia.png");

    // Compare the actual pixels with the expected pixels
    assertImageEquals(expectedPixels, operationPixels);
  }

  /**
   * Tests the sharpening operation on the image.
   */
  @Test
  public void testSharpen() {
    Sharpen sharpen = new Sharpen();
    operationPixels = sharpen.apply(image.getPixels("testKey", load));
    expectedPixels = image.getPixels("expected-sharpened-key", "test/Test_Image/png_op/landscape-sharper.png");

    // Compare the actual pixels with the expected pixels
    assertImageEquals(expectedPixels, operationPixels);
  }
}
