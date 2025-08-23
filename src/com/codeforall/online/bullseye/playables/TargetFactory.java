package com.codeforall.online.bullseye.playables;

public class TargetFactory {

    // Create a new Target with random X and Y from the predefined values
    public static Target createTarget() {
        return new Target(randX(), randY());
    }

    // Create a new Target with random X and Y from the predefined values
    private static int randX() {
        int[] values = {900, 850};
        return values[(int)(Math.random() * values.length)];
    }

    // Return a random Y position (chosen from fixed values)
    private static int randY() {
        int[] values = {100, 200, 300, 400, 500, 600};
        return values[(int)(Math.random() * values.length)];
    }

}
