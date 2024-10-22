package Model;

public class Sharpen extends AbstractFiltering {

  @Override
  protected double[][] getFilter() {
    return new double[][]{
        {-1.0/8, -1.0/8, -1.0/8 , -1.0/8 , -1.0/8},
        {-1.0/8, 1.0/4, 1.0/4 , 1.0/4 , -1.0/8},
        {-1.0/8, 1.0/4, 1.0 , 1.0/4 , -1.0/8},
        {-1.0/8, 1.0/4, 1.0/4 , 1.0/4 , -1.0/8},
        {-1.0/8, -1.0/8, -1.0/8 , -1.0/8 , -1.0/8}
    };
  }


}
