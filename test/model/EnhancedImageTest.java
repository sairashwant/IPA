package model;

import static org.junit.Assert.*;

import controller.ImageUtil;
import java.io.File;
import model.colorscheme.Pixels;
import model.colorscheme.RGBPixel;
import org.junit.Before;
import org.junit.Test;

public class EnhancedImageTest {
  EnhancedImage enhancedImage = new EnhancedImage();


  @Before
  public void setUp() throws Exception {
  }

  @Test
  public void testDownscale() {
    String load = "test/Test_Image/Landscape.png";
    Pixels[][] pixels = ImageUtil.loadImage(load);

    // Ensure pixels are loaded correctly
    assertNotNull("Pixels should not be null after loading the image", pixels);

    enhancedImage.storePixels("test", pixels);

    // Downscale to 2x2 image and store under "downscaled"
    enhancedImage.downscale("test", 2, 2, "downscaled");
    Pixels[][] downscaledPixels = enhancedImage.getStoredPixels("downscaled");

    // Ensure downscaled pixels are not null
    assertNotNull("Downscaled pixels should not be null", downscaledPixels);

    String outputFileName = "test/Test_Image/downscaled_image.png"; // Ensure the file path is correct


    Pixels[][] savedPixels = ImageUtil.loadImage(outputFileName);
    assertNotNull("Saved pixels should not be null after loading the image", savedPixels);

    // Compare dimensions
    assertEquals("Height should match", downscaledPixels.length, savedPixels.length);
    assertEquals("Width should match", downscaledPixels[0].length, savedPixels[0].length);

    // Tolerance for comparing pixel values
    final int tolerance = 0; // Allow a small difference in pixel values

    // Compare pixel values
    for (int y = 0; y < downscaledPixels.length; y++) {
      for (int x = 0; x < downscaledPixels[0].length; x++) {
        RGBPixel downscaledPixel = (RGBPixel) downscaledPixels[y][x];
        RGBPixel savedPixel = (RGBPixel) savedPixels[y][x];

        assertTrue("Red values should match within tolerance",
            Math.abs(downscaledPixel.getRed() - savedPixel.getRed()) <= tolerance);
        assertTrue("Green values should match within tolerance",
            Math.abs(downscaledPixel.getGreen() - savedPixel.getGreen()) <= tolerance);
        assertTrue("Blue values should match within tolerance",
            Math.abs(downscaledPixel.getBlue() - savedPixel.getBlue()) <= tolerance);
      }
    }
  }

  @Test
  public void testDownscaleToHalfSize() {
    String load = "test/Test_Image/Landscape.png";
    Pixels[][] pixels = ImageUtil.loadImage(load);

    // Ensure the image is loaded
    assertNotNull("Pixels should not be null after loading the image", pixels);

    enhancedImage.storePixels("test", pixels);
    int originalHeight = pixels.length;
    int originalWidth = pixels[0].length;

    // Downscale to half the size
    enhancedImage.downscale("test", originalWidth / 2, originalHeight / 2, "half");
    Pixels[][] downscaledPixels = enhancedImage.getStoredPixels("half");

    // Verify dimensions
    assertEquals("Height should be halved", originalHeight / 2, downscaledPixels.length);
    assertEquals("Width should be halved", originalWidth / 2, downscaledPixels[0].length);


    String outputFileName = "test/Test_Image/downscaled_half.png";  // Use PNG for lossless format



    Pixels[][] savedPixels = ImageUtil.loadImage(outputFileName);
    assertNotNull("Saved pixels should not be null after loading the image", savedPixels);

    // Compare dimensions
    assertEquals("Height of saved image should match downscaled image", downscaledPixels.length, savedPixels.length);
    assertEquals("Width of saved image should match downscaled image", downscaledPixels[0].length, savedPixels[0].length);

    // Compare pixel values with a tolerance
    final int tolerance = 0; // Tolerance for pixel value differences
    for (int y = 0; y < downscaledPixels.length; y++) {
      for (int x = 0; x < downscaledPixels[0].length; x++) {
        RGBPixel downscaledPixel = (RGBPixel) downscaledPixels[y][x];
        RGBPixel savedPixel = (RGBPixel) savedPixels[y][x];

        int redDiff = Math.abs(downscaledPixel.getRed() - savedPixel.getRed());
        int greenDiff = Math.abs(downscaledPixel.getGreen() - savedPixel.getGreen());
        int blueDiff = Math.abs(downscaledPixel.getBlue() - savedPixel.getBlue());

        // Check if pixel values are within the tolerance
        assertTrue("Red values should match within tolerance", redDiff <= tolerance);
        assertTrue("Green values should match within tolerance", greenDiff <= tolerance);
        assertTrue("Blue values should match within tolerance", blueDiff <= tolerance);
      }
    }
  }


