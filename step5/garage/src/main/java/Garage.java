package fi.arch26.taxicars;

import java.util.HashMap;
import fi.arch26.triputils.TripCost;

/*
TODO:
* This is the home for all the cars in our Garage.
Add a HashMap that store the cars. Key: plate, hash members: model, brand, motor etc
Add a reporting method
Add a trip cost calculation
*/

public class Garage {

    private TripCost tripCost = new TripCost();

    protected HashMap<String,Car> carMap = new HashMap<String,Car>();
    public void addCar(Car car) {
        carMap.put(car.plate, car);
    }

    public void reportCars() {
        for(String plate: carMap.keySet()) {
            Car car = carMap.get(plate);
            System.out.println("Plate: " + car.plate + " - Model: " + car.model);
        }
    }

    public void reportTrip(String plate, int distance) {
        Car car = carMap.get(plate);
        if (car != null) {
            double cost = tripCost.calcCost(car.model, distance);
            System.out.println("Trip with model "+car.model+" and distance "+distance+" will cost "+cost);
        } else {
            System.out.println("Car not found: " + plate);
        }
    }
}

