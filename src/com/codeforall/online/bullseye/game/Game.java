package com.codeforall.online.bullseye.game;

import com.codeforall.online.bullseye.playables.*;
import com.codeforall.online.bullseye.playables.arrows.Arrows;
import com.codeforall.online.bullseye.playables.target.Target;
import com.codeforall.online.bullseye.playables.target.TargetFactory;
import com.codeforall.simplegraphics.graphics.Color;
import com.codeforall.simplegraphics.graphics.Text;
import com.codeforall.simplegraphics.pictures.Picture;
import org.w3c.dom.ls.LSOutput;

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
    private boolean wallSpawned = false;
    private static final int MIN_GAP_FROM_PLAYER = 100;
    private static final int RIGHT_MARGIN_FROM_TARGETS = 100;
    private int wallW = 0, wallH = 0;

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
        Picture wallProbve = new Picture(0,0, "resources/wallObstacle133x100.png");
        wallW = wallProbve.getWidth();
        wallH = wallProbve.getHeight();

    }

    public void start() throws InterruptedException {

        while (!targets.isEmpty() && (maxArrows > 0 || !arrows.isEmpty())) {

            Thread.sleep(delay);

            maybeSpawnWall();
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

    public void maybeSpawnWall() {
        if (wallSpawned) return;

        boolean arrowCondition = (maxArrows <= 15);
        boolean targetsCondition = (targets.size() <= numberOfTargets / 2);
        if (!(arrowCondition && targetsCondition)) return;

        int minY = arena.getTopBush();
        int maxY = arena.getBottomBush() - wallH;

        int playerRight = player.getRight();
        int minX = player.getRight() + MIN_GAP_FROM_PLAYER;
        int maxX = arena.getRight() - RIGHT_MARGIN_FROM_TARGETS - wallW;

        if (maxY < minY) maxY = minY;
        if (maxX < minX) maxX = minX;

        int x = java.util.concurrent.ThreadLocalRandom.current().nextInt(minX, maxX + 1);
        int y = java.util.concurrent.ThreadLocalRandom.current().nextInt(minY, maxY + 1);

        Obstacle wall = new Obstacle(x, y);
        obstacles.add(wall);
        wallSpawned = true;
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
                    a.removePicture();
                    toRemove.add(a);
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
        wallSpawned = false;
    }

}







