package model;

public class Circle extends Shape {
    private final double cx;
    private final double cy;
    private final double r;

    public Circle(String shapeColor, double cx, double cy, double r) {
        super(shapeColor);
        this.cx = cx;
        this.cy = cy;
        this.r = r;
    }

    @Override
    public void draw() {
        System.out.println("Drawing " + getShapeColor() +" circle: " +
                "cx: " + cx + ", cy: " + cy + ", radius: " + r);
    }

    @Override
    public double calcArea() {
        return Math.PI * r * r;
    }

    @Override
    public String toString() {
        return super.toString() + ", cx=" + cx + ", cy=" + cy + ", r=" + r;
    }
}
