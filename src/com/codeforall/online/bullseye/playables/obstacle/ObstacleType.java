package com.codeforall.online.bullseye.playables.obstacle;

import static com.codeforall.online.bullseye.game.Game.PREFIX;

/**
 * Types of obstacles that can appear in the arena.
 *
 * Each type has its own image path.
 */
public enum ObstacleType {

    /** Haystack obstacle. */
    HAY (PREFIX + "hayObstacle133x119.png"),

    /** Wall obstacle. */
    WALL (PREFIX + "wallObstacle133x100.png");

    /** Path to the obstacle image. */
    private final String path;

    ObstacleType (String path){
        this.path = path;
    }

    /** @return the image path for this obstacle */
    public String getPath() {
        return path;
    }

}
