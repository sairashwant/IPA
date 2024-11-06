package model;

import controller.ImageController;
import controller.ImageUtil;
import model.colorscheme.Pixels;
import model.colorscheme.RGBPixel;
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
  Pixels[][] operationPixels;
  Pixels[][] expectedPixels;
  HashMap<String, Pixels[][]> t1 = new HashMap<>();

  String load = "test/Test_Image/P3.ppm";
  Pixels[][] pixels = ImageUtil.loadImage(load); // The test PPM file path

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
    image.storePixels("testKey", pixels);
  }

  /**
   * Tests the blur operation on the image.
   */
  @Test
  public void testBlur() {
    Blur blur = new Blur();
    operationPixels = blur.apply(image.getStoredPixels("testKey")); // Apply the blur operation
    image.storePixels("expected-Blurred-Key", ImageUtil.loadImage("test/Test_Image/ppm_op/P3-blur.ppm"));
    expectedPixels = image.getStoredPixels("expected-Blurred-Key");

    // Compare the actual pixels with the expected pixels
    assertImageEquals((RGBPixel[][]) expectedPixels, (RGBPixel[][])operationPixels);
  }

  /**
   * Tests the horizontal flip operation on the image.
   */
  @Test
  public void testHorizontalFlip() {
    Flip flip = new Flip();
    operationPixels = flip.apply(image.getStoredPixels("testKey"), Flip.Direction.HORIZONTAL);
    image.storePixels("expected-horizontal-flip-Key", ImageUtil.loadImage("test/Test_Image/ppm_op/P3-horizontal-flip.ppm"));
    expectedPixels = image.getStoredPixels("expected-horizontal-flip-Key");

    // Compare the actual pixels with the expected pixels
    assertImageEquals((RGBPixel[][])expectedPixels, (RGBPixel[][])operationPixels);
  }

  /**
   * Tests the vertical flip operation on the image.
   */
  @Test
  public void testVerticalFlip() {
    Flip flip = new Flip();
    operationPixels = flip.apply(image.getStoredPixels("testKey"), Flip.Direction.VERTICAL);
    image.storePixels("expected-vertical-flip-Key", ImageUtil.loadImage("test/Test_Image/ppm_op/P3-vertical-flip.ppm"));
    expectedPixels = image.getStoredPixels("expected-vertical-flip-Key");

    // Compare the actual pixels with the expected pixels
    assertImageEquals((RGBPixel[][])expectedPixels, (RGBPixel[][])operationPixels);
  }

  /**
   * Tests the brighten operation on the image.
   */
  @Test
  public void testBrighten() {
    Brighten brighten = new Brighten(20); // Brighten by an intensity of 20
    operationPixels = brighten.apply(image.getStoredPixels("testKey"));
    image.storePixels("expected-BrightenedKey", ImageUtil.loadImage("test/Test_Image/ppm_op/P3-brighter.ppm"));
    expectedPixels = image.getStoredPixels("expected-BrightenedKey");

    // Compare the actual pixels with the expected pixels
    assertImageEquals((RGBPixel[][])expectedPixels, (RGBPixel[][])operationPixels);
  }

  /**
   * Tests the darken operation on the image by applying a negative brightness adjustment.
   */
  @Test
  public void testNegativeBrighten() {
    Brighten brighten = new Brighten(-20); // Darken by an intensity of -20
    operationPixels = brighten.apply(image.getStoredPixels("testKey"));
    image.storePixels("expected-darkened-Key", ImageUtil.loadImage("test/Test_Image/ppm_op/P3-darker.ppm"));
    expectedPixels = image.getStoredPixels("expected-darkened-Key");

    // Compare the actual pixels with the expected pixels
    assertImageEquals((RGBPixel[][])expectedPixels, (RGBPixel[][])operationPixels);
  }

  /**
   * Tests the greyscale operation on the image.
   */
  @Test
  public void testGreyscale() {
    GreyScale greyscale = new GreyScale();
    operationPixels = greyscale.apply(image.getStoredPixels("testKey"));
    image.storePixels("expected-greyscale-Key", ImageUtil.loadImage("test/Test_Image/ppm_op/P3-greyscale.ppm"));
    expectedPixels = image.getStoredPixels("expected-greyscale-Key");

    // Compare the actual pixels with the expected pixels
    assertImageEquals((RGBPixel[][])expectedPixels, (RGBPixel[][])operationPixels);
  }

  /**
   * Tests the sepia effect on the image.
   */
  @Test
  public void testSepia() {
    Sepia sepia = new Sepia();
    operationPixels = sepia.apply(image.getStoredPixels("testKey"));
    image.storePixels("expected-sepia-Key", ImageUtil.loadImage("test/Test_Image/ppm_op/P3-sepia.ppm"));
    expectedPixels = image.getStoredPixels("expected-sepia-Key");

    // Compare the actual pixels with the expected pixels
    assertImageEquals((RGBPixel[][])expectedPixels, (RGBPixel[][])operationPixels);
  }

  /**
   * Tests sharpening the image.
   */
  @Test
  public void testSharpen() {
    Sharpen sharpen = new Sharpen();
    operationPixels = sharpen.apply(image.getStoredPixels("testKey"));
    image.storePixels("expected-sharpen-Key", ImageUtil.loadImage("test/Test_Image/ppm_op/P3-sharper.ppm"));
    expectedPixels = image.getStoredPixels("expected-sharpen-Key");

    // Compare the actual pixels with the expected pixels
    assertImageEquals((RGBPixel[][])expectedPixels, (RGBPixel[][])operationPixels);
  }

  /**
   * Tests extracting the Luma component from the image.
   */
  @Test
  public void testLuma() {
    Luma luma = new Luma();
    operationPixels = luma.apply(image.getStoredPixels("testKey"));
    image.storePixels("expected-Luma-component-Key", ImageUtil.loadImage("test/Test_Image/ppm_op/P3-luma.ppm"));
    expectedPixels = image.getStoredPixels("expected-Luma-component-Key");

    // Compare the actual pixels with the expected pixels
    assertImageEquals((RGBPixel[][])expectedPixels, (RGBPixel[][])operationPixels);
  }

  /**
   * Tests the splitting and combining of image channels (red, green, blue).
   */
  @Test
  public void testCombine() {
    Split split = new Split();
    t1.put("testKey", image.getStoredPixels("testKey"));

    HashMap<String, Pixels[][]> splitResult = split.apply(t1, t1.get("testKey"),
        "testKey", "red-Key", "green-Key", "blue-Key");
    t1.putAll(splitResult);

    Combine combine = new Combine();
    operationPixels = combine.apply(t1.get("red-Key"), t1.get("green-Key"), t1.get("blue-Key"));
    image.storePixels("expected-combine-Key", ImageUtil.loadImage("test/Test_Image/P3.ppm"));
    expectedPixels = image.getStoredPixels("expected-combine-Key");

    assertImageEquals((RGBPixel[][]) expectedPixels, (RGBPixel[][]) operationPixels);
  }
}
