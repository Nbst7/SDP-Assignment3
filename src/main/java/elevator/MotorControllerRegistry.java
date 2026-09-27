package elevator;

import elevator.legacy.AnalogMotorLegacyUnit;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class MotorControllerRegistry {
    private final Map<String, MotorController> controllers = new HashMap<>();
    public MotorControllerRegistry(String configResourcePath) {
        Properties props = new Properties();
        try (InputStream in = getClass().getClassLoader().getResourceAsStream(configResourcePath)) {
            if (in == null) {
                throw new IllegalStateException("Config not found on classpath: " + configResourcePath);
            }
            props.load(in);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load elevator car config", e);
        }
        for (String carId : props.stringPropertyNames()) {
            controllers.put(carId, createController(props.getProperty(carId).trim()));
        }
    }
    private MotorController createController(String type) {
        switch (type) {
            case "digital":
                return new DigitalMotorController(0, 50);
            case "hydraulic":
                return new HydraulicMotorController(6);
            case "analog-legacy":
                return new AnalogMotorAdapter(new AnalogMotorLegacyUnit(1000), 20);
            default:
                throw new IllegalArgumentException("Unknown motor controller type: " + type);
        }
    }
    public MotorController resolve(String carId) {
        MotorController controller = controllers.get(carId);
        if (controller == null) {
            throw new IllegalArgumentException("No motor controller configured for car: " + carId);
        }
        return controller;
    }
}
