package model;

import java.io.IOException;
import model.imagetransformation.basicoperation.Flip.Direction;
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

  @Test
  public void testCompression2() {
    image.getPixels("k1", "Images/koala-square.png");
    image.compress("k1","k1-compress-90",90);
    image.savePixels("k1-compress-90", "test/Test_Image/K1-Compress-90.png");
  }

  @Test
  public void testHistogram() throws IOException {
    image.getPixels("m1", "Images/manhattan-small.png");
    assertNotNull(image.h1.get("m1"));
    image.histogram("m1","m1-histogram");
    image.savePixels("m1-histogram", "test/Test_Image/m1-histogram.png");
  }

  @Test
  public void testHistogram2() throws IOException {
    image.getPixels("m2", "test/Test_Image/m2-colorCorrection.png");
    assertNotNull(image.h1.get("m2"));
    image.histogram("m2","m1-histogram");
    image.savePixels("m1-histogram", "test/Test_Image/m1-histogram2.png");
  }

  @Test
  public void testHistogram3() throws IOException {
    image.getPixels("m3", "test/Test_Image/m1-adjust-level.png");
    assertNotNull(image.h1.get("m3"));
    image.histogram("m3","m1x -adjust-level");
    image.savePixels("m1-adjust-level", "test/Test_Image/m1-histogram3.png");
  }

  @Test
  public void testHistogram4() throws IOException {
    image.getPixels("m3", "test/Test_Image/m1-adjust-level2.png");
    assertNotNull(image.h1.get("m3"));
    image.histogram("m3","m1-adjust-level");
    image.savePixels("m1-adjust-level", "test/Test_Image/m1-histogram4.png");
  }

  @Test
  public void testColorCorrection() throws IOException {
    image.getPixels("m2", "Images/manhattan-small.png");
    assertNotNull(image.h1.get("m2"));
    image.colorCorrection("m2","m2-colorCorrection");
    image.savePixels("m2-colorCorrection", "test/Test_Image/m2-colorCorrection.png");
  }

  @Test
  public void testLevelAdjust(){
    image.getPixels("m1", "Images/manhattan-small.png");
    assertNotNull(image.h1.get("m1"));
    image.adjustLevel(20,100,255,"m1","m1-adjust-level");
    image.savePixels("m1-adjust-level", "test/Test_Image/m1-adjust-level.png");
  }

  @Test
  public void testLevelAdjus2(){
    image.getPixels("m1", "Images/manhattan-small2.png");
    assertNotNull(image.h1.get("m1"));
    image.adjustLevel(20,100,255,"m1","m1-adjust-level");
    image.savePixels("m1-adjust-level", "test/Test_Image/m1-adjust-level2.png");
  }

  @Test
  public void testLevelAdjus3(){
    image.getPixels("m1", "Images/galaxy.png");
    assertNotNull(image.h1.get("m1"));
    image.adjustLevel(20,100,255,"m1","m1-adjust-level");
    image.histogram("m1-adjust-level","m1-adjust-level-histogram");
    image.savePixels("m1-adjust-level-histogram", "test/Test_Image/m1-adjust-level-histogram.png");
  }

  @Test
  public void testSplitAndTransform(){
    image.getPixels("m1","Images/manhattan-small.png");
    image.splitAndTransform("m1","m1-split-sepia",50,"Sepia");
    image.savePixels("m1-split-sepia", "test/Test_Image/TestingImageClass/m1-split-sepia.png");
  }

  @Test
  public void testSplitAndTransform2(){
    image.getPixels("m1","Images/manhattan-small.png");
    image.splitAndTransform("m1","m1-split-levels-adjust",50,"levels-adjust",20,100,255);
    image.savePixels("m1-split-levels-adjust", "test/Test_Image/TestingImageClass/m1-split-levels-adjust.png");
  }

  @Test
  public void testimage()
  {
    image.getPixels("m1","Images/manhattan-small.png");
    image.brighten(90,"m1","m1-brighten");
    image.flip("m1-brighten","m1-flip", Direction.VERTICAL);
    image.savePixels("m1-brighten", "test/Test_Image/m1-brightennew.png");
  }

}