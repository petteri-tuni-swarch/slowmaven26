package fi.arch26.taxicars;

public class App {
	public static void main() {
		System.out.println("Step 3a - adding more files for the application");

		Garage garage = new Garage();
		Car car = new Car("Audi A3", "ABC-123");
		garage.addCar(car);
		garage.reportCars();

		garage.reportTrip("ABC-123", 100);
		garage.reportTrip("ABC-12", 100);
	}
}

