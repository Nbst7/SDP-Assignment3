package elevator;

public class HydraulicMotorController implements MotorController {
    private final int maxFloorsPerMove;
    private int currentFloor;
    public HydraulicMotorController(int maxFloorsPerMove) {
        this.maxFloorsPerMove = maxFloorsPerMove;
        this.currentFloor = 0;
    }
    @Override
    public void moveToFloor(int floor) throws MotorException {
        int distance = Math.abs(floor - currentFloor);
        if (distance > maxFloorsPerMove) {
            throw new MotorException(MotorException.ErrorType.STALLED,
                    "Hydraulic pressure insufficient to cross " + distance
                            + " floors in one move (limit " + maxFloorsPerMove + ")");
        }
        currentFloor = floor;
    }
    @Override
    public int getCurrentFloor() {
        return currentFloor;
    }
}
