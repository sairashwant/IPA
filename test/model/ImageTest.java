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
    assertNotNull(image.h1.get("landscape"));
    image.splitAndTransform("landscape","landscape-split-brighten",50,"Sepia");
    image.savePixels("landscape-split-brighten", "test/Test_Image/TestingImageClass/Landscape-Split-Brighten.png");


  }
}