package controller;

import model.*;
import service.ShapeFileHandler;
import view.ShapeView;

import java.io.File;
import java.util.Scanner;

public class ShapeController {
    private final ShapeModel model;
    private final ShapeView view;
    private final Scanner sc =  new Scanner(System.in);

    private final Shape[] defaultShapes = {
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

    public ShapeController(ShapeModel model, ShapeView view) {
        this.model = model;
        this.view = view;
    }

    public void processUser() {
        int menuItem;
        while (true) {
            view.printMessage("""
                --- Menu ---
                1 - Load shapes from file
                2 - Export shapes to file
                3 - Print shapes information
                0 - Exit
                Enter menu item number (0..3):\s""");

            String item = sc.nextLine();
            if (item.trim().isEmpty()) {
                view.printMessage("Invalid input.");
                continue;
            }

            try {
                menuItem = Integer.parseInt(item);
                if (menuItem < 0 || menuItem > 3) {
                    view.printMessage("Invalid input.");
                    continue;
                }

                if (menuItem == 0) {
                    view.printMessage("Exiting...");
                    return;
                }

                menuSelection(menuItem);
            } catch (NumberFormatException e) {
                view.printMessage("Invalid input.");
            }
        }
    }

    public void showInfo() {
        view.printMessage("Current shapes set:");
        view.printShapes(model.getShapes());

        view.printMessage("\nArea of all shapes: " + model.calcAreaAll());

        view.printMessage("\nEnter shape type to calculate total area: ");

        String shapeType;
        while (true) {
            shapeType = sc.nextLine().trim();
            if (shapeType.equalsIgnoreCase("Rectangle") ||
                    shapeType.equalsIgnoreCase("Circle") ||
                    shapeType.equalsIgnoreCase("Triangle")) break;
            view.printMessage("Invalid shape type.");
        }
        view.printMessage("\nArea of all " + shapeType + "s: " + model.calcAreaByType(shapeType));

        view.printMessage("\nSorted shapes by area:");
        view.printShapes(model.sortShapesByArea());

        view.printMessage("\nSorted shapes by color:");
        view.printShapes(model.sortShapesByColor());
    }

    public void menuSelection(int item) {
        try {
            String filePath = null;
            if (item == 1 || item == 2) {
                while (!isPathValid(filePath)) {
                    view.printMessage("Enter valid relative path: ");
                    filePath = sc.nextLine();
                }
            }

            switch (item) {
                case 1:
                    Shape[] loadedShapes = ShapeFileHandler.readFile(filePath);
                    if (loadedShapes != null) {
                        model.setShapes(loadedShapes);
                        view.printMessage("Shapes loaded.");
                    }
                    break;
                case 2:
                    Shape[] shapesToExport = model.getShapes();
                    if (shapesToExport == null) {
                        view.printMessage("Model is empty. Exporting default initial shapes...");
                        shapesToExport = defaultShapes;
                    }
                    ShapeFileHandler.writeFile(filePath, shapesToExport);
                    view.printMessage("Shapes exported.");
                    break;
                case 3:
                    if (model.getShapes() == null) {
                        view.printMessage("First, load shapes from file.");
                        break;
                    }
                    showInfo();
                    break;
            }
        } catch (Exception e) {
            view.printMessage("Error processing menu: " + e.getMessage());
        }
    }

    public static boolean isPathValid(String filePath) {
        if (filePath == null || filePath.trim().isEmpty()) {
            return false;
        }

        try {
            File file = new File(filePath);
            file.getCanonicalPath();
            return true;
        } catch (Exception e) {
            System.out.println("Invalid file path!");
            return false;
        }
    }
}
