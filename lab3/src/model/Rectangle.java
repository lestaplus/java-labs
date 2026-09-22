package model;

public class Rectangle extends Shape {
    private final double x1, y1;
    private final double x2, y2;

    public Rectangle(String shapeColor, double x1, double y1, double x2, double y2) {
        super(shapeColor);
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    @Override
    public void draw() {
        System.out.println("Drawing " + getShapeColor() + " rectangle: " +
                "x1: " + x1 + ", y1: " + y1 +
                ", x2: " + x2 + ", y2: " + y2);
    }

    @Override
    public double calcArea() {
        return Math.abs((x2 - x1) * (y2 - y1));
    }

    @Override
    public String toString() {
        return super.toString() + ", x1=" + x1 + ", y1=" + y1 + ", x2=" + x2 + ", y2=" + y2;
    }
}
