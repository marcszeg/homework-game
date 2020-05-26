package homeworkGame.state;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SlotTest {

    @Test
    void of() {
        assertThrows(IllegalArgumentException.class, () -> Slot.of(-1));
        assertEquals(Slot.EMPTY, Slot.of(0));
        assertEquals(Slot.BLUE, Slot.of(1));
        assertEquals(Slot.RED, Slot.of(2));
    }

}