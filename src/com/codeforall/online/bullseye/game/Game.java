package com.codeforall.online.bullseye.game;

import com.codeforall.online.bullseye.playables.*;
import com.codeforall.simplegraphics.graphics.Color;
import com.codeforall.simplegraphics.graphics.Text;
import com.codeforall.simplegraphics.keyboard.Keyboard;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private Arena arena;
    private MyKeyboard myKeyboard;
    private List<Arrows> arrows = new ArrayList<>();
    private List<Target> targets = new ArrayList<>();
    private Player player;
    private TargetFactory targetFactory;
    private int score = 999;
    private int numberOfTargets = 10;
    private int delay = 16;
    private int maxArrows = 30;

    public void init() {

        arena = new Arena();
        player = new Player(arena.getBUSHPADDING(), 384);

        myKeyboard = new MyKeyboard(player, arena);

        for(int i = 0; i < numberOfTargets; i++) {
            targets.add(TargetFactory.createTarget());
        }

    }

    public void start() throws InterruptedException {

        while (true) {

            Thread.sleep(delay);

            //moveAllArrows();
            moveAllTargets();
            //checkCollision();
            //overTheBush();
            scoreDisplay(score);
            maxArrowsDisplay(maxArrows);
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
            //a.update(arena);
        }
    }

    public void checkCollision() {
        for (Arrows a : arrows) {
            for (Target t : targets) {
                if (a.getMaxX() > t.getX() && a.getMaxY() > t.getY() && a.getY() < t.getMaxY()) {
                    t.removePicture();
                    //a.removePicture();
                    targets.remove(t);
                    arrows.remove(a);
                    score += 10;
                }
            }
        }
    }

    public void overTheBush() {
        for (Arrows a: arrows) {
            if (a.getX() > arena.getRight()) {
                //a.removePicture();
                arrows.remove(a);
            }
        }
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
