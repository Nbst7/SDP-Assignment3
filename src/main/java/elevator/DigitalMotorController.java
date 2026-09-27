package elevator;

public class DigitalMotorController implements MotorController {

    private final int minFloor;
    private final int maxFloor;
    private int currentFloor;

    public DigitalMotorController(int minFloor, int maxFloor) {
        this.minFloor = minFloor;
        this.maxFloor = maxFloor;
        this.currentFloor = minFloor;
    }
    @Override
    public void moveToFloor(int floor) throws MotorException {
        if (floor < minFloor || floor > maxFloor) {
            throw new MotorException(MotorException.ErrorType.OUT_OF_RANGE,
                    "Floor " + floor + " is outside digital motor range [" + minFloor + ", " + maxFloor + "]");
        }
        currentFloor = floor;
    }
    @Override
    public int getCurrentFloor() {
        return currentFloor;
    }
}
