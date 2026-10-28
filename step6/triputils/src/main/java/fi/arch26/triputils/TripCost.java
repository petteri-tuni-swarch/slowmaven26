package fi.arch26.triputils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class TripCost {
    private static final BigDecimal RATE_PER_KM = new BigDecimal("0.073");

    /** Returns cost in currency units, rounded to two decimals using HALF_UP. */
    public BigDecimal calcCost(int distanceKm) {
        if (distanceKm < 0) {
            throw new IllegalArgumentException("Distance must not be negative");
        }
        return RATE_PER_KM.multiply(BigDecimal.valueOf(distanceKm))
                .setScale(2, RoundingMode.HALF_UP);
    }
}
