package homeworkGame.state;

import com.sun.javafx.scene.traversal.Direction;

public enum Slot {

    EMPTY,
    BLUE,
    RED;

    private static final int[][] T = {
            {0, 0, 0, 0},
            {1, 1, 1, 1},
            {2, 2, 2, 2}
    };

    /**
     * Returns the instance represented by the value specified.
     *
     * @param value the value representing an instance
     * @return the instance represented by the value specified
     * @throws IllegalArgumentException if the value specified does not
     * represent an instance
     */
    public static Slot of(int value) {
        if (value < 0 || value >= values().length) {
            throw new IllegalArgumentException();
        }
        return values()[value];
    }

    /**
     * Returns the integer value that represents this instance.
     *
     * @return the integer value that represents this instance
     */
    public int getValue() {
        return ordinal();
    }

    public String toString() {
        return Integer.toString(ordinal());
    }
}
