package homeworkGame.state;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * Class representing the state of the puzzle.
 */
@Data
@Slf4j
public class HomeworkGameState implements Cloneable {

    /**
     * The array representing the initial configuration of the board.
     */
    public static final int[][] INITIAL = {
            {2, 2, 2, 2, 1},
            {2, 2, 2, 2, 2},
            {2, 2, 1, 2, 2},
            {2, 2, 2, 2, 2},
            {1, 2, 2, 2, 2}
    };

    /**
     * The array storing the current configuration of the board.
     */

        private Slot[][] board;

    /**
     * Creates a {@code HomeworkGameState} object representing the (original)
     * initial state of the puzzle.
     */
    public HomeworkGameState() {
        this(INITIAL);
    }


    /**
     * Creates a {@code HomeworkGameState} object that is initialized it with
     * the specified array.
     *
     * @param a an array of size 5;5 representing the initial configuration
     *          of the board
     * @throws IllegalArgumentException if the array does not represent a valid
     *                                  configuration of the board
     */
    public HomeworkGameState(int[][] a) {
        if (!isValidBoard(a)) {
            throw new IllegalArgumentException();
        }
        initBoard(a);
    }

    private void initBoard(int[][] a) {
        this.board = new Slot[5][5];
    }

    private boolean isValidBoard(int[][] a) {
        if (a == null || a.length != 5) {
            return false;
        }
        int b = 0;
        int r = 0;
        boolean enoughDiscs = false;
        for (int[] row : a) {
            if (row == null || row.length != 5) {
                return false;
            }
            for (int space : row) {
                if (space < 0 || space >= Slot.values().length) {
                    return false;
                }
                if (space == Slot.BLUE.getValue()) {
                    b++;
                }
                if (space == Slot.RED.getValue()) {
                    r++;
                }
            }
        }
        if (b == 3 && r <= 22) {
            enoughDiscs = true;
        }

        return enoughDiscs;
    }

//--------------------OK----------------------------------------

