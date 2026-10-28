package fi.arch26.taxicars;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import fi.arch26.triputils.TripCost;

public class Garage {
    private final TripCost tripCost = new TripCost();
    private final Map<String, Car> cars = new LinkedHashMap<>();

    public void addCar(Car car) {
        Objects.requireNonNull(car, "Car must not be null");
        if (cars.putIfAbsent(car.getPlate(), car) != null) {
            throw new IllegalArgumentException("Duplicate plate: " + car.getPlate());
        }
    }

    public Optional<Car> findCar(String plate) {
        return Optional.ofNullable(cars.get(plate));
    }

    public void reportCars() {
        for (Car car : cars.values()) {
            System.out.println("Plate: " + car.getPlate() + " - Model: " + car.getModel());
        }
    }

    public void reportTrip(String plate, int distance) {
        if (distance < 0) {
            throw new IllegalArgumentException("Distance must not be negative");
        }
        findCar(plate).ifPresentOrElse(car -> {
            var cost = tripCost.calcCost(distance);
            System.out.println("Trip with model " + car.getModel() + " and distance "
                    + distance + " km will cost " + cost);
        }, () -> System.out.println("Car not found: " + plate));
    }
}
