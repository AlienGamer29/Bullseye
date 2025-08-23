package com.codeforall.online.bullseye.playables;


public enum Direction {

    UP,
    DOWN;


    public boolean isOpposite(Direction dir) {
        return dir.equals(oppositeDirection());
    }


    public Direction oppositeDirection() {

        Direction opposite = Direction.UP;

        switch (this) {
            case UP:
                opposite = Direction.DOWN;
                break;
            case DOWN:
                opposite = Direction.UP;
                break;
        }
        return opposite;
    }
}
