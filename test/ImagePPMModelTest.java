import Controller.ImageController;
import Model.ColorScheme.RGBPixel;
import Model.Image;
import java.io.IOException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * Unit tests for the {@link Image} model and the {@link ImageController} operations specifically
 * focused on handling PPM images. This class includes tests for various image operations such as
 * loading an image, applying filters (e.g., blur, greyscale, sepia), flipping, brightness
 * adjustment, and extracting intensity and luma components.
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
 *   <li>Calculating the luma component.</li>
 *   <li>Calculating the intensity component.</li>
 *   <li>Applying a sepia effect.</li>
 *   <li>Sharpening the image.</li>
 *   <li>Extracting the value component.</li>
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
public class ImagePPMModelTest {

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
        assertEquals(expectedPixels[i][j].getRed(), operationPixels[i][j].getRed());
        assertEquals(expectedPixels[i][j].getGreen(), operationPixels[i][j].getGreen());
        assertEquals(expectedPixels[i][j].getBlue(), operationPixels[i][j].getBlue());
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
    controller.loadIMage("testKey", "test/Test_Image/P3.ppm");
  }

  /**
   * Tests loading an image from a specified file path.
   */
  @Test
  public void testLoadImage() {
    controller.loadIMage("testKey", "test/Test_Image/P3.ppm");

    RGBPixel[][] loadedPixels = image.getPixels("testKey", "test/Test_Image/P3.ppm");
    assertArrayEquals(image.h1.get("testKey"), loadedPixels);
  }

  /**
   * Tests the blur operation on the image.
   */
  @Test
  public void testBlur() {
    controller.applyOperations("blur", "testKey", "blurred-Key");

    operationPixels = image.h1.get("blurred-Key");
    expectedPixels = image.getPixels("expected-Blurred-Key", "test/Test_Image/ppm_op/P3-blur.ppm");
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
        "test/Test_Image/ppm_op/P3-horizontal-flip.ppm");
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
        "test/Test_Image/ppm_op/P3-vertical-flip.ppm");
    assertImageEquals(expectedPixels, operationPixels);
  }

  /**
   * Tests the brighten operation on the image.
   */
  @Test
  public void testBrighten() {
    controller.brighten(20, "testKey", "brightened-Key");

    operationPixels = image.h1.get("brightened-Key");
    expectedPixels = image.getPixels("expected-BrightenedKey",
        "test/Test_Image/ppm_op/P3-brighter.ppm");

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
        "test/Test_Image/ppm_op/P3-darker.ppm");

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
        "test/Test_Image/ppm_op/P3-greyscale.ppm");

    assertImageEquals(expectedPixels, operationPixels);
  }

  /**
   * Tests extracting the luma component from the image.
   */
  @Test
  public void testLuma() {
    controller.applyOperations("luma-component", "testKey", "luma-component-Key");

    operationPixels = image.h1.get("luma-component-Key");
    expectedPixels = image.getPixels("expected-luma-component-Key",
        "test/Test_Image/ppm_op/P3-luma.ppm");

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
        "test/Test_Image/ppm_op/P3-intensity.ppm");

    assertImageEquals(expectedPixels, operationPixels);
  }

  /**
   * Tests applying a sepia effect to the image.
   */
  @Test
  public void testSepia() {
    controller.applyOperations("sepia", "testKey", "sepia-Key");

    operationPixels = image.h1.get("sepia-Key");
    expectedPixels = image.getPixels("expected-sepia-key", "test/Test_Image/ppm_op/P3-sepia.ppm");

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
        "test/Test_Image/ppm_op/P3-sharper.ppm");

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
        "test/Test_Image/ppm_op/P3-value.ppm");

    assertImageEquals(expectedPixels, operationPixels);
  }

  /**
   * Tests loading an image from an unsupported file format to ensure that an {@link IOException} is
   * thrown.
   *
   * @throws IOException if the image format is unsupported.
   */
  @Test(expected = IOException.class)
  public void testLoadImageWithUnsupportedFormatThrowsIOException() throws IOException {
    try {
      controller.loadIMage("testKey", "test/Test_Image/unsupported.xyz");
    } catch (Exception e) {
      throw new IOException(e);
    }
  }

}
