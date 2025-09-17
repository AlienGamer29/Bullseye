package com.codeforall.online.bullseye.playables;

import com.codeforall.online.bullseye.game.Arena;

/**
 * Interface for all objects that can collide in the game.
 *
 * Defines position, size and basic behavior.
 */
public interface Collidables {

    /** @return current X position */
    int getX();

    /** @return current Y position */
    int getY();

    /** @return maximum X (right edge) */
    int getMaxX();

    /** @return maximum Y (bottom edge) */
    int getMaxY();

    /** @return object width */
    int getWidth();

    /** @return object height */
    int getHeight();

    /**
     * Updates the object (movement or logic).
     *
     * @param arena the game arena
     */
    void update(Arena arena);

    /** Removes the picture from the screen. */
    void removePicture();
}
