import Model.Image;
import Controller.ImageController;
import Model.ColorScheme.RGBPixel;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ImageExceptionTest {

  private Image image;
  private ImageController controller;
  RGBPixel[][] operationPixels;
  RGBPixel[][] expectedPixels;


  private void assertImageEquals(RGBPixel[][] expected, RGBPixel[][] actual) {
    for (int i = 0; i < operationPixels.length; i++) {
      for (int j = 0; j < operationPixels[0].length; j++) {
        assertEquals(expectedPixels[i][j].getRed(), operationPixels[i][j].getRed());
        assertEquals(expectedPixels[i][j].getGreen(), operationPixels[i][j].getGreen());
        assertEquals(expectedPixels[i][j].getBlue(), operationPixels[i][j].getBlue());
      }
    }
  }

  @Before
  public void setUp() {
    image = new Image();
    controller = new ImageController(image);
    controller.loadIMage("testKey", "test/Test_Image/Landscape.png");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testLoadImageWithUnsupportedFormatThrowsIllegalArgumentException() {
    controller.loadIMage("testKey", "test/Test_Image/unsupported.xyz");
  }

  @Test(expected = NullPointerException.class)
  public void testRedComponentWithNullImageThrowsIOException() {
    controller.loadIMage("nullKey", null);
    controller.applyOperations("red-component", "nullKey", "red-component-Key");
  }

  @Test(expected = NullPointerException.class)
  public void testGreenComponentWithNullImageThrowsIOException() {
    controller.loadIMage("nullKey", null);
    controller.applyOperations("green-component", "nullKey", "green-component-Key");
  }

  @Test(expected = NullPointerException.class)
  public void testBlueComponentWithNullImageThrowsIOException() {
    controller.loadIMage("nullKey", null);
    controller.applyOperations("blue-component", "nullKey", "blue-component-Key");
  }

  @Test(expected = NullPointerException.class)
  public void testValueComponentWithNullImageThrowsNullPointerException() {
    controller.loadIMage("nullKey", null);
    controller.applyOperations("value-component", "nullKey", "value-component-Key");
  }

  @Test(expected = NullPointerException.class)
  public void testLumaComponentWithNullImageThrowsNullPointerException() {
    controller.loadIMage("nullKey", null);
    controller.applyOperations("luma-component", "nullKey", "luma-component-Key");
  }

  @Test(expected = NullPointerException.class)
  public void testIntensityComponentWithNullImageThrowsNullPointerException() {
    controller.loadIMage("nullKey", null);
    controller.applyOperations("intensity-component", "nullKey", "intensity-Key");
  }

  @Test(expected = NullPointerException.class)
  public void testFlipHorizontallyWithNullImageThrowsNullPointerException() {
    controller.loadIMage("nullKey", null);
    controller.applyOperations("horizontal-flip", "nullKey", "horizontal-flip-Key");
  }

  @Test(expected = NullPointerException.class)
  public void testFlipVerticallyWithNullImageThrowsNullPointerException() {
    controller.loadIMage("nullKey", null);
    controller.applyOperations("vertical-flip", "nullKey", "vertical-flip-Key");
  }

  @Test(expected = NullPointerException.class)
  public void testBrightenImageWithNullImageThrowsNullPointerException() {
    controller.loadIMage("nullKey", null);
    controller.brighten(20, "nullKey", "brightened-Key");
  }

  @Test(expected = NullPointerException.class)
  public void testRgbSplitWithNullImageThrowsNullPointerException() {
    controller.loadIMage("nullKey", null);
    controller.split("nullKey", "red-Key", "green-Key", "blue-Key");
  }

  @Test(expected = NullPointerException.class)
  public void testApplySepiaWithNullImageThrowsNullPointerException() {
    controller.loadIMage("nullKey", null);
    controller.applyOperations("sepia", "nullKey", "sepia-Key");
  }

  @Test(expected = NullPointerException.class)
  public void testBlurWithNullImageThrowsNullPointerException() {
    controller.loadIMage("nullKey", null);
    controller.applyOperations("blur", "nullKey", "blur-Key");
  }

  @Test(expected = NullPointerException.class)
  public void testSharpenWithNullImageThrowsNullPointerException() {
    controller.loadIMage("nullKey", null);
    controller.applyOperations("sharpen", "nullKey", "sharpen-Key");
  }

  @Test(expected = NullPointerException.class)
  public void testApplyGreyScaleWithNullImageThrowsNullPointerException() {
    controller.loadIMage("nullKey", null);
    controller.applyOperations("greyscale", "nullKey", "greyscale-Key");
  }

  @Test(expected = NullPointerException.class)
  public void testCombineRgbWithNullRedComponentThrowsNullPointerException() {
    controller.loadIMage("nullKey", null);
    controller.combine("nullKey", "nullRedKey", "green-Key", "blue-Key");
  }

  @Test(expected = NullPointerException.class)
  public void testCombineRgbWithNullGreenComponentThrowsNullPointerException() {
    controller.loadIMage("nullKey", null);
    controller.combine("nullKey", "red-Key", "nullGreenKey", "blue-Key");
  }

  @Test(expected = NullPointerException.class)
  public void testCombineRgbWithNullBlueComponentThrowsNullPointerException() {
    controller.loadIMage("nullKey", null);
    controller.combine("nullKey", "red-Key", "green-Key", "nullBlueKey");
  }

}