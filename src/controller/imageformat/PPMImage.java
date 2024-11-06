package controller.imageformat;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import model.colorscheme.Pixels;
import model.colorscheme.RGBPixel;

/**
 * The class handles the saving of images in the PPM format. Provides functionality specific to the
 * PPM format. The class writes image data to a file using the P3 ASCII format, which is a
 * human-readable format.
 */

public class PPMImage extends AbstractRawImageFormat {

  /**
   * Saves the given pixel data as a PPM file at the specified filename. The method outputs the
   * image data in P3 format, including the header and pixel color values.
   *
   * @param filename the output file path where the PPM image will be saved
   * @param pixels   a 2D array of {@code RGBPixel} representing the image pixel data (rows x
   *                 columns). The pixel data must not be null or empty.
   */
  @Override
  public void save(String filename, Pixels[][] pixels) {
    String outputPath = filename;
    if (pixels == null || pixels.length == 0 || pixels[0].length == 0) {
      System.out.println("Invalid pixel data.");
      return;
    }

    try (PrintWriter writer = new PrintWriter(outputPath)) {
      writer.println("P3");
      int height = pixels.length;
      int width = pixels[0].length;
      writer.println(width + " " + height);
      writer.println("255");
      for (int y = 0; y < height; y++) {
        for (int x = 0; x < width; x++) {
          RGBPixel pixel = (RGBPixel) pixels[y][x];
          writer.println(pixel.getRed() + " " + pixel.getGreen() + " " + pixel.getBlue());
        }
      }

      System.out.println("Image saved successfully to " + outputPath);
    } catch (FileNotFoundException e) {
      System.out.println("File not found: " + e.getMessage());
    } catch (Exception e) {
      System.out.println("Error saving the PPM file: " + e.getMessage());
    }
  }
}

