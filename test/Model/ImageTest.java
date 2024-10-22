package Model;
import Model.Flip.Direction;
import org.junit.Test;

public class ImageTest {

@Test
  public void test() {
  Image i1 = new Image();
  i1.getPixels("Thalapathy-org","thalapathy.jpg");
  i1.blur("Thalapathy-org","Thalapathyblur");
  i1.brighten("Thalapathyblur",50,"ThalapathyBrighten");
  i1.brighten("ThalapathyBrighten",20,"ThalapathyBrighten2");
  i1.brighten("ThalapathyBrighten",-110,"ThalapathyBrighten3");
  i1.Flip("Thalapathyblur","ThalapathyFlip",Direction.VERTICAL);
  i1.split("ThalapathyBrighten");
  i1.combine("ThalapathyCombined","ThalapathyBrighten-Red","ThalapathyBrighten-Green","ThalapathyBrighten-Blue");
  i1.GreyScale("ThalapathyCombined","ThalapathyGreyScale");
  i1.sepia("ThalapathyCombined","ThalapathySepia");
  i1.Sharpen("ThalapathyCombined","ThalapathySharpen");
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
  i1.split("Thalapathy");
  i1.brighten("Thalapathy-Red",100,"Thalapathy-Red-Brighten");
  i1.sepia("Thalapathy-Red","Thalapathy-Red-Sepia");
  i1.savePixels("Thalapathy-Red-Brighten","thalapathy-RED-brighten.jpg");
  i1.combine("Thalapathycombine","Thalapathy-Red-Brighten","Thalapathy-Green","Thalapathy-Blue");
  i1.savePixels("Thalapathycombine","thalapathycombine.jpg");
  i1.savePixels("Thalapathy-Red-Sepia","thalapathy-Red-Sepia.jpg");
  i1.Flip("Thalapathy-Red-Sepia","Thalapathy-Red-Sepia-Flip", Direction.VERTICAL);
  i1.sepia("Thalapathy-org","thalapathy-org-sepia");
  i1.savePixels("thalapathy-org-sepia","thalapathy-org-sepia.jpg");
  i1.savePixels("Thalapathy-Red-Sepia-Flip","thalapathy-RED-sepia-flip.jpg");
}
}