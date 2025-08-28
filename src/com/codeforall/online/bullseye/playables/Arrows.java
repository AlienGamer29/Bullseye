package com.codeforall.online.bullseye.playables;

import com.codeforall.online.bullseye.game.Arena;
import com.codeforall.simplegraphics.pictures.Picture;

import static com.codeforall.online.bullseye.game.Game.PREFIX;

public class Arrows extends Entity implements Collidables{


    public Arrows(int x, int y) {
        super(x, y);
        this.picture = new Picture(x, y, PREFIX + "arrowright_215x83.png");
        picture.grow(-50, -25);
        picture.translate(-50, -25);
        picture.draw();
        speed = 4;
    }

    public void removePicture(){
        picture.delete();
    }

    public void update(Arena arena){
        move(arena);

    }

    public void move(Arena arena) {
        picture.translate(speed, 0);
    }

    @Override
    public int getX() {
        return picture.getX();
    }

    @Override
    public int getY() {
        return picture.getY();
    }

    @Override
    public int getMaxX() { return picture.getMaxX(); }

    @Override
    public int getMaxY() {
        return picture.getMaxY();
    }
}
