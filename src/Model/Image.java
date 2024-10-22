package Model;

import Model.ImageTransformation.BasicOperation.Split;
import Model.ImageTransformation.Filtering.Blur;
import Model.ImageTransformation.BasicOperation.Brighten;
import Model.ImageTransformation.BasicOperation.Combine;
import Model.ImageTransformation.BasicOperation.Flip;
import Model.ImageTransformation.BasicOperation.Flip.Direction;
import Model.ImageFormat.JPGImage;
import Model.ImageFormat.PNGImage;
import Model.ImageFormat.PPMImage;
import Model.ImageTransformation.ColorTransformation.GreyScale;
import Model.ImageTransformation.BasicOperation.Intensity;
import Model.ImageTransformation.ColorTransformation.Sepia;
import Model.ImageTransformation.Filtering.Sharpen;
import Model.ImageTransformation.BasicOperation.Value;
import Model.ImageTransformation.BasicOperation.luma;
import java.util.HashMap;

public class Image {

  RGBPixel[][] updatedPixel;
  HashMap<String, RGBPixel[][]> h1= new HashMap<String, RGBPixel[][]>();


  public RGBPixel[][] getPixels(String key,String filename) {
    filename = filename;
    String fileExtension = filename.substring(filename.lastIndexOf("."));
    if (fileExtension.equals(".png")) {
      PNGImage imageFormat = new PNGImage();
      updatedPixel = imageFormat.load(filename);
    } else if (fileExtension.equals(".jpg")) {
      JPGImage imageFormat = new JPGImage();
      updatedPixel = imageFormat.load(filename);
    } else if (fileExtension.equals(".ppm")) {
      PPMImage imageFormat = new PPMImage();
      updatedPixel = imageFormat.load(filename);
    }
    h1.put(key, updatedPixel);
    return updatedPixel;
  }

  public void savePixels(String key,String filename) {
    String outputFile = filename;
    String fileExtension = filename.substring(filename.lastIndexOf("."));
    RGBPixel[][] tosavepixels = h1.get(key);
    if (fileExtension.equals(".png")) {
      PNGImage imageFormat = new PNGImage();
      if (tosavepixels == null) {
        System.out.println("Image has not been saved");
        return;
      }
      imageFormat.save(outputFile, tosavepixels);
    } else if (fileExtension.equals(".jpg")) {
      JPGImage imageFormat = new JPGImage();
      if (tosavepixels == null) {
        System.out.println("Image has not been saved");
        return;
      }
      imageFormat.save(outputFile, tosavepixels);
    } else if (fileExtension.equals(".ppm")) {
      PPMImage imageFormat = new PPMImage();
      if (tosavepixels == null) {
        System.out.println("Image has not been saved");
        return;
      }
      imageFormat.save(outputFile, updatedPixel);
    }
  }

  public void blur(String key, String savekey) {
    Blur b1 = new Blur();
    updatedPixel = b1.apply(key,h1);
    h1.put(savekey, updatedPixel);
  }

  public void brighten(int brightenFactor,String key, String savekey) {
    Brighten b1 = new Brighten(brightenFactor);
    updatedPixel = b1.apply(key,h1);
    h1.put(savekey, updatedPixel);
  }


  public void split(String key, String saveKey1, String saveKey2, String saveKey3) {
    Split s1=new Split();
    HashMap<String, RGBPixel[][]> temp = s1.apply(h1,updatedPixel,key,saveKey1,saveKey2,saveKey3  );
    h1.putAll(temp);
  }

  public void combine(String key, String key1,String key2, String key3)
  {
    Combine c1 = new Combine();
    updatedPixel = c1.apply(h1.get(key1),h1.get(key2),h1.get(key3));
    h1.put(key, updatedPixel);
  }
  public void flip(String key,String savekey, Direction d)
  {
    Flip f1 = new Flip();
    updatedPixel = f1.apply(key,h1,d);
    h1.put(savekey, updatedPixel);
  }
  public void greyScale(String key,String savekey)
  {
    GreyScale g1 = new GreyScale();
    updatedPixel = g1.apply(key,h1);
    h1.put(savekey, updatedPixel);
  }
  public void sepia(String key,String savekey)
  {
    Sepia sp1 = new Sepia();
    updatedPixel = sp1.apply(key,h1);
    h1.put(savekey, updatedPixel);
  }
  public void sharpen(String key,String savekey)
  {
    Sharpen sp1 = new Sharpen();
    updatedPixel = sp1.apply(key,h1);
    h1.put(savekey, updatedPixel);
  }
  public void luma(String key,String savekey)
  {
    luma l1= new luma();
    updatedPixel=l1.apply(key,h1);
    h1.put(savekey, updatedPixel);
  }
  public void value(String key,String savekey)
  {
    Value l1= new Value();
    updatedPixel=l1.apply(key,h1);
    h1.put(savekey, updatedPixel);
  }
  public void intensity(String key,String savekey)
  {
    Intensity l1= new Intensity();
    updatedPixel=l1.apply(key,h1);
    h1.put(savekey, updatedPixel);
  }
}
