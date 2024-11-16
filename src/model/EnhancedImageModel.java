package model;

public interface EnhancedImageModel extends ImageModel {

  void downscale(String key, int newwidth,int newht, String savekey );
  public void maskedOperation(String key, String operation, String maskKey, String saveKey);
}
