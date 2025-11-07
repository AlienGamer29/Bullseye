package com.codeforall.online.bullseye.playables.target;

import com.codeforall.online.bullseye.game.Arena;
import com.codeforall.online.bullseye.playables.Collidables;
import com.codeforall.online.bullseye.playables.Entity;
import com.codeforall.simplegraphics.pictures.Picture;

import static com.codeforall.online.bullseye.game.Game.PREFIX;

/**
 * Represents a moving target in the arena.
 *
 * Targets move up and down until they hit the arena borders.
 */
public class Target extends Entity implements Collidables {

    /** Current direction of movement. */
    private Direction currDirection;

    /**
     * Creates a target at the given position and direction.
     *
     * @param x starting X
     * @param y starting Y
     * @param direction initial direction (UP or DOWN)
     */
    public Target(int x, int y, Direction direction) {
        super(x, y);
        displayTarget(direction);
    }

    /** @return current X of the target */
    @Override
    public int getX() {
        return picture.getX();
    }

    /** @return current Y of the target */
    @Override
    public int getY() {
        return picture.getY();
    }

    /** @return right edge X */
    @Override
    public int getMaxX() {
        return picture.getMaxX();
    }

    /** @return bottom edge Y */
    @Override
    public int getMaxY() {
        return picture.getMaxY();
    }

    /** @return width of the target */
    @Override
    public int getWidth() {
        return picture.getWidth();
    }

    /** @return height of the target */
    @Override
    public int getHeight() {
        return picture.getHeight();
    }

    /**
     * Updates target position.
     * Moves in the current direction and checks arena bounds.
     *
     * @param arena the game arena
     */
    @Override
    public void update(Arena arena) {
        moveInDirection(currDirection, arena);

    }

    /**
     * Moves the target up or down.
     * If it hits the top/bottom, reverses direction.
     *
     * @param dir direction to move
     * @param arena the arena to check limits
     */
    private void moveInDirection(Direction dir, Arena arena) {

        if (picture.getY() <= arena.getTopBush()) {
            currDirection = Direction.DOWN;
        } else if (picture.getMaxY() >= arena.getBottomBush()) {
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

    /**
     * Creates and draws the target picture.
     * Resizes, recenters and sets initial direction and speed.
     *
     * @param direction starting direction
     */
    private void displayTarget(Direction direction) {
        // Create and draw the target picture, resize and recenter
        this.picture = new Picture(x, y, PREFIX + "target309x314.png");
        picture.grow(-118, -110);
        picture.translate(-118, -110);
        picture.draw();

        this.speed = 1;
        this.currDirection = direction;
    }
}
