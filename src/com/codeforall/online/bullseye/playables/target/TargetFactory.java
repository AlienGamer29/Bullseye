package com.codeforall.online.bullseye.playables.target;

public class TargetFactory {


    private static final int[] X_POS = {886, 808};
    private static final int[] Y_POS = {34, 174, 314, 454, 594};
    private static int indexX = 0;
    private static int indexY = 0;
    private static final Direction[] DIR = {Direction.DOWN, Direction.UP};

    // Creates a new Target using sequentially distributed X and Y positions.
    // The initial direction is chosen based on the X position.
    public static Target createTarget() {
        int x = distributionX();
        int y = distributionY();

        Direction d = (x == X_POS[0]) ? DIR[0] : DIR[1];
        return new Target(x, y, d);
    }

    // Returns the next X position in sequence (alternates between values in X_POS).
    private static int distributionX() {
        int x = X_POS[indexX];
        indexX = (indexX + 1) % X_POS.length;
        return x;
    }

    // Returns the next Y position in sequence (cycles through values in Y_POS).
    private static int distributionY() {
        int y = Y_POS[indexY];
        indexY = (indexY + 1) % Y_POS.length;
        return y;
    }
}


