package elevator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class FreightRequestTest {

    @Test
    void dispatch_delegatesToMotorController_whenLoadWithinLimit() throws MotorException {
        MotorController motor = mock(MotorController.class);
        FreightRequest request = new FreightRequest(motor, 1000);

        request.dispatch(4);

        verify(motor).moveToFloor(4);
    }

    @Test
    void dispatch_rejectsOverweightLoad_withoutTouchingMotor() {
        MotorController motor = mock(MotorController.class);
        FreightRequest request = new FreightRequest(motor, 5000);

        assertThrows(MotorException.class, () -> request.dispatch(4));
        verifyNoInteractions(motor);
    }
}
