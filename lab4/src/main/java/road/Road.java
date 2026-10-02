package road;

import model.vehicle.Vehicle;

import java.util.ArrayList;
import java.util.List;

public class Road {
    public List<Vehicle<?>> carsOnRoad = new ArrayList<>();

    public void addCarToRoad(Vehicle<?> vehicle) {
        if (carsOnRoad.contains(vehicle)) {
            throw new IllegalArgumentException("Vehicle already exists");
        }
        if (vehicle.getDriver() == null) {
            throw new IllegalArgumentException("Driver is null");
        }
        carsOnRoad.add(vehicle);
    }

    public int getCountOfHumans() {
        int count = 0;
        for (Vehicle<?> vehicle : carsOnRoad) {
            count += vehicle.getAllHumansCount();
        }
        return count;
    }
}
