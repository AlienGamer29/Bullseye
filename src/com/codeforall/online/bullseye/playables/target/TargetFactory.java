package com.codeforall.online.bullseye.playables.target;

/**
 * Factory class to create {@link Target} objects.
 *
 * Targets are placed using predefined X and Y positions
 * and alternate their initial direction.
 */
public class TargetFactory {

    /** Possible X positions for targets. */
    private static final int[] X_POS = {886, 808};

    /** Possible Y positions for targets. */
    private static final int[] Y_POS = {34, 174, 314, 454, 594};

    /** Index used to alternate X positions. */
    private static int indexX = 0;

    /** Index used to cycle Y positions. */
    private static int indexY = 0;

    /** Directions to alternate based on X position. */
    private static final Direction[] DIR = {Direction.DOWN, Direction.UP};

    /**
     * Creates a new target using the next available X and Y positions.
     * The initial direction is chosen based on the X position.
     *
     * @return a new Target
     */
    public static Target createTarget() {
        int x = distributionX();
        int y = distributionY();

        Direction d = (x == X_POS[0]) ? DIR[0] : DIR[1];
        return new Target(x, y, d);
    }

    /**
     * Returns the next X position in sequence.
     * Alternates between the values in {@code X_POS}.
     *
     * @return the next X position
     */
    private static int distributionX() {
        int x = X_POS[indexX];
        indexX = (indexX + 1) % X_POS.length;
        return x;
    }

    /**
     * Returns the next Y position in sequence.
     * Cycles through the values in {@code Y_POS}.
     *
     * @return the next Y position
     */
    private static int distributionY() {
        int y = Y_POS[indexY];
        indexY = (indexY + 1) % Y_POS.length;
        return y;
    }
}


