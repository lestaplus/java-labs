package model.vehicle;

import model.person.Person;

public class Taxi extends Car<Person> {
    public Taxi(String model, int capacity) {
        super(model, capacity);
    }
}
