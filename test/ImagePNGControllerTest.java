import Model.Image;
import Controller.ImageController;
import Model.ColorScheme.RGBPixel;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * Unit tests for the ImageController class, specifically focusing on
 * operations related to loading and manipulating PNG images.
 */
public class ImagePNGControllerTest {

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
    controller.loadIMage("testKey", "test/Test_Image/Landscape.png");
  }

  /**
   * Tests the loading of an image and verifies the loaded pixels.
   */
  @Test
  public void testLoadImage() {
    controller.loadIMage("testKey", "test/Test_Image/Landscape.png");

    RGBPixel[][] loadedPixels = image.getPixels("testKey", "test/Test_Image/Landscape.png");
    assertArrayEquals(image.h1.get("testKey"), loadedPixels);
  }

  /**
   * Tests the blurring operation on an image and verifies the result.
   */
  @Test
  public void testBlur() {
    controller.applyOperations("blur", "testKey", "blurred-Key");

    operationPixels = image.h1.get("blurred-Key");
    expectedPixels = image.getPixels("expected-Blurred-Key", "test/Test_Image/png_op/landscape-blur.png");
    assertImageEquals(expectedPixels, operationPixels);
  }

  /**
   * Tests the horizontal flip operation on an image and verifies the result.
   */
  @Test
  public void testHorizontalFlip() {
    controller.applyOperations("horizontal-flip", "testKey", "horizontal-flip-Key");

    operationPixels = image.h1.get("horizontal-flip-Key");
    expectedPixels = image.getPixels("expected-horizontal-flip-Key", "test/Test_Image/png_op/landscape-horizontal-flip.png");
    assertImageEquals(expectedPixels, operationPixels);
  }

  /**
   * Tests the vertical flip operation on an image and verifies the result.
   */
  @Test
  public void testVerticallFlip() {
    controller.applyOperations("vertical-flip", "testKey", "vertical-flip-Key");

    operationPixels = image.h1.get("vertical-flip-Key");
    expectedPixels = image.getPixels("expected-vertical-flip-Key", "test/Test_Image/png_op/landscape-vertical-flip.png");
    assertImageEquals(expectedPixels, operationPixels);
  }

  /**
   * Tests the brighten operation on an image and verifies the result.
   */
  @Test
  public void testBrighten() {
    controller.brighten(20, "testKey", "brightened-Key");

    operationPixels = image.h1.get("brightened-Key");
    expectedPixels = image.getPixels("expected-BrightenedKey", "test/Test_Image/png_op/landscape-brighter.png");

    assertImageEquals(expectedPixels, operationPixels);
  }

  /**
   * Tests the negative brighten operation on an image and verifies the result.
   */
  @Test
  public void testNegativeBrighten() {
    controller.brighten(-20, "testKey", "darkened-Key");

    operationPixels = image.h1.get("darkened-Key");
    expectedPixels = image.getPixels("expected-darkened-Key", "test/Test_Image/png_op/landscape-darker.png");

    assertImageEquals(expectedPixels, operationPixels);
  }

  /**
   * Tests the greyscale operation on an image and verifies the result.
   */
  @Test
  public void testGreyscale() {
    controller.applyOperations("greyscale", "testKey", "greyscale-Key");

    operationPixels = image.h1.get("greyscale-Key");
    expectedPixels = image.getPixels("expected-greyscale-Key", "test/Test_Image/png_op/landscape-greyscale.png");

    assertImageEquals(expectedPixels, operationPixels);
  }

  /**
   * Tests the red component extraction operation on an image and verifies the result.
   */
  @Test
  public void testGetRed() {
    controller.applyOperations("red-component", "testKey", "red-component-Key");

    operationPixels = image.h1.get("red-component-Key");
    expectedPixels = image.getPixels("expected-red-component-Key", "test/Test_Image/png_op/landscape-red-component.png");

    assertImageEquals(expectedPixels, operationPixels);
  }

  /**
   * Tests the green component extraction operation on an image and verifies the result.
   */
  @Test
  public void testGetGreen() {
    controller.applyOperations("green-component", "testKey", "green-component-Key");

    operationPixels = image.h1.get("green-component-Key");
    expectedPixels = image.getPixels("expected-green-component-Key", "test/Test_Image/png_op/landscape-green-component.png");

    assertImageEquals(expectedPixels, operationPixels);
  }

  /**
   * Tests the blue component extraction operation on an image and verifies the result.
   */
  @Test
  public void testGetBlue() {
    controller.applyOperations("blue-component", "testKey", "blue-component-Key");

    operationPixels = image.h1.get("blue-component-Key");
    expectedPixels = image.getPixels("expected-blue-component-Key", "test/Test_Image/png_op/landscape-blue-component.png");

    assertImageEquals(expectedPixels, operationPixels);
  }

  /**
   * Tests the luma component extraction operation on an image and verifies the result.
   */
  @Test
  public void testLuma() {
    controller.applyOperations("luma-component", "testKey", "luma-component-Key");

    operationPixels = image.h1.get("luma-component-Key");
    expectedPixels = image.getPixels("expected-luma-component-Key", "test/Test_Image/png_op/landscape-luma.png");

    assertImageEquals(expectedPixels, operationPixels);
  }

  /**
   * Tests the RGB split operation on an image and verifies the results of each channel.
   */
  @Test
  public void testSplit() {
    controller.split("testKey", "red-split-key", "green-split-key", "blue-split-key");

    operationPixels = image.h1.get("red-split-key");
    RGBPixel[][] operationPixels2 = image.h1.get("green-split-key");
    RGBPixel[][] operationPixels3 = image.h1.get("blue-split-key");
    expectedPixels = image.getPixels("expected-red-split-key", "test/Test_Image/png_op/landscape-red-split.png");
    RGBPixel[][] expectedPixels2 = image.getPixels("expected-green-split-key", "test/Test_Image/png_op/landscape-green-split.png");
    RGBPixel[][] expectedPixels3 = image.getPixels("expected-blue-split-key", "test/Test_Image/png_op/landscape-blue-split.png");

    assertImageEquals(expectedPixels, operationPixels);
    assertImageEquals(expectedPixels2, operationPixels2);
    assertImageEquals(expectedPixels3, operationPixels3);
  }

  /**
   * Tests the intensity component extraction operation on an image and verifies the result.
   */
  @Test
  public void testIntensity() {
    controller.applyOperations("intensity-component", "testKey", "intensity-Key");

    operationPixels = image.h1.get("intensity-Key");
    expectedPixels = image.getPixels("expected-intensity-key", "test/Test_Image/png_op/landscape-intensity.png");

    assertImageEquals(expectedPixels, operationPixels);
  }

  /**
   * Tests the sepia tone application on an image and verifies the result.
   */
  @Test
  public void testSepia() {
    controller.applyOperations("sepia", "testKey", "sepia-Key");

    operationPixels = image.h1.get("sepia-Key");
    expectedPixels = image.getPixels("expected-sepia-key", "test/Test_Image/png_op/landscape-sepia.png");

    assertImageEquals(expectedPixels, operationPixels);
  }

  /**
   * Tests the sharpening operation on an image and verifies the result.
   */
  @Test
  public void testSharpen() {
    controller.applyOperations("sharpen", "testKey", "sharpen-Key");

    operationPixels = image.h1.get("sharpen-Key");
    expectedPixels = image.getPixels("expected-sharpen-key", "test/Test_Image/png_op/landscape-sharper.png");

    assertImageEquals(expectedPixels, operationPixels);
  }

  /**
   * Tests the value component extraction operation on an image and verifies the result.
   */
  @Test
  public void testValue() {
    controller.applyOperations("value-component", "testKey", "value-component-Key");

    operationPixels = image.h1.get("value-component-Key");
    expectedPixels = image.getPixels("expected-value-component-key", "test/Test_Image/png_op/landscape-value.png");

    assertImageEquals(expectedPixels, operationPixels);
  }
}