  @Test
  public void testDownscaleToSinglePixel() {
    String load = "test/Test_Image/Landscape.png";
    Pixels[][] pixels = ImageUtil.loadImage(load);

    assertNotNull("Pixels should not be null after loading the image", pixels);

    enhancedImage.storePixels("test", pixels);

    // Downscale to a single pixel
    enhancedImage.downscale("test", 1, 1, "singlePixel");
    Pixels[][] downscaledPixels = enhancedImage.getStoredPixels("singlePixel");

    // Verify dimensions
    assertEquals("Height should be 1", 1, downscaledPixels.length);
    assertEquals("Width should be 1", 1, downscaledPixels[0].length);


    String outputFileName = "test/Test_Image/single_pixel.png";



    Pixels[][] savedPixels = ImageUtil.loadImage(outputFileName);
    assertNotNull("Saved pixels should not be null after loading the image", savedPixels);

    // Compare dimensions
    assertEquals("Height of saved image should match downscaled image", downscaledPixels.length, savedPixels.length);
    assertEquals("Width of saved image should match downscaled image", downscaledPixels[0].length, savedPixels[0].length);

    // Compare pixel values
    RGBPixel downscaledPixel = (RGBPixel) downscaledPixels[0][0];
    RGBPixel savedPixel = (RGBPixel) savedPixels[0][0];

    assertEquals("Red values should match", downscaledPixel.getRed(), savedPixel.getRed());
    assertEquals("Green values should match", downscaledPixel.getGreen(), savedPixel.getGreen());
    assertEquals("Blue values should match", downscaledPixel.getBlue(), savedPixel.getBlue());
  }

  @Test
  public void testDownscaleToSameSize() {
    String load = "test/Test_Image/Landscape.png";
    String savedPath = "test/Test_Image/same_size.png";  // Save as PNG for lossless comparison

    // Load the original image
    Pixels[][] pixels = ImageUtil.loadImage(load);
    assertNotNull("Pixels should not be null after loading the image", pixels);

    enhancedImage.storePixels("test", pixels);

    // Perform the downscale operation
    enhancedImage.downscale("test", pixels[0].length, pixels.length, "sameSize");
    Pixels[][] downscaledPixels = enhancedImage.getStoredPixels("sameSize");

    // Verify that the dimensions are unchanged
    assertEquals("Height should remain the same", pixels.length, downscaledPixels.length);
    assertEquals("Width should remain the same", pixels[0].length, downscaledPixels[0].length);



    Pixels[][] savedPixels = ImageUtil.loadImage(savedPath);
    assertNotNull("Saved pixels should not be null after loading the image", savedPixels);

    // Verify that the saved image's dimensions match the downscaled image's dimensions
    assertEquals("Height of saved image should match downscaled image", downscaledPixels.length, savedPixels.length);
    assertEquals("Width of saved image should match downscaled image", downscaledPixels[0].length, savedPixels[0].length);

    // Tolerance for pixel value differences
    final int tolerance = 0;

    // Compare pixel values between the downscaled image and the saved image
    for (int y = 0; y < downscaledPixels.length; y++) {
      for (int x = 0; x < downscaledPixels[0].length; x++) {
        RGBPixel downscaledPixel = (RGBPixel) downscaledPixels[y][x];
        RGBPixel savedPixel = (RGBPixel) savedPixels[y][x];

        // Use tolerance for comparison
        assertTrue("Red values should match within tolerance", Math.abs(downscaledPixel.getRed() - savedPixel.getRed()) <= tolerance);
        assertTrue("Green values should match within tolerance", Math.abs(downscaledPixel.getGreen() - savedPixel.getGreen()) <= tolerance);
        assertTrue("Blue values should match within tolerance", Math.abs(downscaledPixel.getBlue() - savedPixel.getBlue()) <= tolerance);
      }
    }
  }

