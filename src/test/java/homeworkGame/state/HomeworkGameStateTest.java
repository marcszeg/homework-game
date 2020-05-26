package homeworkGame.state;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HomeworkGameStateTest {

    @Test
    void isBlueWon() {
        //assertFalse(new HomeworkGameState().isBlueWon());
        /*assertTrue(new HomeworkGameState(new int[][] {
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
                {0, 1, 0, 0, 0}}).isBlueWon());*/
    }

    @Test
    void isRedWon() {
        //assertFalse(new HomeworkGameState().isRedWon());
        /*assertTrue(new HomeworkGameState(new int[][] {
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
                {0, 1, 0, 0, 0}}).isRedWon());*/
    }

    @Test
    void canMove() {
        /*assertTrue(new HomeworkGameState(new int[][] {
                {0, 0, 0, 2, 1},
                {2, 0, 0, 1, 0},
                {0, 0, 0, 0, 0},
                {0, 2, 0, 0, 0},
                {0, 1, 0, 0, 0}}).canMove(0, 4, 0, 3));*/
        assertFalse(new HomeworkGameState(new int[][] {
                {0, 0, 0, 2, 1},
                {2, 0, 0, 1, 0},
                {0, 0, 0, 0, 0},
                {0, 2, 0, 0, 0},
                {0, 1, 0, 0, 0}}).canMove(1, 3, 2, 3));
    }

    @Test
    void moveDisc() {

    }

    @Test
    void testClone() {
    }

    @Test
    void testToString() {
    }
}