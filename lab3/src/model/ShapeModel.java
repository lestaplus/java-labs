package model;

import java.util.Arrays;
import java.util.Comparator;

public class ShapeModel {
    private Shape[] shapes;

    public void setShapes(Shape[] shapes) {
        this.shapes = shapes;
    }

    public double calcAreaAll() {
        double totalArea = 0;

        for (Shape shape : shapes) {
            totalArea += shape.calcArea();
        }

        return totalArea;
    }

    public double calcAreaByType(String type) {
        double totalArea = 0;

        for (Shape shape : shapes) {
            String shapeType = shape.getClass().getSimpleName().toLowerCase();

            if (shapeType.equals(type.toLowerCase())) {
                totalArea += shape.calcArea();
            }
        }

        return totalArea;
    }

    public Shape[] sortShapesByArea() {
        Shape[] shapesCopy = this.shapes.clone();

        Arrays.sort(shapesCopy, Comparator.comparingDouble(Shape::calcArea));

        return shapesCopy;
    }

    public Shape[] sortShapesByColor() {
        Shape[] shapesCopy = this.shapes.clone();

        Arrays.sort(shapesCopy, Comparator.comparing(Shape::getShapeColor));

        return shapesCopy;
    }

    public Shape[] getShapes() {
        return shapes;
    }
}
