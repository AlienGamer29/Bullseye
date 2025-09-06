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
    private int maxArrows = 20;
    private final int NUMBER_OF_TARGETS = 10;
    private final int DELAY = 16;
    private final int ARROWS_AVAILABLE = 15;
    private Text scoreText;
    private Text arrowsText;
    private Text highestScore;
    private final int COOLDOWN_MS = 600;
    private long lastShotMs = -COOLDOWN_MS;
    private GameState gameState;
    private boolean running = false;
    private Thread gameThread;
    private Sfx Shoot, Hit, Win, Lose;
    private Sfx bgm;
    private int highScore = -1;

    public void initIntro() {

        bgm = Sfx.load("/Sound/Background.wav");
        bgm.prime();
        Shoot = Sfx.load("/Sound/Arrow_Shoot.wav");
        Hit = Sfx.load("/Sound/Target_Hit.wav");
        Win = Sfx.load("/Sound/Game_win.wav");
        Lose = Sfx.load("/Sound/Game_Over.wav");

        //System.out.println("Initializing intro");
        if (myKeyboard == null) {
            myKeyboard = new MyKeyboard(this);
        }
        if (gameState == null) {
            gameState = new GameState();
        }
        gameState.displayIntro(true);
    }


    public void initGame() {

        //System.out.println("Initializing game");
        bgm.playLoop();
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

        //System.out.println("Score: " + score + ", Arrows available: " + maxArrows + ", Previews High Score: " + highScore);
        start();
    }


    public void start() {

        //System.out.println("Starting game loop");
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


            if (targets.isEmpty()) {
                showGameWin();
            } else {
                showGameOver();
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
        //System.out.println("Number of arrows left: " + maxArrows);
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
        Shoot.play();
        //System.out.println("Arrow decremented. Number of arrows left: " + maxArrows);
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
        //System.out.println("Number of arrows: " + arrows.size() + ". Number of targets: " + targets.size());
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
                    //System.out.println(a.getType() + " hit target");
                    Hit.play();
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
        //System.out.println("Number of arrows: " + arrows.size() + ". Number of targets: " + targets.size());
    }


    private void overTheBush() {
        //System.out.println("Arrows left: " + maxArrows);
        List<Arrows> toRemove = new ArrayList<>();

        for (Arrows a: arrows) {
            if (a.getX() > arena.getRight()) {
                //System.out.println("Arrow out of arena");
                a.removePicture();
                toRemove.add(a);
            }
        }
        arrows.removeAll(toRemove);
        //System.out.println("Arrows left: " + maxArrows);
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

        bgm.stop();
        gameState.displayGameOver();
        scoreDisplay(score);
        Lose.play();
    }

    public void resetGame() {

        //System.out.println("Restarting game");
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

        //System.out.println("Number of arrows: " + arrows.size() + "Arrows left: " + maxArrows + ". Number of targets: " + targets.size());
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

    public void showGameWin() {
        bgm.stop();
        gameState.displayGameWin();
        scoreDisplay(score);
        Win.play();
    }
    private void endGame() {
        //System.out.println("Number of targets: " + targets.size() + ". Number of arrows left: " + maxArrows);
        if (score > highScore) {
            highScore = score;
        }

        if (targets.isEmpty()) {
            //System.out.println("All targets destroyed");
            showGameWin();
            displayHighScore();
        } else {
            //System.out.println("You have no more arrows");
            showGameOver();
            displayHighScore();
        }

    }

    private void checkHighScore() {
        if (score > highScore) {
            //System.out.println("Player Score: " + score + ", HighScore: " + highScore);
            //System.out.println("New High Score? " + (score > highScore));
            HighScoreManager.saveHighScore(score);
        }
    }

}




