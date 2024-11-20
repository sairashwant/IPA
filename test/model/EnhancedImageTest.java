package model;

import static org.junit.Assert.*;

import controller.ImageUtil;
import model.colorscheme.Pixels;
import model.colorscheme.RGBPixel;
import model.imagetransformation.advancedoperations.MaskedOperation;
import org.junit.Test;

public class EnhancedImageTest {

  @Test
  public void testEnhancedImage() {
    EnhancedImage enhancedImage = new EnhancedImage();
    String load = "test/Test_Image/4x4.jpg";
    Pixels[][] pixels = ImageUtil.loadImage(load);

    // Ensure pixels are loaded correctly
    assertNotNull("Pixels should not be null after loading the image", pixels);

    enhancedImage.storePixels("test", pixels);
    // Check if the pixels are stored properly
    assertNotNull("Stored pixels should not be null", enhancedImage.getStoredPixels("test"));

    enhancedImage.downscale("test", 2, 2, "down");
    Pixels[][] downscaledPixels = enhancedImage.getStoredPixels("down");

    // Ensure downscaled pixels are not null
    assertNotNull("Downscaled pixels should not be null", downscaledPixels);

    // Use a valid file name with an appropriate extension
    String outputFileName = "test/Test_Image/downscaled_image.jpg"; // Ensure the file path is correct
    ImageUtil.saveImage(outputFileName, downscaledPixels);
  }
//  @Test
//  public void testMaskedBlurOperation() {
//    EnhancedImage enhancedImage = new EnhancedImage();
//    String load = "test/Test_Image/4x4.jpg";
//    Pixels[][] originalPixels = ImageUtil.loadImage(load);
//    enhancedImage.storePixels("test", originalPixels);
//    // Create a mask for the operation (for example, a simple black mask)
//    Pixels[][] mask = new Pixels[originalPixels.length][originalPixels[0].length];
//    for (int i = 0; i < mask.length; i++) {
//      for (int j = 0; j < mask[0].length; j++) {
//        mask[i][j] = new RGBPixel(0, 0, 0); // Black mask
//      }
//    }
//
//    // Apply a masked operation (e.g., blur) using the mask
//    MaskedOperation maskedOperation = new MaskedOperation("blur", mask);
//    Pixels[][] resultPixels = maskedOperation.apply(enhancedImage.getStoredPixels("test"));
//
//    // Ensure the result is not null
//    assertNotNull("Result pixels should not be null after masked operation", resultPixels);
//
//    // Optionally, you can save the result to verify visually
//    String outputFileName = "test/Test_Image/masked_blur_result.jpg"; // Ensure the file path is correct
//    ImageUtil.saveImage(outputFileName, resultPixels);
//
//    // Optionally, you can compare the result with an expected output
//    Pixels[][] expectedPixels = ImageUtil.loadImage("test/Test_Image/masked_blur_result.jpg");
//    assertImageEquals(expectedPixels, resultPixels);
//  }

  // Helper method to assert that two pixel arrays are equal
  private void assertImageEquals(Pixels[][] expected, Pixels[][] actual) {
    for (int i = 0; i < expected.length; i++) {
      for (int j = 0; j < expected[0].length; j++) {
        RGBPixel expectedPixel = (RGBPixel) expected[i][j];
        RGBPixel actualPixel = (RGBPixel) actual[i][j];
        assertEquals(expectedPixel.getRed(), actualPixel.getRed(), 2);
        assertEquals(expectedPixel.getGreen(), actualPixel.getGreen(), 2);
        assertEquals(expectedPixel.getBlue(), actualPixel.getBlue(), 2);
      }
    }
  }
}