package com.codeforall.online.bullseye.game;

import com.codeforall.online.bullseye.playables.*;
import com.codeforall.online.bullseye.playables.arrows.Arrows;
import com.codeforall.online.bullseye.playables.obstacle.Obstacle;
import com.codeforall.online.bullseye.playables.target.Target;
import com.codeforall.online.bullseye.playables.target.TargetFactory;
import com.codeforall.simplegraphics.graphics.Color;
import com.codeforall.simplegraphics.graphics.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Main game controller.
 *
 * Handles setup, game loop, input, scoring and end screens.
 */
public class Game {

    /** Path prefix for resource files (images/sounds). */
    public static final String PREFIX = "resources/";

    /** The game arena (background and limits). */
    private Arena arena;

    /** Keyboard handler for player input. */
    private MyKeyboard myKeyboard;

    /** List of arrows currently on screen. */
    private List<Arrows> arrows = new ArrayList<>();

    /** List of targets currently on screen. */
    private List<Target> targets = new ArrayList<>();

    /** List of obstacles. */
    private List<Obstacle> obstacles = new ArrayList<>();

    /** The player character. */
    private Player player;

    /** Number of arrows left to shoot. */
    int maxArrows;

    /** Current score. */
    private int score = 0;

    /** Total number of targets to spawn. */
    private final int NUMBER_OF_TARGETS = 10;

    /** Delay in milliseconds between loop ticks. */
    private final int DELAY = 16;

    /** Initial arrows available at the start. */
    private final int ARROWS_AVAILABLE = 15;

    /** Text element showing the score. */
    private Text scoreText;

    /** Text element showing the arrows left. */
    private Text arrowsText;

    /** Text element showing the highest score. */
    private Text highestScore;

    /** Minimum time between shots (ms). */
    private final int COOLDOWN_MS = 600;

    /** Timestamp of the last shot. */
    private long lastShotMs = -COOLDOWN_MS;

    /** Holds and draws the different game screens (intro, win, over). */
    private GameState gameState;

    /** True while the game loop is running. */
    private boolean running = false;

    /** Game loop thread. */
    private Thread gameThread;

    /** Sound effects. */
    private Sfx Shoot, Hit, Win, Lose;

    /** Background music. */
    private Sfx bgm;

    /** How many walls have been spawned. */
    private int wallSpawned = 0;

    /** Minimum distance from the player to place obstacles. */
    private static final int MIN_GAP_FROM_PLAYER = 100;

    /** Persisted highest score (loaded/saved). */
    private int highScore = -1;

    /**
     * Initializes the intro screen and loads sounds.
     * Call this first to start the game flow.
     */
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


    /**
     * Sets up a new match: arena, player, targets and HUD.
     * Starts the game loop at the end.
     */
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

