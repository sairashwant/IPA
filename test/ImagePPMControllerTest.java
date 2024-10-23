import Controller.ImageController;
import Model.ColorScheme.RGBPixel;
import Model.Image;
import java.io.IOException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * Unit tests for the ImageController class, specifically focusing on
 * operations related to loading and manipulating PPM images.
 */
public class ImagePPMControllerTest {

  private Image image;
  private ImageController controller;
  RGBPixel[][] operationPixels;
  RGBPixel[][] expectedPixels;

  /**
   * Compares the expected RGBPixel array with the actual one.
   *
   * @param expected the expected RGBPixel array
   * @param actual   the actual RGBPixel array
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
   * Initializes the Image and ImageController objects before each test.
   */
  @Before
  public void setUp() {
    image = new Image();
    controller = new ImageController(image);
    controller.loadIMage("testKey", "test/Test_Image/P3.ppm");
  }

  /**
   * Tests the loading of a PPM image and verifies the loaded pixels.
   */
  @Test
  public void testLoadImage() {
    controller.loadIMage("testKey", "test/Test_Image/P3.ppm");

    RGBPixel[][] loadedPixels = image.getPixels("testKey", "test/Test_Image/P3.ppm");
    assertArrayEquals(image.h1.get("testKey"), loadedPixels);
  }

  /**
   * Tests the blurring operation on a PPM image and verifies the result.
   */
  @Test
  public void testBlur() {
    controller.applyOperations("blur", "testKey", "blurred-Key");

    operationPixels = image.h1.get("blurred-Key");
    expectedPixels = image.getPixels("expected-Blurred-Key", "test/Test_Image/ppm_op/P3-blur.ppm");
    assertImageEquals(expectedPixels, operationPixels);
  }

  /**
   * Tests the horizontal flip operation on a PPM image and verifies the result.
   */
  @Test
  public void testHorizontalFlip() {
    controller.applyOperations("horizontal-flip", "testKey", "horizontal-flip-Key");

    operationPixels = image.h1.get("horizontal-flip-Key");
    expectedPixels = image.getPixels("expected-horizontal-flip-Key", "test/Test_Image/ppm_op/P3-horizontal-flip.ppm");
    assertImageEquals(expectedPixels, operationPixels);
  }

  /**
   * Tests the vertical flip operation on a PPM image and verifies the result.
   */
  @Test
  public void testVerticallFlip() {
    controller.applyOperations("vertical-flip", "testKey", "vertical-flip-Key");

    operationPixels = image.h1.get("vertical-flip-Key");
    expectedPixels = image.getPixels("expected-vertical-flip-Key", "test/Test_Image/ppm_op/P3-vertical-flip.ppm");
    assertImageEquals(expectedPixels, operationPixels);
  }

  /**
   * Tests the brighten operation on a PPM image and verifies the result.
   */
  @Test
  public void testBrighten() {
    controller.brighten(20, "testKey", "brightened-Key");

    operationPixels = image.h1.get("brightened-Key");
    expectedPixels = image.getPixels("expected-BrightenedKey", "test/Test_Image/ppm_op/P3-brighter.ppm");

    assertImageEquals(expectedPixels, operationPixels);
  }

  /**
   * Tests the negative brighten operation on a PPM image and verifies the result.
   */
  @Test
  public void testNegativeBrighten() {
    controller.brighten(-20, "testKey", "darkened-Key");

    operationPixels = image.h1.get("darkened-Key");
    expectedPixels = image.getPixels("expected-darkened-Key", "test/Test_Image/ppm_op/P3-darker.ppm");

    assertImageEquals(expectedPixels, operationPixels);
  }

  /**
   * Tests the greyscale operation on a PPM image and verifies the result.
   */
  @Test
  public void testGreyscale() {
    controller.applyOperations("greyscale", "testKey", "greyscale-Key");

    operationPixels = image.h1.get("greyscale-Key");
    expectedPixels = image.getPixels("expected-greyscale-Key", "test/Test_Image/ppm_op/P3-greyscale.ppm");

    assertImageEquals(expectedPixels, operationPixels);
  }

  /**
   * Tests the luma component extraction operation on a PPM image and verifies the result.
   */
  @Test
  public void testLuma() {
    controller.applyOperations("luma-component", "testKey", "luma-component-Key");

    operationPixels = image.h1.get("luma-component-Key");
    expectedPixels = image.getPixels("expected-luma-component-Key", "test/Test_Image/ppm_op/P3-luma.ppm");

    assertImageEquals(expectedPixels, operationPixels);
  }

  /**
   * Tests the intensity component extraction operation on a PPM image and verifies the result.
   */
  @Test
  public void testIntensity() {
    controller.applyOperations("intensity-component", "testKey", "intensity-Key");

    operationPixels = image.h1.get("intensity-Key");
    expectedPixels = image.getPixels("expected-intensity-key", "test/Test_Image/ppm_op/P3-intensity.ppm");

    assertImageEquals(expectedPixels, operationPixels);
  }

  /**
   * Tests the sepia tone application on a PPM image and verifies the result.
   */
  @Test
  public void testSepia() {
    controller.applyOperations("sepia", "testKey", "sepia-Key");

    operationPixels = image.h1.get("sepia-Key");
    expectedPixels = image.getPixels("expected-sepia-key", "test/Test_Image/ppm_op/P3-sepia.ppm");

    assertImageEquals(expectedPixels, operationPixels);
  }

  /**
   * Tests the sharpening operation on a PPM image and verifies the result.
   */
  @Test
  public void testSharpen() {
    controller.applyOperations("sharpen", "testKey", "sharpen-Key");

    operationPixels = image.h1.get("sharpen-Key");
    expectedPixels = image.getPixels("expected-sharpen-key", "test/Test_Image/ppm_op/P3-sharper.ppm");

    assertImageEquals(expectedPixels, operationPixels);
  }

  /**
   * Tests the value component extraction operation on a PPM image and verifies the result.
   */
  @Test
  public void testValue() {
    controller.applyOperations("value-component", "testKey", "value-component-Key");

    operationPixels = image.h1.get("value-component-Key");
    expectedPixels = image.getPixels("expected-value-component-key", "test/Test_Image/ppm_op/P3-value.ppm");

    assertImageEquals(expectedPixels, operationPixels);
  }

  /**
   * Tests loading an image with an unsupported format, expecting an IOException to be thrown.
   *
   * @throws IOException if the image format is unsupported
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
