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
 * Unit tests for the {@link Image} model and the {@link ImageController} operations specifically
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
   * Tests the blur operation on the image.
   */
  @Test
  public void testBlur() {
    Blur blur = new Blur();
    operationPixels = blur.apply(image.getStoredPixels("testKey")); // Apply the blur
    image.storePixels("expected-Blurred-Key", ImageUtil.loadImage("test/Test_Image/png_op/landscape-blur.png"));
    expectedPixels = image.getStoredPixels("expected-Blurred-Key"); // Expected image after blur

    // Compare the actual pixels with the expected pixels
    assertImageEquals((RGBPixel[][]) expectedPixels, (RGBPixel[][]) operationPixels);
  }

  /**
   * Tests the horizontal flip operation on the image.
   */
  @Test
  public void testHorizontalFlip() {
    Flip flip = new Flip();
    operationPixels = flip.apply(image.getStoredPixels("testKey"), Direction.HORIZONTAL);
    image.storePixels("expected-horizontal-flip-Key", ImageUtil.loadImage("test/Test_Image/png_op/landscape-horizontal-flip.png"));
    expectedPixels = image.getStoredPixels("expected-horizontal-flip-Key");

    // Compare the actual pixels with the expected pixels
    assertImageEquals((RGBPixel[][]) expectedPixels, (RGBPixel[][]) operationPixels);
  }

  /**
   * Tests the vertical flip operation on the image.
   */
  @Test
  public void testVerticalFlip() {
    Flip flip = new Flip();
    operationPixels = flip.apply(image.getStoredPixels("testKey"), Direction.VERTICAL);
    image.storePixels("expected-vertical-flip-Key", ImageUtil.loadImage("test/Test_Image/png_op/landscape-vertical-flip.png"));
    expectedPixels = image.getStoredPixels("expected-vertical-flip-Key");

    // Compare the actual pixels with the expected pixels
    assertImageEquals((RGBPixel[][]) expectedPixels, (RGBPixel[][]) operationPixels);
  }

  /**
   * Tests the brighten operation on the image.
   */
  @Test
  public void testBrighten() {
    Brighten brighten = new Brighten(20); // Assuming Brighten class takes an intensity parameter
    operationPixels = brighten.apply(image.getStoredPixels("testKey"));
    image.storePixels("expected-BrightenedKey", ImageUtil.loadImage("test/Test_Image/png_op/landscape-brighter.png"));
    expectedPixels = image.getStoredPixels("expected-BrightenedKey");

    // Compare the actual pixels with the expected pixels
    assertImageEquals((RGBPixel[][]) expectedPixels, (RGBPixel[][]) operationPixels);
  }

  /**
   * Tests the darken operation on the image by applying a negative brightness adjustment.
   */
  @Test
  public void testNegativeBrighten() {
    Brighten brighten = new Brighten(-20); // Negative brightness to darken the image
    operationPixels = brighten.apply(image.getStoredPixels("testKey"));
    image.storePixels("expected-darkened-Key", ImageUtil.loadImage("test/Test_Image/png_op/landscape-darker.png"));
    expectedPixels = image.getStoredPixels("expected-darkened-Key");

    // Compare the actual pixels with the expected pixels
    assertImageEquals((RGBPixel[][]) expectedPixels, (RGBPixel[][]) operationPixels);
  }

  /**
   * Tests the greyscale operation on the image.
   */
  @Test
  public void testGreyscale() {
    GreyScale greyscale = new GreyScale();
    operationPixels = greyscale.apply(image.getStoredPixels("testKey"));
    image.storePixels("expected-greyscale-Key", ImageUtil.loadImage("test/Test_Image/png_op/landscape-greyscale.png"));
    expectedPixels = image.getStoredPixels("expected-greyscale-Key");

    // Compare the actual pixels with the expected pixels
    assertImageEquals((RGBPixel[][]) expectedPixels, (RGBPixel[][]) operationPixels);
  }

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

  @Test
  public void testSplit() {
    Split split = new Split();
    HashMap<String, Pixels[][]> t1 = new HashMap<>();
    t1.put("testKey", image.getStoredPixels("testKey"));

    HashMap<String, Pixels[][]> splitResult = split.apply(t1, t1.get("testKey"),
        "testKey", "red-Key", "green-Key", "blue-Key");

    Pixels[][] operationPixels = splitResult.get("red-Key");
    image.storePixels("expected-red-Key", ImageUtil.loadImage("test/Test_Image/png_op/landscape-red-component.png"));
    Pixels[][] expectedPixels = image.getStoredPixels("expected-red-Key");
    assertImageEquals((RGBPixel[][]) expectedPixels, (RGBPixel[][]) operationPixels);

    operationPixels = splitResult.get("green-Key");
    image.storePixels("expected-green-split-Key", ImageUtil.loadImage("test/Test_Image/png_op/landscape-green-component.png"));
    expectedPixels = image.getStoredPixels("expected-green-split-Key");
    assertImageEquals((RGBPixel[][]) expectedPixels, (RGBPixel[][]) operationPixels);

    operationPixels = splitResult.get("blue-Key");
    image.storePixels("expected-blue-split-Key", ImageUtil.loadImage("test/Test_Image/png_op/landscape-blue-component.png"));
    expectedPixels = image.getStoredPixels("expected-blue-split-Key");
    assertImageEquals((RGBPixel[][]) expectedPixels, (RGBPixel[][]) operationPixels);
  }

  /**
   * Tests extracting the Luma component from the image.
   */
  @Test
  public void testLuma() {
    Luma luma = new Luma();
    operationPixels = luma.apply(image.getStoredPixels("testKey"));
    image.storePixels("expected-Luma-component-Key", ImageUtil.loadImage("test/Test_Image/png_op/landscape-luma.png"));
    expectedPixels = image.getStoredPixels("expected-Luma-component-Key");

    assertImageEquals((RGBPixel[][]) expectedPixels, (RGBPixel[][]) operationPixels);
  }

  /**
   * Tests applying the sepia effect to the image.
   */
  @Test
  public void testSepia() {
    Sepia sepia = new Sepia();
    operationPixels = sepia.apply(image.getStoredPixels("testKey"));
    image.storePixels("expected-sepia-key", ImageUtil.loadImage("test/Test_Image/png_op/landscape-sepia.png"));
    expectedPixels = image.getStoredPixels("expected-sepia-key");

    assertImageEquals((RGBPixel[][]) expectedPixels, (RGBPixel[][]) operationPixels);
  }

  /**
   * Tests the sharpening operation on the image.
   */
  @Test
  public void testSharpen() {
    Sharpen sharpen = new Sharpen();
    operationPixels = sharpen.apply(image.getStoredPixels("testKey"));
    image.storePixels("expected-sharpened-key", ImageUtil.loadImage("test/Test_Image/png_op/landscape-sharper.png"));
    expectedPixels = image.getStoredPixels("expected-sharpened-key");

    assertImageEquals((RGBPixel[][]) expectedPixels, (RGBPixel[][]) operationPixels);
  }

  @Test
  public void testHistogram(){
    image.histogram("testKey","histogram-key");
    operationPixels = image.getStoredPixels("histogram-key");
    image.storePixels("expected-histogram-key", ImageUtil.loadImage("test/Test_Image/png_op/Landscape-histogram.png"));
    expectedPixels = image.getStoredPixels("expected-histogram-key");

    assertImageEquals((RGBPixel[][]) expectedPixels, (RGBPixel[][]) operationPixels);
  }

  @Test
  public void testColorCorrection(){
    image.colorCorrection("testKey","cc-key");
    operationPixels = image.getStoredPixels("cc-key");
    image.storePixels("expected-cc-key", ImageUtil.loadImage("test/Test_Image/png_op/Landscape-color-correction.png"));
    expectedPixels = image.getStoredPixels("expected-cc-key");

    assertImageEquals(convertToRGBPixelArray(expectedPixels), convertToRGBPixelArray(operationPixels));
  }

  @Test
  public void testCompression(){
    image.compress("testKey","compression-key",50);
    operationPixels = image.getStoredPixels("compression-key");
    image.storePixels("expected-comp-key", ImageUtil.loadImage("test/Test_Image/png_op/Landscape-compressed-50.png"));
    expectedPixels = image.getStoredPixels("expected-comp-key");

    assertImageEquals(convertToRGBPixelArray(expectedPixels), convertToRGBPixelArray(operationPixels));
  }

  @Test
  public void testLevelsAdjust(){
    image.adjustLevel(20,100,255,"testKey","levelsAdjust-key");
    operationPixels = image.getStoredPixels("levelsAdjust-key");
    image.storePixels("expected-levels-adjust-key", ImageUtil.loadImage("test/Test_Image/png_op/Landscape-levels-adjust.png"));
    expectedPixels = image.getStoredPixels("expected-levels-adjust-key");

    assertImageEquals(convertToRGBPixelArray(expectedPixels), convertToRGBPixelArray(operationPixels));
  }

  @Test
  public void testSplitAndTransformBlur(){
    image.splitAndTransform("testKey","split-blur-key",50,"blur");
    operationPixels = image.getStoredPixels("split-blur-key");
    image.storePixels("expected-split-blur-key", ImageUtil.loadImage("test/Test_Image/png_op/Landscape-split-blur-50.png"));
    expectedPixels = image.getStoredPixels("expected-split-blur-key");

    assertImageEquals(convertToRGBPixelArray(expectedPixels), convertToRGBPixelArray(operationPixels));
  }

  @Test
  public void testSplitAndTransformSepia(){
    image.splitAndTransform("testKey","split-sepia-key",80,"sepia");
    operationPixels = image.getStoredPixels("split-sepia-key");
    image.storePixels("expected-split-sepia-key", ImageUtil.loadImage("test/Test_Image/png_op/Landscape-split-sepia-80.png"));
    expectedPixels = image.getStoredPixels("expected-split-sepia-key");

    assertImageEquals(convertToRGBPixelArray(expectedPixels), convertToRGBPixelArray(operationPixels));
  }

  @Test
  public void testSplitAndTransformLevelsAdjust(){
    image.splitAndTransform("testKey","split-levels-adjust-key",25,"levels-adjust",20,100,255);
    operationPixels = image.getStoredPixels("split-levels-adjust-key");
    image.storePixels("expected-split-levels-adjust-key", ImageUtil.loadImage("test/Test_Image/png_op/Landscape-split-levels-adjust-25.png"));
    expectedPixels = image.getStoredPixels("expected-split-levels-adjust-key");

    assertImageEquals(convertToRGBPixelArray(expectedPixels), convertToRGBPixelArray(operationPixels));
  }

  @Test
  public void testMultipleOperationsSharpenBrightenBy50(){
    image.sharpen("testKey","sharpen-key");
    image.brighten(50,"sharpen-key","brighten-key");
    operationPixels = image.getStoredPixels("brighten-key");
    image.storePixels("expected-combination-key",ImageUtil.loadImage("test/Test_Image/png_op/Landscape-multiple-operations.png"));
    expectedPixels = image.getStoredPixels("expected-combination-key");

    assertImageEquals(convertToRGBPixelArray(expectedPixels), convertToRGBPixelArray(operationPixels));
  }

  @Test
  public void testMultipleOperationsCompressBy50LevelsAdjustHistogram(){
    image.compress("testKey","compress-key",80);
    image.adjustLevel(20,100,255 ,"compress-key","levels-adjust-key");
    image.histogram("levels-adjust-key","histogram-key");
    operationPixels = image.getStoredPixels("histogram-key");
    image.storePixels("expected-histogram-key",ImageUtil.loadImage("test/Test_Image/png_op/Landscape-compress-histogram.png"));
    expectedPixels = image.getStoredPixels("expected-histogram-key");

    assertImageEquals(convertToRGBPixelArray(expectedPixels), convertToRGBPixelArray(operationPixels));
  }



  private RGBPixel[][] convertToRGBPixelArray(Pixels[][] pixelsArray) {
    int height = pixelsArray.length;
    int width = pixelsArray[0].length;
    RGBPixel[][] rgbPixels = new RGBPixel[height][width];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        if (pixelsArray[i][j] instanceof RGBPixel) {
          rgbPixels[i][j] = (RGBPixel) pixelsArray[i][j];
        } else {
          throw new ClassCastException("Element is not of type RGBPixel.");
        }
      }
    }
    return rgbPixels;
  }

}