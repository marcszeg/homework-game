package homeworkGame.state;

/**
 * Class representing the empty slots and the discs.
 */
public enum Slot {

    EMPTY,
    BLUE,
    RED;

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

    /**
     * Creates a string of the value of the slot.
     *
     * @return string of the value of the slot.
     */
    public String toString() {
        return Integer.toString(ordinal());
    }
}
