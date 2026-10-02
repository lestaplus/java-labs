package model.vehicle;

import model.person.Policeman;

public class PoliceCar extends Car<Policeman> {
    public PoliceCar(String model, int capacity) {
        super(model, capacity);
    }
}
