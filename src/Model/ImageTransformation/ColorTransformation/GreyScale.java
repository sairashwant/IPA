package Model.ImageTransformation.ColorTransformation;


public class GreyScale extends AbstractColorTransformation{

  @Override
  public double[][] getMatrix() {
    return new double[][]{
        {0.2126, 0.7152, 0.0722},
        {0.2126, 0.7152, 0.0722},
        {0.2126, 0.7152, 0.0722}
    };
  };
}





