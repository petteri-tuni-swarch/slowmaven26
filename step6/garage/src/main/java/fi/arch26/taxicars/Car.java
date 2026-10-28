package fi.arch26.taxicars;

public final class Car {
    private final String model;
    private final String plate;

    public Car(String model, String plate) {
        if (model == null || model.isBlank() || plate == null || plate.isBlank()) {
            throw new IllegalArgumentException("Model and plate must not be blank");
        }
        this.model = model;
        this.plate = plate;
    }

    public String getModel() {
        return model;
    }

    public String getPlate() {
        return plate;
    }
}
