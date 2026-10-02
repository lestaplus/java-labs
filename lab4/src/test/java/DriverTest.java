import model.person.Fireman;
import model.person.Person;
import model.vehicle.Taxi;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class DriverTest {
    private Taxi taxi;
    private Person driver;

    @BeforeEach
    void setUp() {
        taxi = new Taxi("Toyota", 4);
        driver = new Person("Andriy");
    }

    @Test
    void countOnlyPassengers_ShouldReturnPassengersWithoutDriver() {
        taxi.addDriver(driver);

        Person passenger = new Fireman("Evgeniy");
        taxi.addPassenger(passenger);

        assertEquals(1, taxi.getPassengers().size());
        assertTrue(taxi.getPassengers().contains(passenger));
        assertFalse(taxi.getPassengers().contains(taxi.getDriver()));
    }

    @Test
    void countDriver_ShouldReturnPassengersCountWithDriver() {
        taxi.addDriver(driver);

        assertEquals(1, taxi.getAllHumansCount());
        assertEquals(driver, taxi.getDriver());
    }

    @Test
    void addNullDriver_ShouldThrowException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            taxi.addDriver(null);
        });
        assertEquals("Driver cannot be null", exception.getMessage());
        assertNull(taxi.getDriver());
    }
}
