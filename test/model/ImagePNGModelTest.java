package model;

import controller.ImageUtil;
import java.util.HashMap;
import controller.ImageController;
import model.colorscheme.Pixels;
import model.colorscheme.RGBPixel;
import model.imagetransformation.basicoperation.Brighten;
import model.imagetransformation.basicoperation.Combine;
import model.imagetransformation.basicoperation.Flip;
import model.imagetransformation.basicoperation.Flip.Direction;
import model.imagetransformation.basicoperation.Luma;
import model.imagetransformation.basicoperation.Split;
import model.imagetransformation.colortransformation.GreyScale;
import model.imagetransformation.colortransformation.Sepia;
import model.imagetransformation.filtering.Blur;
import model.imagetransformation.filtering.Sharpen;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Unit tests for the {@link Image} model and the {@link ImageController} operations, specifically
 * focused on handling PNG images. This class includes tests for various image operations such as
 * loading an image, applying filters (e.g., blur, greyscale, sepia), flipping, brightness
 * adjustment, and channel extraction (red, green, blue components).
 */
public class ImagePNGModelTest {

  private Image image;
  Pixels[][] operationPixels;
  Pixels[][] expectedPixels;
  HashMap<String, Pixels[][]> t1 = new HashMap<>();

  String load = "test/Test_Image/Landscape.png";
  Pixels[][] pixels = ImageUtil.loadImage(load);

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
   * Tests the blur operation on the image by applying the blur filter and comparing the result with
   * an expected output image.
   */
  @Test
  public void testBlur() {
    Blur blur = new Blur();
    operationPixels = blur.apply(image.getStoredPixels("testKey"));
    image.storePixels("expected-Blurred-Key",
        ImageUtil.loadImage("test/Test_Image/png_op/landscape-blur.png"));
    expectedPixels = image.getStoredPixels("expected-Blurred-Key");

    assertImageEquals((RGBPixel[][]) expectedPixels, (RGBPixel[][]) operationPixels);
  }

  /**
   * Tests the horizontal flip operation on the image by flipping the image horizontally and
   * comparing the result with an expected output image.
   */
  @Test
  public void testHorizontalFlip() {
    Flip flip = new Flip();
    operationPixels = flip.apply(image.getStoredPixels("testKey"), Direction.HORIZONTAL);
    image.storePixels("expected-horizontal-flip-Key",
        ImageUtil.loadImage("test/Test_Image/png_op/landscape-horizontal-flip.png"));
    expectedPixels = image.getStoredPixels("expected-horizontal-flip-Key");

    assertImageEquals((RGBPixel[][]) expectedPixels, (RGBPixel[][]) operationPixels);
  }

  /**
   * Tests the vertical flip operation on the image by flipping the image vertically and comparing
   * the result with an expected output image.
   */
  @Test
  public void testVerticalFlip() {
    Flip flip = new Flip();
    operationPixels = flip.apply(image.getStoredPixels("testKey"), Direction.VERTICAL);
    image.storePixels("expected-vertical-flip-Key",
        ImageUtil.loadImage("test/Test_Image/png_op/landscape-vertical-flip.png"));
    expectedPixels = image.getStoredPixels("expected-vertical-flip-Key");

    assertImageEquals((RGBPixel[][]) expectedPixels, (RGBPixel[][]) operationPixels);
  }

  /**
   * Tests the brighten operation on the image by applying a brightness adjustment and comparing the
   * result with an expected output image.
   */
  @Test
  public void testBrighten() {
    Brighten brighten = new Brighten(20);
    operationPixels = brighten.apply(image.getStoredPixels("testKey"));
    image.storePixels("expected-BrightenedKey",
        ImageUtil.loadImage("test/Test_Image/png_op/landscape-brighter.png"));
    expectedPixels = image.getStoredPixels("expected-BrightenedKey");

    assertImageEquals((RGBPixel[][]) expectedPixels, (RGBPixel[][]) operationPixels);
  }

  /**
   * Tests the darken operation on the image by applying a negative brightness adjustment and
   * comparing the result with an expected output image.
   */
  @Test
  public void testNegativeBrighten() {
    Brighten brighten = new Brighten(-20);
    operationPixels = brighten.apply(image.getStoredPixels("testKey"));
    image.storePixels("expected-darkened-Key",
        ImageUtil.loadImage("test/Test_Image/png_op/landscape-darker.png"));
    expectedPixels = image.getStoredPixels("expected-darkened-Key");

    assertImageEquals((RGBPixel[][]) expectedPixels, (RGBPixel[][]) operationPixels);
  }

