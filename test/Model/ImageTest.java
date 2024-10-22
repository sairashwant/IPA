package Model;
import Model.ImageTransformation.Flip.Direction;
import org.junit.Test;

public class ImageTest {

@Test
  public void test() {
  Image i1 = new Image();
  i1.getPixels("Thalapathy-org","thalapathy.jpg");
  i1.blur("Thalapathy-org","Thalapathyblur");
  i1.brighten(50,"Thalapathyblur","ThalapathyBrighten");
  i1.brighten(20,"ThalapathyBrighten","ThalapathyBrighten2");
  i1.brighten(-110,"ThalapathyBrighten","ThalapathyBrighten3");
  i1.flip("Thalapathyblur","ThalapathyFlip",Direction.VERTICAL);
  i1.split("ThalapathyBrighten","ThalapathyBrighten-Red","ThalapathyBrighten-Green","ThalapathyBrighten-Blue");
  i1.combine("ThalapathyCombined","ThalapathyBrighten-Red","ThalapathyBrighten-Green","ThalapathyBrighten-Blue");
  i1.greyScale("ThalapathyCombined","ThalapathyGreyScale");
  i1.sepia("ThalapathyCombined","ThalapathySepia");
  i1.sharpen("ThalapathyCombined","ThalapathySharpen");
  i1.savePixels("Thalapathyblur","thalapathyblur.jpg");
  i1.savePixels("ThalapathyFlip","thalapathyFlip.jpg");
  i1.savePixels("ThalapathyBrighten","thalapathyBright.jpg");
  i1.savePixels("ThalapathyBrighten-Red","thalapathyBrightred.jpg");
  i1.savePixels("ThalapathyBrighten-Green","thalapathyBrightgreen.jpg");
  i1.savePixels("ThalapathyBrighten-Blue","thalapathyBrightblue.jpg");
  i1.savePixels("ThalapathyCombined","thalapathyCombine.jpg");
  i1.savePixels("ThalapathyGreyScale","thalapathyGreyScale.jpg");
  i1.savePixels("ThalapathySepia","thalapathySepia.jpg");
  i1.savePixels("ThalapathySharpen","thalapathySharpen.jpg");

}
@Test
  public void test2() {
  Image i1 = new Image();
  i1.getPixels("Thalapathy-org","thalapathy.jpg");
  i1.split("Thalapathy","Thalapathy-Red","Thalapathy-Greeen","Thalapathy-Blue");
  i1.brighten(100,"Thalapathy-Red","Thalapathy-Red-Brighten");
  i1.sepia("Thalapathy-Red","Thalapathy-Red-Sepia");
  i1.savePixels("Thalapathy-Red-Brighten","thalapathy-RED-brighten.jpg");
  i1.savePixels("Thalapathycombine","thalapathycombine.jpg");
  i1.savePixels("Thalapathy-Red-Sepia","thalapathy-Red-Sepia.jpg");
  i1.flip("Thalapathy-Red-Sepia","Thalapathy-Red-Sepia-flip", Direction.VERTICAL);
  i1.sepia("Thalapathy-org","thalapathy-org-sepia");
  i1.savePixels("thalapathy-org-sepia","thalapathy-org-sepia.jpg");
  i1.savePixels("Thalapathy-Red-Sepia-flip","thalapathy-RED-sepia-flip.jpg");
  i1.luma("Thalapathy-org","Thalapathyluma");
  i1.value("Thalapathy-org","Thalapathyvalue");
  i1.intensity("Thalapathy-org","Thalapathyintensity");
  i1.savePixels("Thalapathyluma","Thalapathyluma.jpg");
  i1.savePixels("Thalapathyvalue","thalapathyvalue.jpg");
  i1.savePixels("Thalapathyintensity","thalapathyintensity.jpg");
}
}