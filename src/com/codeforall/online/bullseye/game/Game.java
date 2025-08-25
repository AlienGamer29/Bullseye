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

    public void init() {

        arena = new Arena();
        player = new Player(arena.getBUSHPADDING(), 384);

        myKeyboard = new MyKeyboard(player, arena, this);

        for(int i = 0; i < numberOfTargets; i++) {
            targets.add(TargetFactory.createTarget());
        }

    }

    public void start() throws InterruptedException {

        while (true) {

            Thread.sleep(delay);

            moveAllArrows();
            moveAllTargets();
            checkCollision();
            overTheBush();
            scoreDisplay(score);
            maxArrowsDisplay(maxArrows);
            System.out.println(arrows.size());
        }

    }

    public void playerShoot() {
        arrows.add(player.shoot());
        maxArrows--;
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
                if (a.getMaxX() > t.getX() && a.getMaxY() > t.getY()
                        && a.getY() < t.getMaxY() && a.getX() < t.getMaxX()) {
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

        Text sc = new Text(arena.getRight()-100, 10, "Score: " + score);
        sc.grow(45, 17);
        sc.setColor(Color.WHITE);
        sc.draw();
    }

    public void maxArrowsDisplay(int maxArrows) {

        Text sc = new Text(arena.getLeft()+80, 10, "Arrows left: " + maxArrows);
        sc.grow(45, 17);

        sc.draw();

    }



    // próximas preocupações: imagem game over, fazer aparecer no ecrã o número de setas que temos e o score, menu
    // conseguimos inserir texto por cima das imagens, por exemplo por cima do arbustos? texto constantemente atualizado à medida que o checkcolision funciona e decrementamos as setas no player.shoot




}
