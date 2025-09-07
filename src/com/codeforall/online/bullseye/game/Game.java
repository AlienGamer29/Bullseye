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
    private TargetFactory targetFactory;
    private int score = 0;
    private final int numberOfTargets = 10;
    private final int delay = 16;
    private int maxArrows = 20;
    private Text scoreText;
    private Text arrowsText;
    private final int cooldownMs = 600;
    private long lastShotMs = -cooldownMs;
    private Picture gameOver;
    private GameState gameState;
    public static final String PREFIX = "resources/";
    private Sfx Shoot, Hit, Win, Lose;
    private Sfx bgm;
    private final List<Obstacle> obstacles = new ArrayList<>();
    private int wallSpawned = 0;
    private static final int MIN_GAP_FROM_PLAYER = 100;
    //private static int rightMarginFromTargets = 100;
    private int wallW = 133, wallH = 119;

    public void initIntro() {

        arena = new Arena();
        player = new Player(arena.getBUSHPADDING(), arena.getHeight()/2);
        bgm = Sfx.load("/Sound/Background.wav");
        bgm.prime();
        Shoot = Sfx.load("/Sound/Arrow_Shoot.wav");
        Hit = Sfx.load("/Sound/Target_Hit.wav");
        Win = Sfx.load("/Sound/Game_win.wav");
        Lose = Sfx.load("/Sound/Game_Over.wav");

        myKeyboard = new MyKeyboard(player, arena, this);
        gameState = new GameState();

        scoreDisplay(score);
        maxArrowsDisplay(maxArrows);


        for(int i = 0; i < numberOfTargets; i++) {
            targets.add(TargetFactory.createTarget());
        }

        gameState.displayIntro(true);

    }

    public void initGame() {

        gameState.displayIntro(false);
        bgm.playLoop();

    }

    public void start() throws InterruptedException {

        while (!targets.isEmpty() && (maxArrows > 0 || !arrows.isEmpty())) {

            Thread.sleep(delay);

            maybeSpawnObstacles();
            moveAllArrows();
            moveAllTargets();
            checkCollision();
            checkArrowObstacleCollisions();
            overTheBush();
            updateHUD();

        }

        if (targets.isEmpty()) {
            showGameOver();
        } else if (maxArrows <= 0 && arrows.isEmpty()) {
            showGameOver();
        }

    }

    private void showGameOver() {
        bgm.stop();
        arena.displayArena(false);
        gameState.displayGameOver();
        scoreDisplay(score);
        Lose.play();

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
        Shoot.play();
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
                    Hit.play();
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

    public void maybeSpawnObstacles() {

        //Picture wallObstacle = new Picture(0,0, "resources/wallObstacle133x100.png");
        //wallW = wallObstacle.getWidth();
        //wallH = wallObstacle.getHeight();

        if (wallSpawned>=2) return;

        if (wallSpawned == 0) {
        boolean arrowCondition = (maxArrows <= 15);
        boolean targetsCondition = (targets.size() <= numberOfTargets / 2);
        if (!(arrowCondition && targetsCondition)) return;

        int minY = arena.getTopBush();
        int maxY = arena.getBottomBush()/2 - wallH;

        //int playerRight = player.getRight();
        int minX = player.getRight() + MIN_GAP_FROM_PLAYER;
        int maxX = setDistanceFromTargets() - wallW;

        if (maxY < minY) maxY = minY;
        if (maxX < minX) maxX = minX;

        int x = rand(minX, maxX);
        int y = rand(minY, maxY);
        //int x = java.util.concurrent.ThreadLocalRandom.current().nextInt(minX, maxX + 1);
        //int y = java.util.concurrent.ThreadLocalRandom.current().nextInt(minY, maxY + 1);

            Obstacle obstacle = new Obstacle(x, y);
            obstacles.add(obstacle);
            wallSpawned += 1;
        }
        if (wallSpawned == 1) {

            boolean arrowCondition = (maxArrows <= 5);
            boolean targetsCondition = (targets.size() <= numberOfTargets / 2);
            if (!(arrowCondition && targetsCondition)) return;

            int minY = arena.getBottomBush()/2;
            int maxY = arena.getBottomBush() - wallH;

            //int playerRight = player.getRight();
            int minX = player.getRight() + MIN_GAP_FROM_PLAYER;
            int maxX = setDistanceFromTargets() - wallW;

            if (maxY < minY) maxY = minY;
            if (maxX < minX) maxX = minX;

            int x = rand(minX, maxX);
            int y = rand(minY, maxY);
            //int x = java.util.concurrent.ThreadLocalRandom.current().nextInt(minX, maxX + 1);
            //int y = java.util.concurrent.ThreadLocalRandom.current().nextInt(minY, maxY + 1);

            Obstacle obstacle = new Obstacle(x, y);
            obstacles.add(obstacle);
            wallSpawned += 1;
        }


    }

    private int rand(int min, int maxInclusive) {
        return java.util.concurrent.ThreadLocalRandom.current()
                .nextInt(min, maxInclusive + 1);
    }

    private void checkArrowObstacleCollisions() {
        if (obstacles.isEmpty() || arrows.isEmpty()) return;

        List<Arrows> toRemove = new ArrayList<>();

        for (Arrows a : arrows) {
            for (Obstacle o : obstacles) {
                if (intersects(a,o)) {
                    Hit.play();
                    a.removePicture();
                    toRemove.add(a);
                    score-=10;
                    break;
                }
            }
        }
        arrows.removeAll(toRemove);
    }

    private boolean intersects(Collidables a, Collidables b) {
        return a.getMaxX() > b.getX() && a.getX() < b.getMaxX() &&  a.getMaxY() > b.getY() && a.getY() < b.getMaxY();
    }

    private void clearObstacles() {
        for (Obstacle o : obstacles) {
            o.removePicture();
        }
        obstacles.clear();
        wallSpawned = 0;
    }

    private int setDistanceFromTargets() {

        Target current = targets.get(0);
        int currentPositionX = current.getX();

        for (Target t : targets) {
            if (t.getX()<currentPositionX) {
                currentPositionX = t.getX();
            }
        }

        return currentPositionX - 20;
    }

}







