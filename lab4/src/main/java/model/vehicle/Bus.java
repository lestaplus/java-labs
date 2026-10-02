package model.vehicle;

import model.person.Person;

public class Bus extends Vehicle<Person> {
    public Bus(String model, int capacity) {
        super(model, capacity);
    }
}
