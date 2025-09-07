package com.codeforall.online.bullseye.game;

import com.codeforall.online.bullseye.playables.Collidables;
import com.codeforall.online.bullseye.playables.Entity;
import com.codeforall.simplegraphics.pictures.Picture;

public class Obstacle extends Entity implements Collidables {

    public Obstacle(int x, int y) {
        super(x, y);
        this.picture = new Picture(x,y, "resources/wallObstacle133x100.png");;
        this.picture.draw();
    }

    @Override
    public int getX() {
        return picture.getX();
    }

    @Override
    public int getY() {
        return picture.getY();
    }

    @Override
    public int getMaxX() {
        return picture.getMaxX();
    }

    @Override
    public int getMaxY() {
        return picture.getMaxY();
    }

    @Override
    public int getWidth() {
        return picture.getWidth();
    }

    @Override
    public int getHeight() {
        return picture.getHeight();
    }

    @Override
    public void update(Arena arena) {

    }

    @Override
    public void removePicture() {
        picture.delete();

    }
}
