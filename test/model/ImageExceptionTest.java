package model;

import static org.junit.Assert.assertEquals;

import controller.ImageController;
import controller.ImageUtil;
import model.colorscheme.Pixels;
import model.colorscheme.RGBPixel;
import model.imagetransformation.basicoperation.Flip.Direction;
import org.junit.Before;
import org.junit.Test;

/**
 * The model.ImageExceptionTest class contains unit tests for the {@link ImageController} class to
 * validate that appropriate exceptions are thrown when invalid operations are performed on images,
 * such as loading unsupported formats or applying operations on null images.
 *
 * <p>
 * This class tests various operations on the {@link Image} class, including loading images,
 * applying image transformations, and manipulating pixel components. It ensures that exceptions are
 * thrown when operations are performed on null images or unsupported formats, and verifies that the
 * behavior is consistent with the expected error handling.
 * </p>
 */
public class ImageExceptionTest {

  /**
   * The Image model instance used in the tests.
   */
  private Image image;

  /**
   * The ImageController instance that interacts with the image.
   */
  private ImageController controller;

  /**
   * The pixels of the image used during operations.
   */
  RGBPixel[][] operationPixels;

  /**
   * The expected pixels after performing operations.
   */
  RGBPixel[][] expectedPixels;

  /**
   * The file path to the test image.
   */
  String load = "test/Test_Image/Landscape.png";

  /**
   * Asserts that the actual image pixels are equal to the expected pixels.
   *
   * @param expected the expected RGBPixel 2D array
   * @param actual   the actual RGBPixel 2D array to compare
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
   * Sets up the test environment before each test. Initializes the Image model and ImageController,
   * and loads a test image into the system.
   */
  @Before
  public void setUp() {
    image = new Image();
    controller = new ImageController(image);
    Pixels[][] pixels = ImageUtil.loadImage(load);
  }

  /**
   * Tests that loading an image with an unsupported format throws an IllegalArgumentException.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testLoadImageWithUnsupportedFormatThrowsIllegalArgumentException() {
    // Test loading an unsupported image format
    ImageUtil.loadImage("test/Test_Image/unsupported.xyz");
  }

  /**
   * Tests that applying the red component operation on a null image throws a NullPointerException.
   */
  @Test(expected = NullPointerException.class)
  public void testRedComponentWithNullImageThrowsNullPointerException() {
    // Test red component operation on a null image
    image.storePixels("nullKey", null);
    image.getRedChannel("nullKey", "red-component-Key");
  }

  /**
   * Tests that applying the green component operation on a null image throws a
   * NullPointerException.
   */
  @Test(expected = NullPointerException.class)
  public void testGreenComponentWithNullImageThrowsNullPointerException() {
    // Test green component operation on a null image
    image.storePixels("nullKey", null);
    image.getGreenChannel("nullKey", "green-component-Key");
  }

  /**
   * Tests that applying the blue component operation on a null image throws a
   * NullPointerException.
   */
  @Test(expected = NullPointerException.class)
  public void testBlueComponentWithNullImageThrowsNullPointerException() {
    // Test blue component operation on a null image
    image.storePixels("nullKey", null);
    image.getBlueChannel("nullKey", "blue-component-Key");
  }

  /**
   * Tests that applying the value component operation on a null image throws a
   * NullPointerException.
   */
  @Test(expected = NullPointerException.class)
  public void testValueComponentWithNullImageThrowsNullPointerException() {
    // Test value component operation on a null image
    image.storePixels("nullKey", null);
    image.value("nullKey", "value-component-Key");
  }

  /**
   * Tests that applying the Luma component operation on a null image throws a
   * NullPointerException.
   */
  @Test(expected = NullPointerException.class)
  public void testLumaComponentWithNullImageThrowsNullPointerException() {
    // Test luma component operation on a null image
    image.storePixels("nullKey", null);
    image.luma("nullKey", "luma-component-Key");
  }

  /**
   * Tests that applying the intensity component operation on a null image throws a
   * NullPointerException.
   */
  @Test(expected = NullPointerException.class)
  public void testIntensityComponentWithNullImageThrowsNullPointerException() {
    // Test intensity component operation on a null image
    image.storePixels("nullKey", null);
    image.intensity("nullKey", "intensity-component-Key");
  }

