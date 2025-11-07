package com.codeforall.online.bullseye.game;

import com.codeforall.simplegraphics.graphics.Canvas;
import com.codeforall.simplegraphics.pictures.Picture;

import static com.codeforall.online.bullseye.game.Game.PREFIX;

/**
 * Handles the different game screens (intro, game over, win).
 */
public class GameState {

    /** Background image for the intro screen. */
    private Picture gameIntro;

    /** Background image for the game over screen. */
    private Picture gameOver;

    /** Background image for the win screen. */
    private Picture gameWin;

    /**
     * Creates the game state with all screen images.
     * Also sets the canvas size based on the background.
     */
    public GameState() {
        this.gameIntro = new Picture(0,0, PREFIX +"introbackground.png");
        gameIntro.grow(-256, -128);
        gameIntro.translate(-256, -128);

        this.gameOver = new Picture(0,0, PREFIX + "gameoverbackground.png");
        gameOver.grow(-256, -128);
        gameOver.translate(-256, -128);

        this.gameWin = new Picture(0,0, PREFIX + "youwin_bg.png");
        gameWin.grow(-256, -128);
        gameWin.translate(-256, -128);

        //Set the canvas size
        Canvas.setMaxX(gameWin.getWidth() - 10);
        Canvas.setMaxY(gameWin.getHeight() - 10);

    }

    /**
     * Shows or hides the intro screen.
     * @param show true to draw, false to delete
     */
    public void displayIntro(Boolean show) {

        if (show) {

            gameIntro.draw();

        } else {

            gameIntro.delete();
        }
    }

    /** Shows the game over screen. */
    public void displayGameOver() {

        gameOver.draw();
    }

    /** Shows the win screen. */
    public void displayGameWin() {

        gameWin.draw();
    }

    /** Removes all screen images. */
    public void removePicture() {
        gameOver.delete();
        gameWin.delete();
        gameIntro.delete();

    }


}

