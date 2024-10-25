package model.imagetransformation.colortransformation;

/**
 * The Sepia class is a specific implementation of AbstractColorTransformation that applies a sepia
 * tone effect to an image. This transformation adjusts the red, green, and blue components of each
 * pixel using a sepia-tone matrix, which gives the image a warm, brownish tint often associated
 * with vintage photography.
 */
public class Sepia extends AbstractColorTransformation {

  /**
   * Provides the transformation matrix used to apply the sepia effect. The matrix adjusts the red,
   * green, and blue components of a pixel to give it a sepia tone. The coefficients are chosen to
   * create the characteristic warm, brownish color of sepia images.
   *
   * @return a 3x3 transformation matrix used to apply the sepia tone to the image
   */
  @Override
  protected double[][] getMatrix() {
    return new double[][]{
        {0.393, 0.769, 0.189},
        {0.349, 0.686, 0.168},
        {0.272, 0.534, 0.131}
    };
  }

  ;
}