    /**
     * Starts the main game loop in a separate thread.
     * Does nothing if already running.
     */
    public void start() {

        //System.out.println("Starting game loop");
        if (running) {
            return;
        }

        running = true;

        gameThread = new Thread(() -> {
            while (running && !targets.isEmpty()
                    && (maxArrows > 0 || !arrows.isEmpty())) {

                moveAllArrows();
                moveAllTargets();
                checkCollision();
                overTheBush();
                maybeSpawnObstacles();
                checkArrowObstacleCollisions();
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

    /**
     * Stops the game loop thread (if running).
     */
    private void stop() {
        running = false;
        if (gameThread != null) {
            gameThread.interrupt();
        }
    }

    /**
     * Tries to shoot one arrow.
     * Respects cooldown and arrow stock.
     */
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

    /**
     * Updates all targets (movement).
     */
    private void moveAllTargets() {
        for (Target t : targets) {
            t.update(arena);
        }
    }

    /**
     * Updates all arrows (movement).
     */
    private void moveAllArrows() {
        for (Arrows a : arrows) {
            a.update(arena);
        }
    }

    /**
     * Checks arrow-target collisions.
     * Removes hit targets and arrows and adds score.
     */
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

    /**
     * Removes arrows that left the arena on the right side.
     */
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

    /**
     * Draws the score text on screen.
     * @param score current score
     */
    private void scoreDisplay(int score) {
        scoreText = new Text(arena.getRight()-100, 10, "Score: " + score);
        scoreText.grow(45, 17);
        scoreText.setColor(Color.WHITE);
        scoreText.draw();
    }

    /**
     * Draws the arrows-left text on screen.
     * @param maxArrows arrows remaining
     */
    private void maxArrowsDisplay(int maxArrows) {

        arrowsText = new Text(arena.getLeft()+80, 10, "Arrows left: " + maxArrows);
        arrowsText.grow(45, 17);
        arrowsText.draw();

    }

    /**
     * Refreshes HUD texts (score and arrows left).
     */
    private void updateHUD() {
        scoreText.setText("Score: " + score);
        arrowsText.setText("Arrows left: " + maxArrows);
    }

    /**
     * Shows the Game Over screen and plays the sound.
     */
    private void showGameOver() {

        bgm.stop();
        gameState.displayGameOver();
        scoreDisplay(score);
        Lose.play();
    }

    /**
     * Resets everything and returns to the intro screen.
     */
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
        clearObstacles();

        //System.out.println("Number of arrows: " + arrows.size() + "Arrows left: " + maxArrows + ". Number of targets: " + targets.size());
        initIntro();
    }

    /**
     * Deletes all arrow pictures from the screen.
     */
    private void removeArrowsPicture() {
        for (Arrows a : arrows) {
            a.removePicture();
        }
    }

    /**
     * Deletes all target pictures from the screen.
     */
    private void removeTargetsPicture() {
        for (Target t : targets) {
            t.removePicture();
        }
    }

    /**
     * Draws the highest score text.
     */
    private void displayHighScore() {
        highestScore = new Text(500, 150, "Highest Score: " + highScore);
        highestScore.setColor(Color.WHITE);
        highestScore.grow(100, 30);
        highestScore.draw();
    }

    /**
     * Shows the Game Win screen and plays the sound.
     */
    public void showGameWin() {
        bgm.stop();
        gameState.displayGameWin();
        scoreDisplay(score);
        Win.play();
    }

    /**
     * Shows the final screen (win or game over) and the high score.
     */
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

    /**
     * Saves a new high score if the player beat the previous one.
     */
    private void checkHighScore() {
        if (score > highScore) {
            //System.out.println("Player Score: " + score + ", HighScore: " + highScore);
            //System.out.println("New High Score? " + (score > highScore));
            HighScoreManager.saveHighScore(score);
        }
    }

    /**
     * Spawns up to two wall obstacles when certain conditions are met.
     * First in the top half, then in the bottom half.
     */
    public void maybeSpawnObstacles() {

        int wallW = 133;
        int wallH = 119;

        if (wallSpawned>=2) return;

        if (wallSpawned == 0) {
            boolean arrowCondition = (maxArrows <= 10);
            boolean targetsCondition = (targets.size() <= NUMBER_OF_TARGETS / 2);
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

            Obstacle obstacle = new Obstacle(x, y);
            obstacles.add(obstacle);
            wallSpawned += 1;
        }
        if (wallSpawned == 1) {

            boolean arrowCondition = (maxArrows <= 5);
            boolean targetsCondition = (targets.size() <= NUMBER_OF_TARGETS / 2);
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

    /**
     * Random integer between min and max (inclusive).
     * @param min minimum value
     * @param maxInclusive maximum value (inclusive)
     * @return a random int in range
     */
    private int rand(int min, int maxInclusive) {
        return java.util.concurrent.ThreadLocalRandom.current()
                .nextInt(min, maxInclusive + 1);
    }

    /**
     * Checks collisions between arrows and obstacles.
     * Removes the arrow and subtracts score when hit.
     */
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

    /**
     * Simple AABB intersection using entity bounds.
     * @param a first collidable
     * @param b second collidable
     * @return true if rectangles overlap
     */
    private boolean intersects(Collidables a, Collidables b) {
        return a.getMaxX() > b.getX() && a.getX() < b.getMaxX() &&  a.getMaxY() > b.getY() && a.getY() < b.getMaxY();
    }

    /**
     * Clears all obstacles from the screen and resets the counter.
     */
    private void clearObstacles() {
        for (Obstacle o : obstacles) {
            o.removePicture();
        }
        obstacles.clear();
        wallSpawned = 0;
    }

    /**
     * Returns an X coordinate to keep new obstacles away from the nearest target.
     * Uses the leftmost target and pushes obstacle at least half an arena width behind it.
     *
     * @return a safe maximum X for spawning obstacles
     */
    private int setDistanceFromTargets() {

        Target current = targets.get(0);
        int currentPositionX = current.getX();

        for (Target t : targets) {
            if (t.getX()<currentPositionX) {
                currentPositionX = t.getX();
            }
        }
        System.out.println((currentPositionX - (arena.getWidth() / 2)));
        return (currentPositionX-(arena.getWidth()/2));
    }

}




