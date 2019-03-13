public class Lanterns {
  public static void main(String[] args) {
    d123();
    lf();
    d123();
    light();
    d3();
    d123();
    lf();
    d123();
    d1();
    light();
    light();
    d1();
    d1();
  }
  public static void d1() {
    System.out.println("    *****");
  }
  public static void d2() {
    System.out.println("  *********");
  }
  public static void d3() {
    System.out.println("*************");
  }
  public static void d123() {
    d1();
    d2();
    d3();
  }
  public static void light() {
    System.out.println("* | | | | | *");
  }
  public static void lf() {
    System.out.println();
  }
}
