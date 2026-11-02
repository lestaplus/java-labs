package controller;

import model.*;
import service.ShapeFileHandler;
import view.ShapeView;

import java.io.File;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ShapeController {
    private static final Logger logger = Logger.getLogger(ShapeController.class.getName());

    private ResourceBundle bundle = ResourceBundle.getBundle("location.text", Locale.ENGLISH);

    private final ShapeModel model;
    private final ShapeView view;
    private final Scanner sc = new Scanner(System.in);

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
            view.printMessage(
                    bundle.getString("menu.header") + "\n" +
                            bundle.getString("menu.load") + "\n" +
                            bundle.getString("menu.export") + "\n" +
                            bundle.getString("menu.print") + "\n" +
                            bundle.getString("menu.lang") + "\n" +
                            bundle.getString("menu.exit") + "\n" +
                            bundle.getString("menu.prompt")
            );

            String item = sc.nextLine();
            if (item.trim().isEmpty()) {
                logger.warning("The user entered an empty string");
                view.printMessage(bundle.getString("msg.invalid"));
                continue;
            }

            try {
                menuItem = Integer.parseInt(item);
                if (menuItem < 0 || menuItem > 4) {
                    logger.warning("The user selected an unknown option");
                    view.printMessage(bundle.getString("msg.invalid"));
                    continue;
                }

                if (menuItem == 0) {
                    logger.info("The user chose to exit the application.");
                    view.printMessage(bundle.getString("msg.exiting"));
                    return;
                }

                if (menuItem == 4) {
                    changeLanguage();
                } else {
                    logger.fine("Selected menu item: " + menuItem);
                    menuSelection(menuItem);
                }
            } catch (NumberFormatException e) {
                logger.log(Level.WARNING, "Number format exception: non-numeric input", e);
                view.printMessage(bundle.getString("msg.invalid"));
            }
        }
    }

    private void changeLanguage() {
        view.printMessage(bundle.getString("msg.lang_prompt"));
        String choice = sc.nextLine().trim();

        if (choice.equals("1")) {
            bundle = ResourceBundle.getBundle("location.text", Locale.ENGLISH);
            view.printMessage(bundle.getString("msg.lang_changed"));
            logger.info("Language changed to English");
        } else if (choice.equals("2")) {
            bundle = ResourceBundle.getBundle("location.text", Locale.of("uk", "UA"));
            view.printMessage(bundle.getString("msg.lang_changed"));
            logger.info("Language changed to Ukrainian");
        } else {
            view.printMessage(bundle.getString("msg.invalid"));
        }
    }

    public void showInfo() {
        view.printMessage(bundle.getString("view.curr_shapes"));
        view.printShapes(model.getShapes());

        view.printMessage("\n" + bundle.getString("view.area_shapes") + " " + model.calcAreaAll());

        view.printMessage("\n" + bundle.getString("view.type_for_area"));

        String shapeType;
        while (true) {
            shapeType = sc.nextLine().trim();
            if (shapeType.equalsIgnoreCase("Rectangle") ||
                    shapeType.equalsIgnoreCase("Circle") ||
                    shapeType.equalsIgnoreCase("Triangle")) break;
            view.printMessage(bundle.getString("view.invalid_shape"));
        }
        view.printMessage("\n" + bundle.getString("view.type_area") + " " + model.calcAreaByType(shapeType));

        view.printMessage("\n" + bundle.getString("view.sorted_by_area"));
        view.printShapes(model.sortShapesByArea());

        view.printMessage("\n" + bundle.getString("view.sorted_by_color"));
        view.printShapes(model.sortShapesByColor());
    }

    public void menuSelection(int item) {
        try {
            String filePath = null;
            if (item == 1 || item == 2) {
                while (!isPathValid(filePath)) {
                    view.printMessage(bundle.getString("view.valid_path"));
                    filePath = sc.nextLine();
                }
            }

            switch (item) {
                case 1:
                    Shape[] loadedShapes = ShapeFileHandler.readFile(filePath);
                    if (loadedShapes != null) {
                        model.setShapes(loadedShapes);
                        logger.info("Loaded " + loadedShapes.length + " shapes");
                        view.printMessage(bundle.getString("view.shapes_loaded"));
                    }
                    break;
                case 2:
                    Shape[] shapesToExport = model.getShapes();
                    if (shapesToExport == null) {
                        logger.warning("Model is empty");
                        view.printMessage(bundle.getString("view.empty_model"));
                        shapesToExport = defaultShapes;
                    }
                    ShapeFileHandler.writeFile(filePath, shapesToExport);
                    logger.info("Exported " + shapesToExport.length + " shapes");
                    view.printMessage(bundle.getString("view.shapes_exported"));
                    break;
                case 3:
                    if (model.getShapes() == null) {
                        logger.warning("Model is empty");
                        view.printMessage(bundle.getString("view.load_needed"));
                        break;
                    }
                    logger.info("Loading " + model.getShapes().length + " shapes");
                    showInfo();
                    break;
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error processing menu", e);
            view.printMessage(bundle.getString("menu.error") + " " + e.getMessage());
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
            logger.log(Level.SEVERE, "Invalid file path", e);
            System.out.println("Invalid file path!");
            return false;
        }
    }
}
