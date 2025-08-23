package com.codeforall.online.bullseye.playables;

import com.codeforall.online.bullseye.game.Arena;
import com.codeforall.simplegraphics.pictures.Picture;

public class Target extends Entity implements Collidables {

    private Direction currDirection;
    private int distance;

    public Target(int x, int y) {
        super(x, y);
        picture = new Picture(x, y, "resources/target.png");
        picture.grow(-210, -210);
        picture.translate(-210, -210);
        picture.draw();
        speed = 3;
        currDirection = Direction.values()[(int) (Math.random() * Direction.values().length)];
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

    public void update(Arena arena) {
            moveInDirection(currDirection, arena);
    }

    public void moveInDirection(Direction dir, Arena arena) {
        Direction newDirection = dir;

        if (picture.getY() <= arena.getTop() || picture.getMaxY() >= arena.getBottom()) {
            newDirection = currDirection.oppositeDirection();
        }

        currDirection = newDirection;

        switch (currDirection) {
            case UP:
                picture.translate(0, -speed);
                break;
            case DOWN:
                picture.translate(0, speed);
                break;
        }


    }
}
