import controller.ImageController;
import model.colorscheme.RGBPixel;
import model.Image;
import model.imagetransformation.filtering.Blur;
import model.imagetransformation.basicoperation.Flip;
import model.imagetransformation.basicoperation.Brighten;
import model.imagetransformation.colortransformation.GreyScale;
import model.imagetransformation.colortransformation.Sepia;
import model.imagetransformation.filtering.Sharpen;
import model.imagetransformation.basicoperation.Luma;
import model.imagetransformation.basicoperation.Split;
import model.imagetransformation.basicoperation.Combine;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;

/**
 * Unit tests for the {@link Image} model and the {@link ImageController} operations specifically
 * focused on handling PPM images. This class includes tests for various image operations such as
 * loading an image, applying filters (e.g., blur, greyscale, sepia), flipping, brightness
 * adjustment, and extracting intensity and Luma components.
 *
 * @see Image
 * @see ImageController
 * @see RGBPixel
 */
public class ImagePPMModelTest {

  private Image image;
  private ImageController controller;
  RGBPixel[][] operationPixels;
  RGBPixel[][] expectedPixels;
  HashMap<String, RGBPixel[][]> t1 = new HashMap<>();

  String load = "test/Test_Image/P3.ppm";  // The test PPM file path

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
    controller = new ImageController(image);

    // Load the PPM image for the test
    controller.loadIMage("testKey", load);
  }

  /**
   * Tests the blur operation on the image.
   */
  @Test
  public void testBlur() {
    Blur blur = new Blur();
    operationPixels = blur.apply(image.getPixels("testKey", load)); // Apply the blur operation
    expectedPixels = image.getPixels("expected-Blurred-Key", "test/Test_Image/ppm_op/P3-blur.ppm"); // Expected image

    // Compare the actual pixels with the expected pixels
    assertImageEquals(expectedPixels, operationPixels);
  }

  /**
   * Tests the horizontal flip operation on the image.
   */
  @Test
  public void testHorizontalFlip() {
    Flip flip = new Flip();
    operationPixels = flip.apply(image.getPixels("testKey", load), Flip.Direction.HORIZONTAL);
    expectedPixels = image.getPixels("expected-horizontal-flip-Key", "test/Test_Image/ppm_op/P3-horizontal-flip.ppm");

    // Compare the actual pixels with the expected pixels
    assertImageEquals(expectedPixels, operationPixels);
  }

  /**
   * Tests the vertical flip operation on the image.
   */
  @Test
  public void testVerticalFlip() {
    Flip flip = new Flip();
    operationPixels = flip.apply(image.getPixels("testKey", load), Flip.Direction.VERTICAL);
    expectedPixels = image.getPixels("expected-vertical-flip-Key", "test/Test_Image/ppm_op/P3-vertical-flip.ppm");

    // Compare the actual pixels with the expected pixels
    assertImageEquals(expectedPixels, operationPixels);
  }

  /**
   * Tests the brighten operation on the image.
   */
  @Test
  public void testBrighten() {
    Brighten brighten = new Brighten(20); // Brighten by an intensity of 20
    operationPixels = brighten.apply(image.getPixels("testKey", load));
    expectedPixels = image.getPixels("expected-BrightenedKey", "test/Test_Image/ppm_op/P3-brighter.ppm");

    // Compare the actual pixels with the expected pixels
    assertImageEquals(expectedPixels, operationPixels);
  }

  /**
   * Tests the darken operation on the image by applying a negative brightness adjustment.
   */
  @Test
  public void testNegativeBrighten() {
    Brighten brighten = new Brighten(-20); // Darken by an intensity of -20
    operationPixels = brighten.apply(image.getPixels("testKey", load));
    expectedPixels = image.getPixels("expected-darkened-Key", "test/Test_Image/ppm_op/P3-darker.ppm");

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
    expectedPixels = image.getPixels("expected-greyscale-Key", "test/Test_Image/ppm_op/P3-greyscale.ppm");

    // Compare the actual pixels with the expected pixels
    assertImageEquals(expectedPixels, operationPixels);
  }

  /**
   * Tests the sepia effect on the image.
   */
  @Test
  public void testSepia() {
    Sepia sepia = new Sepia();
    operationPixels = sepia.apply(image.getPixels("testKey", load));
    expectedPixels = image.getPixels("expected-sepia-Key", "test/Test_Image/ppm_op/P3-sepia.ppm");

    // Compare the actual pixels with the expected pixels
    assertImageEquals(expectedPixels, operationPixels);
  }

  /**
   * Tests sharpening the image.
   */
  @Test
  public void testSharpen() {
    Sharpen sharpen = new Sharpen();
    operationPixels = sharpen.apply(image.getPixels("testKey", load));
    expectedPixels = image.getPixels("expected-sharpen-Key", "test/Test_Image/ppm_op/P3-sharper.ppm");

    // Compare the actual pixels with the expected pixels
    assertImageEquals(expectedPixels, operationPixels);
  }

  /**
   * Tests extracting the Luma component from the image.
   */
  @Test
  public void testLuma() {
    Luma luma = new Luma();
    operationPixels = luma.apply(image.getPixels("testKey", load));
    expectedPixels = image.getPixels("expected-Luma-component-Key", "test/Test_Image/ppm_op/P3-Luma.ppm");

    // Compare the actual pixels with the expected pixels
    assertImageEquals(expectedPixels, operationPixels);
  }

  /**
   * Tests the splitting and combining of image channels (red, green, blue).
   */
  @Test
  public void testCombine() {
    Split split = new Split();

    t1.put("testKey", image.getPixels("testKey", load));

    // Split image into individual channels
    HashMap<String, RGBPixel[][]> splitResult = split.apply(t1, t1.get("testKey"), "testKey", "red-Key", "green-Key", "blue-Key");
    t1.putAll(splitResult);

    // Combine the individual channels back into an image
    Combine combine = new Combine();
    operationPixels = combine.apply(t1.get("red-Key"), t1.get("green-Key"), t1.get("blue-Key"));

    expectedPixels = image.getPixels("expected-combine-Key", "test/Test_Image/P3.ppm");

    // Compare the actual pixels with the expected pixels
    assertImageEquals(expectedPixels, operationPixels);
  }


}