  /**
   * Tests the greyscale operation on the image by applying a greyscale filter and comparing the
   * result with an expected output image.
   */
  @Test
  public void testGreyscale() {
    GreyScale greyscale = new GreyScale();
    operationPixels = greyscale.apply(image.getStoredPixels("testKey"));
    image.storePixels("expected-greyscale-Key",
        ImageUtil.loadImage("test/Test_Image/png_op/landscape-greyscale.png"));
    expectedPixels = image.getStoredPixels("expected-greyscale-Key");

    assertImageEquals((RGBPixel[][]) expectedPixels, (RGBPixel[][]) operationPixels);
  }

  /**
   * Tests the combine operation on the image by combining split red, green, and blue components
   * into a full image and comparing the result with an expected output image.
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
    image.storePixels("expected-combine-Key", ImageUtil.loadImage("test/Test_Image/Landscape.png"));
    expectedPixels = image.getStoredPixels("expected-combine-Key");

    assertImageEquals((RGBPixel[][]) expectedPixels, (RGBPixel[][]) operationPixels);
  }

  /**
   * Tests the split operation on the image by extracting red, green, and blue components and
   * comparing each component with the expected output images.
   */
  @Test
  public void testSplit() {
    Split split = new Split();
    HashMap<String, Pixels[][]> t1 = new HashMap<>();
    t1.put("testKey", image.getStoredPixels("testKey"));

    HashMap<String, Pixels[][]> splitResult = split.apply(t1, t1.get("testKey"),
        "testKey", "red-Key", "green-Key", "blue-Key");

    Pixels[][] operationPixels = splitResult.get("red-Key");
    image.storePixels("expected-red-Key",
        ImageUtil.loadImage("test/Test_Image/png_op/landscape-red-component.png"));
    Pixels[][] expectedPixels = image.getStoredPixels("expected-red-Key");
    assertImageEquals((RGBPixel[][]) expectedPixels, (RGBPixel[][]) operationPixels);

    operationPixels = splitResult.get("green-Key");
    image.storePixels("expected-green-split-Key",
        ImageUtil.loadImage("test/Test_Image/png_op/landscape-green-component.png"));
    expectedPixels = image.getStoredPixels("expected-green-split-Key");
    assertImageEquals((RGBPixel[][]) expectedPixels, (RGBPixel[][]) operationPixels);

    operationPixels = splitResult.get("blue-Key");
    image.storePixels("expected-blue-split-Key",
        ImageUtil.loadImage("test/Test_Image/png_op/landscape-blue-component.png"));
    expectedPixels = image.getStoredPixels("expected-blue-split-Key");
    assertImageEquals((RGBPixel[][]) expectedPixels, (RGBPixel[][]) operationPixels);
  }

  /**
   * Tests extracting the Luma component from the image and compares the result with an expected
   * output.
   */
  @Test
  public void testLuma() {
    Luma luma = new Luma();
    operationPixels = luma.apply(image.getStoredPixels("testKey"));
    image.storePixels("expected-Luma-component-Key",
        ImageUtil.loadImage("test/Test_Image/png_op/landscape-luma.png"));
    expectedPixels = image.getStoredPixels("expected-Luma-component-Key");

    assertImageEquals((RGBPixel[][]) expectedPixels, (RGBPixel[][]) operationPixels);
  }

  /**
   * Tests the sepia tone operation on the image by applying a sepia filter and comparing the result
   * with an expected output image.
   */
  @Test
  public void testSepia() {
    Sepia sepia = new Sepia();
    operationPixels = sepia.apply(image.getStoredPixels("testKey"));
    image.storePixels("expected-SepiaKey",
        ImageUtil.loadImage("test/Test_Image/png_op/landscape-sepia.png"));
    expectedPixels = image.getStoredPixels("expected-SepiaKey");

    assertImageEquals((RGBPixel[][]) expectedPixels, (RGBPixel[][]) operationPixels);
  }

  /**
   * Tests the sharpen operation on the image by applying the sharpen filter and comparing the
   * result with an expected output image.
   */
  @Test
  public void testSharpen() {
    Sharpen sharpen = new Sharpen();
    operationPixels = sharpen.apply(image.getStoredPixels("testKey"));
    image.storePixels("expected-sharpen-Key",
        ImageUtil.loadImage("test/Test_Image/png_op/landscape-sharpened.png"));
    expectedPixels = image.getStoredPixels("expected-sharpen-Key");

    assertImageEquals((RGBPixel[][]) expectedPixels, (RGBPixel[][]) operationPixels);
  }
}
