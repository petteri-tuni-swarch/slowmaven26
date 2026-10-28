# Taxi garage demo

Requires JDK 17 or newer and Maven 3.9 or newer.

Build both modules and run all tests from this directory:

```sh
mvn clean verify
```

The reactor builds `triputils` from the sibling source before `garage`; no manual
installation of the library is required. `verify` also launches the assembled JAR
in a separate JVM to check its manifest, entry point, and bundled dependencies.
For unit tests only, run `mvn test`.

Run the demo:

```sh
java -jar garage/target/taxicars-1.5.0-jar-with-dependencies.jar
```

Cars require nonblank model and plate values. Plates are exact, case-sensitive
identifiers. Duplicate plates are rejected without replacing the original car.
Unknown plates produce a “Car not found” report.

Distances are nonnegative whole kilometres. All cars use a fixed rate of 0.073
currency units per kilometre; this demo does not assign a specific currency.
Costs use decimal arithmetic and are rounded once to two decimal places with
HALF_UP rounding. For example, 100 km costs 7.30 and 5 km costs 0.37.

The library API is now `BigDecimal TripCost.calcCost(int distanceKm)`; model is
no longer an argument because the rate does not depend on the model.

Build artifacts under `target/` are ignored. Garage sources follow their Java
package path under `garage/src/main/java/fi/arch26/taxicars/`.
