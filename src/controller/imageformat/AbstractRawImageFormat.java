package controller.imageformat;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import model.colorscheme.Pixels;
import model.colorscheme.RGBPixel;

/**
 * AbstractRawImageFormat is an abstract class for handling raw image formats like PPM. It provides
 * a `load` method to read and convert PPM image data (in P3 format) into a 2D array of RGB pixels.
 * Specific raw formats should implement the `save` method to define how files are written.
 */
public abstract class AbstractRawImageFormat implements ImageFormat {

  /**
   * Loads a PPM (P3 format) file and converts it into a 2D array of RGB pixels. This method reads
   * the file, verifies the format, and extracts width, height, and color data for each pixel. Only
   * files with a maximum color value of 255 are supported.
   *
   * @param fileName the name or path of the PPM file to load
   * @return a 2D array of {@link RGBPixel} objects representing the RGB values of each pixel in the
   *         image, or {@code null} if an error occurs or if the file is in an unsupported format
   */

  public Pixels[][] load(String fileName) {
    Scanner scanner = null;
    try {
      File file = new File(fileName);
      scanner = new Scanner(file);

      String format = scanner.next();
      if (!format.equals("P3")) {
        System.out.println("Not a P3 PPM file.");
        return null;
      }

      while (scanner.hasNext()) {
        String line = scanner.nextLine().trim();
        if (line.isEmpty() || line.startsWith("#")) {
          continue;
        }

        String[] dimensions = line.split("\\s+");
        if (dimensions.length < 2) {
          System.out.println("Invalid PPM file format.");
          return null;
        }
        int width = Integer.parseInt(dimensions[0]);
        int height = Integer.parseInt(dimensions[1]);

        if (!scanner.hasNextInt()) {
          System.out.println("Invalid max color value.");
          return null;
        }
        int maxColorValue = scanner.nextInt();
        if (maxColorValue != 255) {
          System.out.println("Unsupported max color value: " + maxColorValue);
          return null;
        }

        Pixels[][] pixels = new RGBPixel[height][width];

        for (int y = 0; y < height; y++) {
          for (int x = 0; x < width; x++) {
            while (scanner.hasNext("#")) {
              scanner.nextLine();
            }

            int red = scanner.nextInt();
            int green = scanner.nextInt();
            int blue = scanner.nextInt();

            pixels[y][x] = new RGBPixel(red, green, blue);
          }
        }

        return pixels;
      }

    } catch (FileNotFoundException e) {
      System.out.println("File not found: " + e.getMessage());
    } catch (Exception e) {
      System.out.println("Error processing the PPM file: " + e.getMessage());
    } finally {
      if (scanner != null) {
        scanner.close();
      }
    }
    return null;
  }

  /**
   * Saves a 2D array of RGB pixel data to an image file in a raw format. Subclasses must implement
   * this method to specify the saving behavior for the specific file format.
   *
   * @param filename The name or path of the file to save the image.
   * @param pixels   A 2D array of {@link Pixels} objects representing the RGB values of each pixel
   *                 to be saved.
   */
  public abstract void save(String filename, Pixels[][] pixels);
}

