import Model.Image;
import Controller.ImageController;
import Model.ColorScheme.RGBPixel;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * The ImageExceptionTest class contains unit tests for the ImageController
 * class to validate that appropriate exceptions are thrown when invalid
 * operations are performed on images, such as loading unsupported formats
 * or applying operations on null images.
 */
public class ImageExceptionTest {

  private Image image; // The image model being tested
  private ImageController controller; // The controller for managing image operations
  RGBPixel[][] operationPixels; // Pixels for testing image operations
  RGBPixel[][] expectedPixels; // Expected pixels for comparison in tests

  /**
   * Asserts that the actual image pixels are equal to the expected pixels.
   *
   * @param expected the expected RGBPixel 2D array
   * @param actual the actual RGBPixel 2D array to compare
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
   * Sets up the test environment before each test.
   * Initializes the Image model and ImageController, and loads a test image.
   */
  @Before
  public void setUp() {
    image = new Image();
    controller = new ImageController(image);
    controller.loadIMage("testKey", "test/Test_Image/Landscape.png");
  }

  /**
   * Tests that loading an image with an unsupported format throws
   * an IllegalArgumentException.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testLoadImageWithUnsupportedFormatThrowsIllegalArgumentException() {
    controller.loadIMage("testKey", "test/Test_Image/unsupported.xyz");
  }

  /**
   * Tests that applying the red component operation on a null image
   * throws a NullPointerException.
   */
  @Test(expected = NullPointerException.class)
  public void testRedComponentWithNullImageThrowsIOException() {
    controller.loadIMage("nullKey", null);
    controller.applyOperations("red-component", "nullKey", "red-component-Key");
  }

  /**
   * Tests that applying the green component operation on a null image
   * throws a NullPointerException.
   */
  @Test(expected = NullPointerException.class)
  public void testGreenComponentWithNullImageThrowsIOException() {
    controller.loadIMage("nullKey", null);
    controller.applyOperations("green-component", "nullKey", "green-component-Key");
  }

  /**
   * Tests that applying the blue component operation on a null image
   * throws a NullPointerException.
   */
  @Test(expected = NullPointerException.class)
  public void testBlueComponentWithNullImageThrowsIOException() {
    controller.loadIMage("nullKey", null);
    controller.applyOperations("blue-component", "nullKey", "blue-component-Key");
  }

  /**
   * Tests that applying the value component operation on a null image
   * throws a NullPointerException.
   */
  @Test(expected = NullPointerException.class)
  public void testValueComponentWithNullImageThrowsNullPointerException() {
    controller.loadIMage("nullKey", null);
    controller.applyOperations("value-component", "nullKey", "value-component-Key");
  }

  /**
   * Tests that applying the luma component operation on a null image
   * throws a NullPointerException.
   */
  @Test(expected = NullPointerException.class)
  public void testLumaComponentWithNullImageThrowsNullPointerException() {
    controller.loadIMage("nullKey", null);
    controller.applyOperations("luma-component", "nullKey", "luma-component-Key");
  }

  /**
   * Tests that applying the intensity component operation on a null image
   * throws a NullPointerException.
   */
  @Test(expected = NullPointerException.class)
  public void testIntensityComponentWithNullImageThrowsNullPointerException() {
    controller.loadIMage("nullKey", null);
    controller.applyOperations("intensity-component", "nullKey", "intensity-Key");
  }

  /**
   * Tests that flipping an image horizontally when it is null throws
   * a NullPointerException.
   */
  @Test(expected = NullPointerException.class)
  public void testFlipHorizontallyWithNullImageThrowsNullPointerException() {
    controller.loadIMage("nullKey", null);
    controller.applyOperations("horizontal-flip", "nullKey", "horizontal-flip-Key");
  }

  /**
   * Tests that flipping an image vertically when it is null throws
   * a NullPointerException.
   */
  @Test(expected = NullPointerException.class)
  public void testFlipVerticallyWithNullImageThrowsNullPointerException() {
    controller.loadIMage("nullKey", null);
    controller.applyOperations("vertical-flip", "nullKey", "vertical-flip-Key");
  }

  /**
   * Tests that brightening a null image throws a NullPointerException.
   */
  @Test(expected = NullPointerException.class)
  public void testBrightenImageWithNullImageThrowsNullPointerException() {
    controller.loadIMage("nullKey", null);
    controller.brighten(20, "nullKey", "brightened-Key");
  }

  /**
   * Tests that splitting a null image throws a NullPointerException.
   */
  @Test(expected = NullPointerException.class)
  public void testRgbSplitWithNullImageThrowsNullPointerException() {
    controller.loadIMage("nullKey", null);
    controller.split("nullKey", "red-Key", "green-Key", "blue-Key");
  }

  /**
   * Tests that applying a sepia effect on a null image throws a
   * NullPointerException.
   */
  @Test(expected = NullPointerException.class)
  public void testApplySepiaWithNullImageThrowsNullPointerException() {
    controller.loadIMage("nullKey", null);
    controller.applyOperations("sepia", "nullKey", "sepia-Key");
  }

  /**
   * Tests that applying a blur effect on a null image throws a
   * NullPointerException.
   */
  @Test(expected = NullPointerException.class)
  public void testBlurWithNullImageThrowsNullPointerException() {
    controller.loadIMage("nullKey", null);
    controller.applyOperations("blur", "nullKey", "blur-Key");
  }

  /**
   * Tests that applying a sharpen effect on a null image throws a
   * NullPointerException.
   */
  @Test(expected = NullPointerException.class)
  public void testSharpenWithNullImageThrowsNullPointerException() {
    controller.loadIMage("nullKey", null);
    controller.applyOperations("sharpen", "nullKey", "sharpen-Key");
  }

  /**
   * Tests that applying a greyscale effect on a null image throws a
   * NullPointerException.
   */
  @Test(expected = NullPointerException.class)
  public void testApplyGreyScaleWithNullImageThrowsNullPointerException() {
    controller.loadIMage("nullKey", null);
    controller.applyOperations("greyscale", "nullKey", "greyscale-Key");
  }

  /**
   * Tests that combining RGB components with a null red component throws
   * a NullPointerException.
   */
  @Test(expected = NullPointerException.class)
  public void testCombineRgbWithNullRedComponentThrowsNullPointerException() {
    controller.loadIMage("nullKey", null);
    controller.combine("nullKey", "nullRedKey", "green-Key", "blue-Key");
  }

  /**
   * Tests that combining RGB components with a null green component throws
   * a NullPointerException.
   */
  @Test(expected = NullPointerException.class)
  public void testCombineRgbWithNullGreenComponentThrowsNullPointerException() {
    controller.loadIMage("nullKey", null);
    controller.combine("nullKey", "red-Key", "nullGreenKey", "blue-Key");
  }

  /**
   * Tests that combining RGB components with a null blue component throws
   * a NullPointerException.
   */
  @Test(expected = NullPointerException.class)
  public void testCombineRgbWithNullBlueComponentThrowsNullPointerException() {
    controller.loadIMage("nullKey", null);
    controller.combine("nullKey", "red-Key", "green-Key", "nullBlueKey");
  }

}
