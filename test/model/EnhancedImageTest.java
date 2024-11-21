package model;

import static org.junit.Assert.*;

import controller.ImageUtil;
import model.colorscheme.Pixels;
import model.colorscheme.RGBPixel;
import model.imagetransformation.advancedoperations.MaskedOperation;
import model.imagetransformation.basicoperation.Brighten;
import org.junit.Test;

public class EnhancedImageTest {

  @Test
  public void testDownscale() {
    EnhancedImage enhancedImage = new EnhancedImage();
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
    EnhancedImage enhancedImage = new EnhancedImage();
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
    EnhancedImage enhancedImage = new EnhancedImage();
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
    EnhancedImage enhancedImage = new EnhancedImage();
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

    // Save the downscaled image
    ImageUtil.saveImage(savedPath, downscaledPixels);


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
    EnhancedImage enhancedImage = new EnhancedImage();
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
    EnhancedImage enhancedImage = new EnhancedImage();
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
    EnhancedImage enhancedImage = new EnhancedImage();
    String load = "test/Test_Image/Landscape.png";
    Pixels[][] originalPixels = ImageUtil.loadImage(load);

    // Ensure the original image is loaded correctly
    assertNotNull("Original pixels should not be null after loading the image", originalPixels);

    enhancedImage.storePixels("test", originalPixels);

    // Load the mask image (assuming it's a black-and-white mask)
    Pixels[][] maskPixels = ImageUtil.loadImage("res/Landscape-L-shaped-masked-image.png");
    assertNotNull("Mask pixels should not be null after loading the mask", maskPixels);

    // Create a Brighten operation to apply
    Brighten brighten = new Brighten(50); // Brighten by 50

    // Create a MaskedOperation with the Brighten operation and the mask
    MaskedOperation maskedOperation = new MaskedOperation(brighten, maskPixels);

    // Apply the masked operation to the original image
    Pixels[][] resultImage = maskedOperation.apply(originalPixels);

    // Ensure the result image is not null
    assertNotNull("Result image should not be null", resultImage);

    // Load the expected output image for comparison
    Pixels[][] expectedImage = ImageUtil.loadImage("test/Test_Image/expected_masked_brightened.png");
    assertNotNull("Expected image should not be null after loading", expectedImage);

    // Compare the actual result with the expected output
    assertImageEquals((RGBPixel[][]) expectedImage, (RGBPixel[][]) resultImage);

    // Save the resulting masked image
    String outputFilePath = "test/Test_Image/masked_brightened_output.png"; // Specify the output path
    ImageUtil.saveImage(outputFilePath, resultImage); // Save the result image
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
}
