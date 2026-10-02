import model.person.Fireman;
import model.person.Person;
import model.person.Policeman;
import model.vehicle.Bus;
import model.vehicle.FireTruck;
import model.vehicle.PoliceCar;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GenericsTest {
    private PoliceCar policeCar;
    private FireTruck fireTruck;
    private Bus bus;

    private Person person;
    private Policeman policeman;
    private Fireman fireman;

    @BeforeEach
    void setUp() {
        policeCar = new PoliceCar("BMW", 2);
        fireTruck = new FireTruck("Ford", 6);
        bus = new Bus("Bogdan", 20);

        person = new Person("Anatolii");
        policeman = new Policeman("Oleksiy");
        fireman = new Fireman("Evgeniy");
    }

    @Test
    void addPolicemanToPoliceCar_ShouldIncreasePassengersCount() {
        policeCar.addPassenger(policeman);

        assertEquals(1, policeCar.getAllHumansCount());
        assertTrue(policeCar.getPassengers().contains(policeman));
    }

    @Test
    void addFiremanToFireTruck_ShouldIncreasePassengersCount() {
        fireTruck.addPassenger(fireman);

        assertEquals(1, fireTruck.getAllHumansCount());
        assertTrue(fireTruck.getPassengers().contains(fireman));
    }

    @Test
    void addAnybodyToBus_ShouldAddDriverAndIncreasePassengersCount() {
        bus.addDriver(person);
        bus.addPassenger(policeman);
        bus.addPassenger(fireman);

        List<Person> passengers = bus.getPassengers();

        assertEquals(person, bus.getDriver());
        assertEquals(2, bus.getPassengers().size());

        assertTrue(passengers.contains(policeman));
        assertTrue(passengers.contains(fireman));
    }
}
