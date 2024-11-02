package model;

import model.colorscheme.RGBPixel;
import model.imagetransformation.basicoperation.Compression;
import model.imagetransformation.basicoperation.Luma;
import model.imagetransformation.basicoperation.Split;
import model.imagetransformation.filtering.Blur;
import model.imagetransformation.basicoperation.Brighten;
import model.imagetransformation.basicoperation.Combine;
import model.imagetransformation.basicoperation.Flip;
import model.imagetransformation.basicoperation.Flip.Direction;
import model.imageformat.JPGImage;
import model.imageformat.PNGImage;
import model.imageformat.PPMImage;
import model.imagetransformation.colortransformation.GreyScale;
import model.imagetransformation.basicoperation.Intensity;
import model.imagetransformation.colortransformation.Sepia;
import model.imagetransformation.filtering.Sharpen;
import model.imagetransformation.basicoperation.Value;
import java.util.HashMap;

/**
 * Represents an image and provides methods for loading, saving, and manipulating pixel data using
 * various transformations.
 */
public class Image {

  RGBPixel[][] updatedPixel;
  public HashMap<String, RGBPixel[][]> h1 = new HashMap<String, RGBPixel[][]>();

  /**
   * Loads pixel data from an image file and stores it in the specified key.
   *
   * @param key      the key under which the pixel data will be stored
   * @param filename the name of the image file to be loaded
   * @return a 2D array of RGBPixel representing the loaded image
   * @throws IllegalArgumentException if the image format is unsupported
   */
  public RGBPixel[][] getPixels(String key, String filename) {
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
    } else {
      throw new IllegalArgumentException("Unsupported image format");
    }
    h1.put(key, updatedPixel);
    return updatedPixel;
  }

  /**
   * Saves pixel data to an image file based on the specified key.
   *
   * @param key      the key used to retrieve the pixel data to save
   * @param filename the name of the output image file
   * @throws IllegalArgumentException if the image format is unsupported
   */
  public void savePixels(String key, String filename) {
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
    } else {
      throw new IllegalArgumentException("Unsupported image format");
    }
  }

  /**
   * Extracts the red channel from the image associated with the specified key and saves it under
   * the specified save key.
   *
   * @param key     the key used to retrieve the pixel data
   * @param saveKey the key under which the red channel will be stored
   */
  public void getRedChannel(String key, String saveKey) {
    Split s1 = new Split();
    HashMap<String, RGBPixel[][]> temp = s1.apply(h1, h1.get(key), key, saveKey, "temp1", "temp2");
    RGBPixel[][] redChannel = temp.get(saveKey);
    h1.put(saveKey, redChannel);
  }

  /**
   * Extracts the green channel from the image associated with the specified key and saves it under
   * the specified save key.
   *
   * @param key     the key used to retrieve the pixel data
   * @param saveKey the key under which the green channel will be stored
   */
  public void getGreenChannel(String key, String saveKey) {
    Split s1 = new Split();
    HashMap<String, RGBPixel[][]> temp = s1.apply(h1, h1.get(key), key, "temp1", saveKey, "temp2");
    RGBPixel[][] greenChannel = temp.get(saveKey);
    h1.put(saveKey, greenChannel);
  }

  /**
   * Extracts the blue channel from the image associated with the specified key and saves it under
   * the specified save key.
   *
   * @param key     the key used to retrieve the pixel data
   * @param saveKey the key under which the blue channel will be stored
   */
  public void getBlueChannel(String key, String saveKey) {
    Split s1 = new Split();
    HashMap<String, RGBPixel[][]> temp = s1.apply(h1, h1.get(key), key, "temp1", "temp2", saveKey);
    RGBPixel[][] blueChannel = temp.get(saveKey);
    h1.put(saveKey, blueChannel);
  }

  /**
   * Applies a blur effect to the image associated with the specified key and saves the result under
   * the specified save key.
   *
   * @param key     the key used to retrieve the pixel data
   * @param savekey the key under which the blurred image will be stored
   */
  public void blur(String key, String savekey) {
    Blur b1 = new Blur();
    updatedPixel = b1.apply(key, h1);
    h1.put(savekey, updatedPixel);
  }

  /**
   * Brightens the image associated with the specified key by a given factor and saves the result
   * under the specified save key.
   *
   * @param brightenFactor the amount to brighten the image
   * @param key            the key used to retrieve the pixel data
   * @param savekey        the key under which the brightened image will be stored
   */
  public void brighten(int brightenFactor, String key, String savekey) {
    Brighten b1 = new Brighten(brightenFactor);
    updatedPixel = b1.apply(key, h1);
    h1.put(savekey, updatedPixel);
  }

  /**
   * Splits the image associated with the specified key into three separate channels and stores them
   * under the specified save keys.
   *
   * @param key      the key used to retrieve the pixel data
   * @param saveKey1 the key under which the first split channel will be stored
   * @param saveKey2 the key under which the second split channel will be stored
   * @param saveKey3 the key under which the third split channel will be stored
   */
  public void split(String key, String saveKey1, String saveKey2, String saveKey3) {
    Split s1 = new Split();
    HashMap<String, RGBPixel[][]> temp = s1.apply(h1, updatedPixel, key, saveKey1, saveKey2,
        saveKey3);
    h1.putAll(temp);
  }

  /**
   * Combines three color channels into a single image based on the specified keys.
   *
   * @param key  the key under which the combined image will be stored
   * @param key1 the key for the first channel
   * @param key2 the key for the second channel
   * @param key3 the key for the third channel
   */
  public void combine(String key, String key1, String key2, String key3) {
    Combine c1 = new Combine();
    updatedPixel = c1.apply(h1.get(key1), h1.get(key2), h1.get(key3));
    h1.put(key, updatedPixel);
  }

  /**
   * Flips the image associated with the specified key in the given direction and saves the result
   * under the specified save key.
   *
   * @param key     the key used to retrieve the pixel data
   * @param savekey the key under which the flipped image will be stored
   * @param d       the direction in which to flip the image (horizontal or vertical)
   */
  public void flip(String key, String savekey, Direction d) {
    Flip f1 = new Flip();
    updatedPixel = f1.apply(key, h1, d);
    h1.put(savekey, updatedPixel);
  }

  /**
   * Converts the image associated with the specified key to grayscale and saves the result under
   * the specified save key.
   *
   * @param key     the key used to retrieve the pixel data
   * @param savekey the key under which the grayscale image will be stored
   */
  public void greyScale(String key, String savekey) {
    GreyScale g1 = new GreyScale();
    updatedPixel = g1.apply(key, h1);
    h1.put(savekey, updatedPixel);
  }

  /**
   * Applies a sepia effect to the image associated with the specified key and saves the result
   * under the specified save key.
   *
   * @param key     the key used to retrieve the pixel data
   * @param savekey the key under which the sepia image will be stored
   */
  public void sepia(String key, String savekey) {
    Sepia sp1 = new Sepia();
    updatedPixel = sp1.apply(key, h1);
    h1.put(savekey, updatedPixel);
  }

  /**
   * Applies a sharpening effect to the image associated with the specified key and saves the result
   * under the specified save key.
   *
   * @param key     the key used to retrieve the pixel data
   * @param savekey the key under which the sharpened image will be stored
   */
  public void sharpen(String key, String savekey) {
    Sharpen sp1 = new Sharpen();
    updatedPixel = sp1.apply(key, h1);
    h1.put(savekey, updatedPixel);
  }

  /**
   * Applies a Luma transformation to the image associated with the specified key and saves the
   * result under the specified save key.
   *
   * @param key     the key used to retrieve the pixel data
   * @param savekey the key under which the Luma image will be stored
   */
  public void luma(String key, String savekey) {
    Luma l1 = new Luma();
    updatedPixel = l1.apply(key, h1);
    h1.put(savekey, updatedPixel);
  }

  /**
   * Applies a value transformation to the image associated with the specified key and saves the
   * result under the specified save key.
   *
   * @param key     the key used to retrieve the pixel data
   * @param savekey the key under which the value image will be stored
   */
  public void value(String key, String savekey) {
    Value l1 = new Value();
    updatedPixel = l1.apply(key, h1);
    h1.put(savekey, updatedPixel);
  }

  /**
   * Applies an intensity transformation to the image associated with the specified key and saves
   * the result under the specified save key.
   *
   * @param key     the key used to retrieve the pixel data
   * @param savekey the key under which the intensity image will be stored
   */
  public void intensity(String key, String savekey) {
    Intensity l1 = new Intensity();
    updatedPixel = l1.apply(key, h1);
    h1.put(savekey, updatedPixel);
  }

  public void compress(String key, String savekey, double compressionration) {
    Compression l1 = new Compression(compressionration);
    updatedPixel = l1.apply(key, h1);
    h1.put(savekey, updatedPixel);
  }
}
