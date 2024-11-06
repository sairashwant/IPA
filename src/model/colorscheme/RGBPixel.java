package model.colorscheme;

/**
 * The RGBPixel class represents a pixel with red, green, and blue (RGB) color components. Each
 * color component is clamped between 0 and 255 to ensure valid RGB values.
 */
public class RGBPixel implements Pixels{

  private int r;
  private int g;
  private int b;

  /**
   * Constructs an RGBPixel with specified red, green, and blue values. Values are clamped between 0
   * and 255 to ensure they are valid RGB values.
   *
   * @param r the red component (0-255)
   * @param g the green component (0-255)
   * @param b the blue component (0-255)
   */
  public RGBPixel(int r, int g, int b) {
    this.r = Math.min(255, Math.max(0, r));
    this.g = Math.min(255, Math.max(0, g));
    this.b = Math.min(255, Math.max(0, b));
  }

  /**
   * Returns the red component of this pixel.
   *
   * @return the red value (0-255)
   */
  public int getRed() {
    return r;
  }

  /**
   * Returns the green component of this pixel.
   *
   * @return the green value (0-255)
   */
  public int getGreen() {
    return g;
  }

  /**
   * Returns the blue component of this pixel.
   *
   * @return the blue value (0-255)
   */
  public int getBlue() {
    return b;
  }

}
