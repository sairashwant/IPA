package model;

public interface EnhancedImageModel extends ImageModel {

  void downscale(String key, int newwidth,int newht, String savekey );
   void maskedOperation(String key, String operation, String maskKey, String saveKey);
}
