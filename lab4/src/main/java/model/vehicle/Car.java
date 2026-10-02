package model.vehicle;

import model.person.Person;

public class Car<T extends Person> extends Vehicle<T> {
    public Car(String model, int capacity) {
        super(model, capacity);
    }
}
