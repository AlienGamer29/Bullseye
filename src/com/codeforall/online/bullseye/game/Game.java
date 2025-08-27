package com.codeforall.online.bullseye.game;

import com.codeforall.online.bullseye.playables.*;
import com.codeforall.simplegraphics.graphics.Color;
import com.codeforall.simplegraphics.graphics.Text;

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
    private int delay = 16;
    private int maxArrows = 30;
    private Text scoreText;
    private Text arrowsText;
    private final int cooldownMs = 600;
    private long lastShotMs = -cooldownMs;


    public void init() {

        arena = new Arena();
        player = new Player(arena.getBUSHPADDING(), 384);

        myKeyboard = new MyKeyboard(player, arena, this);

        scoreDisplay(score);
        maxArrowsDisplay(maxArrows);


        for(int i = 0; i < numberOfTargets; i++) {
            targets.add(TargetFactory.createTarget());
        }

    }

    public void start() throws InterruptedException {

        while (!targets.isEmpty() && (maxArrows > 0 || !arrows.isEmpty())) {

            Thread.sleep(delay);

            moveAllArrows();
            moveAllTargets();
            checkCollision();
            overTheBush();
            updateHUD();
        }

        if(targets.isEmpty()) {
            // CRIAIR MENSAGEM NO ECRA "YOU WIN"
        } else if (maxArrows <= 0 && arrows.isEmpty()) {
            // CRIAR MENSAGEM DE GAME OVER NO ECRA
        }

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

    public void moveAllTargets() {
        for (Target t : targets) {
            t.update(arena);
        }
    }
    public void moveAllArrows(){
        for (Arrows a : arrows) {
            a.update(arena);
        }
    }

    public void checkCollision() {
        List<Arrows> aToRemove = new ArrayList<>();
        List<Target> tToRemove = new ArrayList<>();

        for (Arrows a : arrows) {
            for (Target t : targets) {
                if (a.getMaxX() > t.getX() && a.getMaxY() > t.getY() && a.getY() < t.getMaxY()) {
                    t.removePicture();
                    a.removePicture();
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


    public void overTheBush() {
        List<Arrows> toRemove = new ArrayList<>();

        for (Arrows a: arrows) {
            if (a.getX() > arena.getRight()) {
                a.removePicture();
                toRemove.add(a);
            }
        }
        arrows.removeAll(toRemove);
    }


    public void scoreDisplay(int score) {
        scoreText = new Text(arena.getRight()-100, 10, "Score: " + score);
        scoreText.grow(45, 17);
        scoreText.setColor(Color.WHITE);
        scoreText.draw();
    }

    public void maxArrowsDisplay(int maxArrows) {

        arrowsText = new Text(arena.getLeft()+80, 10, "Arrows left: " + maxArrows);
        arrowsText.grow(45, 17);
        arrowsText.draw();

    }

    public void updateHUD() {
        scoreText.setText("Score: " + score);
        arrowsText.setText("Arrows left: " + maxArrows);
    }



    // próximas preocupações: imagem game over, fazer aparecer no ecrã o número de setas que temos e o score, menu
    // conseguimos inserir texto por cima das imagens, por exemplo por cima do arbustos? texto constantemente atualizado à medida que o checkcolision funciona e decrementamos as setas no player.shoot




}
