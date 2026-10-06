package service;

import model.Shape;

import java.io.*;

public class ShapeFileHandler {
    public static void writeFile(String filePath, Shape[] shapes) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filePath))) {
            out.writeObject(shapes);
            out.flush();
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    public static Shape[] readFile(String filePath) {
        Shape[] shapes;
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filePath))) {
            shapes = (Shape[]) in.readObject();
            return shapes;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error reading file: " + e.getMessage());
            return null;
        }
    }
}