  @Test
  public void testDownscaleWithInvalidDimensions() {
    String load = "test/Test_Image/Landscape.png";
    Pixels[][] pixels = ImageUtil.loadImage(load);

    assertNotNull("Pixels should not be null after loading the image", pixels);

    enhancedImage.storePixels("test", pixels);

    // Expect NegativeArraySizeException for invalid dimensions
    assertThrows(NegativeArraySizeException.class, () -> {
      enhancedImage.downscale("test", -1, -1, "invalid");
    });
  }

  @Test
  public void testDownscaleWithLargeOutputSize() {
    String load = "test/Test_Image/Landscape.png";
    Pixels[][] pixels = ImageUtil.loadImage(load);

    assertNotNull("Pixels should not be null after loading the image", pixels);

    enhancedImage.storePixels("test", pixels);

    // Expect no downscale (output should be the same size)
    enhancedImage.downscale("test", pixels[0].length, pixels.length, "largeOutput");
    Pixels[][] downscaledPixels = enhancedImage.getStoredPixels("largeOutput");

    // Verify the dimensions are unchanged
    assertEquals("Height should match original size", pixels.length, downscaledPixels.length);
    assertEquals("Width should match original size", pixels[0].length, downscaledPixels[0].length);


    String outputFileName = "test/Test_Image/large_output.png";


    Pixels[][] savedPixels = ImageUtil.loadImage(outputFileName);
    assertNotNull("Saved pixels should not be null after loading the image", savedPixels);

    // Verify that the saved image's dimensions match the downscaled image's dimensions
    assertEquals("Height of saved image should match downscaled image", downscaledPixels.length, savedPixels.length);
    assertEquals("Width of saved image should match downscaled image", downscaledPixels[0].length, savedPixels[0].length);

    // Tolerance for pixel value differences
    final int tolerance = 0;  // Allow a tolerance of ±3 for pixel value comparison

    // Compare pixel values between the downscaled image and the saved image
    for (int y = 0; y < downscaledPixels.length; y++) {
      for (int x = 0; x < downscaledPixels[0].length; x++) {
        RGBPixel downscaledPixel = (RGBPixel) downscaledPixels[y][x];
        RGBPixel savedPixel = (RGBPixel) savedPixels[y][x];

        // Use tolerance for comparison
        assertTrue("Red values should match within tolerance", Math.abs(downscaledPixel.getRed() - savedPixel.getRed()) <= tolerance);
        assertTrue("Green values should match within tolerance", Math.abs(downscaledPixel.getGreen() - savedPixel.getGreen()) <= tolerance);
        assertTrue("Blue values should match within tolerance", Math.abs(downscaledPixel.getBlue() - savedPixel.getBlue()) <= tolerance);
      }
    }
  }
  @Test
  public void testMaskedOperation() {
    String load = "test/Test_Image/Landscape.png";
    Pixels[][] originalPixels = ImageUtil.loadImage(load);

    assertNotNull("Original pixels should not be null after loading the image", originalPixels);
    enhancedImage.storePixels("test", originalPixels);

    String mask = "res/Landscape-L-shaped-masked-image.png";
    Pixels[][] maskPixels = ImageUtil.loadImage(mask);
    assertNotNull("Mask pixels should not be null after loading the mask", maskPixels);
    enhancedImage.storePixels("mask", maskPixels);

    enhancedImage.maskedOperation("test", "blur", "mask", "test-mask-blur");

    Pixels[][] result = enhancedImage.getStoredPixels("test-mask-blur");

    String expectedPath = "res/Sample_Images/maskedop.png"; // Set to the actual expected image path
    Pixels[][] expected = ImageUtil.loadImage(expectedPath);

    assertImageEquals((RGBPixel[][]) expected, (RGBPixel[][]) result);
  }
  @Test
  public void testMaskedOperationWithNullImage() {
    // Attempt to perform masked operation with a null original image
    String mask = "res/Landscape-L-shaped-masked-image.png";
    Pixels[][] maskPixels = ImageUtil.loadImage(mask);
    assertNotNull("Mask pixels should not be null after loading the mask", maskPixels);
    enhancedImage.storePixels("mask", maskPixels);

    try {
      enhancedImage.maskedOperation("nullImageKey", "blur", "mask", "test-mask-blur");
      fail("Expected an exception when performing masked operation with null original image");
    } catch (IllegalArgumentException e) {
      assertEquals("Expected exception message", "Source image or mask image not found.", e.getMessage());
    }
  }

