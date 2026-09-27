package elevator;

public abstract class ElevatorRequest {
    protected final MotorController motor;
    protected ElevatorRequest(MotorController motor) {
        this.motor = motor;
    }
    public abstract void dispatch(int destinationFloor) throws MotorException;
    public int getCurrentFloor() {
        return motor.getCurrentFloor();
    }
}
