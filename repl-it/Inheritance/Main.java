class Main {
  static void prinfo(Shape s) {
    System.out.format("It's a %.1f units\u00B2 %s %s\n\n", s.getArea(), s.getColor(), s.getShapeType());
  }

  static void main(String[] args) {
    prinfo(new Rectangle(10, 5, "blue"));
    prinfo(new Circle(15, "red"));
    prinfo(new Triangle(5, 10, "green"));
  }
}
