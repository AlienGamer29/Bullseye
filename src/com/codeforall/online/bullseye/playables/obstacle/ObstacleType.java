package com.codeforall.online.bullseye.playables.obstacle;

import static com.codeforall.online.bullseye.game.Game.PREFIX;

public enum ObstacleType {
    HAY (PREFIX + "hayObstacle133x119.png"),
    WALL (PREFIX + "wallObstacle133x100.png");

    private final String path;

    ObstacleType (String path){
        this.path = path;
    }

    public String getPath() {
        return path;
    }

}
