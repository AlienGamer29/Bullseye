package com.codeforall.online.bullseye.playables.arrows;

import static com.codeforall.online.bullseye.game.Game.PREFIX;

public enum ArrowTypes {
    RED (PREFIX + "arrowright_215x83.png", 5, 10),
    BLUE (PREFIX + "arrowright_215x83_blue.png", 10, 5),
    GREEN (PREFIX + "arrowright_215x83_green.png", 1, 30);

    private final String PATH;
    private final int SPEED;
    private int SCORE;

    ArrowTypes(String path, int speed, int score) {
        this.PATH = path;
        this.SPEED = speed;
        this.SCORE = score;
    }

    public String getPATH() {
        return PATH;
    }

    public int getSPEED() {
        return SPEED;
    }

    public int getSCORE() {
        return SCORE;
    }
}
