package com.codeforall.online.bullseye.playables;

public enum ArrowTypes {
    RED ("resources/arrowright_215x83.png", 5),
    BLUE ("resources/arrowright_215x83_blue.png", 10),
    GREEN ("resources/arrowright_215x83_green.png", 1);

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
