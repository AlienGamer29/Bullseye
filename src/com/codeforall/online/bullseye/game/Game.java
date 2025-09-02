package com.codeforall.online.bullseye.game;

import com.codeforall.online.bullseye.playables.*;
import com.codeforall.online.bullseye.playables.arrows.Arrows;
import com.codeforall.online.bullseye.playables.target.Target;
import com.codeforall.online.bullseye.playables.target.TargetFactory;
import com.codeforall.simplegraphics.graphics.Canvas;
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
    private TargetFactory targetFactory;
    private int score = 0;
    private int numberOfTargets = 10;
    private final int delay = 16;
    private int maxArrows = 10;
    private Text scoreText;
    private Text arrowsText;
    private final int cooldownMs = 600;
    private long lastShotMs = -cooldownMs;
    private Picture gameOver;
    private GameState gameState;
    public static final String PREFIX = "resources/";
    private boolean gameEnded = false;

    public void initIntro() {

        arena = new Arena();
        player = new Player(arena.getBUSHPADDING(), arena.getHeight() / 2);

        myKeyboard = new MyKeyboard(player, arena, this);
        gameState = new GameState();

        scoreDisplay(score);
        maxArrowsDisplay(maxArrows);


        for (int i = 0; i < numberOfTargets; i++) {
            targets.add(TargetFactory.createTarget());
        }

        gameState.displayIntro(true);

    }

    public void initGame() {

        gameState.displayIntro(false);

    }

    public void start() throws InterruptedException {
        System.out.println("Game begins");

        while (!targets.isEmpty() && (maxArrows > 0 || !arrows.isEmpty())) {

            Thread.sleep(delay);

            moveAllArrows();
            moveAllTargets();
            checkCollision();
            overTheBush();
            updateHUD();
        }

        if (targets.isEmpty()) {
            showGameOver();
            gameEnded = true;
            System.out.println("Game ended");
        } else if (maxArrows <= 0 && arrows.isEmpty()) {
            showGameOver();
            gameEnded = true;
            System.out.println("Game ended");
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
                if (a.getMaxX() > t.getX() && a.getMaxY() > t.getY() && a.getY() < t.getMaxY()) {
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

        for (Arrows a : arrows) {
            if (a.getX() > arena.getRight()) {
                a.removePicture();
                toRemove.add(a);
            }
        }
        arrows.removeAll(toRemove);
    }


    private void scoreDisplay(int score) {
        scoreText = new Text(arena.getRight() - 100, 10, "Score: " + score);
        scoreText.grow(45, 17);
        scoreText.setColor(Color.WHITE);
        scoreText.draw();
    }

    private void maxArrowsDisplay(int maxArrows) {

        arrowsText = new Text(arena.getLeft() + 80, 10, "Arrows left: " + maxArrows);
        arrowsText.grow(45, 17);
        arrowsText.draw();

    }

    private void updateHUD() {
        scoreText.setText("Score: " + score);
        arrowsText.setText("Arrows left: " + maxArrows);
    }

    public boolean isGameOver() {
        return gameEnded == true;
    }

    public void reset() {
        System.out.println("Reset called");
        if (gameEnded == true) {
            maxArrows = 10;
            score = 0;
            numberOfTargets = 10;
            arrows = new ArrayList<>();
            targets = new ArrayList<>();

            for (int i = 0; i < numberOfTargets; i++) {
                targets.add(TargetFactory.createTarget());
            }

            arena = new Arena();
            player = new Player(arena.getBUSHPADDING(), arena.getHeight() / 2);

            myKeyboard.setPlayer(player);
            myKeyboard.setArena(arena);

            gameState = new GameState();

            scoreDisplay(score);
            maxArrowsDisplay(maxArrows);

            System.out.println("targets after reset: " + numberOfTargets);
            System.out.println("arrows after reset:" + maxArrows);

            gameEnded = false;

        }

    }

}




