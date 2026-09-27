package elevator;

public class ElevatorDispatcher {
    private final MotorControllerRegistry registry;
    public ElevatorDispatcher(MotorControllerRegistry registry) {
        this.registry = registry;
    }
    public void dispatchPassenger(String carId, int destinationFloor) throws MotorException {
        MotorController motor = registry.resolve(carId);
        new PassengerRequest(motor).dispatch(destinationFloor);
    }
    public void dispatchFreight(String carId, int destinationFloor, double loadKg) throws MotorException {
        MotorController motor = registry.resolve(carId);
        new FreightRequest(motor, loadKg).dispatch(destinationFloor);
    }
}
