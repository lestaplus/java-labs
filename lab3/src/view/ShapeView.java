package view;

import model.Shape;

public class ShapeView {
    public void printMessage(String message) {
        System.out.println(message);
    }

    public void printShapes(Shape[] shapes) {
        for (Shape shape : shapes) {
            shape.draw();
            System.out.println(shape + ", area: " + shape.calcArea());
        }
    }
}
