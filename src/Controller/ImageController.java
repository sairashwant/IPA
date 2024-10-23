package Controller;

import Model.ImageTransformation.BasicOperation.Flip.Direction;
import Model.Image;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * The ImageController class provides an interface to control various image processing operations
 * on an Image object. It handles loading, saving, and applying a variety of transformations and
 * operations like flipping, blurring, brightening, and splitting images.
 */
public class ImageController {

  private final Image image;

  private final Map<String, BiConsumer<String, String>> operations;

  private final Map<String, TriConsumer<String ,Integer ,String>> bOperations;

  /**
   * Constructs an ImageController with the given Image model and initializes
   * a set of supported image operations.
   *
   * @param image the Image model that this controller operates on
   */
  public ImageController(Image image) {
    this.image = image;
    this.operations = new HashMap<>();
    this.bOperations = new HashMap<>();



    operations.put("blur",(src,dest) -> image.blur(src,dest));
    operations.put("horizontal-flip",(src,dest) -> image.flip(src,dest, Direction.HORIZONTAL));
    operations.put("vertical-flip",(src,dest) -> image.flip(src,dest, Direction.VERTICAL));
    operations.put("greyscale",(src,dest) -> image.greyScale(src,dest));
    operations.put("sepia",(src,dest) -> image.sepia(src,dest));
    operations.put("sharpen",(src,dest) -> image.sharpen(src,dest));
    operations.put("split", (src,dest) -> image.split(src,dest,dest,dest));
    operations.put("rgb-combine",(src,dest) -> image.combine(dest,src,"src2","src3"));
    operations.put("value-component",(src,dest) -> image.value(src,dest));
    operations.put("luma-component",(src,dest) -> image.luma(src,dest));
    operations.put("intensity-component",(src,dest) -> image.intensity(src,dest));
    operations.put("red-component",(src,dest) -> image.getRedChannel(src,dest));
    operations.put("green-component",(src,dest) -> image.getGreenChannel(src,dest));
    operations.put("blue-component",(src,dest) -> image.getBlueChannel(src,dest));
    bOperations.put("brighten", (src,factor,dest) -> image.brighten(factor, src,dest));
  }

  /**
   * Loads an image from a file and associates it with the given key.
   *
   * @param key the key to store the image pixels under
   * @param fileName the name of the file to load the image from
   */
  public void loadIMage(String key,String fileName){
    image.getPixels(key,fileName);
  }

  /**
   * Saves the image associated with the given key to a file.
   *
   * @param key the key associated with the image to be saved
   * @param fileName the name of the file to save the image to
   */
  public void saveImage(String key,String fileName){
    image.savePixels(key,fileName);
  }

  /**
   * Applies a specified image operation using the source and destination keys.
   *
   * @param operationName the name of the operation to apply (e.g., "blur", "greyscale")
   * @param srcKey the key of the source image
   * @param destKey the key to store the transformed image under
   */
  public void applyOperations(String operationName, String srcKey, String destKey){
    if(operations.containsKey(operationName)){
      operations.get(operationName).accept(srcKey,destKey);
    }else{
      System.out.println("No such operation: "+operationName);
    }
  }

  /**
   * Brightens an image by a given factor.
   *
   * @param factor the factor by which to brighten the image
   * @param srcKey the key of the source image
   * @param destKey the key to store the brightened image under
   */
  public void brighten(int factor, String srcKey, String destKey){
    image.brighten(factor,srcKey,destKey);
  }

  /**
   * Splits an image into its red, green, and blue channels and stores them
   * under the provided save keys.
   *
   * @param key the key of the source image
   * @param saveKey1 the key to store the red channel
   * @param saveKey2 the key to store the green channel
   * @param saveKey3 the key to store the blue channel
   */
  public void split(String key, String saveKey1, String saveKey2, String saveKey3) {
    image.split(key,saveKey1,saveKey2,saveKey3);
  }

  /**
   * Combines the red, green, and blue channels stored under the provided keys
   * into a single image.
   *
   * @param key the key to store the combined image
   * @param key1 the key of the red channel image
   * @param key2 the key of the green channel image
   * @param key3 the key of the blue channel image
   */
  public void combine(String key, String key1,String key2, String key3){
    image.combine(key,key1,key2,key3);
  }

}
