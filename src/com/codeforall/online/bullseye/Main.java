package com.codeforall.online.bullseye;


import com.codeforall.online.bullseye.game.Game;

/**
 * Entry point for the Bullseye game.
 *
 * Initializes the game, then initializes game intro.
 */
public class Main {

    /**
     * Launches the Bullseye game
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {

        Game game = new Game();

        game.initIntro();

    }
}