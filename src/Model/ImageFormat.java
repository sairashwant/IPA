package Model;

public interface ImageFormat {

  RGBPixel[][] load(String filename);
  void save(String filename, RGBPixel[][] pixels);

}
