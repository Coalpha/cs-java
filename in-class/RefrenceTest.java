import static java.lang.System.*;

class RefrenceTest {
  public static void main(String[] args) {
    Integer x = 1;
    Integer y = x;
    out.println("x: " + x);
    out.println("y: " + y);
    x = 2;
    out.println("x: " + x);
    out.println("y: " + y);
  }
}
