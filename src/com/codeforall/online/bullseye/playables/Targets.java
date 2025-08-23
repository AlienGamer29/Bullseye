package com.codeforall.online.bullseye.playables;

public class Targets extends Entity implements Collidables {

    public Targets(int x, int y) {
        super(x, y);
    }

    @Override
    public int getX() {
        return 0;
    }

    @Override
    public int getY() {
        return 0;
    }

    @Override
    public int getMaxX() {
        return 0;
    }

    @Override
    public int getMaxY() {
        return 0;
    }
}
