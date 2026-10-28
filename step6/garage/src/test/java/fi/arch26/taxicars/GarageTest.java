package fi.arch26.taxicars;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GarageTest {
    @Test void storesAndFindsCars() {
        Garage garage = new Garage();
        Car car = new Car("Audi A3", "ABC-123");
        garage.addCar(car);
        assertSame(car, garage.findCar("ABC-123").orElseThrow());
        assertTrue(garage.findCar("unknown").isEmpty());
    }

    @Test void rejectsDuplicatesWithoutReplacingOriginal() {
        Garage garage = new Garage();
        Car original = new Car("Audi A3", "ABC-123");
        garage.addCar(original);
        assertThrows(IllegalArgumentException.class,
                () -> garage.addCar(new Car("Other", "ABC-123")));
        assertSame(original, garage.findCar("ABC-123").orElseThrow());
    }

    @Test void rejectsInvalidCars() {
        Garage garage = new Garage();
        assertThrows(NullPointerException.class, () -> garage.addCar(null));
        for (String invalid : new String[] {null, "", "  "}) {
            assertThrows(IllegalArgumentException.class, () -> new Car(invalid, "ABC-123"));
            assertThrows(IllegalArgumentException.class, () -> new Car("Audi A3", invalid));
        }
    }

    @Test void rejectsNegativeTripsEvenForUnknownCars() {
        Garage garage = new Garage();
        garage.addCar(new Car("Audi A3", "ABC-123"));
        assertThrows(IllegalArgumentException.class, () -> garage.reportTrip("ABC-123", -1));
        assertThrows(IllegalArgumentException.class, () -> garage.reportTrip("unknown", -1));
    }

    @Test void reportsTripsAndUnknownPlates() {
        Garage garage = new Garage();
        garage.addCar(new Car("Audi A3", "ABC-123"));
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream original = System.out;
        try (PrintStream captured = new PrintStream(output, true, StandardCharsets.UTF_8)) {
            System.setOut(captured);
            garage.reportCars();
            garage.reportTrip("ABC-123", 100);
            garage.reportTrip("ABC-123", 0);
            garage.reportTrip("unknown", 100);
        } finally {
            System.setOut(original);
        }
        String text = output.toString(StandardCharsets.UTF_8);
        assertTrue(text.contains("Plate: ABC-123 - Model: Audi A3"));
        assertTrue(text.contains("100 km will cost 7.30"));
        assertTrue(text.contains("0 km will cost 0.00"));
        assertTrue(text.contains("Car not found: unknown"));
    }
}
