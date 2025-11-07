package com.codeforall.online.bullseye.playables.arrows;

import com.codeforall.online.bullseye.game.Arena;
import com.codeforall.online.bullseye.playables.Collidables;
import com.codeforall.online.bullseye.playables.Entity;
import com.codeforall.simplegraphics.pictures.Picture;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Represents an arrow fired by the player.
 *
 * Each arrow has a type, image and speed.
 */
public class Arrows extends Entity implements Collidables {

    /** Path to the arrow image. */
    private String arrowPath;

    /** Horizontal speed of the arrow. */
    private int arrowSpeed;

    /** Arrow type (defines image and speed). */
    private ArrowTypes type;

    /**
     * Creates a new arrow at the given position.
     *
     * @param x start X
     * @param y start Y
     */
    public Arrows(int x, int y) {
        super(x, y);
        displayArrows();
    }

    /** Sets the arrow picture based on the current path. */
    public void setPicture() {
        this.picture = new Picture(x, y, arrowPath);
    }

    /** Applies the current arrow speed to the entity speed. */
    public void setSpeed() {
        speed = arrowSpeed;
    }

    /**
     * Moves the arrow to the right every tick.
     *
     * @param arena the arena (unused here, kept for consistency)
     */
    @Override
    public void update(Arena arena){
        picture.translate(speed, 0);

    }

    /** Picks a random arrow type and updates path and speed. */
    private void setRandomArrow (){
        int randomArrowType;

        randomArrowType = (int)(Math.random()*5);

        switch (randomArrowType){
            case 0:
                type = ArrowTypes.BLUE;
                break;
            case 1:
                type = ArrowTypes.GREEN;
                break;
            default:
                type = ArrowTypes.RED;
                break;
        }

        arrowPath = type.getPATH();
        arrowSpeed = type.getSPEED();
    }

    /** Creates, sizes and draws the arrow on screen. */
    public void displayArrows() {
        setRandomArrow();
        setPicture();
        picture.grow(-50, -25);
        picture.translate(-50, -25);
        setSpeed();
        picture.draw();
    }

    /** @return current X of the arrow */
    @Override
    public int getX() {
        return picture.getX();
    }

    /** @return current Y of the arrow */
    @Override
    public int getY() {
        return picture.getY();
    }

    /** @return max X (right edge) of the arrow */
    @Override
    public int getMaxX() { return picture.getMaxX(); }

    /** @return max Y (bottom edge) of the arrow */
    @Override
    public int getMaxY() {
        return picture.getMaxY();
    }

    /** @return arrow width */
    @Override
    public int getWidth() {
        return picture.getWidth();
    }

    /** @return arrow height */
    @Override
    public int getHeight() {
        return picture.getHeight();
    }

    /** @return the arrow type */
    public ArrowTypes getType() {
        return type;
    }

    /**
     * Temporarily teleports the arrow to the middle of the arena (same Y),
     * and returns it to the original position after a delay.
     *
     * @param arena the arena to compute positions
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
                picture.translate(oldX - newX, oldY - newY);
            }

        }, delayMillis);
    }
}
