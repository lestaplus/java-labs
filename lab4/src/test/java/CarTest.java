import model.person.Person;
import model.vehicle.Car;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CarTest {
    private Car<Person> car;
    private Person passenger;
    private Person driver;

    @BeforeEach
    void setUp() {
        car = new Car<>("Chevrolet", 4);
        driver = new Person("Timur");
        passenger = new Person("Dmytro");
    }

    @Test
    void addPassenger_ShouldIncreasePassengersCount() {
        car.addPassenger(passenger);

        assertEquals(1,  car.getAllHumansCount());
        assertTrue(car.getPassengers().contains(passenger));
    }

    @Test
    void addDuplicatePassenger_ShouldThrowException() {
        car.addPassenger(passenger);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            car.addPassenger(passenger);
        });
        assertEquals(1,  car.getAllHumansCount());
        assertEquals("Passenger already exists", exception.getMessage());
    }

    @Test
    void removePassenger_ShouldDecreasePassengersCount() {
        car.addPassenger(passenger);
        car.removePassenger(passenger);

        assertEquals(0, car.getAllHumansCount());
        assertFalse(car.getPassengers().contains(passenger));
    }

    @Test
    void overflowCapacity_ShouldThrowException() {
        car.addDriver(driver);
        for (int i = 0; i < 3; i++) {
            car.addPassenger(new Person("Passenger" + i));
        }

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            car.addPassenger(new Person("Passenger 3"));
        });
        assertEquals(4, car.getAllHumansCount());
        assertEquals("Too many passengers", exception.getMessage());
    }

    @Test
    void removeMissingPassenger_ShouldThrowException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            car.removePassenger(passenger);
        });
        assertEquals(0, car.getAllHumansCount());
        assertEquals("Passenger does not exist", exception.getMessage());
    }
}
