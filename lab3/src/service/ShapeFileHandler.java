package service;

import model.Shape;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ShapeFileHandler {
    private static final Logger logger = Logger.getLogger(ShapeFileHandler.class.getName());

    public static void writeFile(String filePath, Shape[] shapes) {
        logger.info("Start writing to file: " + filePath);
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filePath))) {
            out.writeObject(shapes);
            out.flush();
            logger.fine("Start writing " + shapes.length + " shapes");
            logger.info("Finish writing to file: " + filePath);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error writing to file: " + filePath, e);
        }
    }

    public static Shape[] readFile(String filePath) {
        logger.info("Start reading file: " + filePath);
        Shape[] shapes;
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filePath))) {
            shapes = (Shape[]) in.readObject();
            logger.fine("Finish reading " + shapes.length + " shapes");
            logger.info("Finish reading file: " + filePath);
            return shapes;
        } catch (IOException | ClassNotFoundException e) {
            logger.log(Level.SEVERE, "Error reading file: " + filePath, e);
            return null;
        }
    }
}
