package Model;

public class Blur extends AbstractFiltering {

  protected double[][] getFilter() {
    return new double[][]{
        {1.0 / 16, 1.0 / 8, 1.0 / 16},
        {1.0 / 8, 1.0 / 4, 1.0 / 8},
        {1.0 / 16, 1.0 / 8, 1.0 / 16}
    };
  };
}

