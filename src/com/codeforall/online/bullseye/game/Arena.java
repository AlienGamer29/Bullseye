package com.codeforall.online.bullseye.game;

import com.codeforall.simplegraphics.graphics.Canvas;
import com.codeforall.simplegraphics.pictures.Picture;

public class Arena {

    private int width;
    private int height;
    private Picture picture;
    private final int BUSHPADDING = 34;

    public Arena() {

        picture = new Picture(0, 0, "resources/background.jpeg");
        picture.grow(-256, -128); // 1534-1024= 512/2 = 256, 1024-768 = 256/2= 128
        picture.translate(-256, -128); // volta a encostar ao (0,0)


        width = picture.getWidth();
        height = picture.getHeight();

        //Ajusta o canvas
        Canvas.setMaxX(width - 10);
        Canvas.setMaxY(height - 10);

        picture.draw();
    }

    public int getTop() {
        return picture.getY() + BUSHPADDING;
    }

    public int getBottom() {
        return picture.getMaxY() - BUSHPADDING;
    }

    public int getRight() {
        return picture.getMaxX();
    }

    public int getLeft() {
        return picture.getX();
    }

    public int getBUSHPADDING() {
        return BUSHPADDING;
    }


}
