package fi.arch26.triputils;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TripCostTest {
    private final TripCost calculator = new TripCost();

    @Test void calculatesAndRoundsCost() {
        assertEquals(new BigDecimal("7.30"), calculator.calcCost(100));
        assertEquals(new BigDecimal("0.07"), calculator.calcCost(1));
        assertEquals(new BigDecimal("0.37"), calculator.calcCost(5));
    }

    @Test void acceptsZeroDistance() {
        assertEquals(new BigDecimal("0.00"), calculator.calcCost(0));
    }

    @Test void rejectsNegativeDistance() {
        assertThrows(IllegalArgumentException.class, () -> calculator.calcCost(-1));
    }
}
