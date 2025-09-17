package com.codeforall.online.bullseye.game;

import com.codeforall.simplegraphics.graphics.Canvas;
import com.codeforall.simplegraphics.pictures.Picture;

import static com.codeforall.online.bullseye.game.Game.PREFIX;

/**
 * Represents the game arena
 *
 *  The arena has a background image and defines the limits
 *  where the game elements can move.
 */
public class Arena {

    /** The width of the arena */
    private int width;

    /** The height of the arena */
    private int height;

    /** The background picture of the arena */
    private Picture picture;

    /** Space between the bushes and the border of the image */
    private final int BUSH_PUDDING = 50;


    /**
     * Creates an arena with a background image
     *
     * Also adjusts the canvas size to match the image.
     */
    public Arena() {

        picture = new Picture(0, 0, PREFIX + "background.jpeg");
        picture.grow(-256, -128); // 1534-1024= 512/2 = 256, 1024-768 = 256/2= 128
        picture.translate(-256, -128); // volta a encostar ao (0,0)


        width = picture.getWidth();
        height = picture.getHeight();

        //Ajusta o canvas
        Canvas.setMaxX(width - 10);
        Canvas.setMaxY(height - 10);

        displayArena();
    }

    /** @return the height of the arena */
    public int getHeight() {
        return height;
    }

    /** @return the width of the arena */
    public int getWidth() {
        return width;
    }

    /** @return the Y coordinate of the top bush */
    public int getTopBush() {
        return picture.getY() + BUSH_PUDDING;
    }

    /** @return the Y coordinate of the bottom bush */
    public int getBottomBush() {
        return picture.getMaxY() - BUSH_PUDDING;
    }

    /** @return the X coordinate of the right border */
    public int getRight() {
        return picture.getMaxX();
    }

    /** @return the X coordinate of the left border */
    public int getLeft() {
        return picture.getX();
    }

    /** @return the space between bushes and border */
    public int getBUSH_PUDDING() {
        return BUSH_PUDDING;
    }

    /** Removes the background picture from the screen */
    public void removePicture() {
        picture.delete();
    }

    /** Displays the background picture of the arena */
    public void displayArena() {
        picture.draw();

    }


}
