package com.codeforall.online.bullseye.playables;

import com.codeforall.online.bullseye.game.Arena;

public interface Collidables {

    int getX();
    int getY();
    int getMaxX();
    int getMaxY();
    int getWidth();
    int getHeight();
    void update(Arena arena);
    void removePicture();

}
