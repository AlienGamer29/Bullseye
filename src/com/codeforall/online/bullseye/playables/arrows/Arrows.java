package com.codeforall.online.bullseye.playables.arrows;

import com.codeforall.online.bullseye.game.Arena;
import com.codeforall.online.bullseye.game.Obstacle;
import com.codeforall.online.bullseye.playables.Collidables;
import com.codeforall.online.bullseye.playables.Entity;
import com.codeforall.simplegraphics.pictures.Picture;

import java.util.Timer;
import java.util.TimerTask;

public class Arrows extends Entity implements Collidables {

    private String arrowPath;
    private int arrowSpeed;
    private ArrowTypes type;

    public Arrows(int x, int y) {
        super(x, y);
        setRandomArrow();
        setPicture();
        picture.grow(-50, -25);
        picture.translate(-50, -25);
        displayArrows(true);
        setSpeed();
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

    public ArrowTypes getType() {
        return type;
    }

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
