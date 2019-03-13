public class Egg2 {
  public static void main(String[] args) {
    top();
    bottom();
    center();
    top();
    bottom();
    center();
    bottom();
    top();
    center();
    bottom();
  }
  public static void top() {
    System.out.println("  _______\n /       \\\n/         \\");
  }
  public static void bottom() {
    System.out.println("\\         /\n \\_______/");
  }
  public static void center() {
    System.out.println("-\"-'-\"-'-\"-");
  }
}
