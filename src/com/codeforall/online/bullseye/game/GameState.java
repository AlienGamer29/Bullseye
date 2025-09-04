package com.codeforall.online.bullseye.game;

import com.codeforall.simplegraphics.graphics.Canvas;
import com.codeforall.simplegraphics.pictures.Picture;

import static com.codeforall.online.bullseye.game.Game.PREFIX;

public class GameState {

    private Picture gameIntro;
    private Picture gameOver;
    private Picture gameWin;

    public GameState() {
        this.gameIntro = new Picture(0,0, PREFIX +"introbackground.png");
        this.gameOver = new Picture(0,0, PREFIX + "gameoverbackground.png");
        this.gameWin = new Picture(0,0, PREFIX + "youwin_bg.png");
    }


    public void displayIntro(Boolean show) {

        if (show) {
            gameIntro.grow(-256, -128);
            gameIntro.translate(-256, -128);

            //Set the canvas size
            Canvas.setMaxX(gameIntro.getWidth() - 10);
            Canvas.setMaxY(gameIntro.getHeight() - 10);

            gameIntro.draw();

        } else {

            gameIntro.delete();
        }
    }

    public void displayGameOver() {
        gameOver.grow(-256, -128);
        gameOver.translate(-256, -128);

        //Set the canvas size
        Canvas.setMaxX(gameOver.getWidth() - 10);
        Canvas.setMaxY(gameOver.getHeight() - 10);

        gameOver.draw();
    }

    public void displayGameWin() {
        gameWin.grow(-256, -128);
        gameWin.translate(-256, -128);

        //Set the canvas size
        Canvas.setMaxX(gameWin.getWidth() - 10);
        Canvas.setMaxY(gameWin.getHeight() - 10);

        gameWin.draw();
    }


    }

