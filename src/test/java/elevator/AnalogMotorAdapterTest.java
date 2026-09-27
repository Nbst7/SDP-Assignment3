package elevator;

import elevator.legacy.AnalogMotorLegacyUnit;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AnalogMotorAdapterTest {
    @Test
    void moveToFloor_succeeds_whenLegacyReturnsOk() throws MotorException {
        AnalogMotorLegacyUnit legacy = mock(AnalogMotorLegacyUnit.class);
        when(legacy.setVoltageLevel(anyInt())).thenReturn(AnalogMotorLegacyUnit.STATUS_OK);
        AnalogMotorAdapter adapter = new AnalogMotorAdapter(legacy, 20);

        assertDoesNotThrow(() -> adapter.moveToFloor(3));
        verify(legacy).setVoltageLevel(60); // 3 floors * 20 volts/floor
    }
    @Test
    void moveToFloor_translatesOverloadCode_toMotorException() {
        AnalogMotorLegacyUnit legacy = mock(AnalogMotorLegacyUnit.class);
        when(legacy.setVoltageLevel(anyInt())).thenReturn(AnalogMotorLegacyUnit.STATUS_OVERLOAD);
        AnalogMotorAdapter adapter = new AnalogMotorAdapter(legacy, 20);

        MotorException ex = assertThrows(MotorException.class, () -> adapter.moveToFloor(5));
        assertEquals(MotorException.ErrorType.OVERLOAD, ex.getErrorType());
    }
    @Test
    void moveToFloor_translatesStalledCode_toMotorException() {
        AnalogMotorLegacyUnit legacy = mock(AnalogMotorLegacyUnit.class);
        when(legacy.setVoltageLevel(anyInt())).thenReturn(AnalogMotorLegacyUnit.STATUS_STALLED);
        AnalogMotorAdapter adapter = new AnalogMotorAdapter(legacy, 20);

        MotorException ex = assertThrows(MotorException.class, () -> adapter.moveToFloor(2));
        assertEquals(MotorException.ErrorType.STALLED, ex.getErrorType());
    }
    @Test
    void moveToFloor_translatesOutOfRangeCode_toMotorException() {
        AnalogMotorLegacyUnit legacy = mock(AnalogMotorLegacyUnit.class);
        when(legacy.setVoltageLevel(anyInt())).thenReturn(AnalogMotorLegacyUnit.STATUS_OUT_OF_RANGE);
        AnalogMotorAdapter adapter = new AnalogMotorAdapter(legacy, 20);

        MotorException ex = assertThrows(MotorException.class, () -> adapter.moveToFloor(100));
        assertEquals(MotorException.ErrorType.OUT_OF_RANGE, ex.getErrorType());
    }
    @Test
    void moveToFloor_translatesUnknownCode_toUnknownMotorException() {
        AnalogMotorLegacyUnit legacy = mock(AnalogMotorLegacyUnit.class);
        when(legacy.setVoltageLevel(anyInt())).thenReturn(99);
        AnalogMotorAdapter adapter = new AnalogMotorAdapter(legacy, 20);

        MotorException ex = assertThrows(MotorException.class, () -> adapter.moveToFloor(1));
        assertEquals(MotorException.ErrorType.UNKNOWN, ex.getErrorType());
    }
    @Test
    void getCurrentFloor_convertsVoltageBackToFloor() {
        AnalogMotorLegacyUnit legacy = mock(AnalogMotorLegacyUnit.class);
        when(legacy.readVoltageLevel()).thenReturn(80);
        AnalogMotorAdapter adapter = new AnalogMotorAdapter(legacy, 20);

        assertEquals(4, adapter.getCurrentFloor());
    }
}
