package elevator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class PassengerRequestTest {

    @Test
    void dispatch_delegatesToMotorController() throws MotorException {
        MotorController motor = mock(MotorController.class);
        PassengerRequest request = new PassengerRequest(motor);

        request.dispatch(7);

        verify(motor).moveToFloor(7);
    }

    @Test
    void dispatch_propagatesMotorException() throws MotorException {
        MotorController motor = mock(MotorController.class);
        doThrow(new MotorException(MotorException.ErrorType.STALLED, "stuck"))
                .when(motor).moveToFloor(anyInt());
        PassengerRequest request = new PassengerRequest(motor);

        assertThrows(MotorException.class, () -> request.dispatch(2));
    }
}
