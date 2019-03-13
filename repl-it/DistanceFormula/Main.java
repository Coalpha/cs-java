class Main {
  static Point point1 = new Point();
  static Point point2 = new Point(3, 4);
  public static void main(String[] args) {
    point1.x = 0;
    point1.y = 0;
    System.out.println(point2.distance(point1));
  }
}
