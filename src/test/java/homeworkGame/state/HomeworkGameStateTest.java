package homeworkGame.state;

import org.checkerframework.common.value.qual.StaticallyExecutable;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HomeworkGameStateTest {

    @Test
    void testOneArgConstructor_InvalidArg() {
        assertThrows(IllegalArgumentException.class, () -> new HomeworkGameState(null));
        assertThrows(IllegalArgumentException.class, () -> new HomeworkGameState(new int[][] {
                {2, 1},
                {0, 0}}));
        assertThrows(IllegalArgumentException.class, () -> new HomeworkGameState(new int[][] {
                {2, 0},
                {2},
                {1, 1, 1,},
                {0},
                {2, 2, 2, 2, 2}}));
        assertThrows(IllegalArgumentException.class, () -> new HomeworkGameState(new int[][] {
                {0, 1, 2, 3, 4},
                {1, 2, 3, 4, 5},
                {2, 3, 4, 5, 6},
                {3, 4, 5, 6, 7},
                {4, 5, 6, 7, 8}}));
    }

    @Test
    void testOneArgConstructor_ValidArg() {
        int[][] a = new int[][] {
                {2, 2, 2, 2, 1},
                {2, 2, 2, 2, 2},
                {2, 2, 1, 2, 2},
                {2, 2, 2, 2, 2},
                {1, 2, 2, 2, 2}
        };
        HomeworkGameState state = new HomeworkGameState(a);
        assertArrayEquals(new Slot[][] {
                {Slot.RED, Slot.RED, Slot.RED, Slot.RED, Slot.BLUE},
                {Slot.RED, Slot.RED, Slot.RED, Slot.RED, Slot.RED},
                {Slot.RED, Slot.RED, Slot.BLUE, Slot.RED, Slot.RED},
                {Slot.RED, Slot.RED, Slot.RED, Slot.RED, Slot.RED},
                {Slot.BLUE, Slot.RED, Slot.RED, Slot.RED, Slot.RED}
        }, state.getBoard());
    }

    @Test
    void isBlueWon() {
        assertFalse(new HomeworkGameState().isBlueWon());
        assertTrue(new HomeworkGameState(new int[][] {
                {0, 0, 0, 0, 1},
                {2, 0, 0, 1, 0},
                {0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0},
                {0, 1, 0, 0, 0}}).isBlueWon());
        assertFalse(new HomeworkGameState(new int[][] {
                {0, 0, 0, 2, 1},
                {2, 0, 0, 1, 0},
                {0, 0, 0, 0, 0},
                {0, 2, 0, 0, 0},
                {0, 1, 0, 0, 0}}).isBlueWon());
    }

    @Test
    void isRedWon() {
        assertFalse(new HomeworkGameState().isRedWon());
        assertTrue(new HomeworkGameState(new int[][] {
                {1, 0, 1, 0, 1},
                {2, 0, 0, 0, 0},
                {0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0}}).isRedWon());
        assertFalse(new HomeworkGameState(new int[][] {
                {0, 0, 0, 2, 1},
                {2, 0, 0, 1, 0},
                {0, 0, 0, 0, 0},
                {0, 2, 0, 0, 0},
                {0, 1, 0, 0, 0}}).isRedWon());
    }

    @Test
    void canMove() {
        assertTrue(new HomeworkGameState(new int[][] {
                {0, 0, 0, 2, 1},
                {2, 0, 0, 1, 0},
                {0, 0, 0, 0, 0},
                {0, 2, 0, 0, 0},
                {0, 1, 0, 0, 0}}).canMove(0, 4, 0, 3));
        assertFalse(new HomeworkGameState(new int[][] {
                {0, 0, 0, 2, 1},
                {2, 0, 0, 1, 0},
                {0, 0, 0, 0, 0},
                {0, 2, 0, 0, 0},
                {0, 1, 0, 0, 0}}).canMove(1, 3, 2, 3));
    }

    @Test
    void moveDisc() {
        HomeworkGameState stateRed = new HomeworkGameState(new int[][] {
                {0, 0, 0, 2, 1},
                {2, 0, 0, 1, 0},
                {0, 0, 0, 0, 0},
                {0, 2, 0, 0, 0},
                {0, 1, 0, 0, 0}});
        HomeworkGameState expectedRed = new HomeworkGameState(new int[][] {
                {0, 0, 0, 2, 1},
                {0, 2, 0, 1, 0},
                {0, 0, 0, 0, 0},
                {0, 2, 0, 0, 0},
                {0, 1, 0, 0, 0}});

        stateRed.moveDisc(1, 0, 1, 1);
        assertEquals(expectedRed.getBoard()[1][1], stateRed.getBoard()[1][1]);


        HomeworkGameState stateBlue = new HomeworkGameState(new int[][] {
                {0, 0, 0, 2, 1},
                {2, 0, 0, 1, 0},
                {0, 0, 0, 0, 0},
                {0, 2, 0, 0, 0},
                {0, 1, 0, 0, 0}});
        HomeworkGameState expectedBlue = new HomeworkGameState(new int[][] {
                {0, 0, 0, 1, 0},
                {2, 0, 0, 1, 0},
                {0, 0, 0, 0, 0},
                {0, 2, 0, 0, 0},
                {0, 1, 0, 0, 0}});

        stateBlue.moveDisc(0, 4, 0, 3);
        assertEquals(expectedBlue.getBoard()[1][1], stateBlue.getBoard()[1][1]);
    }

    /*@Test
    void testClone() {

    }*/

    @Test
    void testToString() {
        HomeworkGameState state = new HomeworkGameState(new int[][] {
                {2, 2, 2, 2, 1},
                {2, 2, 2, 2, 2},
                {2, 2, 1, 2, 2},
                {2, 2, 2, 2, 2},
                {1, 2, 2, 2, 2}});
        assertEquals("2 2 2 2 1 \n"
                            + "2 2 2 2 2 \n"
                            + "2 2 1 2 2 \n"
                            + "2 2 2 2 2 \n"
                            + "1 2 2 2 2 \n", state.toString());
    }
}