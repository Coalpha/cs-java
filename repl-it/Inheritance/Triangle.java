class Triangle extends Rectangle {
  public Triangle(double base, double height, String color) {
    super(base, height, color);
  }

  @Override
  double getArea() {
    return base * height / 2;
  }
}
