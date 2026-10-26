package taxicars;
import java.util.HashMap;

class Garage {
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
}
