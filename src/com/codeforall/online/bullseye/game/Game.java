package com.codeforall.online.bullseye.game;

import com.codeforall.online.bullseye.playables.*;
import com.codeforall.online.bullseye.playables.arrows.Arrows;
import com.codeforall.online.bullseye.playables.target.Target;
import com.codeforall.online.bullseye.playables.target.TargetFactory;
import com.codeforall.simplegraphics.graphics.Color;
import com.codeforall.simplegraphics.graphics.Text;
import com.codeforall.simplegraphics.pictures.Picture;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private Arena arena;
    private MyKeyboard myKeyboard;
    private List<Arrows> arrows = new ArrayList<>();
    private List<Target> targets = new ArrayList<>();
    private Player player;
    private int score = 0;
    private final int numberOfTargets = 10;
    private final int delay = 16;
    private int maxArrows = 10;
    private Text scoreText;
    private Text arrowsText;
    private final int cooldownMs = 600;
    private long lastShotMs = -cooldownMs;
    private GameState gameState;
    public static final String PREFIX = "resources/";
    private boolean running = false;
    private Thread gameThread;

    public void initIntro() {


        myKeyboard = new MyKeyboard(this);
        gameState = new GameState();

        gameState.displayIntro(true);

    }

    public void initGame() {

        arrows.clear();
        targets.clear();

        gameState.displayIntro(false);
        arena = new Arena();
        player = new Player(arena.getBUSHPADDING(), arena.getHeight()/2);
        myKeyboard.setArenaAndPlayer(arena, player);

        scoreDisplay(score);
        maxArrowsDisplay(maxArrows);

        for(int i = 0; i < numberOfTargets; i++) {
            targets.add(TargetFactory.createTarget());
        }
        System.out.println(targets.size());

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
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }

            if (targets.isEmpty()) {
                showGameOver();
            } else {
                showGameOver();
            }
            running = false;
        });

        gameThread.start();

    }

    public void stop() {
        running = false;
        if (gameThread != null) {
            gameThread.interrupt();
        }
    }



    private void showGameOver() {
        arena.displayArena(false);
        gameState.displayGameOver();
        scoreDisplay(score);
    }

    public void playerShoot() {
        if (maxArrows <= 0) {
            return;
        }
        long now = System.currentTimeMillis();
        if (now - lastShotMs < cooldownMs) {
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
                    score += 10;
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

    public void resetGame() {

        stop();

        score = 0;
        maxArrows = 10;

        for (Arrows a : arrows) {
            a.removePicture();
        }

        for (Target t : targets) {
            t.removePicture();
        }

        arrows.clear();
        targets.clear();
        initIntro();
    }



}




