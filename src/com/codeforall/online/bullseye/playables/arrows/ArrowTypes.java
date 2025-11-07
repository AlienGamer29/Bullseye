package com.codeforall.online.bullseye.playables.arrows;

import static com.codeforall.online.bullseye.game.Game.PREFIX;

/**
 * Different types of arrows used in the game.
 *
 * Each type has its own image path, speed and score value.
 */
public enum ArrowTypes {

    /** Red arrow: normal speed, normal score. */
    RED (PREFIX + "arrowright_215x83.png", 5, 10),

    /** Blue arrow: faster but gives less score. */
    BLUE (PREFIX + "arrowright_215x83_blue.png", 10, 5),

    /** Green arrow: slow but gives higher score. */
    GREEN (PREFIX + "arrowright_215x83_green.png", 1, 30);

    /** Path to the image file. */
    private final String PATH;

    /** Speed of the arrow. */
    private final int SPEED;

    /** Score gained when hitting a target. */
    private int SCORE;

    ArrowTypes(String path, int speed, int score) {
        this.PATH = path;
        this.SPEED = speed;
        this.SCORE = score;
    }

    /** @return the image path */
    public String getPATH() {
        return PATH;
    }

    /** @return the arrow speed */
    public int getSPEED() {
        return SPEED;
    }

    /** @return the score value */
    public int getSCORE() {
        return SCORE;
    }
}
