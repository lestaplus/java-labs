import model.person.Fireman;
import model.person.Person;
import model.person.Policeman;
import model.vehicle.Bus;
import model.vehicle.Car;
import model.vehicle.FireTruck;
import model.vehicle.PoliceCar;
import road.Road;

public class Main {
    public static void main(String[] args) {
        Road road = new Road();

        Car<Person> car = new Car<>("Chevrolet", 4);
        PoliceCar policeCar = new PoliceCar("BMW", 2);
        FireTruck fireTruck = new FireTruck("Ford", 6);
        Bus bus = new Bus("Bogdan", 20);

        car.addDriver(new Person("Anatolii"));
        car.addPassenger(new Policeman("Dmytro"));

        bus.addDriver(new Person("Vladislav"));
        bus.addPassenger(new Person("Anastasia"));
        bus.addPassenger(new Fireman("Andriy"));
        bus.addPassenger(new Person("Oleksandr"));

        policeCar.addDriver(new Policeman("Viktoria"));

        fireTruck.addDriver(new Fireman("Volodymyr"));
        fireTruck.addPassenger(new Fireman("Olena"));

        road.addCarToRoad(car);
        road.addCarToRoad(policeCar);
        road.addCarToRoad(fireTruck);
        road.addCarToRoad(bus);

        System.out.println("Cars on the road: " + road.carsOnRoad.size());
        System.out.println("Number of people on the road: " + road.getCountOfHumans());
    }
}
