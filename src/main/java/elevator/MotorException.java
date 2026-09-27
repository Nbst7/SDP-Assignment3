package elevator;

public class MotorException extends Exception {
    public enum ErrorType {
        OVERLOAD,
        STALLED,
        OUT_OF_RANGE,
        UNKNOWN
    }
    private final ErrorType errorType;
    public MotorException(ErrorType errorType, String message) {
        super(message);
        this.errorType = errorType;
    }
    public MotorException(ErrorType errorType, String message, Throwable cause) {
        super(message, cause);
        this.errorType = errorType;
    }
    public ErrorType getErrorType() {
        return errorType;
    }
}
