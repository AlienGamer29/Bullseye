package com.codeforall.online.bullseye.playables;

import com.codeforall.simplegraphics.pictures.Picture;

/**
 * Base class for all game entities.
 *
 * Stores position, speed and picture.
 */
public abstract class Entity {

    /** X position of the entity. */
    protected int x;

    /** Y position of the entity. */
    protected int y;

    /** Movement speed. */
    protected int speed;

    /** Picture representing the entity. */
    protected Picture picture;

    /**
     * Creates an entity at the given position.
     *
     * @param x starting X
     * @param y starting Y
     */
    public Entity(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /** Removes the picture from the screen. */
    public void removePicture() {
        picture.delete();
    }

}
