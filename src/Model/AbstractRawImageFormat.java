package Model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public abstract class AbstractRawImageFormat implements ImageFormat{

  public RGBPixel[][] load(String fileName)
  {
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

          RGBPixel[][] pixels = new RGBPixel[height][width];

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

  @Override
  public void save(String filename, RGBPixel[][] pixels) {

  }
}

