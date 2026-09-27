package elevator;
import elevator.legacy.AnalogMotorLegacyUnit;

public class AnalogMotorAdapter implements MotorController {
    private final AnalogMotorLegacyUnit legacyUnit;
    private final int voltsPerFloor;

    public AnalogMotorAdapter(AnalogMotorLegacyUnit legacyUnit, int voltsPerFloor) {
        this.legacyUnit = legacyUnit;
        this.voltsPerFloor = voltsPerFloor;
    }
    @Override
    public void moveToFloor(int floor) throws MotorException {
        int level = floor * voltsPerFloor;
        int status = legacyUnit.setVoltageLevel(level);
        translate(status, floor);
    }
    private void translate(int status, int floor) throws MotorException {
        switch (status) {
            case AnalogMotorLegacyUnit.STATUS_OK:
                return;
            case AnalogMotorLegacyUnit.STATUS_OVERLOAD:
                throw new MotorException(MotorException.ErrorType.OVERLOAD,
                        "Analog unit reported overload while moving to floor " + floor);
            case AnalogMotorLegacyUnit.STATUS_STALLED:
                throw new MotorException(MotorException.ErrorType.STALLED,
                        "Analog unit stalled while moving to floor " + floor);
            case AnalogMotorLegacyUnit.STATUS_OUT_OF_RANGE:
                throw new MotorException(MotorException.ErrorType.OUT_OF_RANGE,
                        "Analog unit reports floor " + floor + " is outside its voltage range");
            default:
                throw new MotorException(MotorException.ErrorType.UNKNOWN,
                        "Analog unit returned unrecognized status code " + status);
        }
    }
    @Override
    public int getCurrentFloor() {
        return legacyUnit.readVoltageLevel() / voltsPerFloor;
    }
}
