package elevator;

public class FreightRequest extends ElevatorRequest {
    private static final double MAX_LOAD_KG = 2000.0;
    private final double loadKg;
    public FreightRequest(MotorController motor, double loadKg) {
        super(motor);
        this.loadKg = loadKg;
    }
    @Override
    public void dispatch(int destinationFloor) throws MotorException {
        if (loadKg > MAX_LOAD_KG) {
            throw new MotorException(MotorException.ErrorType.OVERLOAD,
                    "Freight load " + loadKg + "kg exceeds max capacity " + MAX_LOAD_KG + "kg");
        }
        motor.moveToFloor(destinationFloor);
    }
}
