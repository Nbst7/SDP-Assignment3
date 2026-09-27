# Elevator Dispatch System — Bridge + Adapter

Assignment 3: an elevator dispatch system that combines the **Bridge** pattern
(separating request type from motor hardware) with the **Adapter** pattern
(wrapping a genuinely incompatible legacy motor unit into the same
hardware-facing contract).

## Build & test (single command)

```bash
mvn clean test
```

## Run the demo

```bash
mvn clean package
java -jar target/elevator-dispatch.jar
```

## Structure

| Role | Class(es) |
|---|---|
| Abstraction | `ElevatorRequest` |
| Refined Abstraction | `PassengerRequest`, `FreightRequest` |
| Implementor | `MotorController` |
| Concrete Implementor (native) | `DigitalMotorController`, `HydraulicMotorController` |
| Adaptee (legacy, unmodified) | `elevator.legacy.AnalogMotorLegacyUnit` |
| Adapter | `AnalogMotorAdapter` |
| Required complexity module | `MotorControllerRegistry` (dynamic implementor selection, driven by `elevator-cars.properties`) |
| Client | `ElevatorDispatcher`, `Main` |

## Why Bridge alone isn't enough

Two independent axes of variation — request type (passenger/freight, and
whatever gets added later) and motor hardware (digital/hydraulic/legacy, and
whatever gets added later) — would multiply into one subclass per
combination without Bridge.

## Why Adapter alone isn't enough

`AnalogMotorLegacyUnit` cannot be a `MotorController` as-is: different method
name and parameters (`setVoltageLevel(int)` vs `moveToFloor(int)`, voltage
units vs floor numbers) and a different failure mechanism (integer status
codes vs a thrown exception). Bridge gives the Abstraction a stable
interface to depend on; Adapter is what lets the legacy hardware sit behind
that interface at all.

## Tests

`AnalogMotorAdapterTest` is the key test class: it mocks
`AnalogMotorLegacyUnit` and asserts that every one of its status codes
(`OK`, `OVERLOAD`, `STALLED`, `OUT_OF_RANGE`, and an unrecognized code) is
translated into the correct `MotorException.ErrorType` — nothing
legacy-specific leaks past the adapter. `PassengerRequestTest` and
`FreightRequestTest` mock `MotorController` directly to verify normal
delegation and exception propagation on the Abstraction side.
