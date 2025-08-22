package com.codeforall.online.bullseye.game;

import com.codeforall.online.bullseye.playables.Player;
import com.codeforall.simplegraphics.mouse.MouseEvent;
import com.codeforall.simplegraphics.mouse.MouseHandler;

public class MyMouse implements MouseHandler {

    private Player player;

    public MyMouse(Player player) {
        this.player = player;
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {

    }
}
