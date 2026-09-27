package elevator.legacy;

public class AnalogMotorLegacyUnit {
    public static final int STATUS_OK = 0;
    public static final int STATUS_OVERLOAD = 1;
    public static final int STATUS_STALLED = 2;
    public static final int STATUS_OUT_OF_RANGE = 3;

    private final int maxVoltage;
    private int currentVoltage;

    public AnalogMotorLegacyUnit(int maxVoltage) {
        this.maxVoltage = maxVoltage;
        this.currentVoltage = 0;
    }
    public int setVoltageLevel(int level) {
        if (level < 0 || level > maxVoltage) {
            return STATUS_OUT_OF_RANGE;
        }
        if (Math.abs(level - currentVoltage) > 40) {
            return STATUS_OVERLOAD;
        }
        if (level == currentVoltage) {
            return STATUS_STALLED;
        }
        currentVoltage = level;
        return STATUS_OK;
    }
    public int readVoltageLevel() {
        return currentVoltage;
    }
}
