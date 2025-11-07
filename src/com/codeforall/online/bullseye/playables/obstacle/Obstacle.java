package com.codeforall.online.bullseye.playables.obstacle;

import com.codeforall.online.bullseye.game.Arena;
import com.codeforall.online.bullseye.playables.Collidables;
import com.codeforall.online.bullseye.playables.Entity;
import com.codeforall.simplegraphics.pictures.Picture;

/**
 * Represents an obstacle on the arena.
 *
 * Obstacles can be haystacks or walls, chosen randomly.
 */
public class Obstacle extends Entity implements Collidables {

    /** The type of obstacle (defines image). */
    private ObstacleType obstacleType;

    /**
     * Creates an obstacle at the given position.
     * A random type is chosen (hay or wall).
     *
     * @param x starting X position
     * @param y starting Y position
     */
    public Obstacle(int x, int y) {
        super(x, y);
        obstacleType = randomObstacle();
        this.picture = new Picture(x,y,obstacleType.getPath());
        this.picture.draw();
    }

    /** @return current X of the obstacle */
    @Override
    public int getX() {
        return picture.getX();
    }

    /** @return current Y of the obstacle */
    @Override
    public int getY() {
        return picture.getY();
    }

    /** @return right edge X of the obstacle */
    @Override
    public int getMaxX() {
        return picture.getMaxX();
    }

    /** @return bottom edge Y of the obstacle */
    @Override
    public int getMaxY() {
        return picture.getMaxY();
    }

    /** @return obstacle width */
    @Override
    public int getWidth() {
        return picture.getWidth();
    }

    /** @return obstacle height */
    @Override
    public int getHeight() {
        return picture.getHeight();
    }

    /**
     * Obstacles don’t move, so update does nothing.
     */
    @Override
    public void update(Arena arena) {

    }

    /** Removes the obstacle picture from the screen. */
    @Override
    public void removePicture() {
        picture.delete();

    }

    /**
     * Chooses randomly between hay and wall.
     *
     * @return a random obstacle type
     */
    public ObstacleType randomObstacle(){
        int randomNumber = (int)(Math.random()*10);
        if (randomNumber%2 == 1){
            return ObstacleType.HAY;
        } else {
            return ObstacleType.WALL;
        }
    }
}
