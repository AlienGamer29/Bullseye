package com.codeforall.online.bullseye.playables.arrows;

import com.codeforall.online.bullseye.game.Arena;
import com.codeforall.online.bullseye.playables.Collidables;
import com.codeforall.online.bullseye.playables.Entity;
import com.codeforall.simplegraphics.pictures.Picture;

public class Arrows extends Entity implements Collidables {


    public String arrowPath;
    public int arrowSpeed;

    public Arrows(int x, int y) {
        super(x, y);
        setRandomArrow();
        setPicture();
        picture.grow(-50, -25);
        picture.translate(-50, -25);
        displayArrows(true);
        setSpeed();
    }

    public void removePicture(){
        displayArrows(false);
    }

    public void setPicture() {
        this.picture = new Picture(x, y, arrowPath);
    }

    public void setSpeed() {
        speed = arrowSpeed;
    }

    @Override
    public void update(Arena arena){
        picture.translate(speed, 0);

    }

    private void setRandomArrow (){
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
            default:
                arrowPath = ArrowTypes.RED.getPath();
                arrowSpeed = ArrowTypes.RED.getSpeed();
                break;
        }
    }

    public void displayArrows(Boolean show) {
        if (show) {
            picture.draw();
        } else {
            picture.delete();
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

    @Override
    public int getWidth() {
        return picture.getWidth();
    }

    @Override
    public int getHeight() {
        return picture.getHeight();
    }
}
