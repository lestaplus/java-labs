package controller;

import model.*;
import view.ShapeView;

public class ShapeController {
    private ShapeModel model;
    private ShapeView view;

    public ShapeController(ShapeModel model, ShapeView view) {
        this.model = model;
        this.view = view;
    }

    public void processUser() {
        Shape[] shapes = {
                new Rectangle("Red", 0.0, 0.5, 4.0, 5.0),
                new Circle("Blue", 1.0, 2.0, 3.5),
                new Triangle("Green", 0.0, 0.0, 4.0, 0.0, 0.0, 3.0),
                new Rectangle("Yellow", 2.0, 1.0, 7.0, 6.0),
                new Circle("Black", -2.0, 3.0, 5.0),
                new Triangle("Red", 1.0, 1.0, 6.0, 2.5, 3.0, 7.0),
                new Rectangle("Blue", -1.0, -1.0, 3.0, 2.0),
                new Circle("White", 0.0, 0.0, 2.0),
                new Triangle("Yellow", 2.0, 2.0, 5.0, 2.0, 2.5, 6.0),
                new Rectangle("Green", 3.0, 3.5, 8.0, 5.0)
        };

        model.setShapes(shapes);

        view.printMessage("Initial shapes set:");
        view.printShapes(model.getShapes());

        double totalArea = model.calcAreaAll();
        view.printMessage("\nArea of all shapes: " + totalArea);

        double rectanglesArea = model.calcAreaByType("Rectangle");
        view.printMessage("\nArea of all rectangles: " + rectanglesArea);

        double trianglesArea = model.calcAreaByType("Triangle");
        view.printMessage("\nArea of all triangles: " + trianglesArea);

        double circlesArea = model.calcAreaByType("Circle");
        view.printMessage("\nArea of all circles: " + circlesArea);

        view.printMessage("\nSorted shapes by area:");
        view.printShapes(model.sortShapesByArea());

        view.printMessage("\nSorted shapes by color:");
        view.printShapes(model.sortShapesByColor());
    }
}
