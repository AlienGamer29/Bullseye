package com.codeforall.online.bullseye.game;

import com.codeforall.online.bullseye.playables.*;
import com.codeforall.online.bullseye.playables.arrows.Arrows;
import com.codeforall.online.bullseye.playables.target.Target;
import com.codeforall.online.bullseye.playables.target.TargetFactory;
import com.codeforall.simplegraphics.graphics.Color;
import com.codeforall.simplegraphics.graphics.Text;

import java.util.ArrayList;
import java.util.List;

public class Game {

    public static final String PREFIX = "resources/";
    private Arena arena;
    private MyKeyboard myKeyboard;
    private List<Arrows> arrows = new ArrayList<>();
    private List<Target> targets = new ArrayList<>();
    private Player player;
    private int score = 0;
    private final int NUMBER_OF_TARGETS = 10;
    private final int DELAY = 16;
    private final int ARROWS_AVAILABLE = 15;
    private int maxArrows;
    private Text scoreText;
    private Text arrowsText;
    private Text highestScore;
    private final int COOLDOWN_MS = 600;
    private long lastShotMs = -COOLDOWN_MS;
    private GameState gameState;
    private boolean running = false;
    private Thread gameThread;
    private int highScore = -1;

    public void initIntro() {

        if (myKeyboard == null) {
            myKeyboard = new MyKeyboard(this);
        }
        if (gameState == null) {
            gameState = new GameState();
        }
        gameState.displayIntro(true);
    }


    public void initGame() {

        arrows.clear();
        targets.clear();

        gameState.displayIntro(false);
        arena = new Arena();
        player = new Player(arena.getBUSH_PUDDING(), arena.getHeight()/2);
        myKeyboard.setArenaAndPlayer(arena, player);

        score = 0;
        maxArrows = ARROWS_AVAILABLE;
        scoreDisplay(score);
        maxArrowsDisplay(maxArrows);


        for (int i = 0; i < NUMBER_OF_TARGETS; i++) {
            targets.add(TargetFactory.createTarget());
        }

        if (highScore == -1)  {
            highScore = HighScoreManager.getHighScore();
        }

        start();
    }


    public void start() {


        if (running) {
            return;
        }

        running = true;

        gameThread = new Thread(() -> {
            while (running
                    && !targets.isEmpty()
                    && (maxArrows > 0 || !arrows.isEmpty())) {

                moveAllArrows();
                moveAllTargets();
                checkCollision();
                overTheBush();
                updateHUD();

                try {
                    Thread.sleep(DELAY);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }

            checkHighScore();
            endGame();

            running = false;
        });

        gameThread.start();


    }

    private void stop() {
        running = false;
        if (gameThread != null) {
            gameThread.interrupt();
        }
    }

    public void playerShoot() {
        if (maxArrows <= 0) {
            return;
        }
        long now = System.currentTimeMillis();
        if (now - lastShotMs < COOLDOWN_MS) {
            return;
        }
        arrows.add(player.shoot());
        maxArrows--;
        lastShotMs = now;
    }

    private void moveAllTargets() {
        for (Target t : targets) {
            t.update(arena);
        }
    }

    private void moveAllArrows() {
        for (Arrows a : arrows) {
            a.update(arena);
        }
    }

    private void checkCollision() {
        List<Arrows> aToRemove = new ArrayList<>();
        List<Target> tToRemove = new ArrayList<>();

        for (Arrows a : arrows) {
            for (Target t : targets) {
                //Target center
                int targetCenterX = t.getX() + t.getWidth()/2;
                int targetCenterY = t.getY() + t.getHeight()/2;
                int targetRadius = t.getWidth()/2;

                //Arrow tip
                int arrowTipX = a.getMaxX();
                int arrowTipY = a.getY() + a.getHeight()/2;

                //distance
                double dx = arrowTipX - targetCenterX;
                double dy = arrowTipY - targetCenterY;

                double distance = Math.sqrt(dx * dx + dy * dy);

                if (distance <= targetRadius) {
                    t.removePicture();
                    a.removePicture();
                    aToRemove.add(a);
                    tToRemove.add(t);
                    score += a.getType().getSCORE();
                    break;
                }
            }
        }

        targets.removeAll(tToRemove);
        arrows.removeAll(aToRemove);
    }


    private void overTheBush() {
        List<Arrows> toRemove = new ArrayList<>();

        for (Arrows a: arrows) {
            if (a.getX() > arena.getRight()) {
                a.removePicture();
                toRemove.add(a);
            }
        }
        arrows.removeAll(toRemove);
    }


    private void scoreDisplay(int score) {
        scoreText = new Text(arena.getRight()-100, 10, "Score: " + score);
        scoreText.grow(45, 17);
        scoreText.setColor(Color.WHITE);
        scoreText.draw();
    }

    private void maxArrowsDisplay(int maxArrows) {

        arrowsText = new Text(arena.getLeft()+80, 10, "Arrows left: " + maxArrows);
        arrowsText.grow(45, 17);
        arrowsText.draw();

    }

    private void updateHUD() {
        scoreText.setText("Score: " + score);
        arrowsText.setText("Arrows left: " + maxArrows);
    }

    private void showGameOver() {

        gameState.displayGameOver();
        scoreDisplay(score);

    }

    public void resetGame() {

        stop();

        score = 0;
        maxArrows = ARROWS_AVAILABLE;

        removeArrowsPicture();
        removeTargetsPicture();
        player.removePicture();
        arena.removePicture();
        gameState.removePicture();
        scoreText.delete();
        arrowsText.delete();
        arrows.clear();
        targets.clear();
        highestScore.delete();


        initIntro();
    }

    private void removeArrowsPicture() {
        for (Arrows a : arrows) {
            a.removePicture();
        }
    }

    private void removeTargetsPicture() {
        for (Target t : targets) {
            t.removePicture();
        }
    }


    private void displayHighScore() {
        highestScore = new Text(500, 150, "Highest Score: " + highScore);
        highestScore.setColor(Color.WHITE);
        highestScore.grow(100, 30);
        highestScore.draw();
    }

    private void endGame() {
        if (targets.isEmpty()) {
            gameState.displayGameWin();
            scoreDisplay(score);
            displayHighScore();
        } else {
            showGameOver();
            displayHighScore();
        }

        if (score > highScore) {
            highScore = score;
        }
    }

    private void checkHighScore() {
        if (score > highScore) {
            HighScoreManager.saveHighScore(score);

        }
    }






}




