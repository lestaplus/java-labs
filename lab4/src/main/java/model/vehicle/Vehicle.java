package model.vehicle;

import model.person.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class Vehicle<T extends Person> {
    protected final String model;
    protected final int capacity;
    protected T driver;
    protected final List<T> passengers = new ArrayList<>();

    public Vehicle(String model, int capacity) {
        this.model = model;
        this.capacity = capacity;
    }

    public int getAllHumansCount() {
        int count = (driver != null) ? 1 : 0;
        return count + passengers.size();
    }

    public void addPassenger(T p) {
        if (passengers.contains(p)) {
            throw new IllegalArgumentException("Passenger already exists");
        }
        if (getAllHumansCount() >= capacity) {
            throw new IllegalArgumentException("Too many passengers");
        }
        passengers.add(p);
    }

    public void removePassenger(T p) {
        if (!passengers.contains(p)) {
            throw new IllegalArgumentException("Passenger does not exist");
        }
        passengers.remove(p);
    }

    public String getModel() {
        return model;
    }

    public Person getDriver() {
        return driver;
    }

    public void addDriver(T driver) {
        if (driver == null) {
            throw new IllegalArgumentException("Driver cannot be null");
        }
        if (passengers.contains(driver)) {
            throw new IllegalArgumentException("Driver already exists as passenger");
        }
        if (passengers.size() == capacity) {
            throw new IllegalArgumentException("Too many passengers");
        }
        this.driver = driver;
    }

    public void removeDriver() {
        driver = null;
    }

    public int getCapacity() {
        return capacity;
    }

    public List<T> getPassengers() {
        return passengers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vehicle<?> vehicle = (Vehicle<?>) o;
        return capacity == vehicle.capacity &&
                Objects.equals(model, vehicle.model) &&
                Objects.equals(driver, vehicle.driver) &&
                Objects.equals(passengers, vehicle.passengers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(model, capacity, driver, passengers);
    }
}
