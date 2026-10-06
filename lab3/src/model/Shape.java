package model;

import java.io.Serializable;

public abstract class Shape implements Drawable, Serializable {
    private final String shapeColor;

    public Shape(String shapeColor) {
        this.shapeColor = shapeColor;
    }

    public abstract double calcArea();

    public String getShapeColor() {
        return shapeColor;
    }

    @Override
    public String toString() {
        return "class=" + this.getClass().getSimpleName() + ", shapeColor=" + shapeColor;
    }
}
