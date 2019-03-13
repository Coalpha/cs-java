class Circle extends Shape {
  private double radius;

  Circle(double radius, String color) {
    this.radius = radius;
    this.color = color;
  }

  double getArea() {
    return Math.PI * Math.pow(radius, 2);
  }
}
