package com.codeforall.online.bullseye.playables;

public class Player extends Entity {
    protected int arrowCounter;

    public Player(int x, int y) {
        super(x, y);
    }

    public void moveDOWN() {
    }

    public void moveUP(){
    }

    public void shoot(Arrows arrows) {
        arrowCounter--;
    }

    public int getArrowCounter(){
        return arrowCounter;
    }

    public void setArrowCounter(int counter) {
        this.arrowCounter = counter;
    }
}
