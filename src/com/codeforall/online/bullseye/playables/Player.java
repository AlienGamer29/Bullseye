package com.codeforall.online.bullseye.playables;

import com.codeforall.online.bullseye.game.Arena;
import com.codeforall.online.bullseye.playables.arrows.Arrows;
import com.codeforall.simplegraphics.pictures.Picture;

import java.util.Timer;
import java.util.TimerTask;

import static com.codeforall.online.bullseye.game.Game.PREFIX;

/**
 * Represents the player in the game.
 *
 * The player can move up and down, shoot arrows and use the "woosh" cheat.
 */
public class Player extends Entity {

    /**
     * Creates a player at the given position.
     *
     * @param x starting X
     * @param y starting Y
     */
    public Player(int x, int y) {
        super(x, y);
        this.speed = 10;

        displayPlayer();
    }

    /**
     * Moves the player up inside the arena limits.
     *
     * @param arena the game arena
     */
    public void moveUp(Arena  arena) {
        int oldY = y;

        int minY = arena.getTopBush();
        int maxY = arena.getBottomBush() - picture.getHeight();

        int newY = y - speed;
        y = Math.max(minY, Math.min(newY, maxY));

        picture.translate(0, y - oldY);
    }

    /**
     * Moves the player down inside the arena limits.
     *
     * @param arena the game arena
     */
    public void moveDown(Arena arena) {
        int oldY = y;

        int minY = arena.getTopBush();
        int maxY = arena.getBottomBush() - picture.getHeight();

        int newY = y + speed;
        y = Math.max(minY, Math.min(newY, maxY));

        picture.translate(0, y - oldY);
    }

    /**
     * Shoots a new arrow from the player's position.
     *
     * @return the new arrow
     */
    public Arrows shoot() {
        int arrowX = x + picture.getWidth();
        int arrowY = y + picture.getHeight() / 2;

        return new Arrows(arrowX, arrowY);

    }

    /** Draws the player on the screen. */
    public void displayPlayer() {
        int shrinkX = 10;
        int shrinkY = 10;
        picture = new Picture(x, y,PREFIX + "player_resized.png");
        picture.grow(-shrinkX, -shrinkY);
        picture.translate(-shrinkX, -shrinkY);
        this.picture.draw();
    }

    /**
     * Teleports the player to the middle of the arena (woosh cheat).
     * After a delay, returns to the old position.
     *
     * @param arena the arena
     * @param delayMillis delay before returning, in milliseconds
     */
    public void woosh(Arena arena, int delayMillis) {

        int oldX = picture.getX();
        int oldY = picture.getY();

        int newX = arena.getLeft() + (arena.getWidth() - picture.getWidth()) / 2;
        int newY = oldY;

        this.x = newX;
        this.y = newY;

        picture.translate(newX - oldX, newY - oldY);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                x = oldX;
                picture.translate(oldX - newX, oldY - newY);
            }

        }, delayMillis);
    }


    /** @return the right edge X of the player */
    public int getRight() {
        return x + picture.getWidth();
    }



}
