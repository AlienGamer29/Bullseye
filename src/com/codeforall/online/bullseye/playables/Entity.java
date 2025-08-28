package com.codeforall.online.bullseye.playables;

import com.codeforall.simplegraphics.pictures.Picture;

public abstract class Entity {

    protected int x;
    protected int y;
    protected int speed;
    protected Picture picture;
    protected final String PREFIX = "resources/";

    public Entity(int x, int y) {
        this.x = x;
        this.y = y;
    }

}
