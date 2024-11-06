package controller;

import static org.junit.Assert.*;

import java.io.File;
import java.io.StringReader;
import model.Image;
import model.colorscheme.Pixels;
import model.colorscheme.RGBPixel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ImageControllerTest {
  private StringBuilder output;
  private Readable in;
  private ImageController controller;
  private Image image;
  Pixels[][] operationPixels;
  Pixels[][] expectedPixels;
  String source = "load test/Test_Image/Landscape.png testKey\n";

  private void assertImageEquals(RGBPixel[][] expected, RGBPixel[][] actual) {
    for (int i = 0; i < expected.length; i++) {
      for (int j = 0; j < expected[0].length; j++) {
        assertEquals(expected[i][j].getRed(), actual[i][j].getRed(), 2);
        assertEquals(expected[i][j].getGreen(), actual[i][j].getGreen(), 2);
        assertEquals(expected[i][j].getBlue(), actual[i][j].getBlue(), 2);
      }
    }
  }

  @Before
  public void setUp() {
    output = new StringBuilder();
    image = new Image();
    in = new StringReader("");
    controller = new ImageController(image, in, output);
  }


  @Test
  public void testBlur() {
    String input = source+"blur testKey blurred-test\nexit";
    runControllerWithInput(input);
    operationPixels = image.getStoredPixels("blurred-test");
    image.storePixels("expected-Blurred-Key", ImageUtil.loadImage("test/Test_Image/png_op/landscape-blur.png"));
    expectedPixels = image.getStoredPixels("expected-Blurred-Key");
    assertImageEquals((RGBPixel[][]) expectedPixels, (RGBPixel[][]) operationPixels);
  }

  @Test
  public void testHorizontalFlip() {
    String input = "load test/Test_Image/Landscape.png testKey\nhorizontal-flip testKey flipped-test\nsave test/Test_Image/flipped-horizontal.png flipped-test\nexit";
    runControllerWithInput(input);
    assertTrue(new File("test/Test_Image/flipped-horizontal.png").exists());
  }

  @Test
  public void testVerticalFlip() {
    String input = "load test/Test_Image/Landscape.png testKey\nvertical-flip testKey flipped-test\nsave test/Test_Image/flipped-vertical.png flipped-test\nexit";
    runControllerWithInput(input);
    assertTrue(new File("test/Test_Image/flipped-vertical.png").exists());
  }

  @Test
  public void testBrighten() {
    String input = "load test/Test_Image/Landscape.png testKey\nbrighten 20 testKey brightened-test\nsave test/Test_Image/brightened.png brightened-test\nexit";
    runControllerWithInput(input);

    assertTrue(new File("test/Test_Image/brightened.png").exists());
  }

  @Test
  public void testGreyscale() {
    String input = "load test/Test_Image/Landscape.png testKey\ngreyscale testKey grey-test\nsave test/Test_Image/greyscale.png grey-test\nexit";
    runControllerWithInput(input);
    assertTrue(new File("test/Test_Image/greyscale.png").exists());
  }

  @Test
  public void testRGBSplit() {
    String input = "load test/Test_Image/Landscape.png testKey\nrgb-split testKey red-test green-test blue-test\n" +
        "save test/Test_Image/red-component.png red-test\n" +
        "save test/Test_Image/green-component.png green-test\n" +
        "save test/Test_Image/blue-component.png blue-test\nexit";
    runControllerWithInput(input);
    assertTrue(new File("test/Test_Image/red-component.png").exists());
    assertTrue(new File("test/Test_Image/green-component.png").exists());
    assertTrue(new File("test/Test_Image/blue-component.png").exists());
  }

  @Test
  public void testRGBCombine() {
    String input = "load test/Test_Image/red-component.png red-test\n" +
        "load test/Test_Image/green-component.png green-test\n" +
        "load test/Test_Image/blue-component.png blue-test\n" +
        "rgb-combine combined-test red-test green-test blue-test\n" +
        "save test/Test_Image/combined.png combined-test\nexit";
    runControllerWithInput(input);
    assertTrue(new File("test/Test_Image/combined.png").exists());
  }

  @Test
  public void testLuma() {
    String input = "load test/Test_Image/Landscape.png testKey\nluma-component testKey luma-test\nsave test/Test_Image/luma.png luma-test\nexit";
    runControllerWithInput(input);
    assertTrue(new File("test/Test_Image/luma.png").exists());
  }

  @Test
  public void testSepia() {
    String input = "load test/Test_Image/Landscape.png testKey\nsepia testKey sepia-test\nsave test/Test_Image/sepia.png sepia-test\nexit";
    runControllerWithInput(input);
    assertTrue(new File("test/Test_Image/sepia.png").exists());
  }

  @Test
  public void testSharpen() {
    String input = "load test/Test_Image/Landscape.png testKey\nsharpen testKey sharp-test\nsave test/Test_Image/sharpened.png sharp-test\nexit";
    runControllerWithInput(input);
    assertTrue(new File("test/Test_Image/sharpened.png").exists());
  }

  @Test
  public void testLoadImage() {
    String input = "load test/Test_Image/Landscape.png testKey\nexit";
    runControllerWithInput(input);
    assertNotNull(image.getStoredPixels("testKey"));
  }

  @Test
  public void testSaveImage() {
    String input = "load test/Test_Image/Landscape.png testKey\nsave test/Test_Image/output.png testKey\nexit";
    runControllerWithInput(input);
    assertTrue(new File("test/Test_Image/output.png").exists());
    new File("test/Test_Image/output.png").delete(); // Clean up
  }

  private void runControllerWithInput(String input) {
    output = new StringBuilder(); // Reset the output
    in = new StringReader(input);
    controller = new ImageController(image, in, output);
    controller.run();
  }
}