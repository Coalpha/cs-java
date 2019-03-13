public class TwoRockets {
  public static void main(String[] args) {
    Point();
    Box();
    Merica();
    Box();
    Point();
  }
  public static void Point() {
    System.out.println("   /\\       /\\\n  /  \\     /  \\\n /    \\   /    \\");
  }
  public static void Box() {
    System.out.println("+------+ +------+\n|      | |      |\n|      | |      |\n+------+ +------+");
  }
  public static void Merica() {
    System.out.println("|United| |United|\n|States| |States|");
  }
}
