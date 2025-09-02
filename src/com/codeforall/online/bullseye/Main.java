package com.codeforall.online.bullseye;


import com.codeforall.online.bullseye.game.Game;


public class Main {
    public static void main(String[] args) throws InterruptedException {

        Game game = new Game();

        game.initIntro();
        game.start();
    }
}