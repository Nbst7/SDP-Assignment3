package elevator;

public class Main {
    public static void main(String[] args) throws Exception {
        MotorControllerRegistry registry = new MotorControllerRegistry("elevator-cars.properties");
        ElevatorDispatcher dispatcher = new ElevatorDispatcher(registry);

        dispatcher.dispatchPassenger("car-A", 5);
        System.out.println("car-A (digital) now at floor " + registry.resolve("car-A").getCurrentFloor());

        dispatcher.dispatchFreight("car-B", 3, 500);
        System.out.println("car-B (hydraulic) now at floor " + registry.resolve("car-B").getCurrentFloor());

        dispatcher.dispatchPassenger("car-C", 2);
        dispatcher.dispatchPassenger("car-C", 4);
        System.out.println("car-C (adapted analog legacy) now at floor " + registry.resolve("car-C").getCurrentFloor());
    }
}
