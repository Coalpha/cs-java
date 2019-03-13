class Main {
  public static void main(String[] args) {
    int[][] a = {
      { 1, 2, 3 },
      { 4, 5, 6 },
      { 1, 3, 5 }
    };
    System.out.println(sum2D(a)); // should print 30
  }
  public static int sum2D(int[][] nums) {
    int s = 0;
    for (int[] r : nums) {
      for (int i : r) { s += i; }
    }
    return s;
  }
}
