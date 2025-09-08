package com.codeforall.online.bullseye.game;

import com.codeforall.online.bullseye.playables.Collidables;
import com.codeforall.online.bullseye.playables.Entity;
import com.codeforall.simplegraphics.pictures.Picture;

public class Obstacle extends Entity implements Collidables {

    private ObstacleType obstacleType;

    public Obstacle(int x, int y) {
        super(x, y);
        obstacleType = randomObstacle();
        this.picture = new Picture(x,y,obstacleType.getPath());
        this.picture.draw();
        this.x = x;
        this.y = y;
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
    public int getMaxX() {
        return picture.getMaxX();
    }

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

    @Override
    public void update(Arena arena) {

    }

    @Override
    public void removePicture() {
        picture.delete();

    }

    public ObstacleType randomObstacle(){
        int randomNumber = (int)(Math.random()*10);
        if (randomNumber%2 == 1){
            return ObstacleType.HAY;
        } else {
            return ObstacleType.WALL;
        }
    }
}
