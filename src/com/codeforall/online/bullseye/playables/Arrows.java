package com.codeforall.online.bullseye.playables;

import com.codeforall.online.bullseye.game.Arena;
import com.codeforall.simplegraphics.pictures.Picture;

public class Arrows extends Entity implements Collidables{


    public String arrowPath;
    public int arrowSpeed;

    public Arrows(int x, int y) {
        super(x, y);
        setRandomArrow();
        setPicture();
        picture.grow(-50, -25);
        picture.translate(-50, -25);
        picture.draw();
        setSpeed();
    }

    public void removePicture(){
        picture.delete();
    }

    public void setPicture() {
        this.picture = new Picture(x, y, arrowPath);
    }

    public void setSpeed() {
        speed = arrowSpeed;
    }

    public void update(Arena arena){
        move(arena);

    }

    public void move(Arena arena) {
        picture.translate(speed, 0);
    }

    public void setRandomArrow (){
        int randomArrowType;

        randomArrowType = (int)(Math.random()*5);

        switch (randomArrowType){
            case 0:
                arrowPath = ArrowTypes.BLUE.getPath();
                arrowSpeed = ArrowTypes.BLUE.getSpeed();
                break;
            case 1:
                arrowPath = ArrowTypes.GREEN.getPath();
                arrowSpeed = ArrowTypes.GREEN.getSpeed();
                break;
            case 2:
            case 3:
            case 4:
                arrowPath = ArrowTypes.RED.getPath();
                arrowSpeed = ArrowTypes.RED.getSpeed();
                break;
        }
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
