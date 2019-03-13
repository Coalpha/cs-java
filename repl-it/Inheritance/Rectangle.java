class Rectangle extends Shape {
  protected double base;
  protected double height;

  Rectangle(double base, double height, String color) {
    this.base = base;
    this.height = height;
    this.color = color;
  }

  double getArea() {
    return base * height;
  }
}
