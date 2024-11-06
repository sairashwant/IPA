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
 * The model.ImageExceptionTest class contains unit tests for the ImageController class to validate that
 * appropriate exceptions are thrown when invalid operations are performed on images, such as
 * loading unsupported formats or applying operations on null images.
 */
public class ImageExceptionTest {

  private Image image;
  private ImageController controller;
  RGBPixel[][] operationPixels;
  RGBPixel[][] expectedPixels;
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
   * and loads a test image.
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
    ImageUtil.loadImage( "test/Test_Image/unsupported.xyz");
  }

  /**
   * Tests that applying the red component operation on a null image throws a NullPointerException.
   */
  @Test(expected = NullPointerException.class)
  public void testRedComponentWithNullImageThrowsNullPointerException() {
    image.storePixels("nullKey", null);
    image.getRedChannel("nullKey", "red-component-Key");
  }

  /**
   * Tests that applying the green component operation on a null image throws a NullPointerException.
   */
  @Test(expected = NullPointerException.class)
  public void testGreenComponentWithNullImageThrowsNullPointerException() {
    image.storePixels("nullKey", null);
    image.getGreenChannel("nullKey", "green-component-Key");
  }

  /**
   * Tests that applying the blue component operation on a null image throws a NullPointerException.
   */
  @Test(expected = NullPointerException.class)
  public void testBlueComponentWithNullImageThrowsNullPointerException() {
    image.storePixels("nullKey", null);
    image.getBlueChannel("nullKey", "blue-component-Key");
  }

  /**
   * Tests that applying the value component operation on a null image throws a NullPointerException.
   */
  @Test(expected = NullPointerException.class)
  public void testValueComponentWithNullImageThrowsNullPointerException() {
    image.storePixels("nullKey", null);
    image.value("nullKey", "value-component-Key");
  }

  /**
   * Tests that applying the Luma component operation on a null image throws a NullPointerException.
   */
  @Test(expected = NullPointerException.class)
  public void testLumaComponentWithNullImageThrowsNullPointerException() {
    image.storePixels("nullKey", null);
    image.luma("nullKey", "luma-component-Key");
  }

  /**
   * Tests that applying the intensity component operation on a null image throws a NullPointerException.
   */
  @Test(expected = NullPointerException.class)
  public void testIntensityComponentWithNullImageThrowsNullPointerException() {
    image.storePixels("nullKey", null);
    image.intensity("nullKey", "intensity-component-Key");
  }

  /**
   * Tests that flipping an image horizontally when it is null throws a NullPointerException.
   */
  @Test(expected = NullPointerException.class)
  public void testFlipHorizontallyWithNullImageThrowsNullPointerException() {
    image.storePixels("nullKey", null);
    image.flip("nullKey", "horizontal-flip-Key", Direction.HORIZONTAL);
  }

  /**
   * Tests that flipping an image vertically when it is null throws a NullPointerException.
   */
  @Test(expected = NullPointerException.class)
  public void testFlipVerticallyWithNullImageThrowsNullPointerException() {
    image.storePixels("nullKey", null);
    image.flip("nullKey", "vertical-flip-Key",Direction.VERTICAL);
  }

  /**
   * Tests that brightening a null image throws a NullPointerException.
   */
  @Test(expected = NullPointerException.class)
  public void testBrightenImageWithNullImageThrowsNullPointerException() {
    image.storePixels("nullKey", null);
    image.brighten(20, "nullKey", "brightened-Key");
  }

  /**
   * Tests that splitting a null image throws a NullPointerException.
   */
  @Test(expected = NullPointerException.class)
  public void testRgbSplitWithNullImageThrowsNullPointerException() {
    image.storePixels("nullKey", null);
    image.split("nullKey", "red-Key", "green-Key", "blue-Key");
  }

  /**
   * Tests that applying a sepia effect on a null image throws a NullPointerException.
   */
  @Test(expected = NullPointerException.class)
  public void testApplySepiaWithNullImageThrowsNullPointerException() {
    image.storePixels("nullKey", null);
    image.sepia("nullKey", "sepia-Key");
  }

  /**
   * Tests that applying a blur effect on a null image throws a NullPointerException.
   */
  @Test(expected = NullPointerException.class)
  public void testBlurWithNullImageThrowsNullPointerException() {
    image.storePixels("nullKey", null);
    image.blur("nullKey", "blur-Key");
  }

  /**
   * Tests that applying a sharpen effect on a null image throws a NullPointerException.
   */
  @Test(expected = NullPointerException.class)
  public void testSharpenWithNullImageThrowsNullPointerException() {
    image.storePixels("nullKey", null);
    image.sharpen("nullKey", "sharpen-Key");
  }

  /**
   * Tests that applying a greyscale effect on a null image throws a NullPointerException.
   */
  @Test(expected = NullPointerException.class)
  public void testApplyGreyScaleWithNullImageThrowsNullPointerException() {
    image.storePixels("nullKey", null);
    image.greyScale("nullKey", "greyscale-Key");
  }

  /**
   * Tests that combining RGB components with a null red component throws a NullPointerException.
   */
  @Test(expected = NullPointerException.class)
  public void testCombineRgbWithNullRedComponentThrowsNullPointerException() {
    image.storePixels("nullKey", null);
    image.combine("nullKey", "nullRedKey", "green-Key", "blue-Key");
  }

  /**
   * Tests that combining RGB components with a null green component throws a NullPointerException.
   */
  @Test(expected = NullPointerException.class)
  public void testCombineRgbWithNullGreenComponentThrowsNullPointerException() {
    image.storePixels("nullKey", null);
    image.combine("nullKey", "red-Key", "nullGreenKey", "blue-Key");
  }

  /**
   * Tests that combining RGB components with a null blue component throws a NullPointerException.
   */
  @Test(expected = NullPointerException.class)
  public void testCombineRgbWithNullBlueComponentThrowsNullPointerException() {
    image.storePixels("nullKey", null);
    image.combine("nullKey", "red-Key", "green-Key", "nullBlueKey");
  }
}
