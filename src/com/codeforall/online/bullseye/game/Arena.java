package com.codeforall.online.bullseye.game;

import com.codeforall.simplegraphics.graphics.Rectangle;
import com.codeforall.simplegraphics.pictures.Picture;

public class Arena {

    private int width;
    private int height;
    private Picture picture;

    public Arena() {
        picture = new Picture(0,0, "resources/backgroundResized.jpeg");
        picture.draw();
    }


}
