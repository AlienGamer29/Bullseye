package com.codeforall.online.bullseye;


import com.codeforall.online.bullseye.game.Game;
import com.codeforall.online.bullseye.game.GameState;


public class Main {
    public static void main(String[] args) throws InterruptedException {

        Game game = new Game();

        game.initIntro();
        game.start();


    }
}