package model.imagetransformation.colortransformation;

/**
 * The {@code Sepia} class is a specific implementation of the {@code AbstractColorTransformation}
 * class that applies a sepia tone effect to an image. This transformation modifies the red, green,
 * and blue components of each pixel using a sepia-tone matrix, giving the image a warm, brownish
 * tint often associated with vintage photography or classic photo effects.
 *
 * <p>The sepia effect is achieved by applying a transformation matrix that adjusts the RGB values
 * of each pixel in such a way that the resulting image takes on the characteristic brownish tones
 * of sepia images.</p>
 *
 * <p>After applying the sepia transformation, the colors in the image will shift, typically
 * creating a more nostalgic or aged aesthetic.</p>
 */
public class Sepia extends AbstractColorTransformation {

  /**
   * Provides the transformation matrix used to apply the sepia effect. This matrix alters the red,
   * green, and blue components of a pixel to create the sepia tone. The matrix coefficients are
   * chosen to emphasize the warm brownish hues that define sepia-toned images.
   *
   * <p>The resulting matrix applies a weighted sum to each pixel's RGB values to transform them
   * into the sepia tones.</p>
   *
   * @return a 3x3 transformation matrix that is used to apply the sepia tone effect to the image.
   * The matrix coefficients are designed to create a warm, brownish tint for each pixel.
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




