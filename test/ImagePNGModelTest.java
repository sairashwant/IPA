import model.Image;
import controller.ImageController;
import model.colorscheme.RGBPixel;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * Unit tests for the {@link Image} model and the {@link ImageController} operations specifically
 * focused on handling PNG images. This class includes tests for various image operations such as
 * loading an image, applying filters (e.g., blur, greyscale, sepia), flipping, brightness
 * adjustment, and channel extraction (red, green, blue components).
 *
 * <p>
 * The tests ensure that the output image pixels match the expected pixel values after each
 * operation is applied. Each test method is structured to:
 * <ul>
 *   <li>Setup necessary data and state before executing the test.</li>
 *   <li>Perform the image operation using the controller.</li>
 *   <li>Retrieve the resulting pixel data.</li>
 *   <li>Compare the resulting pixels with the expected output using assertions.</li>
 * </ul>
 * </p>
 *
 * <p>
 * The following operations are tested:
 * <ul>
 *   <li>Loading an image from a file.</li>
 *   <li>Applying a blur effect.</li>
 *   <li>Performing horizontal and vertical flips.</li>
 *   <li>Adjusting brightness (both brightening and darkening).</li>
 *   <li>Converting to greyscale.</li>
 *   <li>Extracting color channels (red, green, blue) and intensity components.</li>
 *   <li>Applying a sepia effect.</li>
 *   <li>Sharpening the image.</li>
 *   <li>Splitting the image into its RGB components.</li>
 *   <li>Calculating the Luma component.</li>
 * </ul>
 * </p>
 *
 * <p>
 * Each test utilizes a helper method {@link #assertImageEquals(RGBPixel[][], RGBPixel[][])}
 * to verify the pixel values by comparing red, green, and blue components for each pixel.
 * </p>
 *
 * @see Image
 * @see ImageController
 * @see RGBPixel
 */
public class ImagePNGModelTest {

  private Image image;
  private ImageController controller;
  RGBPixel[][] operationPixels;
  RGBPixel[][] expectedPixels;

  /**
   * Asserts that two images are equal by comparing their pixel values.
   *
   * @param expected The expected pixel values of the image.
   * @param actual   The actual pixel values of the image after an operation.
   */
  private void assertImageEquals(RGBPixel[][] expected, RGBPixel[][] actual) {
    for (int i = 0; i < operationPixels.length; i++) {
      for (int j = 0; j < operationPixels[0].length; j++) {
        assertEquals(expectedPixels[i][j].getRed(), operationPixels[i][j].getRed(),2);
        assertEquals(expectedPixels[i][j].getGreen(), operationPixels[i][j].getGreen(),2);
        assertEquals(expectedPixels[i][j].getBlue(), operationPixels[i][j].getBlue(),2);
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
    controller.loadIMage("testKey", "test/Test_Image/Landscape.png");
  }

  /**
   * Tests loading an image from a specified file path.
   */
  @Test
  public void testLoadImage() {
    controller.loadIMage("testKey", "test/Test_Image/Landscape.png");

    RGBPixel[][] loadedPixels = image.getPixels("testKey", "test/Test_Image/Landscape.png");
    assertArrayEquals(image.h1.get("testKey"), loadedPixels);
  }

  /**
   * Tests the blur operation on the image.
   */
  @Test
  public void testBlur() {
    controller.applyOperations("blur", "testKey", "blurred-Key");

    operationPixels = image.h1.get("blurred-Key");
    expectedPixels = image.getPixels("expected-Blurred-Key",
        "test/Test_Image/png_op/landscape-blur.png");
    assertImageEquals(expectedPixels, operationPixels);
  }

  /**
   * Tests the horizontal flip operation on the image.
   */
  @Test
  public void testHorizontalFlip() {
    controller.applyOperations("horizontal-flip", "testKey", "horizontal-flip-Key");

    operationPixels = image.h1.get("horizontal-flip-Key");
    expectedPixels = image.getPixels("expected-horizontal-flip-Key",
        "test/Test_Image/png_op/landscape-horizontal-flip.png");
    assertImageEquals(expectedPixels, operationPixels);
  }

  /**
   * Tests the vertical flip operation on the image.
   */
  @Test
  public void testVerticallFlip() {
    controller.applyOperations("vertical-flip", "testKey", "vertical-flip-Key");

    operationPixels = image.h1.get("vertical-flip-Key");
    expectedPixels = image.getPixels("expected-vertical-flip-Key",
        "test/Test_Image/png_op/landscape-vertical-flip.png");
    assertImageEquals(expectedPixels, operationPixels);
  }

  /**
   * Tests the brighten operation on the image.
   */
  @Test
  public void testBrighten() {
    controller.brighten(20, "testKey", "brightened-Key");

    operationPixels = image.h1.get("brightened-Key");
    expectedPixels = image.getPixels("expected-Brightenedkey",
        "test/Test_Image/png_op/landscape-brighter.png");

    assertImageEquals(expectedPixels, operationPixels);
  }

  /**
   * Tests the darken operation on the image by applying a negative brightness adjustment.
   */
  @Test
  public void testNegativeBrighten() {
    controller.brighten(-20, "testKey", "darkened-Key");

    operationPixels = image.h1.get("darkened-Key");
    expectedPixels = image.getPixels("expected-darkened-Key",
        "test/Test_Image/png_op/landscape-darker.png");

    assertImageEquals(expectedPixels, operationPixels);
  }

  /**
   * Tests the greyscale operation on the image.
   */
  @Test
  public void testGreyscale() {
    controller.applyOperations("greyscale", "testKey", "greyscale-Key");

    operationPixels = image.h1.get("greyscale-Key");
    expectedPixels = image.getPixels("expected-greyscale-Key",
        "test/Test_Image/png_op/landscape-greyscale.png");

    assertImageEquals(expectedPixels, operationPixels);
  }

  /**
   * Tests extracting the red component from the image.
   */
  @Test
  public void testGetRed() {
    controller.applyOperations("red-component", "testKey", "red-component-Key");

    operationPixels = image.h1.get("red-component-Key");
    expectedPixels = image.getPixels("expected-red-component-Key",
        "test/Test_Image/png_op/landscape-red-component.png");

    assertImageEquals(expectedPixels, operationPixels);
  }

  /**
   * Tests extracting the green component from the image.
   */
  @Test
  public void testGetGreen() {
    controller.applyOperations("green-component", "testKey", "green-component-Key");

    operationPixels = image.h1.get("green-component-Key");
    expectedPixels = image.getPixels("expected-green-component-Key",
        "test/Test_Image/png_op/landscape-green-component.png");

    assertImageEquals(expectedPixels, operationPixels);
  }

  /**
   * Tests extracting the blue component from the image.
   */
  @Test
  public void testGetBlue() {
    controller.applyOperations("blue-component", "testKey", "blue-component-Key");

    operationPixels = image.h1.get("blue-component-Key");
    expectedPixels = image.getPixels("expected-blue-component-Key",
        "test/Test_Image/png_op/landscape-blue-component.png");

    assertImageEquals(expectedPixels, operationPixels);
  }

  /**
   * Tests extracting the Luma component from the image.
   */
  @Test
  public void testLuma() {
    controller.applyOperations("Luma-component", "testKey", "Luma-component-Key");

    operationPixels = image.h1.get("Luma-component-Key");
    expectedPixels = image.getPixels("expected-Luma-component-Key",
        "test/Test_Image/png_op/landscape-Luma.png");

    assertImageEquals(expectedPixels, operationPixels);
  }

  /**
   * Tests extracting the intensity component from the image.
   */
  @Test
  public void testIntensity() {
    controller.applyOperations("intensity-component", "testKey", "intensity-Key");

    operationPixels = image.h1.get("intensity-Key");
    expectedPixels = image.getPixels("expected-intensity-key",
        "test/Test_Image/png_op/landscape-intensity.png");

    assertImageEquals(expectedPixels, operationPixels);
  }

  /**
   * Tests applying a sepia effect to the image.
   */
  @Test
  public void testSepia() {
    controller.applyOperations("sepia", "testKey", "sepia-Key");

    operationPixels = image.h1.get("sepia-Key");
    expectedPixels = image.getPixels("expected-sepia-key",
        "test/Test_Image/png_op/landscape-sepia.png");

    assertImageEquals(expectedPixels, operationPixels);
  }

  /**
   * Tests sharpening the image.
   */
  @Test
  public void testSharpen() {
    controller.applyOperations("sharpen", "testKey", "sharpen-Key");

    operationPixels = image.h1.get("sharpen-Key");
    expectedPixels = image.getPixels("expected-sharpen-key",
        "test/Test_Image/png_op/landscape-sharper.png");

    assertImageEquals(expectedPixels, operationPixels);
  }

  /**
   * Tests extracting the value component from the image.
   */
  @Test
  public void testValue() {
    controller.applyOperations("value-component", "testKey", "value-component-Key");

    operationPixels = image.h1.get("value-component-Key");
    expectedPixels = image.getPixels("expected-value-component-key",
        "test/Test_Image/png_op/landscape-value.png");

    assertImageEquals(expectedPixels, operationPixels);
  }
}