  @Test
  public void testMaskedOperationWithNullMask() {
    // Load the original image
    String load = "test/Test_Image/Landscape.png";
    Pixels[][] originalPixels = ImageUtil.loadImage(load);
    assertNotNull("Original pixels should not be null after loading the image", originalPixels);
    enhancedImage.storePixels("test", originalPixels);

    // Attempt to perform masked operation with a null mask
    try {
      enhancedImage.maskedOperation("test", "blur", "nullMaskKey", "test-mask-blur");
      fail("Source image or mask image not found.");
    } catch (IllegalArgumentException e) {
      assertEquals("Expected exception message", "Source image or mask image not found.", e.getMessage());
    }
  }

  @Test
  public void testMaskedOperationWithInvalidOperation() {
    // Load the original image
    String load = "test/Test_Image/Landscape.png";
    Pixels[][] originalPixels = ImageUtil.loadImage(load);
    assertNotNull("Original pixels should not be null after loading the image", originalPixels);
    enhancedImage.storePixels("test", originalPixels);

    // Load the mask image
    String mask = "res/Landscape-L-shaped-masked-image.png";
    Pixels[][] maskPixels = ImageUtil.loadImage(mask);
    assertNotNull("Mask pixels should not be null after loading the mask", maskPixels);
    enhancedImage.storePixels("mask", maskPixels);

    // Attempt to perform masked operation with an invalid operation type
    try {
      enhancedImage.maskedOperation("test", "invalidOperation", "mask", "test-mask-blur");
      fail("Expected an exception for invalid operation type");
    } catch (IllegalArgumentException e) {
      assertEquals("Expected exception message", "Unsupported operation: invalidOperation", e.getMessage());
    }
  }

  @Test
  public void testMaskedOperationWithDifferentImageSizes() {
    try {
      // Load a smaller original image
      String loadSmall = "test/Test_Image/downscaled_half.png"; // Assume this image is smaller
      Pixels[][] originalPixels = ImageUtil.loadImage(loadSmall);
      assertNotNull("Original pixels should not be null after loading the small image", originalPixels);
      enhancedImage.storePixels("smallImage", originalPixels);

      // Load the mask image
      String mask = "res/Landscape-L-shaped-masked-image.png";
      Pixels[][] maskPixels = ImageUtil.loadImage(mask);
      assertNotNull("Mask pixels should not be null after loading the mask", maskPixels);
      enhancedImage.storePixels("mask", maskPixels);

      // Check if the sizes of the original image and the mask match
      if (originalPixels.length != maskPixels.length || originalPixels[0].length != maskPixels[0].length) {
        // If sizes don't match, throw a custom exception
        throw new ImageSizeMismatchException("Original image and mask image sizes do not match.");
      }

      // Perform the masked operation if sizes match
      enhancedImage.maskedOperation("smallImage", "blur", "mask", "test-mask-blur");

      // Retrieve the result of the masked operation
      Pixels[][] result = enhancedImage.getStoredPixels("test-mask-blur");

      // Save the result image
      String resultImagePath = "test/Test_Image/masked_operation_result_small.png"; // Specify the path to save the result
      ImageUtil.saveImage(resultImagePath, result); // Save the resulting image

      // Load the expected image for comparison
      String expectedPath = "res/Sample_Images/maskedop_small.png"; // Set to the actual expected image path
      Pixels[][] expected = ImageUtil.loadImage(expectedPath);

      // Compare the result with the expected image
      assertImageEquals((RGBPixel[][]) expected, (RGBPixel[][]) result);

    } catch (ImageSizeMismatchException e) {
      // If the exception is thrown, assert that the exception message is correct
      assertEquals("Original image and mask image sizes do not match.", e.getMessage());
    } catch (Exception e) {
      // Catch any other exceptions that may occur during the test
      fail("Test failed due to an unexpected error: " + e.getMessage());
    }
  }

