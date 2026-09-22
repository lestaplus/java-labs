package model;

public class Triangle extends Shape {
    private final double x1, y1;
    private final double x2, y2;
    private final double x3, y3;

    public Triangle(String shapeColor,
                    double x1, double y1,
                    double x2, double y2,
                    double x3, double y3) {
        super(shapeColor);
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.x3 = x3;
        this.y3 = y3;
    }

    @Override
    public void draw() {
        System.out.println("Drawing " + getShapeColor() + " triangle: " +
                "x1: " + x1 + ", y1: " + y1 +
                ", x2: " + x2 + ", y2: " + y2 +
                ", x3: " + x3 + ", y3: " + y3);
    }

    @Override
    public double calcArea() {
        double d1 = x1*y2 + x2*y3 + x3*y1;
        double d2 = y1*x2 + y2*x3 + y3*x1;

        double diff = Math.abs(d1 - d2);

        return diff / 2;
    }

    @Override
    public String toString() {
        return super.toString() +
                ", x1=" + x1 + ", y1=" + y1 +
                ", x2=" + x2 + ", y2=" + y2 +
                ", x3=" + x3 + ", y3=" + y3;
    }
}
