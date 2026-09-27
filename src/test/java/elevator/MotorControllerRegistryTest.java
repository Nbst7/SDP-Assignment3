package elevator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MotorControllerRegistryTest {

    @Test
    void resolve_returnsDifferentConcreteTypes_perConfiguredCar() {
        MotorControllerRegistry registry = new MotorControllerRegistry("elevator-cars.properties");

        assertInstanceOf(DigitalMotorController.class, registry.resolve("car-A"));
        assertInstanceOf(HydraulicMotorController.class, registry.resolve("car-B"));
        assertInstanceOf(AnalogMotorAdapter.class, registry.resolve("car-C"));
    }

    @Test
    void resolve_throwsForUnknownCar() {
        MotorControllerRegistry registry = new MotorControllerRegistry("elevator-cars.properties");

        assertThrows(IllegalArgumentException.class, () -> registry.resolve("car-Z"));
    }
}
