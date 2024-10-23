package Controller;

import Model.ImageTransformation.BasicOperation.Flip.Direction;
import Model.Image;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 *
 */
public class ImageController {

  private final Image image;

  private final Map<String, BiConsumer<String, String>> operations;

  private final Map<String, TriConsumer<String ,Integer ,String>> bOperations;

  /**
   *
   * @param image
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
   *
   * @param fileName
   * @param key
   */
  public void loadIMage(String key,String fileName){
    image.getPixels(key,fileName);
  }

  /**
   *
   * @param key
   * @param fileName
   */
  public void saveImage(String key,String fileName){
    image.savePixels(key,fileName);
  }

  /**
   *
   * @param operationName
   * @param srcKey
   * @param destKey
   */
  public void applyOperations(String operationName, String srcKey, String destKey){
    if(operations.containsKey(operationName)){
      operations.get(operationName).accept(srcKey,destKey);
    }else{
      System.out.println("No such operation: "+operationName);
    }
  }

  /**
   *
   * @param factor
   * @param srcKey
   * @param destKey
   */
  public void brighten(int factor, String srcKey, String destKey){
    image.brighten(factor,srcKey,destKey);
  }

  /**
   *
   * @param key
   * @param saveKey1
   * @param saveKey2
   * @param saveKey3
   */
  public void split(String key, String saveKey1, String saveKey2, String saveKey3) {
    image.split(key,saveKey1,saveKey2,saveKey3);
  }

  /**
   *
   * @param key
   * @param key1
   * @param key2
   * @param key3
   */
  public void combine(String key, String key1,String key2, String key3){
    image.combine(key,key1,key2,key3);
  }

}