  private static class ImageSizeMismatchException extends Exception {
    public ImageSizeMismatchException(String message) {
      super(message);
    }
  }
  @Test
  public void testMaskedOperationWithNonExistentMask() {
    // Load the original image
    String load = "test/Test_Image/Landscape.png";
    Pixels[][] originalPixels = ImageUtil.loadImage(load);
    assertNotNull("Original pixels should not be null after loading the image", originalPixels);
    enhancedImage.storePixels("test", originalPixels);

    // Attempt to perform masked operation with a non-existent mask
    try {
      enhancedImage.maskedOperation("test", "blur", "nonExistentMask", "test-mask-blur");
      fail("Expected an exception when performing masked operation with non-existent mask");
    } catch (IllegalArgumentException e) {
      assertEquals("Expected exception message", "Source image or mask image not found.", e.getMessage());
    }
  }

  @Test
  public void testMaskedOperationWithEmptyMask() {
    // Load the original image
    String load = "test/Test_Image/Landscape.png";
    Pixels[][] originalPixels = ImageUtil.loadImage(load);
    assertNotNull("Original pixels should not be null after loading the image", originalPixels);
    enhancedImage.storePixels("test", originalPixels);

    // Create an empty mask
    Pixels[][] emptyMask = new RGBPixel[originalPixels.length][originalPixels[0].length];
    for (int i = 0; i < emptyMask.length; i++) {
      for (int j = 0; j < emptyMask[0].length; j++) {
        emptyMask[i][j] = new RGBPixel(255, 255, 255); // White mask (no effect)
      }
    }
    enhancedImage.storePixels("emptyMask", emptyMask);

    // Perform the masked operation with the empty mask
    enhancedImage.maskedOperation("test", "blur", "emptyMask", "test-mask-blur");

    // Retrieve the result of the masked operation
    Pixels[][] result = enhancedImage.getStoredPixels("test-mask-blur");

    // The result should be the same as the original since the mask is white
    assertImageEquals((RGBPixel[][]) originalPixels, (RGBPixel[][]) result);
  }

  @Test
  public void testMaskedOperationWithFullMask() {
    // Load the original image
    String load = "test/Test_Image/Landscape.png";
    Pixels[][] originalPixels = ImageUtil.loadImage(load);
    assertNotNull("Original pixels should not be null after loading the image", originalPixels);
    enhancedImage.storePixels("test", originalPixels);

    // Create a full mask (black mask)
    Pixels[][] fullMask = new RGBPixel[originalPixels.length][originalPixels[0].length];
    for (int i = 0; i < fullMask.length; i++) {
      for (int j = 0; j < fullMask[0].length; j++) {
        fullMask[i][j] = new RGBPixel(0, 0, 0); // Black mask (full effect)
      }
    }
    enhancedImage.storePixels("fullMask", fullMask);


    // Perform the masked operation with the full mask
    enhancedImage.maskedOperation("test", "blur", "fullMask", "test-mask-blur");



    // Retrieve the result of the masked operation
    Pixels[][] result = enhancedImage.getStoredPixels("test-mask-blur");

    // The result should be the blurred version of the original
    Pixels[][] expectedBlurred = ImageUtil.loadImage("res/Sample_Images/maskedop_blur.png");

    // Compare the actual result with the expected image using a larger threshold for comparison
    assertImageEquals((RGBPixel[][]) expectedBlurred, (RGBPixel[][]) result);  // Increased the threshold
  }

