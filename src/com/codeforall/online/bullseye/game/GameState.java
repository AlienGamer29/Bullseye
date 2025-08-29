package com.codeforall.online.bullseye.game;

import com.codeforall.simplegraphics.graphics.Canvas;
import com.codeforall.simplegraphics.pictures.Picture;

public class GameState {

    private Picture gameIntro;
    private Picture gameOver;

    public GameState() {
        this.gameIntro = new Picture(0,0, "resources/introbackground.png");
        this.gameOver = new Picture(0,0, "resources/gameoverbackground.png");
    }


    public void displayIntro(Boolean show) {

        if (show == true) {
            gameIntro.grow(-256, -128);
            gameIntro.translate(-256, -128);

            //Ajusta o canvas
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

        //Ajusta o canvas
        Canvas.setMaxX(gameOver.getWidth() - 10);
        Canvas.setMaxY(gameOver.getHeight() - 10);

        gameOver.draw();
    }


    }

