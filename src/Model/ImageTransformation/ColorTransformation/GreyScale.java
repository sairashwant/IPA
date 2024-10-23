package Model.ImageTransformation.ColorTransformation;


public class GreyScale extends AbstractColorTransformation{

  /**
   *
   * @return
   */
  @Override
  protected double[][] getMatrix() {
    return new double[][]{
        {0.2126, 0.7152, 0.0722},
        {0.2126, 0.7152, 0.0722},
        {0.2126, 0.7152, 0.0722}
    };
  };
}