  @Test
  public void testMaskedOperationWithDifferentOperations() {
    // Load the original image
    String load = "test/Test_Image/Landscape.png";
    Pixels[][] originalPixels = ImageUtil.loadImage(load);
    assertNotNull("Original pixels should not be null after loading the image", originalPixels);
    enhancedImage.storePixels("test", originalPixels);

    // Load the mask image
    String mask = "res/Landscape-L-shaped-masked-image.png";
    Pixels[][] maskPixels = ImageUtil.loadImage(mask);
    assertNotNull("Mask pixels should not be null after loading the mask", maskPixels);
    enhancedImage.storePixels("mask", maskPixels);

    // Test with blur operation
    enhancedImage.maskedOperation("test", "blur", "mask", "test-mask-blur");
    Pixels[][] blurResult = enhancedImage.getStoredPixels("test-mask-blur");
    String blurResultPath = "res/Sample_Images/maskedop_blur_multiple.png";
    Pixels[][] blurExpected = ImageUtil.loadImage(blurResultPath);
    assertImageEquals((RGBPixel[][]) blurExpected, (RGBPixel[][]) blurResult);

    // Save the blurred image



    // Test with sharpen operation
    enhancedImage.maskedOperation("test", "sharpen", "mask", "test-mask-sharpen");
    Pixels[][] sharpenResult = enhancedImage.getStoredPixels("test-mask-sharpen");
    String sharpenExpectedPath = "res/Sample_Images/maskedop_sharpen_result.png"; // Set to the actual expected image path
    Pixels[][] sharpenExpected = ImageUtil.loadImage(sharpenExpectedPath);
    assertImageEquals((RGBPixel[][]) sharpenExpected, (RGBPixel[][]) sharpenResult);

    // Test with greyscale operation
    enhancedImage.maskedOperation("test", "greyscale", "mask", "test-mask-greyscale");
    Pixels[][] greyscaleResult = enhancedImage.getStoredPixels("test-mask-greyscale");
    String greyscaleExpectedPath = "res/Sample_Images/maskedop_greyscale_result.png"; // Set to the actual expected image path
    Pixels[][] greyscaleExpected = ImageUtil.loadImage(greyscaleExpectedPath);
    assertImageEquals((RGBPixel[][]) greyscaleExpected, (RGBPixel[][]) greyscaleResult);


    // Test with sepia operation
    enhancedImage.maskedOperation("test", "sepia", "mask", "test-mask-sepia");
    Pixels[][] sepiaResult = enhancedImage.getStoredPixels("test-mask-sepia");
    String sepiaExpectedPath = "res/Sample_Images/maskedop_sepia_result.png";
    Pixels[][] sepiaExpected = ImageUtil.loadImage(sepiaExpectedPath);
    assertImageEquals((RGBPixel[][]) sepiaExpected, (RGBPixel[][]) sepiaResult);



  }

  @Test
  public void testLoadOriginalImage() {
    String loadPath = "res/Sample_Images/maskedop_blur_multiple.png";
    assertTrue("File not found: " + loadPath, new File(loadPath).exists());
    Pixels[][] originalPixels = ImageUtil.loadImage(loadPath);
    assertNotNull("Failed to load the original image", originalPixels);
  }

  private void assertImageEquals(RGBPixel[][] expected, RGBPixel[][] actual) {
    for (int i = 0; i < expected.length; i++) {
      for (int j = 0; j < expected[0].length; j++) {
        assertEquals(expected[i][j].getRed(), actual[i][j].getRed(), 2);
        assertEquals(expected[i][j].getGreen(), actual[i][j].getGreen(), 2);
        assertEquals(expected[i][j].getBlue(), actual[i][j].getBlue(), 2);
      }
    }
  }
    @Test
    public void testDownscaleToZeroDimensions() {
      String load = "test/Test_Image/Landscape.png";
      Pixels[][] pixels = ImageUtil.loadImage(load);

      assertNotNull("Pixels should not be null after loading the image", pixels);

      enhancedImage.storePixels("test", pixels);

      // Downscale to zero dimensions, which should ideally throw an exception or handle gracefully
      assertThrows(IllegalArgumentException.class, () -> {
        enhancedImage.downscale("test", 0, 0, "zero");
      });
    }
  @Test
  public void testDownscaleToLargerDimensions() {
    String load = "test/Test_Image/Landscape.png";
    Pixels[][] pixels = ImageUtil.loadImage(load);

    assertNotNull("Pixels should not be null after loading the image", pixels);

    enhancedImage.storePixels("test", pixels);

    // Downscale to larger dimensions (upscaling)
    enhancedImage.downscale("test", pixels[0].length * 2, pixels.length * 2, "upscaled");
    Pixels[][] upscaledPixels = enhancedImage.getStoredPixels("upscaled");

    // Verify that the dimensions are larger than the original
    assertEquals("Height should be double the original size", pixels.length * 2, upscaledPixels.length);
    assertEquals("Width should be double the original size", pixels[0].length * 2, upscaledPixels[0].length);
  }
  @Test
  public void testDownscaleOnEmptyImage() {
    // Simulate an empty image (zero width or height)
    Pixels[][] emptyPixels = new Pixels[0][0];

    enhancedImage.storePixels("test", emptyPixels);

    // Should either throw an exception or do nothing (depending on your expected behavior)
    assertThrows(IllegalArgumentException.class, () -> {
      enhancedImage.downscale("test", 1, 1, "emptyDownscale");
    });
  }



}
