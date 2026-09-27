package elevator;

public class PassengerRequest extends ElevatorRequest {
    private static final int DOOR_HOLD_MS = 5000;
    public PassengerRequest(MotorController motor) {
        super(motor);
    }
    @Override
    public void dispatch(int destinationFloor) throws MotorException {
        motor.moveToFloor(destinationFloor);
        holdDoors(DOOR_HOLD_MS);
    }
    private void holdDoors(int ms) {
    }
}
