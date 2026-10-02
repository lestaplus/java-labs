package model.vehicle;

import model.person.Fireman;

public class FireTruck extends Car<Fireman> {
    public FireTruck(String model, int capacity) {
        super(model, capacity);
    }
}
