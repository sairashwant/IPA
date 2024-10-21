package Model;

public class Image {

  public void getPixels() {
    String filename = "Images/manhattan-small.png";
    String fileExtension = filename.substring(filename.lastIndexOf("."));
    if (fileExtension.equals(".png") || fileExtension.equals(".jpg")){
      PNGImage imageFormat = new PNGImage();
      RGBPixel[][] pixels = imageFormat.load(filename);}
      else if(filename.substring(filename.lastIndexOf(".")).equals(".ppm")){
        System.out.println("Failed to load image pixels.");
      }
  }
}

