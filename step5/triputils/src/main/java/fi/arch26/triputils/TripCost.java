package fi.arch26.triputils;

public class TripCost {

    final double multiplier = 0.073;

    public double calcCost(String model, int distance) {
        double cost = distance * multiplier;
        return cost;
    }
}
