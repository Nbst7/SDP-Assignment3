package elevator;

public interface MotorController {
    void moveToFloor(int floor) throws MotorException;
    int getCurrentFloor();
}
