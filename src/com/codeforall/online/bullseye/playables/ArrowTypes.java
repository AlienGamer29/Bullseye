package com.codeforall.online.bullseye.playables;

import static com.codeforall.online.bullseye.game.Game.PREFIX;

public enum ArrowTypes {
    RED (PREFIX + "arrowright_215x83.png", 5),
    BLUE (PREFIX + "arrowright_215x83_blue.png", 10),
    GREEN (PREFIX + "arrowright_215x83_green.png", 1);

    private final String path;
    private final int speed;

    ArrowTypes(String path, int speed) {
        this.path = path;
        this.speed = speed;
    }

    public String getPath() {
        return path;
    }

    public int getSpeed() {
        return speed;
    }

}
