package Model.ImageTransformation;

public class Value extends AbstractBasicOperation{

  @Override
  public int properties(int r, int g, int b) {
    int maxValue = Math.max(Math.max(r,g), b);
    return maxValue;
  }

}
