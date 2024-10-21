package Model;

public class RGBPixel {

  private int r;
  private int g;
  private int b;

  public RGBPixel(int r,int g,int b){
    this.r=Math.min(255, Math.max(0, r));
    this.g=Math.min(255, Math.max(0, g));
    this.b=Math.min(255, Math.max(0, b));
  }

  public int getRed(){
    return r;
  }
  public int getGreen(){
    return g;
  }
  public int getBlue(){
    return b;
  }

}
