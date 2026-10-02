import model.person.Fireman;
import model.person.Person;
import model.person.Policeman;
import model.vehicle.Bus;
import model.vehicle.Car;
import model.vehicle.FireTruck;
import model.vehicle.PoliceCar;
import road.Road;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoadTest {
    private Road road;

    private Car<Person> car;
    private Bus bus;
    private PoliceCar policeCar;
    private FireTruck fireTruck;

    @BeforeEach
    void setUp() {
        road = new Road();

        car = new Car<>("Chevrolet", 4);
        policeCar = new PoliceCar("BMW", 2);
        fireTruck = new FireTruck("Ford", 6);
        bus = new Bus("Bogdan", 20);

        car.addDriver(new Person("Anatolii"));
        bus.addDriver(new Person("Vladislav"));
        policeCar.addDriver(new Policeman("Viktoria"));
        fireTruck.addDriver(new Fireman("Volodymyr"));
    }

    @Test
    void addCarsOnRoad_ShouldAddCars() {
        road.addCarToRoad(car);
        road.addCarToRoad(bus);
        road.addCarToRoad(policeCar);
        road.addCarToRoad(fireTruck);

        assertEquals(4, road.carsOnRoad.size());
        assertTrue(road.carsOnRoad.contains(car));
        assertTrue(road.carsOnRoad.contains(bus));
        assertTrue(road.carsOnRoad.contains(policeCar));
        assertTrue(road.carsOnRoad.contains(fireTruck));
    }

    @Test
    void addDuplicateVehicle_ShouldThrowException() {
        road.addCarToRoad(fireTruck);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            road.addCarToRoad(fireTruck);
        });
        assertEquals(1, road.carsOnRoad.size());
        assertEquals("Vehicle already exists", exception.getMessage());
    }

    @Test
    void countHumansInCars_ShouldCountHumansInCars() {
        car.addPassenger(new Policeman("Dmytro"));

        bus.addPassenger(new Person("Anastasia"));
        bus.addPassenger(new Fireman("Andriy"));
        bus.addPassenger(new Person("Oleksandr"));

        fireTruck.addPassenger(new Fireman("Olena"));

        road.addCarToRoad(car);
        road.addCarToRoad(bus);
        road.addCarToRoad(policeCar);
        road.addCarToRoad(fireTruck);

        assertEquals(9, road.getCountOfHumans());
    }

    @Test
    void addEmptyVehicle_ShouldThrowException() {
        car.removeDriver();

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            road.addCarToRoad(car);
        });

        assertEquals(0, road.carsOnRoad.size());
        assertEquals("Driver is null", exception.getMessage());
    }
}
