package Model.ImageFormat;

import Model.RGBPixel;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class PPMImage extends AbstractRawImageFormat {

  @Override
  public void save(String filename, RGBPixel[][] pixels) {
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