    /**
     * Checks if Blue Player won the game.
     * 
     * @return {@code true} if Blue Player won, {@code false} otherwise.
     */
    public boolean isBlueWon() {
        int[][] bluePositions = new int[3][2];
        int a = 0;
        int bR;
        int bC;
        int noMoveBlueNum = 0;

        for (int i=0; i<5; i++) {
            for (int j=0; i<5; j++) {
                if (board[i][j] == Slot.BLUE) {
                    bluePositions[a][0] = i;
                    bluePositions[a][1] = j;
                    a++;
                }
            }
        }

        for (int k=0; k<3; k++) {
            bR = bluePositions[k][0];
            bC = bluePositions[k][1];
            
            if (bR == 0) {
                if (bC == 0) {
                    if (!canMove(bR, bC, bR-1, bC) && !canMove(bR, bC, bR, bC+1))
                        noMoveBlueNum++;
                }
                else if (bC == 4) {
                    if (!canMove(bR, bC, bR-1, bC) && !canMove(bR, bC, bR, bC-1))
                        noMoveBlueNum++;
                }
                else if (!canMove(bR, bC, bR-1, bC) && !canMove(bR, bC, bR, bC-1) && !canMove(bR, bC, bR, bC+1))
                    noMoveBlueNum++;
            }
            else if (bR == 4) {
                if (bC == 0) {
                    if (!canMove(bR, bC, bR+1, bC) && !canMove(bR, bC, bR, bC+1))
                        noMoveBlueNum++;
                }
                else if (bC == 4) {
                    if (!canMove(bR, bC, bR+1, bC) && !canMove(bR, bC, bR, bC-1))
                        noMoveBlueNum++;
                }
                else if (!canMove(bR, bC, bR+1, bC) && !canMove(bR, bC, bR, bC-1) && !canMove(bR, bC, bR, bC+1))
                    noMoveBlueNum++;
            }
            else if (bC == 0) {
                if (!canMove(bR, bC, bR-1, bC) && !canMove(bR, bC, bR+1, bC) && !canMove(bR, bC, bR, bC+1))
                    noMoveBlueNum++;
            }
            else if (bC == 4) {
                if (!canMove(bR, bC, bR-1, bC) && !canMove(bR, bC, bR+1, bC) && !canMove(bR, bC, bR, bC-1))
                    noMoveBlueNum++;
            }
            else if (!canMove(bR, bC, bR-1, bC) && !canMove(bR, bC, bR+1, bC) && !canMove(bR, bC, bR, bC-1) && !canMove(bR, bC, bR, bC+1))
                noMoveBlueNum++;
        }

        if (noMoveBlueNum == 3) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Checks if Red Player won the game.
     * 
     * @return {@code true} if Red Player won, {@code false} otherwise.
     */
    public boolean isRedWon() {
        int[][] bluePositions = new int[3][2];
        int a = 0;
        boolean rowCheck;
        boolean colCheck;

        for (int i=0; i<5; i++) {
            for (int j=0; i<5; j++) {
                if (board[i][j] == Slot.BLUE) {
                    bluePositions[a][0] = i;
                    bluePositions[a][1] = j;
                    a++;
                }
            }
        }

        rowCheck = bluePositions[0][0] == bluePositions[1][0] && bluePositions[1][0] == bluePositions[2][0];
        colCheck = bluePositions[0][0] == bluePositions[0][1] && bluePositions[0][1] == bluePositions[0][2];

        return rowCheck || colCheck;
    }



    /**
     * Checks if a disc can be moved to a specified slot.
     * 
     * @param fromRow the row of the disc's current position.
     * @param fromCol the column of the disc's current position.
     * @param toRow the row of the disc's target position.
     * @param toCol the colmun of the disc's target position.
     * @return {@code true} if the disc can be moved, {@code false} otherwise.
     */
    public boolean canMove(int fromRow, int fromCol, int toRow, int toCol) {
        boolean sameSlot = fromRow == toRow && fromCol == toCol;
        boolean offBoard = fromRow < 0 || fromRow > 4 || fromCol < 0 || fromCol > 4 || toRow < 0 || toRow > 4 || toCol < 0 || toCol > 4;
        boolean neighbour = (fromRow == toRow && (fromCol == toCol-1 || fromCol == toCol+1)) || (fromCol == toCol && (fromRow == toRow-1 || fromRow == toRow+1));
        boolean moveable;

        if (!sameSlot && !offBoard && neighbour) {
            if (board[fromRow][fromCol] == Slot.BLUE && board[toRow][toCol] == Slot.RED) {
                moveable = true;
            }
            else if (board[fromRow][fromCol] == Slot.RED && board[toRow][toCol] == Slot.EMPTY) {
                moveable = true;
            }
            else {
                moveable = false;
            }
        }
        else {
            moveable =  false;
        }

        return moveable;
    }

    /**
     * Moves a disc from a specified position to the target position.
     *
     * @param fromRow is the row of the disc's current position.
     * @param fromCol is the column of the disc's current position.
     * @param toRow is the row of the target position.
     * @param toCol is the column of the target position.
     *
     * @throws IllegalArgumentException if the disc can not be moved.
     */
    public void moveDisc(int fromRow, int fromCol, int toRow, int toCol) {
        if ( !canMove(fromRow, fromCol, toRow, toCol)) {
            throw new IllegalArgumentException();
        }
        else {
            board[toRow][toCol] = Slot.of(board[fromRow][fromCol].getValue());
            board[fromRow][fromCol] = Slot.EMPTY;
        }
    }

    public HomeworkGameState clone() {
        HomeworkGameState copy = null;
        try {
            copy = (HomeworkGameState) super.clone();
        } catch (CloneNotSupportedException e) {
        }
        copy.board = new Slot[board.length][];
        for (int i = 0; i < board.length; ++i) {
            copy.board[i] = board[i].clone();
        }
        return copy;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Slot[] row : board) {
            for (Slot slot : row) {
                sb.append(slot).append(' ');
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        HomeworkGameState state = new HomeworkGameState();
        System.out.println(state);
    }
}
