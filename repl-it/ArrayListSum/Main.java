import java.util.ArrayList;

class Main {
  public static int test(ArrayList<Integer> ints) {
    return ints.stream().mapToInt((Integer i) -> i).sum();
  }
}
