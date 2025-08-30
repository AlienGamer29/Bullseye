package com.codeforall.online.bullseye.playables;

import com.codeforall.online.bullseye.game.Arena;
import com.codeforall.simplegraphics.pictures.Picture;

import static com.codeforall.online.bullseye.game.Game.PREFIX;

public class Player extends Entity {


    public Player(int x, int y) {
        super(x, y);
        this.speed = 10;

        int shrinkX = 10;
        int shrinkY = 10;

        picture = new Picture(x, y,PREFIX + "player_resized.png");
        picture.grow(-shrinkX, -shrinkY);
        picture.translate(-shrinkX, -shrinkY);
        displayPlayer();
    }

    public void moveUp(Arena  arena) {
        int oldY = y;

        int minY = arena.getTopBush(); //impede o player de passar o arbusto
        int maxY = arena.getBottomBush() - picture.getHeight(); //igual ao top margin mas para baixo

        int newY = y - speed;
        y = Math.max(minY, Math.min(newY, maxY));

        picture.translate(0, y - oldY);
    }

    public void moveDown(Arena arena) {
        int oldY = y;

        int minY = arena.getTopBush();
        int maxY = arena.getBottomBush() - picture.getHeight();

        int newY = y + speed;
        y = Math.max(minY, Math.min(newY, maxY));

        picture.translate(0, y - oldY);
    }

    public Arrows shoot() {
        int arrowX = x + picture.getWidth();// spawn ao lado direito do player
        int arrowY = y + picture.getHeight() / 2; // spawn ao centro da imagem do player

        Arrows arrows = new Arrows(arrowX, arrowY);
        return arrows;

    }

    public void displayPlayer() {
        this.picture.draw();
    }


}
