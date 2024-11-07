package controller.imageformat;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import model.colorscheme.Pixels;
import model.colorscheme.RGBPixel;

/**
 * The PPMImage class provides functionality for saving images in the PPM (Portable Pixmap) format.
 * This class outputs image data in the P3 ASCII format, which is a human-readable representation of
 * pixel color values.
 */

public class PPMImage extends AbstractRawImageFormat {

  /**
   * Saves the given pixel data as a PPM file at the specified filename. This method writes image
   * data in the P3 format, including the PPM header and the RGB color values for each pixel.
   *
   * @param filename The path of the output file where the PPM image will be saved.
   * @param pixels   A 2D array of {@link RGBPixel} representing the image's pixel data in rows and
   *                 columns. This array must be non-null and non-empty.
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

