abstract class Shape {
  protected String color;
  protected String type;

  abstract double getArea();

  String getColor() {
    return color;
  };

  String getShapeType() {
    return getClass().getSimpleName().toLowerCase();
  };
}
