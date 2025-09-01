package com.codeforall.online.bullseye.playables;

import com.codeforall.online.bullseye.game.Arena;

public interface Collidables {

    int getX();
    int getY();
    int getMaxX();
    int getMaxY();
    void update(Arena arena);
    void removePicture();

}
