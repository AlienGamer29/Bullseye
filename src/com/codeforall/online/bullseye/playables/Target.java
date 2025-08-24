package com.codeforall.online.bullseye.playables;

import com.codeforall.online.bullseye.game.Arena;
import com.codeforall.simplegraphics.pictures.Picture;

public class Target extends Entity implements Collidables {

    private Direction currDirection;

    public Target(int x, int y, Direction direction) {
        super(x, y);

        // Create and draw the target picture, resize and recenter
        this.picture = new Picture(x, y, "resources/target309x314.png");
        picture.grow(-180, -180);
        picture.translate(-180, -180);
        picture.draw();

        this.speed = 1;
        this.currDirection = direction;
    }

    //Getters for picture borders
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

    public void removePicture() {
        picture.delete();
    }


    // Updates target position: moves in the current direction and checks arena bounds
    public void update(Arena arena) {
        moveInDirection(currDirection, arena);
    }

    // Moves the target UP or DOWN. If it hits top/bottom arena borders, reverses direction.
    public void moveInDirection(Direction dir, Arena arena) {

        if (picture.getY() <= arena.getTop()) {
            currDirection = Direction.DOWN;
        } else if (picture.getMaxY() >= arena.getBottom()) {
            currDirection = Direction.UP;
        } else {
            currDirection = dir;
        }

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