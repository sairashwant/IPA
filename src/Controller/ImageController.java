package Controller;

import Model.ImageTransformation.Flip.Direction;
import Model.Image;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class ImageController {

  private final Image image;

  private final Map<String, BiConsumer<String, String>> operations;

  private final Map<String, TriConsumer<String ,Integer ,String>> bOperations;


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
    operations.put("combine",(src,dest) -> image.combine(dest,src,src,src));
//    operations.put("value",(src,dest) -> image.val)
//    operations.put("brighten"(src,dest) -> image.brighten(src,factor,dest));

    bOperations.put("brighten", (src,factor,dest) -> image.brighten(src,factor,dest));
  }

  public void loadIMage(String key,String fileName){
    image.getPixels(key,fileName);
  }

  public void saveImage(String key,String fileName){
    image.savePixels(key,fileName);
  }

  public void applyOperations(String operationName, String srcKey, String destKey){
    if(operations.containsKey(operationName)){
      operations.get(operationName).accept(srcKey,destKey);
    }else{
      System.out.println("No such operation: "+operationName);
    }
  }

  public void brighten(String srcKey, String destKey ,int factor){
    image.brighten(srcKey,factor,destKey);
  }

  public void split(String key, String saveKey1, String saveKey2, String saveKey3) {
    image.split(key,saveKey1,saveKey2,saveKey3);
  }

  public void combine(String key, String key1,String key2, String key3){
    image.combine(key,key1,key2,key3);
  }

}