  /**
   * Tests that flipping an image horizontally when it is null throws a NullPointerException.
   */
  @Test(expected = NullPointerException.class)
  public void testFlipHorizontallyWithNullImageThrowsNullPointerException() {
    // Test horizontal flip operation on a null image
    image.storePixels("nullKey", null);
    image.flip("nullKey", "horizontal-flip-Key", Direction.HORIZONTAL);
  }

  /**
   * Tests that flipping an image vertically when it is null throws a NullPointerException.
   */
  @Test(expected = NullPointerException.class)
  public void testFlipVerticallyWithNullImageThrowsNullPointerException() {
    // Test vertical flip operation on a null image
    image.storePixels("nullKey", null);
    image.flip("nullKey", "vertical-flip-Key", Direction.VERTICAL);
  }

  /**
   * Tests that brightening a null image throws a NullPointerException.
   */
  @Test(expected = NullPointerException.class)
  public void testBrightenImageWithNullImageThrowsNullPointerException() {
    // Test brightening operation on a null image
    image.storePixels("nullKey", null);
    image.brighten(20, "nullKey", "brightened-Key");
  }

  /**
   * Tests that splitting a null image throws a NullPointerException.
   */
  @Test(expected = NullPointerException.class)
  public void testRgbSplitWithNullImageThrowsNullPointerException() {
    // Test RGB split operation on a null image
    image.storePixels("nullKey", null);
    image.split("nullKey", "red-Key", "green-Key", "blue-Key");
  }

  /**
   * Tests that applying a sepia effect on a null image throws a NullPointerException.
   */
  @Test(expected = NullPointerException.class)
  public void testApplySepiaWithNullImageThrowsNullPointerException() {
    // Test sepia effect on a null image
    image.storePixels("nullKey", null);
    image.sepia("nullKey", "sepia-Key");
  }

  /**
   * Tests that applying a blur effect on a null image throws a NullPointerException.
   */
  @Test(expected = NullPointerException.class)
  public void testBlurWithNullImageThrowsNullPointerException() {
    // Test blur effect on a null image
    image.storePixels("nullKey", null);
    image.blur("nullKey", "blur-Key");
  }

  /**
   * Tests that applying a sharpen effect on a null image throws a NullPointerException.
   */
  @Test(expected = NullPointerException.class)
  public void testSharpenWithNullImageThrowsNullPointerException() {
    // Test sharpen effect on a null image
    image.storePixels("nullKey", null);
    image.sharpen("nullKey", "sharpen-Key");
  }

  /**
   * Tests that applying a greyscale effect on a null image throws a NullPointerException.
   */
  @Test(expected = NullPointerException.class)
  public void testApplyGreyScaleWithNullImageThrowsNullPointerException() {
    // Test greyscale effect on a null image
    image.storePixels("nullKey", null);
    image.greyScale("nullKey", "greyscale-Key");
  }

  /**
   * Tests that combining RGB components with a null red component throws a NullPointerException.
   */
  @Test(expected = NullPointerException.class)
  public void testCombineRgbWithNullRedComponentThrowsNullPointerException() {
    // Test combining RGB components with null red component
    image.storePixels("nullKey", null);
    image.combine("nullKey", "nullRedKey", "green-Key", "blue-Key");
  }

  /**
   * Tests that combining RGB components with a null green component throws a NullPointerException.
   */
  @Test(expected = NullPointerException.class)
  public void testCombineRgbWithNullGreenComponentThrowsNullPointerException() {
    // Test combining RGB components with null green component
    image.storePixels("nullKey", null);
    image.combine("nullKey", "red-Key", "nullGreenKey", "blue-Key");
  }

  /**
   * Tests that combining RGB components with a null blue component throws a NullPointerException.
   */
  @Test(expected = NullPointerException.class)
  public void testCombineRgbWithNullBlueComponentThrowsNullPointerException() {
    // Test combining RGB components with null blue component
    image.storePixels("nullKey", null);
    image.combine("nullKey", "red-Key", "green-Key", "nullBlueKey");
  }
}
