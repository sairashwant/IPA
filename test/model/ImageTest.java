package model;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ImageTest {
  private Image image;

  @Before
  public void setUp() {
    image = new Image();
  }

  @Test
  public void testCompression() {
    image.getPixels("landscape", "test/Test_Image/Landscape.png");
    image.compress("landscape", "compressed-landscape", 90.0);
    assertNotNull(image.h1.get("compressed-landscape"));
    image.savePixels("compressed-landscape", "test/Test_Image/TestingImageClass/Landscape-Compressed.png");


  }
}